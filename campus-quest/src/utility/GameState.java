package utility;

import characters.Cleaner;
import characters.Person;
import characters.Student;
import characters.Teacher;
import exceptions.*;
import items.*;
import map.Door;
import map.Room;
import views.*;

import java.io.*;
import java.util.*;

/**
 * This class represents the game state at a moment.
 */

public class GameState implements Serializable {

    private static Map<String, Class<?>> types;

    static {
        types = new HashMap<>();
        types.put("room", Room.class);
        types.put("door", Door.class);
        types.put("student", Student.class);
        types.put("teacher", Teacher.class);
        types.put("cleaner", Cleaner.class);
        types.put("airfreshener", AirFreshener.class);
        types.put("beer", Beer.class);
        types.put("camembert", Camembert.class);
        types.put("cloth", Cloth.class);
        types.put("mask", Mask.class);
        types.put("sliderule", Sliderule.class);
        types.put("transistor", Transistor.class);
        types.put("tvsz", TVSZ.class);
    }

    private int playerCounter = 1;

    private enum finalState {WIN, LOSE, PENDING}

    Map<String, GameObject> objects;
    private finalState fstate = finalState.PENDING;
    private transient Queue<Student> studentQueue = new ArrayDeque<>();
    private transient List<View> views = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    public GameState() {
        objects = new HashMap<>();
    }

    public GameState(int studentsNum) {
        // TODO map generation - game design choices
    }

    public void createStudents(List<String> studentNames) {
        for (String name : studentNames) {
            studentQueue.add(new Student(name));
        }

        //Assign player created students to rooms if rooms exist
        if (rooms.isEmpty())
            return;

        Random rand = new Random();
        for (Student st : studentQueue) {
            Room room = rooms.get(rand.nextInt(rooms.size()));
            st.setRoom(room);
            room.addPerson(st);
            views.add(new PersonView(st));
        }
    }

    public Object getObject(String objName) {
        return (objects.containsKey(objName)) ? objects.get(objName).getObj() : null;
    }

    public Class<?> getObjectType(String objName) {
        return objects.get(objName).getObj().getClass();
    }

    public void removeObject(String n) {
        objects.remove(n);
    }

    private void updateObjects() {
        Map<String, GameObject> objectsTemp = new HashMap<>(objects);
        objects.clear();
        fstate = finalState.PENDING;
        boolean foundStudent = false;
        for (Map.Entry<String, GameObject> entry : objectsTemp.entrySet()) {
            if (!entry.getValue().getObj().isDestroyed())
                objects.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, GameObject> entry : objects.entrySet()) {
            if (entry.getValue().getObj() instanceof Student s) {
                foundStudent = true;
                if (s.didWin())
                    win();
            }
            if (!foundStudent) {
                lose();
            } else if (fstate != finalState.WIN) {
                fstate = finalState.PENDING;
            }
        }
    }

    public void addObjectFromLine(String line) throws NecessaryParamsMissingException, NonexistentObjectException, UnexpectedErrorException, NonexistentOperationException {
        String[] splitted = line.split(" ");
        if (splitted.length == 0) return;
        if (splitted.length == 1) throw new NecessaryParamsMissingException();
        // if it should be a Door declaration
        if (splitted[0].charAt(splitted[0].length() - 1) == ':') {

            Object srcObj = getObject(splitted[1]);
            Object destObj = getObject(splitted[3]);
            if (!(srcObj instanceof Room) || !(destObj instanceof Room))
                throw new NonexistentObjectException();
            Room srcRoom = (Room) srcObj;
            Room destRoom = (Room) destObj;
            Door newDoor = new Door();
            switch (splitted[2]) {
                case "<":
                    //switch srcRoom and destRoom
                    Room temp = srcRoom;
                    srcRoom = destRoom;
                    destRoom = temp;
                    srcRoom.addDoor(newDoor);
                    break;
                case ">":
                    srcRoom.addDoor(newDoor);
                    break;
                default:
                    throw new NonexistentOperationException();
            }
            String doorName = splitted[0].substring(0, splitted[0].length() - 1);
            GameObject newDoorObj = new GameObject(doorName, newDoor, this);
            objects.put(doorName, newDoorObj);
            newDoorObj.setProperty("destination", destRoom);
            newDoorObj.setProperty("source", srcRoom);

            if (splitted.length > 4) { //door has "hidden:" property defined
                newDoorObj.setProperty("hidden", splitted[5].equals("true"));
            }
            views.add(new DoorView(newDoor));
        } else {
            Class<?> newObjType;
            String typename = splitted[0];
            newObjType = types.get(typename);
            if (newObjType == null)
                throw new NonexistentObjectException();

            Object constructed = null;
            try {
                constructed = newObjType.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }

            if (!(constructed instanceof Entity newObj))
                throw new NonexistentObjectException();
            GameObject gameObject = new GameObject(splitted[1], newObj, this);
            // if it is not a Room an owner specifier comes after the name
            if (!(newObj instanceof Room r)) {
                if (splitted.length < 3 || !objects.containsKey(splitted[2]))
                    throw new NecessaryParamsMissingException();
                if (newObj instanceof Person p) {
                    views.add(new PersonView(p));
                    if (p instanceof Student s) {
                        studentQueue.offer(s);
                    }
                    Object roomObj = getObject(splitted[2]);
                    if (!(roomObj instanceof Room room)) throw new NonexistentObjectException();
                    room.addPerson(p);
                    p.setRoom(room);
                } else if (newObj instanceof Item i) {
                    views.add(new ItemView(i));
                    Object oobj = getObject(splitted[2]);
                    if (oobj instanceof Person p) {
                        i.setOwner(p);
                        p.addItem(i);
                    } else if (oobj instanceof Room r) {
                        i.setRoom(r);
                        r.addItem(i);
                    } else {
                        throw new NonexistentObjectException();
                    }
                }
            } else {
                views.add(new RoomView(r));
                rooms.add(r);
            }
            boolean propertyName = true;
            String lastname = null;
            for (int i = (newObjType == Room.class) ? 2 : 3; i < splitted.length; i++) {
                String element = (propertyName) ? splitted[i].substring(0, splitted[i].length() - 1) : splitted[i];
                if (propertyName) {
                    lastname = element;
                } else {
                    Object propertyObj = null;
                    if (isReference(element)) {
                        propertyObj = getObject(element);
                    } else if (isBoolean(element)) {
                        propertyObj = Boolean.parseBoolean(element);
                    } else if (isInteger(element)) {
                        propertyObj = Integer.parseInt(element);
                    } else {
                        propertyObj = element;
                    }
                    if (propertyObj != null) gameObject.setProperty(lastname, propertyObj);
                }
                propertyName = !propertyName;
            }
            // we check whether the last property had a value
            if (!propertyName)
                throw new NecessaryParamsMissingException();
            objects.put(splitted[1], gameObject);
        }
    }

