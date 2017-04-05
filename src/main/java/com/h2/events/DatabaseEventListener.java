package com.h2.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

/**
 * @author glick
 */
@SuppressWarnings("unused")
public class DatabaseEventListener implements org.h2.api.DatabaseEventListener {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(String url) {
      if (log.isWarnEnabled())
        log.warn(String.format("DatabaseEventListener received URL is %s", url));
    }

    @Override
    public void opened() {
        log.warn("DatabaseEventListener::opened");
    }

    @Override
    public void exceptionThrown(SQLException e, String sql) {

      if (log.isWarnEnabled())
      {
        log.warn("DatabaseEventListener::exceptionThrown");
        log.warn(String.format("sql statement was %s", sql));
        log.warn("the exception is ", e);
      }
    }

    @Override
    public void setProgress(int state, String name, int x, int max) {
      if (log.isWarnEnabled())
      {
        log.warn("DatabaseEventListener::setProgress");
        log.warn(String.format("state is %d name is %s x is %d max is %d", state, name, x, max));
      }
    }

    @Override
    public void closingDatabase() {
        log.warn("DatabaseEventListener::closingDatabase");
    }
}
