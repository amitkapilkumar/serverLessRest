package com.server.service;

import com.server.exception.InvalidInputException;
import com.server.model.Currency;
import com.server.model.FXSpread;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.server.util.ServerUtil.URL_EXCHANGE_RATES;

public class RateServiceImpl implements RateService {

    @Override
    public List<FXSpread> pullLatestRates(List<Currency> currencies, Currency baseCurrency) throws IOException {
        if(Objects.isNull(baseCurrency) || Objects.isNull(currencies) || currencies.isEmpty()) {
            throw new InvalidInputException("Invalid Input for pullLatestRates");
        }

        String url = getExchangeRates(getCSVString(currencies), baseCurrency.toString());
        String out = new Scanner(new URL(url).openStream(), "UTF-8").next();
        List<FXSpread> spreads = new ArrayList<>();
        for(Currency currency : currencies) {
            FXSpread spread = new FXSpread();
            spread.setBaseCurrency(baseCurrency);
            spread.setCurrency(currency);
            Matcher m = Pattern.compile("[\"']" + currency.toString() + "[\"']\\s*:\\s*(.*?)[,}]").matcher(out);
            if(m.find()) {
                double rate = Double.parseDouble(m.group(1));
                spread.setRate(rate);
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);
                spread.setSpread(Double.parseDouble(df.format(rate)) - 1.0000d); // considering 1 USD as base
            }
            spreads.add(spread);
        }
        return spreads;
    }

    private String getExchangeRates(String listOfCurrencies, String baseCurrency) {
        return URL_EXCHANGE_RATES.replace("SYMBOL_LIST", listOfCurrencies).replace("BASE", baseCurrency);
    }

    private String getCSVString(List<Currency> currencies) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < currencies.size(); i++) {
            if(i == currencies.size() -1) {
                return sb.append(currencies.get(i)).toString();
            }
            sb.append(currencies.get(i)).append(",");
        }
        return null;
    }
}
