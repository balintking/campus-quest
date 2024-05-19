package utility;

import characters.Student;
import views.View;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class GUI {
    private static GameState state;

    public static Student getCurrentStudent() {
        return state.getCurrentStudent();
    }

    public static JFrame frame;

    private static Color bgColor = new Color(40, 40, 40);

    private static String resourcesPath = "campus-quest" + File.separator + "resources";

    public static void initMenu() {
        frame = new JFrame("Campus Quest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(bgColor);
        frame.setSize(1280, 720);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 400, 100, 400));

        //Create a label for the title
        JLabel titleLabel = new JLabel("Campus Quest", SwingConstants.CENTER);
        titleLabel.setFont(getMainFont(72));
        titleLabel.setForeground(new Color(200, 200, 200, 255));
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
        loadButton.setBackground(Color.lightGray);
        loadButton.setBorder(new LineBorder(Color.BLACK, 5));
        loadButton.setFont(getMainFont(48));
        quitButton.setBackground(Color.lightGray);
        quitButton.setBorder(new LineBorder(Color.BLACK, 5));
        quitButton.setFont(getMainFont(48));

        //Add action listeners to the buttons
        newGameButton.addActionListener(e -> {
            // Code to start a new game
            newGame(panel);
        });

        loadButton.addActionListener(e -> {
            // Code to load game
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

        ArrayList<String> playerNames = new ArrayList<>();

        //Create a label for the title
        JLabel titleLabel = new JLabel("Choose Players", SwingConstants.CENTER);
        titleLabel.setFont(getMainFont(36));
        titleLabel.setForeground(new Color(200, 200, 200, 255));
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
        startButton.setBackground(Color.lightGray);
        startButton.setBorder(new LineBorder(Color.BLACK, 5));
        startButton.setFont(getMainFont(36));


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
                updateLabel(namesPanel, playerNames);
            }
        });

        startButton.addActionListener(e -> {
            // Code to start the game
            if (playerNames.isEmpty()) {
                errorLabel.setText("At least one player is required to start");
                return;
            }
        });
    }

    private static Font getMainFont(float fontSize) {
        File file = new File(resourcesPath + File.separator + "AGENCYR.TTF");
        Font font;
        System.out.println(file.getAbsolutePath());
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading main font: " + e);
        }
        font = font.deriveFont(fontSize);
        return font;
    }

    private static void updateLabel(JPanel pane, List<String> names) {
        for (Component c : pane.getComponents())
            c.setVisible(false);
        pane.removeAll();

        for (String name : names){
            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(getMainFont(24));
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setVisible(true);
            nameLabel.setPreferredSize(new Dimension(pane.getWidth(), 20));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pane.add(nameLabel);
        }
    }

    public static void update() {
        for (View v : state.getViews())
            v.draw();
    }

    public static void win() {
        // TODO
    }

    public static void lose() {
        // TODO
    }

    public static void setState(GameState state) {
        GUI.state = state;
    }

    public static void next() {
        Student s = state.nextStudent();
        // TODO
    }
}
