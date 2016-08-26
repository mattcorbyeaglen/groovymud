package org.corbym.groovymud.world.object

import org.junit.Before
import org.junit.Test

class ContainerTest {
  def container
  def mudObject

  @Before
  void setUpTests() {
    container = new MudItem(id: 123, name: "bag", contents: [:] as TreeMap)
    mudObject = new MudItem(id: 234, name: "thingy")
  }

  @Test
  void "Container should be able to hold a MudObject"() {
    assert container.put(mudObject);
    println container.contents
    assert container.contains(mudObject);
  }

  @Test
  void "A MudObject should be retrievable from a container by name"() {
    assert container.put(mudObject)
    def returnedObject = container.contents.get(mudObject.name)
    assert returnedObject == mudObject
  }

  @Test
  void "A container should be able to hold another container"() {
    def other = new MudItem(id: 1234, contents: [:] as TreeMap)
    assert container.put(other);
    assert container.contains(other)
  }

  @Test(expected = AssertionError)
  void "A container cannot contain it's self"() {
    container.put(container)
  }

  @Test
  void "a container with a lid guard on cannot be added to without being open"() {
    def other = new MudItem(id: 234, contents: [:] as TreeMap)
    container.open = false
    container.guards = []
    def guard = {
      if (!delegate.open) {
        return false
      }
      return true
    }
    guard.delegate = container
    container.guards << guard
    assert container.put(other) == false
    container.open = true
    assert container.put(other)
    assert container.contains(other)

  }
}
