package org.corbym.groovymud.player

import com.thoughtworks.xstream.core.util.Base64Encoder
import org.junit.Test

class AccountTest {
    final encoder = new Base64Encoder()

    @Test
    void "password should always be encrypted when set"(){
        Account account = new Account();
        account.password = "hewwo"

        assert account.password == encoder.encode("hewwo".getBytes())
    }
}
