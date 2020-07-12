package com.server.service;

import com.server.model.Currency;
import com.server.model.FXSpread;

import java.io.IOException;
import java.util.List;

public interface RateService {
    List<FXSpread> pullLatestRates(List<Currency> currencies, Currency baseCurrency) throws IOException;
}
