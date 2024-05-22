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
    private Queue<Student> studentQueue = new ArrayDeque<>();
    private transient List<View> views = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    /**
     * Generates a random integer different from the provided number.
     *
     * @param range the range within which the random number is to be generated.
     * @param num the number that the generated number should differ from.
     * @return a random integer different from the provided number.
     */
    private int randDifferentInt(int range, int num) {
        Random rand = new Random();
        int randnum;
        do {
            randnum = rand.nextInt(range);
        } while (randnum == num);
        return randnum;
    }

    /**
     * Default constructor for GameState.
     */
    public GameState() {
        objects = new HashMap<>();
    }

    /**
     * Constructs a GameState with a specified number of students.
     *
     * @param studentsNum the number of students.
     * @throws NecessaryParamsMissingException if necessary parameters are missing.
     * @throws NonexistentObjectException if a nonexistent object is referenced.
     * @throws UnexpectedErrorException if an unexpected error occurs.
     * @throws NonexistentOperationException if a nonexistent operation is attempted.
     */
    public GameState(int studentsNum) throws NecessaryParamsMissingException, NonexistentObjectException, UnexpectedErrorException, NonexistentOperationException {
        Random rand = new Random();
        int roomNum = studentsNum * 3 + 5;
        for (int i = 0; i < roomNum; i++) {
            addObjectFromLine("Room room" + i + " capacity: " + (rand.nextInt(7) + 3) + " cursed: " + ((rand.nextInt(99) < 10) ? "true" : "false" + " gassed: " + ((rand.nextInt(99) < 18) ? "true" : "false")));

        }
        int doorcount = 0;
        // door generation logic
        for(int i = 0; i < roomNum; i++) {
            int toRoom = randDifferentInt(roomNum,i);
            addObjectFromLine(doorcount++ + ": room" + i + "> room" + toRoom);
            addObjectFromLine(doorcount++ + ": room" + toRoom + "> room" + i);
        }
        int otherDoorsNum = roomNum * 2 + 5;
        for (int i = 0; i < otherDoorsNum; i++) {
            int fromRoom = rand.nextInt(roomNum);
            int toRoom = randDifferentInt(roomNum,i);
            addObjectFromLine(doorcount++ + ": room" + fromRoom + "> room" + toRoom);
            if(rand.nextInt(10) < 5) {
                addObjectFromLine(doorcount++ + ": room" + toRoom + "> room" + fromRoom);
            }
        }
        // the real SlideRule is always going to be on the room of the highest number.
        // the players will never notice this because they don't see the rooms name.
        addObjectFromLine("sliderule sr room" + (roomNum-1) + " fake: false");
        for(int i = 0; i < studentsNum; i++)
            addObjectFromLine("sliderule fakesr" + i + " room" + rand.nextInt(roomNum-1) + " fake: true" );
        int fakeableNum = studentsNum * 2 + 4;
        List<String> fakeableObjects = List.of("mask","tvsz");
        for(int i = 0; i < fakeableNum; i++) {
            for(String typename : fakeableObjects) {
                addObjectFromLine(typename + " " + typename + i + " room" + rand.nextInt(roomNum) + " fake: " + ((rand.nextInt(10) < 3) ? "true" : "false"));
            }
        }
        List<String> nonFakeables = List.of("airfreshener","beer","camembert","cloth","transistor");
        int nonFakeableNum = studentsNum * 2 + 1;
        for(int i = 0; i < nonFakeableNum; i++) {
            for(String typename : nonFakeables) {
                addObjectFromLine(typename + " " + typename + i + " room" + rand.nextInt(roomNum));
            }
        }
        List<String> npcs = List.of("teacher","cleaner");
        int npcNum = studentsNum * 2 + 1;
        for(int i = 0; i < npcNum; i++) {
            for(String typename : npcs) {
                addObjectFromLine(typename + " " + typename + i + " room" + rand.nextInt(roomNum));
            }
        }


    }

    /**
     * Creates students from a list of student names.
     *
     * @param studentNames the list of student names.
     */
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


    /**
     * Retrieves an object by its name.
     *
     * @param objName the name of the object.
     * @return the object corresponding to the provided name.
     */
    public void removeDestroyedViews(){
        List<View> viewsToRemove = new ArrayList<>();
        for (View view : views){
            if (view.isDestroyed())
                viewsToRemove.add(view);
        }

        views.removeAll(viewsToRemove);
    }


    public Object getObject(String objName) {
        return (objects.containsKey(objName)) ? objects.get(objName).getObj() : null;
    }

    /**
     * Retrieves the type of an object by its name.
     *
     * @param objName the name of the object.
     * @return the class type of the object.
     */
    public Class<?> getObjectType(String objName) {
        return objects.get(objName).getObj().getClass();
    }


    public void updateObjects() {
        Map<String, GameObject> objectsTemp = new HashMap<>(objects);
        objects.clear();
        fstate = finalState.PENDING;
        boolean foundStudent = false;
        for (Map.Entry<String, GameObject> entry : objectsTemp.entrySet()) {
            if (!entry.getValue().getObj().isDestroyed())
                objects.put(entry.getKey(), entry.getValue());
        }
        for (Student st : studentQueue){
            if (st.didWin())
                win();
        }
//        for (Map.Entry<String, GameObject> entry : objects.entrySet()) {
//            if (entry.getValue().getObj() instanceof Student s) {
//                foundStudent = true;
//                if (s.didWin())
//                    win();
//            }
//            if (!foundStudent) {
//                lose();
//            } else if (fstate != finalState.WIN) {
//                fstate = finalState.PENDING;
//            }
//        }
    }

    /**
     * Adds an object from a line of string.
     *
     * @param line the string representation of the object.
     * @throws NecessaryParamsMissingException if necessary parameters are missing.
     * @throws NonexistentObjectException if a nonexistent object is referenced.
     * @throws UnexpectedErrorException if an unexpected error occurs.
     * @throws NonexistentOperationException if a nonexistent operation is attempted.
     */
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
                    if (p instanceof Student s) {
                        studentQueue.offer(s);
                    } else {
                        views.add(new PersonView(p));
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

    /**
     * Removes an object.
     *
     * @param o the object to be removed.
     */
    public void removeObject(Object o) {
        for (Map.Entry<String, GameObject> e : objects.entrySet()) {
            if (e.getValue() == o) {
                objects.remove(e.getKey());
                break;
            }
        }
    }

    /**
     * Returns a string representation of the current game state.
     *
     * @return the string representation of the game state.
     */
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


    /**
     * Reads the game state from a string.
     *
     * @param stateString the string representation of the game state.
     * @throws UnexpectedErrorException if an unexpected error occurs.
     * @throws NecessaryParamsMissingException if necessary parameters are missing.
     * @throws NonexistentObjectException if a nonexistent object is referenced.
     * @throws NonexistentOperationException if a nonexistent operation is attempted.
     */
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

    /**
     * Compares the current game state with an expected game state.
     *
     * @param expected the expected game state.
     * @return a list of differences between the current and expected game states.
     * @throws NonexistentObjectException if a nonexistent object is referenced.
     * @throws UnexpectedErrorException if an unexpected error occurs.
     */
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

    /**
     * Retrieves the final state of the game.
     *
     * @return a string representation of the final state.
     */
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

    /**
     * Sets the game state to lose.
     */
    public void lose() {
        fstate = finalState.LOSE;
    }

    /**
     * Sets the game state to win.
     */
    public void win() {
        fstate = finalState.WIN;
        GUI.win();
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

    /**
     * Retrieves the name of an object.
     *
     * @param obj the object whose name is to be retrieved.
     * @return the name of the object.
     */
    public String getObjectName(Object obj) {
        for (Map.Entry<String, GameObject> e : objects.entrySet()) {
            if (e.getValue().getObj() == obj) return e.getKey();
        }
        if (obj instanceof Boolean || obj instanceof Integer || obj instanceof Byte || obj instanceof Short ||
                obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof Character)
            return obj.toString();
        return null;
    }

    /**
     * Updates the state of all objects in the game.
     */
    public void tick() {
        for (GameObject go : objects.values())
            go.getObj().tick();
    }

    /**
     * Retrieves the list of views.
     *
     * @return the list of views.
     */
    public List<View> getViews() {
        return views;
    }

    /**
     * Retrieves the next student in the queue.
     *
     * @return the next student.
     */
    public Student nextStudent() {
        Student top = studentQueue.remove();
        studentQueue.offer(top);
        while (!studentQueue.isEmpty() && (top = studentQueue.peek()).isDestroyed()) {
            studentQueue.poll();
        }
        if (studentQueue.isEmpty()) {
            fstate = finalState.LOSE;
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

    /**
     * Retrieves the current student.
     *
     * @return the current student.
     * @throws IllegalStateException if the game is lost and no student can be retrieved.
     */
    public Student getCurrentStudent() {
        if (studentQueue.isEmpty())
            return new Student();

        Student top = studentQueue.peek();
        if (fstate == finalState.PENDING && top == null)
            throw new IllegalStateException("Cannot retrieve the current student for a lost game!");
        return top;
    }

    /**
     * Resets the game state.
     */
    public void reset() {
        objects.clear();
        studentQueue.clear();
        views.clear();
        rooms.clear();
        fstate = finalState.PENDING;
    }

    /**
     * Writes the object state to an output stream.
     *
     * @param out the output stream.
     * @throws IOException if an I/O error occurs.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(studentQueue.size());
        for (Student student : studentQueue) {
            out.writeObject(student);
        }
        out.writeInt(views.size());
        for (View view : views) {
            out.writeObject(view);
        }
    }

    /**
     * Reads the object state from an input stream.
     *
     * @param in the input stream.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        studentQueue = new ArrayDeque<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            studentQueue.add((Student) in.readObject());
        }
        views = new ArrayList<>();
        int viewsSize = in.readInt();
        for (int i = 0; i < viewsSize; i++) {
            views.add((View) in.readObject());
        }
    }

}



