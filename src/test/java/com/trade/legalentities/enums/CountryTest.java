package com.trade.legalentities.enums;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.trade.legalentities.enums.Country;

public class CountryTest {

    @Test
    public void test_allowedCountryCode() {
        String allowedCountry = "CH";
        assertThat(Country.isAllowed(allowedCountry), is(true));
    }

    @Test
    public void test_unknownCountryCode() {
        String allowedCountry = "not sure";
        assertThat(Country.isAllowed(allowedCountry), is(false));
    }

    @Test
    public void test_unknownCountryCode_null() {
        String allowedCountry = null;
        assertThat(Country.isAllowed(allowedCountry), is(false));
    }

}
