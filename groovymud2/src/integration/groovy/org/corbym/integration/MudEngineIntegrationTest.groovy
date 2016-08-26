package org.corbym.integration

import java.util.concurrent.TimeUnit
import org.corbym.acceptance.LifeCycleFilter
import org.corbym.groovymud.engine.NonBlockingTransportManager
import org.corbym.groovymud.engine.event.SystemEventType
import org.corbym.groovymud.world.Bootstrap
import org.corbym.groovymud.world.World
import org.glassfish.grizzly.filterchain.FilterChainBuilder
import org.glassfish.grizzly.filterchain.TransportFilter
import org.glassfish.grizzly.nio.transport.TCPNIOTransport
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Test
import static org.corbym.support.SugarMatcher.*
import static org.hamcrest.core.IsEqual.equalTo
import static org.junit.Assert.assertThat
import static java.util.concurrent.TimeUnit.SECONDS

//@Grab(group='com.jayway.awaitility', module='awaitility-groovy', version='1.3.2')
class MudEngineIntegrationTest extends AbstractIntegrationTest {

    boolean shutdownWasCalled = false
    final connectionCountingLifeCycleFilter = new LifeCycleFilter()
    def bootstrapper
    def systemLoginFilter
    NonBlockingTransportManager nonBlockingServerSocketManager
    Socket socket

    @After
    void cleanUpStuff() {
        if (gameEngine.started) {
            gameEngine.shutdown()
        }
    }

    @Test
    void "Mud Engine can start and informs a bootstrapping startupListener"() {
        given:
        aGameEngine().with {
            aStartupListener of(aBootstrapper())
        }
        when:
        theGameEngine().start()

        then:
        assertThat theGameEngine().isStarted(), CoreMatchers.is(true)
        assertThat theGameEngine().activities[0], CoreMatchers.is(CoreMatchers.any(World))
    }

    @Test
    void "Mud Engine can shutdown and informs a shutdownListener"() {
        given:
        aRunningGameEngine().with {
            andAlso aShutdownListener(of(fakeShutdownListener()))
        }
        when:
        theGameEngine().shutdown()

        then:
        assertThat theGameEngine().isStarted(), CoreMatchers.is(false)
        assertThat shutdownWasCalled, equalTo(true)
    }

    @Test
    void "the Mud Engine can run an a nonBlockingServerSocket as a startup listener and accept connections to port 2222"() {
        given:
        aGameEngine().with {
            aStartupListener(of(aNonBlockingServerSocketConnectionManager(connectedToPort(2222), withFilters(connectionCountingLifeCycleFilter))))
        }

        when:
        theGameEngine().start()
        andWhen aClientConnectionIsMadeToPort(2222)

        then:

        await().atMost(5, SECONDS).until {totalActiveConnections() == 1};
    }

    def fakeShutdownListener() {
        gameEngine.addListener([update: { event ->
            shutdownWasCalled = true
            assertThat event.type, CoreMatchers.is(equalTo(SystemEventType.SHUT_DOWN_EVENT))
        }, accepts: {eventType -> eventType == SystemEventType.SHUT_DOWN_EVENT}])
    }

    def aShutdownListener(def shutdownListener) {
    }

    private OutputStream theClientSends() {
        return theClient().outputStream << "flumpy"
    }

    private String theClientsInputStream() {
        return theClient().inputStream.getText()
    }

    def theClient() {
        socket
    }

    def connectedToPort(int port) {
        return port
    }

    def theNonBlockingServerSocketConnectionManager() {
        nonBlockingServerSocketManager
    }

    def aClientConnectionIsMadeToPort(int port) {
        Thread.start(){
            socket = new Socket("localhost", 2222);
        }
    }

    def aNonBlockingServerSocketConnectionManager(int port, ... filters) {
        FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();
        filterChainBuilder.add(new TransportFilter());
        for (def filter: filters) {
            filterChainBuilder.add(filter);
        }
        final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance().build();
        nonBlockingServerSocketManager = new NonBlockingTransportManager(port: port, filterChain: filterChainBuilder.build(), transport: transport);
    }

    private int totalActiveConnections() {
        return connectionCountingLifeCycleFilter.activeConnections.size()
    }

    def aGameActivityListener(def activity) {
        theGameEngine().addActivity activity
    }

    def aBootstrapper() {
        bootstrapper = new Bootstrap()
    }

    def aStartupListener(def listener) {
        theGameEngine().addListener listener
    }

    def withFilters(... filters) {
        return filters
    }

    def theBootstrapper() {
        return bootstrapper
    }

}
