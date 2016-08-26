package org.corbym.acceptance

import com.thoughtworks.xstream.core.util.Base64Encoder
import org.corbym.groovymud.player.Account
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.internal.matchers.TypeSafeMatcher
import static java.util.concurrent.TimeUnit.SECONDS
import static org.corbym.support.SugarMatcher.andWhen
import static org.corbym.support.SugarMatcher.then
import static org.hamcrest.Matchers.containsString

class PlayerAcceptanceTest extends AbstractMudAcceptanceTest {

    def socket

    @Test
    /**
     * As a player, I want to create a new account so I can
     * log in to the game server
     *
     */
    void "a player who logs on can create a new account"() {
        given:
        aGameEngine()

        when:
        aClientConnectionIsMadeToPort(2222)

        then theServerResponse(), containsString("Username:")

        when:
        thePlayerRespondsWith("flumpy")

        then theServerResponse(), containsString("This username does not exist. Create a new User?")

        when:
        thePlayerRespondsWith("Yes")

        then:
        then theServerResponse(), containsString("Enter a new password:")

        when:
        thePlayerRespondsWith("x12345")

        then theServerResponse(), containsString("Please confirm your password:")

        when:
        thePlayerRespondsWith("x12345")

        then aPlayerAccountExistsWithTheUsername("flumpy"), withHashedPassword("x12345")
    }

    Account aPlayerAccountExistsWithTheUsername(String username) {
        dao.loadAll(Account.class)[0]
    }

    Matcher withHashedPassword(String password) {
        return new TypeSafeMatcher<Account>() {
            String accountPassword

            @Override
            boolean matchesSafely(Account account) {
                this.accountPassword = account.password
                return accountPassword == encodePassword()
            }

            private String encodePassword() {
                return new Base64Encoder().encode(password.getBytes())
            }

            @Override
            void describeTo(Description description) {
                description.appendText """Encoded password $password does not match.
Encoder produced $encodePassword() but was ${accountPassword}"""
            }
        }
    }


    @Test
    /**
     * as a player, I want to create a new character in the game
     * so I can start playing
     */
    void "a player with an account can create a new character in the game"() {
        given:
        aLoggedInUserWithNoCharacter();

    }

    @Test
    /** as a player, I want to be able to log in to my existing account, so I can
     * continue to play the game from where I left off
     */
    void "a player can log in with an existing account"() {
        given:
        anAccountExistsWithTheUsername("flumpy", andHashedPassword("x12345"))

        when:
        aClientConnectionIsMadeToPort(2222)

        then theServerResponse(), containsString("Username:")

        andWhen(thePlayerRespondsWith("flumpy"))

        then await().atMost(1, SECONDS).until { theServerResponse().contains("password")}

        andWhen thePlayerRespondsWith("x12345")

        then thePlayerIsLoggedIn()
    }

    @Test
    void "an invalid login is rejected after three attempts"() {
        fail("implement me")
    }

    private OutputStream thePlayerRespondsWith() {
        return theClient().outputStream << "flumpy"
    }

    private String theServerResponse() {
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
        socket = new Socket("localhost", 2222);
    }

}