    public void removeObject(Object o) {
        for (Map.Entry<String, GameObject> e : objects.entrySet()) {
            if (e.getValue() == o) {
                objects.remove(e.getKey());
                break;
            }
        }
    }

    @Override
    public String toString() {
        updateObjects();
        List<GameObject> sorted = new ArrayList<>(objects.values());
        sorted.sort(Comparator.comparing(GameObject::getOrder));
        StringBuilder sb = new StringBuilder();
        for (GameObject go : sorted) {
            sb.append(go);
        }
        sb.append(getFinalState() + "\n");
        return sb.toString();
    }

    public void readState(String stateString) throws UnexpectedErrorException, NecessaryParamsMissingException, NonexistentObjectException, NonexistentOperationException {
        reset();
        try (Scanner sc = new Scanner(stateString)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.equals("players won")) {
                    fstate = finalState.WIN;
                    continue;
                } else if (line.equals("players lost")) {
                    fstate = finalState.LOSE;
                    continue;
                }
                addObjectFromLine(line);
            }
        }
    }

    public List<String> testCompare(GameState expected) throws NonexistentObjectException, UnexpectedErrorException {
        updateObjects();
        List<String> ret = new ArrayList<>();
        for (String key : objects.keySet()) {
            if (!expected.objects.containsKey(key)) {
                ret.add("EXISTENTIAL: " + key + " (" + objects.get(key).getObj().getClass().getSimpleName() + ") EXISTS CONTRARY TO THE EXPECTED STATE");
            }
        }
        for (Map.Entry<String, GameObject> go : expected.objects.entrySet()) {
            if (!objects.containsKey(go.getKey())) {
                ret.add("EXISTENTIAL: " + go.getKey() + " (" + go.getValue().getObj().getClass().getSimpleName() + ") DOESN'T EXIST CONTRARY TO THE EXPECTED STATE");
            } else {
                ret.addAll(objects.get(go.getKey()).testCompare(go.getValue()));
            }
        }
        if (!getFinalState().equals(expected.getFinalState()))
            ret.add("Final state mismatch: current: " + getFinalState() + " expected: " + expected.getFinalState());
        return ret;
    }

    public String getFinalState() {
        switch (fstate) {
            case WIN:
                return "Players won";
            case LOSE:
                return "Players lost";
            default:
                return "Game running";
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

    private boolean isReference(String str) {
        return objects.containsKey(str);
    }

    public String getObjectName(Object obj) {
        for (Map.Entry<String, GameObject> e : objects.entrySet()) {
            if (e.getValue().getObj() == obj) return e.getKey();
        }
        if (obj instanceof Boolean || obj instanceof Integer || obj instanceof Byte || obj instanceof Short ||
                obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof Character)
            return obj.toString();
        return null;
    }

    public void tick() {
        for (GameObject go : objects.values())
            go.getObj().tick();
    }

    public List<View> getViews() {
        return views;
    }

    public Student nextStudent() {
        Student top = studentQueue.remove();
        studentQueue.offer(top);
        while (!studentQueue.isEmpty() && (top = studentQueue.peek()).isDestroyed()) {
            studentQueue.poll();
        }
        if (studentQueue.isEmpty()) {
            GUI.lose();
            return null;
        }
        if (top != null) {
            playerCounter++;
            if (playerCounter > studentQueue.size()) {
                tick();
                playerCounter = 1;
            }
        }
        return top;
    }

    public Student getCurrentStudent() {
        Student top = studentQueue.peek();
        if (top == null) throw new IllegalStateException("Cannot retrieve the current student for a lost game!");
        return top;
    }

    public void reset() {
        objects.clear();
        studentQueue.clear();
        views.clear();
        rooms.clear();
        fstate = finalState.PENDING;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(studentQueue.size());
        for (Student student : studentQueue) {
            out.writeObject(student);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        studentQueue = new ArrayDeque<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            studentQueue.add((Student) in.readObject());
        }
        views = new ArrayList<>(); // reinitialize views if needed
    }

}



