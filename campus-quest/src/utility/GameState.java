package utility;

import characters.Cleaner;
import characters.Person;
import characters.Student;
import characters.Teacher;
import items.Item;
import map.Door;
import map.Room;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class represents the game state at a moment.
 */

public class GameState {

    private static Map<String,Class<?>> types;
    static{
        types = new HashMap<>();
        types.put("room", Room.class);
        types.put("door", Door.class);
        types.put("student", Student.class);
        types.put("teacher", Teacher.class);
        types.put("cleaner", Cleaner.class);
    }
    private enum finalState{WIN, LOSE, PENDING}
    private Map<String, GameObject> objects;
    private finalState fstate = finalState.PENDING;
    public GameState() {
        objects = new HashMap<>();
    }

    public Object getObject(String objName) {
        return (objects.containsKey(objName)) ? objects.get(objName).getObj(): null;
    }

    public Class<?> getObjectType(String objName) {
        return objects.get(objName).getObj().getClass();
    }

    public void removeObject(String n) {
        objects.remove(n);
    }

    public void removeObject(Object o) {
        for(Map.Entry<String,GameObject> e : objects.entrySet()) {
            if(e.getValue() == o) {
                objects.remove(e.getKey());
                break;
            }
        }
    }

    @Override
    public String toString() {
        List<GameObject> sorted = new ArrayList<>(objects.values());
        sorted.sort(Comparator.comparing(GameObject::getOrder));
        StringBuilder sb = new StringBuilder();
        for(GameObject go : sorted) {
            sb.append(go);
        }
        return sb.toString();
    }

    public void readState(String stateString) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        reset();
        try (Scanner sc = new Scanner(stateString)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                GameObject obj;
                String[] splitted = line.split(" ");
                if (splitted.length == 0) continue;
                if (splitted.length == 1) throw new RuntimeException();
                // if it should be a Door declaration
                if (splitted[0].charAt(splitted[0].length() - 1) == ':' && splitted.length == 4) {
                    Object osrc = getObject(splitted[1]);
                    Object odest = getObject(splitted[3]);
                    if (!(osrc instanceof Room) || !(odest instanceof Room))
                        throw new RuntimeException();
                    Room src = (Room) osrc;
                    Room dest = (Room) odest;
                    Door newdoor = new Door();
                    switch (splitted[2]) {
                        case "<":
                            dest.addDoor(newdoor);
                            break;
                        case ">":
                            src.addDoor(newdoor);
                            break;
                        case "<>":
                            dest.addDoor(newdoor);
                            src.addDoor(newdoor);
                            break;
                        default:
                            throw new RuntimeException();
                    }
                    String doorname = splitted[0].substring(0, splitted[0].length() - 1);
                    GameObject newdoorO = new GameObject(doorname, newdoor,this);
                    objects.put(doorname,newdoorO);
                    newdoorO.setProperty("destination",dest);
                    newdoorO.setProperty("source",src);
                } else {
                    Class<?> newobjtype;
                    String typename = splitted[0];
                    String typenameU = Character.toUpperCase(splitted[0].charAt(0)) + splitted[0].substring(1);
                    try {
                        newobjtype = Class.forName("items." + typenameU);
                    } catch (ClassNotFoundException e) {
                        newobjtype = types.get(typename);
                    }
                    if (newobjtype == null) throw new RuntimeException();
                    Object constructed = newobjtype.getConstructor().newInstance();
                    if(!(constructed instanceof Entity newobj)) throw new RuntimeException();
                    GameObject gameObject = new GameObject(splitted[1],newobj,this);
                    // if it is not a Room an owner specifier comes after the name
                    if (newobjtype != Room.class) {
                        if(splitted.length < 3 || !objects.containsKey(splitted[2])) throw new RuntimeException();
                        if (newobj instanceof Person p) {
                            Object oroom= getObject(splitted[2]);
                            if(!(oroom instanceof Room room)) throw new RuntimeException();
                           p.setRoom(room);
                        } else if(newobj instanceof Item i) {
                            Object oobj = getObject(splitted[2]);
                            if(oobj instanceof Person p) {
                                i.setOwner(p);
                            } else if(oobj instanceof Room r) {
                                i.setRoom(r);
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    }
                    boolean propertyName = true;
                    String lastname = null;
                    for(int i = (newobjtype == Room.class) ? 2 : 3; i < splitted.length; i++) {
                        String element = (propertyName)? splitted[i].substring(0,splitted[i].length()-1) : splitted[i];
                        if(propertyName) {
                            lastname = element;
                        } else {
                            Object prop = null;
                            if(isReference(element)) {
                                prop = getObject(element);
                            } else if(isBoolean(element)) {
                                prop = Boolean.parseBoolean(element);
                            } else if(isInteger(element)) {
                                prop = Integer.parseInt(element);
                            }
                            if(prop != null)
                                gameObject.setProperty(lastname,prop);
                        }
                        propertyName = !propertyName;
                    }
                    // we check whether the last property had a value
                    if(!propertyName) throw new RuntimeException();
                    objects.put(splitted[1],gameObject);
                }
            }
        }
    }

    public List<String> testCompare(GameState expected) {
        List<String> ret = new ArrayList<>();
        for(String key : objects.keySet()) {
            if(!expected.objects.containsKey(key)) {
                ret.add("EXISTENTIAL: " + key + " (" + objects.get(key).getClass().getSimpleName() + ") EXISTS CONTRARY TO THE EXPECTED STATE");
            }
        }
        for(Map.Entry<String,GameObject> go : expected.objects.entrySet()) {
            if(!objects.containsKey(go.getKey())) {
                ret.add("EXISTENTIAL: " + go.getKey() + " (" + go.getValue().getObj().getClass().getSimpleName() + ") DOESN'T EXIST CONTRARY TO THE EXPECTED STATE");
            } else {
                ret.addAll(objects.get(go.getKey()).testCompare(go.getValue()));
            }
        }
        return ret;
    }

    public String getFinalState() {
        switch(fstate) {
            case WIN:
                return "Players won\n";
            case LOSE:
                return "Players lost\n";
            default:
                return "";
        }
    }

    public void lose() {
        fstate = finalState.LOSE;
    }

    public void win() {
        fstate = finalState.WIN;
    }

    public boolean isFinished() {
        return fstate != finalState.PENDING;
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isBoolean(String str) {
        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }

    private  boolean isReference(String str) {
        return objects.containsKey(str);
    }

    public String getObjectName(Object obj) {
        for(Map.Entry<String,GameObject> e : objects.entrySet()) {
            if(e.getValue().getObj() == obj)
                return e.getKey();
        }
        return null;
    }

    public void tick() {
        for(GameObject go : objects.values())
            go.getObj().tick();
    }

    public void reset() {
        objects.clear();
        fstate = finalState.PENDING;
    }
}
