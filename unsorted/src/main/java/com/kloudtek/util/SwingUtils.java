/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import com.kloudtek.util.validation.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Various Swing related utility functions
 */
public class SwingUtils {
    private static Logger logger = Logger.getAnonymousLogger();

    public static JFrame createFrame(String title, JPanel panel, Preferences prefs, int defWidth, int defHeight) {
        ValidationUtils.validateWithinBounds("defWidth/defHeight cannot be smaller than 0 or greater than 100"
                , 0, 100, defHeight, defWidth);
        final Frame frame = new Frame(title, prefs);
        centerFrame(frame, prefs, defWidth, defHeight);
        frame.add(panel);
        return frame;
    }

    public static void centerFrame(JFrame frame, Preferences prefs, int defWidth, int defHeight) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) ((screenSize.getWidth() / 100) * defWidth);
        int h = (int) ((screenSize.getHeight() / 100) * defHeight);
        int x = (int) ((screenSize.getWidth() - w) / 2);
        int y = (int) ((screenSize.getHeight() - h) / 2);
        if (prefs != null) {
            frame.setBounds(prefs.getInt("x", x), prefs.getInt("y", y), prefs.getInt("w", w), prefs.getInt("h", h));
        } else {
            frame.setBounds(x, y, w, h);
        }
    }

    public static class Frame extends JFrame {
        private static final long serialVersionUID = -5345962084881252916L;

        public Frame(final String title, final Preferences prefs) throws HeadlessException {
            super(title);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    prefs.putInt("x", getX());
                    prefs.putInt("y", getY());
                    prefs.putInt("h", getHeight());
                    prefs.putInt("w", getWidth());
                    try {
                        prefs.sync();
                    } catch (BackingStoreException ex) {
                        logger.log(Level.WARNING, "Unable to save window preferences", ex);
                    }
                }
            });
        }
    }
}
