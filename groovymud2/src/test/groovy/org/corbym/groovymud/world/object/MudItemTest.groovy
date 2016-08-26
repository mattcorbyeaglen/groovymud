package org.corbym.groovymud.world.object

import org.junit.Test

class MudItemTest {
    @Test
    void "can add some dynamic properties to an item"() {
        MudItem mudItem = new MudItem()
        mudItem.name = "one thing"
        mudItem.description = "some desc"

        assert mudItem.name == "one thing"
        assert mudItem.description == "some desc"
    }

    @Test
    void "can add dynamic properties using a constructor"() {
        def mudItem = new MudItem(name: 'something')

        assert mudItem.name == "something"
    }

    @Test
    void "mud items with the same properties are equal"() {
        def mudItem = new MudItem(itemId: 1, name: 'something')
        def mudItem2 = new MudItem(itemId: 1, name: 'something')

        assert mudItem == mudItem2
    }
}
