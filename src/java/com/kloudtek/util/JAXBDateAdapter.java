/*
 * Copyright (c) Kloudtek Ltd 2012.
 */

package com.kloudtek.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JABX adapter to convert a java.util.Date to a date in the format of yyyy-MM-dd.
 */
public class JAXBDateAdapter extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public synchronized String marshal(Date date) throws Exception {
        return dateFormatter.format(date);
    }

    public synchronized Date unmarshal(String dateString) throws Exception {
        return dateFormatter.parse(dateString);
    }
}