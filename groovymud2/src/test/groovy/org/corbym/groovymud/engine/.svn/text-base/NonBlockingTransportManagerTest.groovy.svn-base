package org.corbym.groovymud.engine

import org.junit.Test
import org.corbym.groovymud.engine.event.SystemEventType
import org.hamcrest.Matchers
import static org.junit.Assert.assertThat

import org.mockito.Mockito
import static org.mockito.Mockito.when
import static org.mockito.Mockito.verify
import org.glassfish.grizzly.filterchain.FilterChain

import junit.framework.AssertionFailedError
import static org.mockito.Mockito.verifyNoMoreInteractions
import org.corbym.groovymud.engine.event.Event
import static org.corbym.groovymud.engine.event.SystemEventType.START_UP_EVENT
import static org.corbym.groovymud.engine.event.SystemEventType.SHUT_DOWN_EVENT
import static org.corbym.groovymud.engine.NonBlockingTransportManagerTest.otherEventType.OTHER_EVENT_TYPE

//@Grab(group='org.mockito', module='mockito-all', version='1.8.4')
class NonBlockingTransportManagerTest {
    final def transport = Mockito.mock(TransportStub)
    final FilterChain filterChain = Mockito.mock(FilterChain);
    def underTest = new NonBlockingTransportManager(port:2222, transport: transport, filterChain: filterChain);

    @Test
    void "Accepts System.startup events"(){
        assertThat(underTest.accepts(START_UP_EVENT), Matchers.is(true))
    }
    @Test
    void "Accepts System.shutdown events"(){
        assertThat(underTest.accepts(SHUT_DOWN_EVENT), Matchers.is(true))
    }

    enum otherEventType {
        OTHER_EVENT_TYPE
    }
    @Test
    void "does not accept other types of event"(){
        assertThat(underTest.accepts(OTHER_EVENT_TYPE), Matchers.is(false))
    }

    @Test
    void "starts a tcpniotransport on port 2222 with start event"(){
        underTest.update(new Event(type:START_UP_EVENT));
        verify(transport).setProcessor(filterChain)
        verify(transport).bind("localhost", 2222)
        verify(transport).start()
        verifyNoMoreInteractions(transport)
    }
    @Test
    void "does not start a tcpniotransport on port 2222 with shutdown system event"(){
        when(transport.start()).thenThrow(new AssertionFailedError("should not call start"))
        underTest.update(new Event(type:SHUT_DOWN_EVENT));
    }

    @Test
    void "stops a tcpniotransport with shutdown system event"(){
        underTest.update(new Event(type:SHUT_DOWN_EVENT));
        verify(transport).stop()
    }
}
