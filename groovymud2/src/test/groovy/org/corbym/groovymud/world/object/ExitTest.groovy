package org.corbym.groovymud.world.object

import org.junit.Test

class ExitTest {
  @Test(expected=AssertionError)
  void "exit should have a locationto when entered"(){
    def item = new MudItem(currentContainer: new Location(name:"somewhere"))

    def exit = new Exit()
    exit.enter item
  }

}
