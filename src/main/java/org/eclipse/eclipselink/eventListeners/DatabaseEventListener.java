package org.eclipse.eclipselink.eventListeners;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.Session;

/**
 * @author glick
 */
public class DatabaseEventListener
    implements org.eclipse.persistence.platform.database.events.DatabaseEventListener {

    @Override
    public void register(Session session) {

    }

    @Override
    public void remove(Session session) {

    }

    @Override
    public void initialize(ClassDescriptor descriptor, AbstractSession session) {

    }
}
