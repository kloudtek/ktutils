/*
 * Copyright (c) 2010. KloudTek Ltd
 */

package com.kloudtek.util;

import javax.persistence.PersistenceException;
import javax.transaction.UserTransaction;

public class JTAUtils {
    public static void beginTransaction(UserTransaction tx) {
        try {
            tx.begin();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public static void commitTransaction(UserTransaction tx) {
        try {
            tx.commit();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public static void rollbackTransaction(UserTransaction tx) {
        try {
            tx.rollback();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
