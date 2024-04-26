package utility;

import characters.Person;
import items.Item;
import map.Door;
import map.Room;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A class representing a game object for testing.
 */
public class GameObject {

    /**
     * The name of the object.
     */
    private String name;
    private Entity obj;

    private GameState state;
    private Map<String,Object> properties;
    /**
     * The Fields.
     */
    private Map<String,Field> fields;
    private int listOrder = 3;

    private void syncProperties() throws IllegalAccessException {
        for(String propName : fields.keySet()) {
            properties.put(propName,fields.get(propName).get(obj));
        }
    }

    /**
     * Instantiates a new Game object.
     *
     * @param n         the n
     * @param o         the o
     */
    public GameObject(String n, Entity o, GameState state) {
        name = n;
        obj = o;
        this.state = state;
        properties = new HashMap<>();
        fields = new HashMap<>();
        if(o instanceof Room) listOrder = 0;
        else if(o instanceof Door) listOrder = 1;
        else if(o instanceof Person) listOrder = 2;
        Class<?> clazz = obj.getClass();
        Class<?> superclazz = clazz.getSuperclass();
        Field[] classFields = clazz.getDeclaredFields();
        for(Field f : classFields) {
            f.setAccessible(true);
            fields.put(f.getName(),f);
        }
        if(superclazz != null) {
            Field[] superFields = superclazz.getDeclaredFields();
            for(Field f : superFields) {
                f.setAccessible(true);
                fields.put(f.getName(),f);
            }
        }
    }

    /**
     * Sets property.
     *
     * @param propName the prop name
     * @param value    the value
     * @throws IllegalAccessException the illegal access exception
     */
    public void setProperty(String propName, Object value) throws IllegalAccessException {
        properties.put(propName,value);
        if(fields.containsKey(propName)) {
            Field propField = fields.get(propName);
            propField.set(obj, value);
        }
    }

    public Class<?> getPropertyType(String propName) {
        return fields.get(propName).getClass();
    }

    public List<String> testCompare(GameObject expected) {
        List<String> ret = new ArrayList<>();
        if(!name.equals(expected.name) || expected.obj.getClass() != obj.getClass()) throw new RuntimeException();
        try {
            syncProperties();
            if(expected.obj instanceof Person p) {
                expected.properties.put("room",p.getRoom());
            } else if(expected.obj instanceof Item i) {
                if(i.getRoom() != null)
                    expected.properties.put("room",i.getRoom());
                else if(i.getOwner() != null)
                    expected.properties.put("owner",i.getOwner());
                else
                    throw new RuntimeException();
            } else if(expected.obj instanceof Door d) {
                StringBuilder realDir = new StringBuilder();
                StringBuilder expectedDir = new StringBuilder();
                if(d.getDest().getDoors().contains(d.getSrc())) {
                    expectedDir.append('<');
                }
                if(d.getSrc().getDoors().contains(d.getDest())) {
                    expectedDir.append('>');
                }
                Door door = (Door) obj;
                if(door.getDest().getDoors().contains(door.getSrc())) {
                    realDir.append('<');
                }
                if(door.getSrc().getDoors().contains(door.getDest())) {
                    realDir.append('>');
                }
                String realDirString = realDir.toString();
                String expectedDirString = expectedDir.toString();
                if(!realDirString.equals(expectedDirString)) {
                    String line = "DIFFERENTIAL: " + name + " (Door) HAS " + "direction: " + realDirString + " CONTRARY TO THE EXPECTED " + "direction:" + expectedDirString;
                    ret.add(line);
                }
            }

            for(String propName : expected.properties.keySet()) {
                String expectedvalname = expected.state.getObjectName(expected.properties.get(propName));
                String valname = state.getObjectName(properties.get(propName));
               if(!(expectedvalname != null && valname != null && expectedvalname.equals(valname)) && properties.containsKey(propName) && properties.get(propName) != null &&!(properties.get(propName).equals(expected.properties.get(propName)))) {
                    String prop = propName + ": ";
                    Object propval = properties.get(propName);
                    Object expropval = expected.properties.get(propName);
                    String line = "DIFFERENTIAL: " + name + " (" + obj.getClass().getSimpleName() + ") HAS " + prop + propval.toString() + " CONTRARY TO THE EXPECTED " + prop + expropval;
                    ret.add(line);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        expected.properties.remove("room");
        expected.properties.remove("owner");
        return ret;
    }

    public Entity getObj() {
        return obj;
    }

    public int getOrder() {return listOrder;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            syncProperties();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        sb.append(obj.getClass().getSimpleName()); sb.append(' ');
        sb.append(name); sb.append(' ');
        if(obj instanceof Person p) {
            sb.append(state.getObjectName(p.getRoom())); sb.append(' ');
        } else if(obj instanceof Item i) {
            if(i.getRoom() != null) {
                sb.append(state.getObjectName(i.getOwner()));
                sb.append(' ');
            }  else if(i.getOwner() != null) {
                sb.append(state.getObjectName(i.getRoom()));
                sb.append(' ');
            } else
                throw new RuntimeException();
        }
        List<String> sorted = new ArrayList<>(properties.keySet());
        Collections.sort(sorted);
        for(String s: sorted) {
            sb.append(s); sb.append(": ");
            Object propval = properties.get(s);
            if(state.getObject(s) != null) {
                sb.append(s);
            } else {
                if(propval == null) sb.append("null");
                else sb.append(propval.toString());
            }
            sb.append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }

}
