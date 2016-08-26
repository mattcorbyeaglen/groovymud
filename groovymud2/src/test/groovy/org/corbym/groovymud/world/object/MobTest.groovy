package org.corbym.groovymud.world.object

import org.junit.Before
import org.junit.Test

class MobTest {

  def mob

  @Before
  void setUp() {
    mob = new MudItem(id:432, name:"goblin", dead: false)
  }
  @Test
  void "mob has a current location and can move from one location to another via an exit"() {
    Location locationOne = new Location(id:123, name:"some loc", description: "desc", contents: [:] as TreeMap, exits: [])
    Location locationTwo = new Location(id: 212, name:"some loc", description: "desc", contents: [:] as TreeMap, exits: [])
    def leftExit = new Exit(id:12)
    def rightExit = new Exit(id:13);

    locationOne.hasA(leftExit.inTheDirectionOf("south").arrivingFrom("north").leadingTo(locationTwo))
    locationTwo.hasA(rightExit.inTheDirectionOf("north").arrivingFrom("south").leadingTo(locationOne))

    locationTwo.put(mob)
    assert mob.currentContainer == locationTwo

    mob.moves("north")

    assert !locationTwo.contains(mob)
    assert locationOne.contains(mob)
    assert mob.currentContainer == locationOne
  }

}
