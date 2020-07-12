package com.server.service;

import com.server.builder.UserBuilder;
import com.server.dao.UserDao;
import com.server.dao.UserDaoImpl;
import com.server.model.Currency;
import com.server.model.FXSpread;
import com.server.model.Tier;
import com.server.model.User;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.util.ServerUtil.*;

public class RestServerImpl implements RestServer {
    private HttpServer server;
    private final UserDao userDao;
    private final RateService rateService;

    public RestServerImpl() {
        userDao = new UserDaoImpl();
        rateService = new RateServiceImpl();
    }

    @Override
    public void start(int port) throws IOException {
        if(server != null) {
            System.out.println("Server Already Running on Port : " + server.getAddress().getPort());
            return;
        }

        System.out.println("Starting Server and exposing API Port : " + port);

        populateProfiles();

        server = HttpServer.create(new InetSocketAddress(port), 0);
        createEndPoints();
        server.setExecutor(null);
        server.start();
    }

    private void createEndPoints() {
        server.createContext(USER_PROFILES, (exchange -> {
            String respText = getAllProfiles();
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.createContext(USER_PROFILE_123, (exchange -> {
            String text = getProfile("123");
            exchange.sendResponseHeaders(200, text.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(text.getBytes());
            output.flush();
            exchange.close();
        }));
        server.createContext(USER_PROFILE_546, (exchange -> {
            String text = getProfile("546");
            exchange.sendResponseHeaders(200, text.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(text.getBytes());
            output.flush();
            exchange.close();
        }));
        server.createContext(USER_PROFILE_894, (exchange -> {
            String text = getProfile("894");
            exchange.sendResponseHeaders(200, text.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(text.getBytes());
            output.flush();
            exchange.close();
        }));
        server.createContext(RATES_LATEST_URL, (exchange -> {
            List<Currency> currencies = new ArrayList<>();
            currencies.add(Currency.HKD);
            currencies.add(Currency.MYR);
            currencies.add(Currency.SGD);
            String text = pullAndAddSpreads(currencies, Currency.USD);
            exchange.sendResponseHeaders(200, text.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(text.getBytes());
            output.flush();
            exchange.close();
        }));
    }

    private void populateProfiles() {
        userDao.addProfile(UserBuilder.aUserBuilder().withId("123").withName("Alex").withEmail("alex@vio.com").withTier(Tier.TIER_A).build());
        userDao.addProfile(UserBuilder.aUserBuilder().withId("546").withName("Mike").withEmail("Mike@vio.com").withTier(Tier.TIER_B).build());
        userDao.addProfile(UserBuilder.aUserBuilder().withId("894").withName("John").withEmail("John@vio.com").withTier(Tier.TIER_C).build());

        for(User user : userDao.getAllProfiles()){
            System.out.println("Profile Added : " + user);
        }
    }

    private String getProfile(String id) {
        return userDao.getProfile(id).toString();
    }

    private String getAllProfiles() {
        return userDao.getAllProfiles().stream().map(User::toString).collect(Collectors.joining());
    }

    private String pullAndAddSpreads(List<Currency> currencies, Currency base) {
        StringBuilder sb = new StringBuilder("{\"rates\": {");
        try {
            List<FXSpread> spreads = rateService.pullLatestRates(currencies, base);
            List<User> users = userDao.getAllProfiles();
            for(User user : users) {
                switch (user.getTier()) {
                    case TIER_A:
                        sb.append(calculate(0.015, spreads));
                        break;
                    case TIER_B:
                        sb.append(calculate(0.035, spreads));
                        break;
                    case TIER_C:
                        sb.append(calculate(0.040, spreads));
                        break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("IOException while fetching latest rates : " + e.getMessage());
        }
        return sb.append("}, \"base\":\"").append(base.toString()).append("\"}").toString();
    }

    private String calculate(double value, List<FXSpread> spreads) { //
        StringBuilder sb = new StringBuilder();
        for(FXSpread fxSpread : spreads) {
            sb.append("\"").append(fxSpread.getCurrency()).append("\":")
                    .append("{\"bid\":").append(fxSpread.getRate() + value * fxSpread.getSpread()).append(",")
                    .append("\"market\":").append(fxSpread.getRate()).append(",")
                    .append("\"ask\":").append(fxSpread.getRate() - value * fxSpread.getSpread()).append("},");
        }
        return sb.toString();
    }
}
