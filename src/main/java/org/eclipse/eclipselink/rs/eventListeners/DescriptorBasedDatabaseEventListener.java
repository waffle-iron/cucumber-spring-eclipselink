package org.eclipse.eclipselink.rs.eventListeners;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.rs.eventlistener.ChangeListener;
import org.eclipse.persistence.sessions.Session;

/**
 * @author glick
 */
public class DescriptorBasedDatabaseEventListener
    implements org.eclipse.persistence.jpa.rs.eventlistener.DescriptorBasedDatabaseEventListener {

    @Override
    public void register(Session session, String queryName) {

    }

    @Override
    public void addChangeListener(ChangeListener listener) {

    }

    @Override
    public void removeChangeListener(ChangeListener listener) {

    }

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
