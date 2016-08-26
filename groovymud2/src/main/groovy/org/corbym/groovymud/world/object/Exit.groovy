package org.corbym.groovymud.world.object

import org.corbym.simplex.persistence.annotations.LazyLoad


import org.corbym.groovymud.world.object.MudItem
import org.corbym.groovymud.world.object.Location

class Exit extends MudItem {
  @LazyLoad
  Location locationTo

  def inTheDirectionOf(def direction) {
    this.direction = direction
    this.name = direction
    return this
  }

  def arrivingFrom(def arrivingFrom) {
    this.arrivingFrom = arrivingFrom
    return this
  }

  def leadingTo(def locationTo) {
    this.locationTo = locationTo
    return this
  }

  def enter(def mob) {
    mob.currentContainer.remove(mob)
    locationTo.put(mob)
  }
}
