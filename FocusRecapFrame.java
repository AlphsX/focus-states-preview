import javax.swing.*;
import java.awt.*;

/**
 * The {@code FocusRecapFrame} class represents a GUI window that displays a recap of the user's focus sessions.
 * It shows statistics like total time focused, total focus sessions completed, and total coins earned.
 * It also provides the functionality to unlock new features using earned coins by interacting with lock buttons.
 * This class is part of a focus management application and interacts with the {@link FocusSystem} to retrieve and update data.
 */
class FocusRecapFrame extends JFrame {
    /** The FocusSystem instance to interact with for data and operations. */
    private FocusSystem focusSystem;

    /** Label to display the total number of coins. */
    private JLabel recapCoinsLabel;

    /** Buttons representing features that can be unlocked. */
    private JButton lockButton1, lockButton2, lockButton3, 
                    lockButton4, lockButton5, lockButton6, 
                    lockButton7, lockButton8, lockButton9;
    
    /**
     * Constructs a new {@code FocusRecapFrame}, initializes GUI components, and sets up the frame layout.
     *
     * @param parent      The parent {@link JFrame} from which this frame is opened.
     * @param focusSystem The {@link FocusSystem} instance to interact with.
     */
    public FocusRecapFrame(JFrame parent, FocusSystem focusSystem) {
        this.focusSystem = focusSystem;
        setupFrame();
        addTopPanel();
        addCenterPanel();
    }

    /**
     * Sets up the main properties of the frame such as title, size, background color,
     * default close operation, layout, and icon image.
     */
    private void setupFrame() {
        setTitle("Focus Recap");
        setSize(430, 930);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#53c2ef"));
        setLayout(new BorderLayout());

        Image icon = new ImageIcon("images/LOGO.png").getImage();
        setIconImage(icon);
    }
    
