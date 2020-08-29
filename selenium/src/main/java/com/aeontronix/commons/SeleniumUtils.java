/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yannick on 26/09/2014.
 */
public class SeleniumUtils {
    public static void setSelected(WebElement el, boolean selected) {
        if (el.isSelected() != selected) {
            el.click();
        }
    }

    public static void findAndClick(SearchContext root, By by) {
        for (WebElement element : root.findElements(by)) {
            if (element.isDisplayed()) {
                element.click();
            }
        }
    }

    /**
     * Select an element (by clicking on it) if it's value
     *
     * @param root     Root element to search under
     * @param name     element name
     * @param deselect If true will deselect any found element if they don't match (otherwise they'll be left selected even if they don't match)
     * @param values   value to select
     */
    public static void select(SearchContext root, String name, boolean deselect, String... values) {
        List<WebElement> elements = root.findElements(By.name(name));
        for (WebElement element : elements) {
            boolean match = compareAttr(element, "value", values);
            boolean selected = element.isSelected();
            if ((match && !selected) || (!match && selected && deselect)) {
                if (element.isDisplayed()) {
                    element.click();
                } else {
                    throw new IllegalArgumentException("Can't click on an element that isn't displayed");
                }
            }
        }
    }

    /**
     * Check if the specified attribute is equals to one of the provided values
     *
     * @param element  Element
     * @param attrName Attribute name
     * @param values   Values
     * @return True is the attribute value is equals to one of the provided values
     */
    public static boolean compareAttr(WebElement element, String attrName, String... values) {
        for (String value : values) {
            String attr = element.getAttribute(attrName);
            if (attr != null && attr.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static void clearAndSendKeys(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public static void waitFor(WebDriver webDriver, final By... by) {
        new FluentWait<WebDriver>(webDriver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                for (By b : by) {
                    if (!input.findElement(b).isDisplayed()) {
                        return false;
                    }
                }
                return true;
            }
        });
    }
}
