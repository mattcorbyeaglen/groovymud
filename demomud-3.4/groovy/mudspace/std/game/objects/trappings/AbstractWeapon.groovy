package std.game.objects.trappings;

import java.util.HashSet;
import java.util.Set;
import std.game.objects.trappings.Holdable
import org.groovymud.object.alive.Alive;
import std.game.objects.MudObjectImpl
/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
public abstract class AbstractWeapon extends MudObjectImpl implements Holdable {

    boolean wielding;

    private Set attacks;
    private Set defences;

    public abstract void doWield(Alive alive);

    public abstract void doLower(Alive alive);

    public Set getAttacks() {
        if (attacks == null) {
            attacks = new HashSet();
        }
        return attacks;
    }

    public void setAttacks(final Set attacks) {
        this.attacks = attacks;
    }

    public void setWielding(final boolean wielding) {
        this.wielding = wielding;
    }

    public boolean addAttack(final def o) {
        if (attacks == null) {
            attacks = new HashSet();
        }
        return attacks.add(o);
    }

    void setDefences(final Set defences) {
        this.defences = defences;
    }

    @Override
    public Set getDefences() {
        if (defences == null) {
            defences = new HashSet();
        }
        return defences;
    }

}
