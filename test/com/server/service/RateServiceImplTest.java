package com.server.service;

import com.server.exception.InvalidInputException;
import com.server.model.Currency;
import com.server.model.FXSpread;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RateServiceImplTest {
    private RateService rateService;

    @Before
    public void setup() {
        rateService = new RateServiceImpl();
    }

    @Test(expected = InvalidInputException.class)
    public void testWithNull() throws IOException {
        rateService.pullLatestRates(null, null);
    }

    @Test
    public void testWithSingleCurrency() throws IOException {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.HKD);

        List<FXSpread> spreads = rateService.pullLatestRates(currencies, Currency.USD);
        assertEquals(spreads.size(), 1);
        assertEquals(spreads.get(0).getBaseCurrency(), Currency.USD);
        assertEquals(spreads.get(0).getCurrency(), Currency.HKD);
        assertTrue(spreads.get(0).getRate() > 0.0d);
        assertTrue(spreads.get(0).getSpread() > 0.0000d);
    }

    @Test
    public void testWithMultipleCurrencyWithBaseUSD() throws IOException {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.HKD);
        currencies.add(Currency.SGD);
        currencies.add(Currency.MYR);
        List<FXSpread> spreads = rateService.pullLatestRates(currencies, Currency.USD);
        assertEquals(spreads.size(), 3);
        for(FXSpread spread : spreads) {
            assertEquals(spread.getBaseCurrency(), Currency.USD);
            assertTrue(currencies.contains(spread.getCurrency()));
            assertTrue(spread.getRate() > 0.0d);
            assertTrue(spreads.get(0).getSpread() > 0.0000d);
        }
    }

    @Test
    public void testWithMultipleCurrencyWithBaseSGD() throws IOException {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.HKD);
        currencies.add(Currency.MYR);
        List<FXSpread> spreads = rateService.pullLatestRates(currencies, Currency.SGD);
        assertEquals(spreads.size(), 2);
        for(FXSpread spread : spreads) {
            assertEquals(spread.getBaseCurrency(), Currency.SGD);
            assertTrue(currencies.contains(spread.getCurrency()));
            assertTrue(spread.getRate() > 0.0d);
            assertTrue(spreads.get(0).getSpread() > 0.0000d);
        }
    }
}