    /**
     * Creates a {@link JButton} with an image icon, scaled to the specified dimensions.
     * The button is styled to be transparent with no border or focus painted.
     *
     * @param imagePath    The path to the image file.
     * @param buttonWidth  The desired button width.
     * @param buttonHeight The desired button height.
     * @return A {@link JButton} with the specified image and dimensions.
     */
    private JButton createImageButton(String imagePath, int buttonWidth, int buttonHeight) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton button = new JButton(scaledIcon);
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        return button;
    }
    
    /**
     * Creates a {@link JButton} with specified text, background color, foreground color,
     * and dimensions. The button is styled with a specific font and tooltip text.
     *
     * @param text    The text to display on the button.
     * @param bgColor The background color in hex format.
     * @param fgColor The foreground color in hex format.
     * @param width   The desired button width.
     * @param height  The desired button height.
     * @return A {@link JButton} with the specified properties.
     */
    private JButton createButton(String text, String bgColor, String fgColor, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode(bgColor));
        button.setForeground(Color.decode(fgColor));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(width, height));
        button.setToolTipText(text);
        return button;
    }
    
    /**
     * Adds the top panel to the frame, which includes the back button.
     * The back button allows the user to close this frame and return to the parent frame.
     */
    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.decode("#53c2ef"));

        JButton backButton = createImageButton("images/BACK.png", 60, 60);
        backButton.addActionListener(e -> this.dispose());
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
    }
    
    /**
     * Adds the center panel to the frame, which includes the lock buttons to unlock features,
     * and the recap information such as total time, total focus sessions, and total coins.
     * Also adds an exit button at the bottom of the panel.
     */
    private void addCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.decode("#53c2ef"));

        JPanel topSplitPanel = new JPanel();
        topSplitPanel.setBackground(Color.decode("#53c2ef"));
        topSplitPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Lock Button 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        lockButton1 = createImageButton(
                focusSystem.isFeatureUnlocked(1) ? "images/Unlock1.png" : "images/Lock.png",
                100, 100);
        lockButton1.addActionListener(e -> handleLockUnlock(1, lockButton1));
        topSplitPanel.add(lockButton1, gbc);

        // Lock Button 2
        gbc.gridx = 1;
        gbc.gridy = 0;
        lockButton2 = createImageButton(
                focusSystem.isFeatureUnlocked(2) ? "images/Unlock2.png" : "images/Lock.png",
                100, 100);
        lockButton2.addActionListener(e -> handleLockUnlock(2, lockButton2));
        topSplitPanel.add(lockButton2, gbc);

        // Lock Button 3
        gbc.gridx = 2;
        gbc.gridy = 0;
        lockButton3 = createImageButton(
                focusSystem.isFeatureUnlocked(3) ? "images/Unlock3.png" : "images/Lock.png",
                100, 100);
        lockButton3.addActionListener(e -> handleLockUnlock(3, lockButton3));
        topSplitPanel.add(lockButton3, gbc);

        // Lock Button 4
        gbc.gridx = 0;
        gbc.gridy = 1;
        lockButton4 = createImageButton(
                focusSystem.isFeatureUnlocked(4) ? "images/Unlock4.png" : "images/Lock.png",
                100, 100);
        lockButton4.addActionListener(e -> handleLockUnlock(4, lockButton4));
        topSplitPanel.add(lockButton4, gbc);

        // Lock Button 5
        gbc.gridx = 1;
        gbc.gridy = 1;
        lockButton5 = createImageButton(
                focusSystem.isFeatureUnlocked(5) ? "images/Unlock5.png" : "images/Lock.png",
                100, 100);
        lockButton5.addActionListener(e -> handleLockUnlock(5, lockButton5));
        topSplitPanel.add(lockButton5, gbc);

        // Lock Button 6
        gbc.gridx = 2;
        gbc.gridy = 1;
        lockButton6 = createImageButton(
                focusSystem.isFeatureUnlocked(6) ? "images/Unlock6.png" : "images/Lock.png",
                100, 100);
        lockButton6.addActionListener(e -> handleLockUnlock(6, lockButton6));
        topSplitPanel.add(lockButton6, gbc);

        // Lock Button 7
        gbc.gridx = 0;
        gbc.gridy = 2;
        lockButton7 = createImageButton(
                focusSystem.isFeatureUnlocked(7) ? "images/Unlock7.png" : "images/Lock.png",
                100, 100);
        lockButton7.addActionListener(e -> handleLockUnlock(7, lockButton7));
        topSplitPanel.add(lockButton7, gbc);

        // Lock Button 8
        gbc.gridx = 1;
        gbc.gridy = 2;
        lockButton8 = createImageButton(
                focusSystem.isFeatureUnlocked(8) ? "images/Unlock8.png" : "images/Lock.png",
                100, 100);
        lockButton8.addActionListener(e -> handleLockUnlock(8, lockButton8));
        topSplitPanel.add(lockButton8, gbc);

        // Lock Button 9
        gbc.gridx = 2;
        gbc.gridy = 2;
        lockButton9 = createImageButton(
                focusSystem.isFeatureUnlocked(9) ? "images/Unlock9.png" : "images/Lock.png",
                100, 100);
        lockButton9.addActionListener(e -> handleLockUnlock(9, lockButton9));
        topSplitPanel.add(lockButton9, gbc);

        centerPanel.add(topSplitPanel, BorderLayout.CENTER);

        // ตัวRecap สถิติ
        JPanel bottomSplitPanel = new JPanel();
        bottomSplitPanel.setBackground(Color.WHITE);
        bottomSplitPanel.setLayout(new BorderLayout());

        JPanel recapInfoPanel = new JPanel(new GridBagLayout());
        recapInfoPanel.setBackground(Color.WHITE);

        JLabel totalTimeLabel = new JLabel("Total Time: " + formatTime(focusSystem.getAccumulatedTime()), SwingConstants.LEFT);
        totalTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalTimeLabel.setForeground(Color.BLACK);

        JLabel totalFocusLabel = new JLabel("Total Focus: " + focusSystem.getCompletedFocusCount(), SwingConstants.LEFT);
        totalFocusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalFocusLabel.setForeground(Color.BLACK);

        recapCoinsLabel = new JLabel("Total Coins: " + focusSystem.getCoins(), SwingConstants.LEFT);
        recapCoinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        recapCoinsLabel.setForeground(Color.BLACK);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.anchor = GridBagConstraints.WEST;

        recapInfoPanel.add(totalTimeLabel, gbc);
        gbc.gridy++;
        recapInfoPanel.add(totalFocusLabel, gbc);
        gbc.gridy++;
        recapInfoPanel.add(recapCoinsLabel, gbc);

        bottomSplitPanel.add(recapInfoPanel, BorderLayout.CENTER);

        centerPanel.add(bottomSplitPanel, BorderLayout.SOUTH);

        // ปุ่มExit
        JButton exitButton = createButton("", "#000000", "#000000", 195, 9);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitButtonPanel.setBackground(Color.WHITE);
        exitButtonPanel.add(exitButton);

        bottomSplitPanel.add(exitButtonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Handles the action of unlocking a feature when a lock button is clicked.
     * Checks if the feature is already unlocked and if the user has enough coins,
     * then proceeds to unlock the feature by updating the icon and deducting coins.
     * Updates the recap coins label and handles any errors during image loading.
     *
     * @param lockNumber The number identifying the feature to unlock.
     * @param lockButton The button associated with the feature.
     */
    private void handleLockUnlock(int lockNumber, JButton lockButton) {
        if (focusSystem.isFeatureUnlocked(lockNumber)) {
            JOptionPane.showMessageDialog(this, "Feature " + lockNumber + " is already unlocked.");
            return;
        }

        if (focusSystem.getCoins() >= 10) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to unlock? This will cost 10 coins.",
                    "Confirm Unlock", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                focusSystem.unlockFeature(lockNumber);
                recapCoinsLabel.setText("Total Coins: " + focusSystem.getCoins());

                try {
                    String unlockImagePath = "images/Unlock" + lockNumber + ".png";
                    ImageIcon originalIcon = new ImageIcon(unlockImagePath);

                    if (originalIcon.getIconWidth() == -1) {
                        throw new Exception("Image not found or corrupted: " + unlockImagePath);
                    }

                    Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    lockButton.setIcon(scaledIcon);
                    lockButton.setText(""); 
                } catch (Exception e) {
                    System.err.println("Error setting unlock image: " + e.getMessage());
                    lockButton.setText("Unlocked");
                }

                lockButton.revalidate();
                lockButton.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough coins!");
        }
    }
    
    /**
     * Formats the time in seconds into a string of format "MM:SS".
     *
     * @param seconds The time in seconds.
     * @return A formatted time string "MM:SS".
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        return String.format("%02d:%02d", minutes, seconds % 60);
    }
}
