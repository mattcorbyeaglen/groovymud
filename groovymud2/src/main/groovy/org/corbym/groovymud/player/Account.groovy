package org.corbym.groovymud.player

import com.thoughtworks.xstream.core.util.Base64Encoder

/**
 * Created by IntelliJ IDEA.
 * User: Matt.Corby
 * Date: 21/02/11
 * Time: 08:18
 * To change this template use File | Settings | File Templates.
 */
class Account {
    final static encoder = new Base64Encoder()
    def username
    def password

    def currentCharacter
    public void setPassword(def value){
        this.password = encoder.encode(value.getBytes())
    }
}
