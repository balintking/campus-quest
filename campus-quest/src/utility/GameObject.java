package utility;

import characters.Person;
import exceptions.NonexistentObjectException;
import exceptions.UnexpectedErrorException;
import items.Item;
import map.Door;
import map.Room;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * A class representing a game object for testing.
 */
public class GameObject implements Serializable{
    private static final long serialVersionUID = 1L;


    /**
     * The name of the object.
     */
    private String name;
    private Entity obj;

    private GameState state;
    private Map<String, Object> properties;
    /**
     * The Fields.
     */
    private transient Map<String, Field> fields;
    private int listOrder = 3;

    private void syncProperties() throws UnexpectedErrorException {
        for (String propName : fields.keySet()) {
            try {
                properties.put(propName, fields.get(propName).get(obj));
            } catch (IllegalAccessException e) {
                throw new UnexpectedErrorException();
            }
        }
    }

    /**
     * Instantiates a new Game object.
     *
     * @param n the n
     * @param o the o
     */
    public GameObject(String n, Entity o, GameState state) {
        name = n;
        obj = o;
        this.state = state;
        properties = new HashMap<>();
        fields = new HashMap<>();
        if (o instanceof Room) listOrder = 0;
        else if (o instanceof Door) listOrder = 1;
        else if (o instanceof Person) listOrder = 2;
        Class<?> clazz = obj.getClass();
        Class<?> superclazz = clazz.getSuperclass();
        Field[] classFields = clazz.getDeclaredFields();
        for (Field f : classFields) {
            f.setAccessible(true);
            fields.put(f.getName(), f);
        }
        if (superclazz != null) {
            Field[] superFields = superclazz.getDeclaredFields();
            for (Field f : superFields) {
                f.setAccessible(true);
                fields.put(f.getName(), f);
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
    public void setProperty(String propName, Object value) throws UnexpectedErrorException {
        properties.put(propName, value);
        if (fields.containsKey(propName)) {
            Field propField = fields.get(propName);
            try {
                propField.set(obj, value);
            } catch (IllegalAccessException e) {
                throw new UnexpectedErrorException();
            }
        }
    }

    public Class<?> getPropertyType(String propName) {
        return fields.get(propName).getClass();
    }

    public List<String> testCompare(GameObject expected) throws NonexistentObjectException, UnexpectedErrorException {
        List<String> ret = new ArrayList<>();
        if (!name.equals(expected.name) || expected.obj.getClass() != obj.getClass())
            throw new NonexistentObjectException();

        syncProperties();
        if (expected.obj instanceof Person p) {
            expected.properties.put("room", p.getRoom());

        } else if (expected.obj instanceof Item i) {
            if (i.getRoom() != null)
                expected.properties.put("room", i.getRoom());
            else if (i.getOwner() != null)
                expected.properties.put("owner", i.getOwner());
            else
                throw new NonexistentObjectException(); //Item i doesn't have an owner nor is it in a room

        } else if (expected.obj instanceof Door expDoor) {
            StringBuilder realDir = new StringBuilder();
            StringBuilder expectedDir = new StringBuilder();
            Door thisDoor = (Door) obj;

            //if a door's destination room has the same door -> the door goes in both directions
            boolean thisDoorIsBidirectional = thisDoor.getDest().getDoors().contains(thisDoor);
            boolean expectedDoorIsBidirectional = expDoor.getDest().getDoors().contains(expDoor);

            //if both doors are bidirectional
            if (thisDoorIsBidirectional && expectedDoorIsBidirectional) {
                String thisSrc = this.state.getObjectName(thisDoor.getSrc());
                String thisDest = this.state.getObjectName(thisDoor.getDest());
                String expSrc = expected.state.getObjectName(expDoor.getSrc());
                String expDest = expected.state.getObjectName(expDoor.getDest());

                //if this door's source == expected door's destination and vice versa
                if (thisSrc.equals(expDest) && thisDest.equals(expSrc)) {
                    //switch expected door's source and destination
                    Room dest = expDoor.getDest();
                    expDoor.setDest(expDoor.getSrc());
                    expDoor.setSrc(dest);
                }
            }

            realDir.append(this.state.getObjectName(thisDoor.getSrc()));
            if (thisDoorIsBidirectional) {
                realDir.append(" <> ");
            } else {
                realDir.append(" > ");
            }
            realDir.append(this.state.getObjectName(thisDoor.getDest()));

            expectedDir.append(expected.state.getObjectName(expDoor.getSrc()));
            if (expectedDoorIsBidirectional) {
                expectedDir.append(" <> ");
            } else {
                expectedDir.append(" > ");
            }
            expectedDir.append(expected.state.getObjectName(expDoor.getDest()));

            String realDirString = realDir.toString();
            String expectedDirString = expectedDir.toString();
            if (!realDirString.equals(expectedDirString)) {
                String line = "DIFFERENTIAL: " + name + " (Door) HAS " + "direction: " + realDirString + " CONTRARY TO THE EXPECTED " + "direction: " + expectedDirString;
                ret.add(line);
            }
        }

        for (String propName : expected.properties.keySet()) {
            if (propName.equals("destination") || propName.equals("source")) {
                continue;  //we already checked the door's source and destination properties
            }
            String expectedValueName = expected.state.getObjectName(expected.properties.get(propName));
            String thisValueName = state.getObjectName(properties.get(propName));
            if (!(expectedValueName != null && thisValueName != null && expectedValueName.equals(thisValueName)) && properties.containsKey(propName) && properties.get(propName) != null && !(properties.get(propName).equals(expected.properties.get(propName)))) {
                String prop = propName + ": ";
                Object thisValue = properties.get(propName);
                Object expectedValue = expected.properties.get(propName);
                String line = "DIFFERENTIAL: " + name + " (" + obj.getClass().getSimpleName() + ") HAS " + prop + state.getObjectName(thisValue) + " CONTRARY TO THE EXPECTED " + prop + expected.state.getObjectName(expectedValue);
                ret.add(line);
            }
        }
        expected.properties.remove("room");
        expected.properties.remove("owner");
        return ret;
    }

    public Entity getObj() {
        return obj;
    }

    public int getOrder() {
        return listOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            syncProperties();
        } catch (UnexpectedErrorException e) {
            System.err.println(e.getMessage());
        }
        sb.append(obj.getClass().getSimpleName());
        sb.append(' ');
        sb.append(name);
        sb.append(' ');
        if (obj instanceof Person p) {
            sb.append(state.getObjectName(p.getRoom()));
            sb.append(' ');
        } else if (obj instanceof Item i) {
            if (i.getRoom() != null) {
                sb.append(state.getObjectName(i.getRoom()));
                sb.append(' ');
            } else if (i.getOwner() != null) {
                sb.append(state.getObjectName(i.getOwner()));
                sb.append(' ');
            } else
                throw new RuntimeException();
        }
        List<String> sorted = new ArrayList<>(properties.keySet());
        Collections.sort(sorted);
        for (String s : sorted) {
            sb.append(s);
            sb.append(": ");
            Object propval = properties.get(s);
            if (state.getObject(s) != null) {
                sb.append(s);
            } else {
                if (propval == null) sb.append("null");
                else sb.append(propval.toString());
            }
            sb.append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(fields.size());
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue().getDeclaringClass());
        }
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        fields = new HashMap<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = (String) in.readObject();
            Class<?> declaringClass = (Class<?>) in.readObject();
            try {
                Field field = declaringClass.getDeclaredField(key);
                field.setAccessible(true);
                fields.put(key, field);
            } catch (NoSuchFieldException e) {
                throw new IOException("Failed to restore field: " + key, e);
            }
        }
    }


}


