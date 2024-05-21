package utility;

import java.io.*;

    public class GameSaver {
        /**
         * saves the current state of the game
         *
         * @param state
         * @param filename
         */
        public static void saveGame(GameState state, String filename) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * loads the saved game
         *
         * @param filename
         * @return
         */
        public static GameState loadGame(String filename) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                return (GameState) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
