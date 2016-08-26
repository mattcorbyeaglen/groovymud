package org.corbym.groovymud.engine

import org.corbym.groovymud.engine.event.SystemEventType
import org.glassfish.grizzly.filterchain.FilterChain
import org.glassfish.grizzly.nio.transport.TCPNIOTransport
import org.glassfish.grizzly.ConnectionProbe
import org.glassfish.grizzly.Connection
import org.glassfish.grizzly.Buffer
import org.glassfish.grizzly.IOEvent

//@GrabResolver(name='Java.net Maven 2 Repository for GlassFish', root='root://download.java.net/maven/glassfish/')
//@Grab(group='org.glassfish.grizzly', module='grizzly-framework', version='2.0')
class NonBlockingTransportManager {
    int port
    def transport
    FilterChain filterChain

    def update(def event) {
        assert event.hasProperty("type")
        if (event.type == SystemEventType.START_UP_EVENT) {
            transport.setProcessor(filterChain)
            transport.bind("localhost", port)
            transport.start()
        }
        if (event.type == SystemEventType.SHUT_DOWN_EVENT) {
            transport.stop();
        }
    }

    def accepts(def eventType) {
        eventType == SystemEventType.START_UP_EVENT  || eventType == SystemEventType.SHUT_DOWN_EVENT
    }
}
