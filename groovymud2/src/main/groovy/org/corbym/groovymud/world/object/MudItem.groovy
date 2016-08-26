package org.corbym.groovymud.world.object

import org.corbym.simplex.persistence.annotations.Id

class MudItem {
    protected static final String GUARDS_PROPERTY = "guards"
    protected static final String CONTENTS_PROPERTY = "contents"
    @Id
    def itemId;

    Map properties = [:]

    transient def currentContainer

    def propertyMissing(String name, value) { properties[name] = value }

    def propertyMissing(String name) { properties[name] }

    def contains(def item) {
        assert properties.containsKey(CONTENTS_PROPERTY), "This item is not a container"
        contents.containsValue(item)
    }

    def put(def item) {
        assert properties.containsKey(CONTENTS_PROPERTY), "This item is not a container."
        assert item != this
        final allowsThis = containerAllowsThis(item)
        if (allowsThis) {
            item.currentContainer = this
            contents.put item.name, item
        }
        return allowsThis
    }
    def putAll(... items){
        items.each(){
            this.put items
        }
    }
    private boolean containerAllowsThis(def item) {
        boolean allow = true
        if (properties[GUARDS_PROPERTY]) {
            this.guards.each {
                allow = it.call(item)
            }
        }
        return allow
    }

    void remove(def object) {
        assert properties.containsKey(CONTENTS_PROPERTY), "This item is not a container."
        assert object != this
        contents.remove(object.name)
    }

    def moves(String direction) {
        def location = this.currentContainer
        assert location
        def exits = location.contents.findAll { key, value ->
            value instanceof Exit
        }
        assert exits != null
        exits[direction].enter(this)
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof MudItem)) return false;

        MudItem mudItem = (MudItem) o;

        if (itemId != mudItem.itemId) return false;
        if (!properties.equals(mudItem.properties)) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "MudItem{id=$itemId}";
    }

}
