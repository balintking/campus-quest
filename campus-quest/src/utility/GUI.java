package utility;

import characters.Student;
import views.DoorView;
import views.ItemView;
import views.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GUI {
    private static GameState state;

    public static Student getCurrentStudent() {
        return state.getCurrentStudent();
    }

    private static JFrame frame;
    private static JLabel nameLabel;
    private static final List<JPanel> inventorySlots = new ArrayList<>();
    private static int itemsInInventory = 0;
    private static JPanel roomPanel;
    private static JPanel doorPanel;
    private static JButton endTurnButton = new JButton("End Turn");
    private static JMenuItem saveMenuItem = new JMenuItem("Save Game");

    private static final Random random = new Random();

    private static boolean gameEnded = false;

    private static final Color bgColor = new Color(40, 40, 40);
    private static final Dimension roomSize = new Dimension(700, 500);

    private static final String resourcesPath = "campus-quest" + File.separator + "resources";

    public static void initMenu() {
        frame = new JFrame("Campus Quest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(bgColor);
        frame.setSize(1280, 720);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 400, 100, 400));

        //Create a label for the title
        JLabel titleLabel = new JLabel("Campus Quest", SwingConstants.CENTER);
        titleLabel.setFont(getMainFont(72));
        titleLabel.setForeground(Color.lightGray);
        panel.add(titleLabel, BorderLayout.NORTH);

        //Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));

        //Create the buttons
        JButton newGameButton = new JButton("New Game");
        JButton loadButton = new JButton("Load Game");
        JButton quitButton = new JButton("Quit");

        //Button styles
        newGameButton.setBackground(Color.lightGray);
        newGameButton.setBorder(new LineBorder(Color.BLACK, 5));
        newGameButton.setFont(getMainFont(48));
        newGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newGameButton.setOpaque(true);
        loadButton.setBackground(Color.lightGray);
        loadButton.setBorder(new LineBorder(Color.BLACK, 5));
        loadButton.setFont(getMainFont(48));
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadButton.setOpaque(true);
        quitButton.setBackground(Color.lightGray);
        quitButton.setBorder(new LineBorder(Color.BLACK, 5));
        quitButton.setFont(getMainFont(48));
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.setOpaque(true);

        //Add action listeners to the buttons
        newGameButton.addActionListener(e -> {
            // Code to start a new game
            newGame(panel);
        });

        loadButton.addActionListener(e -> {
            state = GameSaver.loadGame("savedgame.txt");
            if (state != null) {
                inGameView();
                update();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to load game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        quitButton.addActionListener(e -> {
            // Code to exit the game
            System.exit(0);
        });

        //Add buttons to the button panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(quitButton);

        //Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(600, 600));

        buttonPanel.setBackground(bgColor);
        panel.setBackground(bgColor);

        //Add the main panel to the frame
        frame.getContentPane().add(panel);

        //Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void newGame(JPanel panel) {
        for (Component c : panel.getComponents()) {
            c.setVisible(false);
        }
        panel.removeAll();

        gameEnded = false;

        ArrayList<String> playerNames = new ArrayList<>();

        //Create a label for the title
        JLabel titleLabel = new JLabel("Players", SwingConstants.CENTER);
        titleLabel.setFont(getMainFont(36));
        titleLabel.setForeground(Color.lightGray);
        panel.add(titleLabel, BorderLayout.NORTH);

        //Create the center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(new EmptyBorder(10, 100, 10, 100));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        panel.add(centerPanel);

        //Names listing
        JPanel namesPanel = new JPanel(new FlowLayout());
        namesPanel.setBackground(Color.lightGray);
        namesPanel.setBorder(new LineBorder(Color.BLACK, 5));
        namesPanel.setPreferredSize(new Dimension(centerPanel.getWidth(), 500));

        //Panel for Name field and Buttons
        JPanel newNamePanel = new JPanel(new BorderLayout(5, 10));
        newNamePanel.setPreferredSize(new Dimension(centerPanel.getWidth(), 100));

        //Name field
        JTextField nameField = new JTextField("Player Name");
        nameField.setBackground(Color.lightGray);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setFont(getMainFont(28));
        nameField.setBorder(new LineBorder(Color.BLACK, 5));

        //Buttons
        JButton addButton = new JButton("Add Player");
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(newNamePanel.getWidth(), 50));

        //Button styles
        addButton.setBackground(Color.lightGray);
        addButton.setBorder(new LineBorder(Color.BLACK, 5));
        addButton.setFont(getMainFont(28));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setOpaque(true);
        startButton.setBackground(Color.lightGray);
        startButton.setBorder(new LineBorder(Color.BLACK, 5));
        startButton.setFont(getMainFont(36));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setOpaque(true);


        //newNamePanel components
        newNamePanel.add(nameField);
        newNamePanel.add(addButton, BorderLayout.EAST);
        newNamePanel.add(startButton, BorderLayout.SOUTH);

        //Error label
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(getMainFont(22));
        errorLabel.setForeground(new Color(225, 62, 62, 255));
        errorLabel.setPreferredSize(new Dimension(centerPanel.getWidth(), 50));
        JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBackground(bgColor);
        errorPanel.add(errorLabel);

        //Center panel components
        centerPanel.add(namesPanel);
        centerPanel.add(newNamePanel);
        centerPanel.add(errorPanel);

        centerPanel.setBackground(bgColor);
        newNamePanel.setBackground(bgColor);


        addButton.addActionListener(e -> {
            // Code to add players
            String newName = nameField.getText();
            if (newName.isBlank()) {
                errorLabel.setText("Invalid name");
            } else if (playerNames.contains(newName)) {
                errorLabel.setText("Name already in use");
            } else {
                playerNames.add(nameField.getText());
                startButton.setVisible(true);
                errorLabel.setText("");
                updatePlayersList(namesPanel, playerNames);
                nameField.setText("");
            }
        });

        startButton.addActionListener(e -> {
            // Code to start the game
            if (playerNames.isEmpty()) {
                errorLabel.setText("At least one player is required to start");
                return;
            }
            //Start game
            state.createStudents(playerNames);
            GUI.inGameView();   //And so it begins
        });
    }

    private static Font getMainFont(float fontSize) {
        File file = new File(resourcesPath + File.separator + "AGENCYR.TTF");
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading main font: " + e);
        }
        font = font.deriveFont(fontSize);
        return font;
    }

    private static void updatePlayersList(JPanel pane, List<String> names) {
        for (Component c : pane.getComponents())
            c.setVisible(false);
        pane.removeAll();

        for (String name : names) {
            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(getMainFont(24));
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setVisible(true);
            nameLabel.setPreferredSize(new Dimension(pane.getWidth(), 20));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel);
        }
        pane.revalidate();
        pane.repaint();
    }

    public static void inGameView() {
        //Clear the viewport
        for (Component c : frame.getContentPane().getComponents())
            c.setVisible(false);
        frame.getContentPane().removeAll();

        //menubar
        JMenuBar menuBar = getjMenuBar();
        frame.setJMenuBar(menuBar);

        //Create a label for the name
        nameLabel = new JLabel(getCurrentStudent().toString() + "'s turn", SwingConstants.CENTER);
        nameLabel.setFont(getMainFont(36));
        nameLabel.setForeground(Color.lightGray);
        frame.getContentPane().add(nameLabel, BorderLayout.NORTH);

        //room
        roomPanel = new JPanel();
        roomPanel.setBorder(new LineBorder(Color.BLACK, 5));
        roomPanel.setBackground(bgColor);
        roomPanel.setPreferredSize(roomSize);
        roomPanel.setLayout(null);
        frame.getContentPane().add(roomPanel, BorderLayout.CENTER);

        //inventory
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel inventoryLabel = new JLabel("Inventory", SwingConstants.LEFT);
        inventoryLabel.setFont(getMainFont(28));
        inventoryLabel.setForeground(Color.lightGray);
        inventoryLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        bottomPanel.add(inventoryLabel);

        JPanel inventorySlotsPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        inventorySlotsPanel.setBackground(bgColor);
        inventorySlotsPanel.setBorder(new EmptyBorder(0, 0, 0, 200));
        bottomPanel.add(inventorySlotsPanel);

        for (int i = 0; i < 5; i++) {
            JPanel inventorySlot = new JPanel();
            inventorySlot.setBorder(new LineBorder(Color.BLACK, 5));
            inventorySlot.setBackground(Color.lightGray);
            inventorySlot.setPreferredSize(new Dimension(100, 100));
            inventorySlotsPanel.add(inventorySlot);
            inventorySlots.add(inventorySlot);
        }
        endTurnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        endTurnButton.setBackground(Color.lightGray);
        endTurnButton.setOpaque(true);
        endTurnButton.setBorder(new LineBorder(Color.BLACK, 5));
        endTurnButton.setFont(getMainFont(28));
        endTurnButton.setPreferredSize(new Dimension(100, 50));
        endTurnButton.addActionListener(e -> next());
        bottomPanel.add(endTurnButton);

        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(bgColor);
        sidePanel.setPreferredSize(new Dimension(300, 500));
        frame.getContentPane().add(sidePanel, BorderLayout.EAST);

        //doors
        doorPanel = new JPanel();
        doorPanel.setLayout(new BoxLayout(doorPanel, BoxLayout.Y_AXIS));
        doorPanel.setBackground(bgColor);

        JScrollPane doorScrollPane = new JScrollPane(doorPanel);
        doorScrollPane.setPreferredSize(new Dimension(250, 400));
        doorScrollPane.setBackground(bgColor);
        sidePanel.add(doorScrollPane);

        update();
        frame.pack();
    }

    private static JMenuBar getjMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem quitMenuItem = new JMenuItem("Quit Game");

        // Add action listeners for menu items
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo save game
                GameSaver.saveGame(state, "savedgame.txt");
                System.out.println("savve");
            }
        });

        quitMenuItem.addActionListener(e -> System.exit(0));

        gameMenu.add(saveMenuItem);
        gameMenu.add(quitMenuItem);
        menuBar.add(gameMenu);
        return menuBar;
    }

    /**
     * Updates the GUI to reflect the current state of the game
     * Calls every views draw method
     */
    public static void update() {
        //update name
        if (Objects.equals(state.getFinalState(), "Game running")){
            Student currSt = getCurrentStudent();
            if (currSt != null)
                nameLabel.setText(currSt.toString() + "'s turn");
        }

        //clear room state
        roomPanel.setBackground(bgColor);

        //clear room items
        roomPanel.removeAll();
        roomPanel.revalidate();
        roomPanel.repaint();

        //clear inventory
        for (JPanel slot : inventorySlots) {
            slot.removeAll();
            slot.revalidate();
            slot.repaint();
        }
        itemsInInventory = 0;

        //clear doors
        doorPanel.removeAll();
        doorPanel.revalidate();
        doorPanel.repaint();

        state.updateObjects();
        state.removeDestroyedViews();

        for (View view : state.getViews()) {
            view.draw();
        }
    }

    /**
     * RoomView calls this function to update the room
     *
     * @param color the color to update the room to
     */
    public static void updateRoom(Color color) {
        roomPanel.setBackground(color);
        roomPanel.revalidate();
        roomPanel.repaint();
    }

    /**
     * ItemView and PersonView calls this function to add itself to the room.
     * The view is placed randomly in the room.
     *
     * @param view the view to be added to the room
     */
    public static void addToRoom(View view, int width, int height) {
        //randomly place the view in the room
        int x = random.nextInt(roomSize.width - width);
        int y = random.nextInt(roomSize.height - height);
        view.setBounds(x, y, width, height);

        roomPanel.add(view);
    }

    /**
     * ItemView calls this function to add itself to the inventory
     *
     * @param itemView the item to be added to the inventory
     */
    public static void addToInventory(ItemView itemView) {
        if (itemsInInventory >= inventorySlots.size()) {
            return;
        }
        inventorySlots.get(itemsInInventory).add(itemView);
        itemsInInventory++;
    }

    /**
     * DoorView calls this function to add itself to the doors
     *
     * @param doorView the door to be added to the doors
     */
    public static void addToDoors(DoorView doorView) {
        doorPanel.add(doorView);
    }

    public static void win() {
        roomPanel.removeAll();
        nameLabel.setText("The students won!");
        endTurnButton.setVisible(false);
        saveMenuItem.setVisible(false);

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    public static void lose() {
        roomPanel.removeAll();
        nameLabel.setText("The students lost!");
        endTurnButton.setVisible(false);
        saveMenuItem.setVisible(false);

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    public static void setState(GameState state) {
        GUI.state = state;
    }

    /**
     * Switches to the next student and updates the GUI
     */
    public static void next() {
        if (!gameEnded) {
            state.nextStudent();
            update();
        }
    }

    /**
     * Rescales icon to scale
     *
     * @param icon
     * @param scale
     * @return rescaled icon
     */
    public static ImageIcon rescaleIcon(ImageIcon icon, double scale) {
        int newWidth = (int) (icon.getIconWidth() * scale);
        int newHeight = (int) (icon.getIconHeight() * scale);
        Image img = icon.getImage();

        if (newWidth == 0 || newHeight == 0)
            return icon;

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
