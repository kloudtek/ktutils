/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.common;

import org.jetbrains.annotations.Nullable;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Various LDAP related utility functions
 */
public class LDAPUtils {
    @SuppressWarnings({"unchecked"})
    public static DirContext createLdapContext(String url, @Nullable String userDn, @Nullable String password) throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url);
        if (userDn != null) {
            env.put(DirContext.SECURITY_PRINCIPAL, userDn);
        }
        if (password != null) {
            env.put(DirContext.SECURITY_CREDENTIALS, password);
        }
        return new InitialDirContext(env);
    }

    @SuppressWarnings({"unchecked"})
    public static DirContext createLdapContext(String url) throws NamingException {
        return createLdapContext(url, null, null);
    }

    public static void replaceAttribute(DirContext ctx, String dn, String attrName, String newValue) throws NamingException {
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(attrName, newValue));
        ctx.modifyAttributes(dn, mods);
    }

    public static void delete(DirContext ctx, String dn) throws NamingException {
        Stack<String> toDelete = new Stack<String>();
        toDelete.push(dn);
        while (!toDelete.empty()) {
            String delDn = toDelete.pop();
            final NamingEnumeration<NameClassPair> childrens = ctx.list(delDn);
            if (childrens.hasMoreElements()) {
                toDelete.push(delDn);
                while (childrens.hasMoreElements()) {
                    toDelete.push(childrens.nextElement().getNameInNamespace());
                }
            } else {
                ctx.unbind(delDn);
            }
        }
    }

    public static void close(@Nullable DirContext ctx) {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
