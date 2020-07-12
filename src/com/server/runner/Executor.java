package com.server.runner;

import com.server.service.RestServer;
import com.server.service.RestServerImpl;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Executor {

    public static void main(String[] args) throws IOException {
        RestServer server = new RestServerImpl();
        server.start(8002);
        /*int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/user/profile/userId", (exchange -> {
            String respText = "{" +
                    "'Host' : 'localhost'," +
                    "'Port'Host' : '8000'" +
                    "'Message' : 'Hello'" +
                    "}";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.createContext("/rates/latest", (exchange -> {
            String respText = "{" +
                    "'Host' : 'localhost'," +
                    "'Port'Host' : '8000'," +
                    "'Message' : 'Hi'" +
                    "}";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.setExecutor(null); // creates a default executor
        server.start();
        */
    }
}
