/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a country.
 */
public class Country {
    private static Map<String, String> codeToNameMap;
    private String name;
    private String code;

    public Country() {
    }

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Constructs a country object only providing the code (the country name will automatically be looked up based
     * on that code.
     *
     * @param code Country code
     * @throws IllegalArgumentException If the country code isn't valid.
     */
    public Country(String code) throws IllegalArgumentException {
        this.code = code;
        name = getCountryName(code);
        if (name == null) {
            throw new IllegalArgumentException("Invalid country code " + code);
        }
    }

    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    public String getName() {
        return name;
    }

    @XmlElement(name = "IdentificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCountryName(String code) {
        return getCodeToNameMap().get(code.toUpperCase());
    }

    private static Map<String, String> getCodeToNameMap() {
        if (codeToNameMap == null) {
            try {
                Map<String, String> map = new HashMap<String, String>();
                final Document document = XmlUtils.parse(Country.class.getResourceAsStream("countrycodes.xml"));
                final List<Element> countries = XmlUtils.toElementList(document.getElementsByTagName("ISO_3166-1_Entry"));
                for (Element country : countries) {
                    String code = country.getElementsByTagName("ISO_3166-1_Alpha-2_Code_element").item(0).getTextContent();
                    String name = country.getElementsByTagName("ISO_3166-1_Country_name").item(0).getTextContent();
                    map.put(code, name);
                }
                codeToNameMap = Collections.unmodifiableMap(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return codeToNameMap;
    }
}
