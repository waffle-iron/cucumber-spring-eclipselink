package org.eclipse.eclipselink.rs.eventListeners;

import org.eclipse.persistence.jpa.rs.eventlistener.DescriptorBasedDatabaseEventListener;

import java.util.Map;

/**
 * @author glick
 */
public class DatabaseEventListenerFactory
    implements org.eclipse.persistence.jpa.rs.eventlistener.DatabaseEventListenerFactory {

    @Override
    public DescriptorBasedDatabaseEventListener createDatabaseEventListener(Map<String, Object> properties) {
        return null;
    }
}
