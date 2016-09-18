package com.h2.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

/**
 * @author glick
 */
public class DatabaseEventListener implements org.h2.api.DatabaseEventListener {

    private static final transient Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(String url) {
        log.warn("DatabaseEventListener received URL is " + url);
    }

    @Override
    public void opened() {
        log.warn("DatabaseEventListener::opened");
    }

    @Override
    public void exceptionThrown(SQLException e, String sql) {
        log.warn("DatabaseEventListener::exceptionThrown");
        log.warn("sql statement was " + sql);
        log.warn("the exception is ", e);
    }

    @Override
    public void setProgress(int state, String name, int x, int max) {
        log.warn("DatabaseEventListener::setProgress");
        log.warn("state is " + state + " name is " + name + " x is " + x + " max is " + max);
    }

    @Override
    public void closingDatabase() {
        log.warn("DatabaseEventListener::closingDatabase");
    }
}
