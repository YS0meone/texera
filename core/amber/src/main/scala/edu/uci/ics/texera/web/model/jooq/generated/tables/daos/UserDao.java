/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.web.model.jooq.generated.tables.daos;


import edu.uci.ics.texera.web.model.jooq.generated.tables.User;
import edu.uci.ics.texera.web.model.jooq.generated.tables.records.UserRecord;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDao extends DAOImpl<UserRecord, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User, UInteger> {

    /**
     * Create a new UserDao without any configuration
     */
    public UserDao() {
        super(User.USER, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User.class);
    }

    /**
     * Create a new UserDao with an attached configuration
     */
    public UserDao(Configuration configuration) {
        super(User.USER, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User.class, configuration);
    }

    @Override
    public UInteger getId(edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User object) {
        return object.getUid();
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchByName(String... values) {
        return fetch(User.USER.NAME, values);
    }

    /**
     * Fetch a unique record that has <code>name = value</code>
     */
    public edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User fetchOneByName(String value) {
        return fetchOne(User.USER.NAME, value);
    }

    /**
     * Fetch records that have <code>uid BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchRangeOfUid(UInteger lowerInclusive, UInteger upperInclusive) {
        return fetchRange(User.USER.UID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>uid IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchByUid(UInteger... values) {
        return fetch(User.USER.UID, values);
    }

    /**
     * Fetch a unique record that has <code>uid = value</code>
     */
    public edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User fetchOneByUid(UInteger value) {
        return fetchOne(User.USER.UID, value);
    }

    /**
     * Fetch records that have <code>password BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchRangeOfPassword(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.PASSWORD, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.User> fetchByPassword(String... values) {
        return fetch(User.USER.PASSWORD, values);
    }
}
