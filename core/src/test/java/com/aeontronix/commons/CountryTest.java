package com.aeontronix.commons;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CountryTest {
    @Test
    public void testCountryToCodeMapping() {
        assertEquals(Country.getCountryName("fr"),"FRANCE");
    }
}
