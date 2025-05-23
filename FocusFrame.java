import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code FocusFrame} class is the main GUI frame for the Focus application.
 * It extends {@link JFrame} and implements {@link FocusSystemListener} to interact with the {@link FocusSystem}.
 * This class handles the user interface components, event handling, and updates from the FocusSystem.
 */
public class FocusFrame extends JFrame implements FocusSystemListener { // Listener
    // GUI Components

    /** Label to display the timer countdown. */
    private JLabel timerLabel;

    /** Label to display waiting or status messages. */
    private JLabel waitingLabel;

    /** Button to start the focus timer. */
    private JButton startButton, breakButton, cancelButton, focusRecapFrameButton, setButton;

    /** Panel to display a circular GIF animation. */
    private CircularGIFPanel gifPanel;

    /** Label to display the number of coins earned. */
    private JLabel coinsLabel;

    /** Button to mute or unmute the application sounds. */
    private JButton muteButton;

    /** Label to display total coins in the recap frame (if applicable). */
    private JLabel recapCoinsLabel;

    /** Slider to select the focus time duration. */
    private JSlider timeSlider;

    /** Button to confirm the selected focus time duration. */
    private JButton confirmButton;

    // System Instance

    /** Instance of the FocusSystem that handles the timer and logic. */
    private FocusSystem focusSystem;

    
    /**
     * Constructs a new {@code FocusFrame}, initializes the GUI components,
     * sets up the frame, and adds the necessary panels and buttons.
     */
    public FocusFrame() {
        focusSystem = new FocusSystem(this);
        setupFrame();
        addTopPanel();
        addCenterPanel();
        addExitButton();
    }

    /**
     * Sets up the main frame properties such as title, size, default close operation,
     * layout, background color, and icon image.
     */
    private void setupFrame() {
        setTitle("Focus States");
        setSize(430, 930);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#53c2ef"));

        Image icon = new ImageIcon("images/LOGO.png").getImage();
        setIconImage(icon);
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Updates the timer label with the formatted time string.
     *
     * @param formattedTime The formatted time string to display.
     */
    @Override
    public void onTimeUpdate(String formattedTime) {
        timerLabel.setText(formattedTime);
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Updates the timer label with the formatted time string.
     *
     * @param formattedTime The formatted time string to display.
     */
    @Override
    public void onCoinsUpdate(int coins) {
        this.coinsLabel.setText(String.format("%19d", coins)); // 
        if (recapCoinsLabel != null) {
            recapCoinsLabel.setText("Total Coins: " + coins);
        }
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Notifies when a lock is updated (not implemented in this class).
     *
     * @param lockNumber The lock number.
     * @param isUnlocked Whether the lock is unlocked.
     */
    @Override
    public void onLockUpdate(int lockNumber, boolean isUnlocked) {
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Called when the focus timer finishes.
     * Displays a message, resets the timer, updates the GIF, and adjusts button visibility.
     */
    @Override
    public void onTimerFinished() {
        JOptionPane.showMessageDialog(this, "You’ve Got \n+10 coins");
        focusSystem.resetTimer();
        gifPanel.setGif("gifs/waiting.gif");
        setButton.setEnabled(true);
        startButton.setVisible(true);
        breakButton.setVisible(false);
        cancelButton.setVisible(false);
        if (focusSystem.isMuted()) focusSystem.toggleMute();
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Handles error messages and updates the mute button image accordingly.
     *
     * @param message The error message or status update.
     */
    @Override
    public void onError(String message) {
        switch (message) {
            case "Muted":
                updateMuteButtonImage("images/Mute.png");
                break;
            case "Unmuted":
                updateMuteButtonImage("images/Unmute.png");
                break;
            case "Break":
                if (focusSystem.isMuted()) { // Use getter method
                    // Update mute button to "Unmute"
                    updateMuteButtonImage("images/Unmute.png");
                }
                break;
            default:
                // Handle generic error messages
                JOptionPane.showMessageDialog(this, message);
                break;
        }
    }
    
    /**
     * Callback method from {@link FocusSystemListener}.
     * Updates the mute button image based on the mute status.
     *
     * @param isMuted True if muted, false otherwise.
     */
    @Override
    public void onMuteStatusChanged(boolean isMuted) {
        if (isMuted) {
            updateMuteButtonImage("images/Mute.png");
        } else {
            updateMuteButtonImage("images/Unmute.png");
        }
    }
    
    /**
     * Toggles the mute status of the {@link FocusSystem}.
     * Updates the mute button image accordingly.
     */
    private void toggleMute() {
        focusSystem.toggleMute();
    }
    
    /**
     * Updates the mute button's image based on the provided image path.
     *
     * @param imagePath The path to the mute/unmute image.
     */
    private void updateMuteButtonImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        muteButton.setIcon(new ImageIcon(scaledImage));
        muteButton.setText("");
    }
    
    /**
     * Creates a {@link JButton} with an image icon, scaled to the specified dimensions.
     * The button is styled to be transparent with no border or focus painted.
     *
     * @param imagePath    The path to the image.
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
     * Adds the top panel to the frame, which includes the home button,
     * coins display, and mute button.
     */
    private void addTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#53c2ef"));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.decode("#53c2ef"));

        // Home button with image
        focusRecapFrameButton = createImageButton("images/Home.png", 60, 60);
        focusRecapFrameButton.addActionListener(e -> openFocusRecapFrame());
        leftPanel.add(focusRecapFrameButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.decode("#53c2ef"));
        rightPanel.setPreferredSize(new Dimension(150, 120));

        ImageIcon coinIcon = new ImageIcon("images/Coins.png");
        Image scaledCoinImage = coinIcon.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        coinIcon = new ImageIcon(scaledCoinImage);
        // format coinsLabel
        coinsLabel = new JLabel(String.format("%19d", focusSystem.getCoins()), coinIcon, JLabel.CENTER);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinsLabel.setForeground(Color.BLACK);
        coinsLabel.setHorizontalTextPosition(JLabel.CENTER);
        coinsLabel.setVerticalTextPosition(JLabel.CENTER);
        coinsLabel.setBounds(10, 10, 120, 40);

        rightPanel.add(coinsLabel);

        muteButton = createImageButton("images/Unmute.png", 70, 70);
        muteButton.addActionListener(e -> toggleMute());
        muteButton.setBounds(60, 60, 70, 70);
        rightPanel.add(muteButton);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
    }
    
    /**
     * Adds the center panel to the frame, which includes the GIF panel,
     * timer display, time slider, and control buttons.
     */
    private void addCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.decode("#53c2ef"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        addGifToCenterPanel(centerPanel);

        gbc.gridy = 0;
        gbc.weighty = 1;
        centerPanel.add(Box.createVerticalStrut(100), gbc);

        waitingLabel = new JLabel("Waiting...");
        waitingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        waitingLabel.setForeground(Color.decode("#ffffff"));
        gbc.gridy = 1;
        gbc.weighty = 0;
        centerPanel.add(waitingLabel, gbc);

        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        timerPanel.setBackground(Color.decode("#53c2ef"));

        // Use the public getter for timeLeft
        timerLabel = new JLabel(formatTime(focusSystem.getTimeLeft()), SwingConstants.CENTER);
        styleLabel(timerLabel, "#ffffff", "#53c2ef", 70);
        timerPanel.add(timerLabel);

        // Replace the "S" button with an image button
        setButton = createImageButton("images/SET.png", 60, 60);
        timerPanel.add(setButton);

        timeSlider = new JSlider(JSlider.HORIZONTAL, 1, 60, focusSystem.getTimeLeft() / 60);
        timeSlider.setBackground(Color.decode("#53c2ef"));
        timeSlider.setVisible(false);
        timeSlider.addChangeListener(e -> {
            int selectedMinutes = timeSlider.getValue();
            focusSystem.setInitialTime(selectedMinutes);
        });

        confirmButton = createImageButton("images/ConfirmBTN.png", 145, 45);
        confirmButton.setVisible(false);

        setButton.addActionListener(e -> {
            timeSlider.setVisible(true);
            confirmButton.setVisible(true);
            startButton.setVisible(false);
            setButton.setEnabled(false);
        });

        confirmButton.addActionListener(e -> {
            focusSystem.resetTimer();
            
            timeSlider.setVisible(false);
            confirmButton.setVisible(false);
            startButton.setVisible(true);
            setButton.setEnabled(true);
        });

        gbc.gridy = 2;
        centerPanel.add(timerPanel, gbc);

        gbc.gridy = 3;
        centerPanel.add(timeSlider, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.decode("#53c2ef"));

        startButton = createImageButton("images/StartBTN.png", 145, 45); // images/StartBTN.png
        cancelButton = createImageButton("images/CancelBTN.png", 145, 45); // images/CancelBTN.png
        breakButton = createImageButton("images/BreakBTN.png", 145, 45);

        breakButton.setVisible(false);
        cancelButton.setVisible(false);

        startButton.addActionListener(e -> handleStart());
        breakButton.addActionListener(e -> handleBreak());
        cancelButton.addActionListener(e -> handleCancel());

        buttonPanel.add(startButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(breakButton);

        gbc.gridy = 4;
        centerPanel.add(buttonPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Adds the GIF panel to the center panel.
     * Displays an animated GIF in a circular panel.
     *
     * @param centerPanel The center panel to which the GIF panel is added.
     */
    private void addGifToCenterPanel(JPanel centerPanel) {
        gifPanel = new CircularGIFPanel("gifs/waiting.gif");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        centerPanel.add(gifPanel, gbc);
    }
    
    /**
     * Adds an exit button to the frame.
     * When clicked, stops the countdown and exits the application.
     */

    private void addExitButton() {
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.setBackground(Color.decode("#53c2ef"));

        JButton exitButton = createButton("", "#ffffff", "#ffffff", 195, 9);
        exitButton.addActionListener(e -> {
            focusSystem.stopCountdown();
            System.exit(0);
        });

        exitPanel.add(exitButton);
        add(exitPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Handles the start button action.
     * Updates the UI components, starts the countdown, and toggles mute if necessary.
     */
    private void handleStart() {
        waitingLabel.setText("Under Construction...");
        gifPanel.setGif("gifs/penguin.gif");
        startButton.setVisible(false);
        breakButton.setVisible(true);
        cancelButton.setVisible(true);
        setButton.setEnabled(false);

        if (!focusSystem.isMuted()) { // Use getter method
            focusSystem.toggleMute();
        }

        focusSystem.startCountdown();
    }
    
    /**
     * Handles the break button action.
     * Updates the button image based on the mute status and handles the break in the FocusSystem.
     */
    private void handleBreak() {
        String imagePath;
        if (focusSystem.isMuted()) imagePath = "images/NextBTN.png";
        else imagePath = "images/BreakBTN.png";
        breakButton.setText(null); // Hide Text
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(145, 45, Image.SCALE_SMOOTH);
        breakButton.setIcon(new ImageIcon(scaledImage));
        focusSystem.handleBreak();
    }
    
    /**
     * Handles the cancel button action.
     * Resets the timer, updates the UI components, and toggles mute if necessary.
     */
    private void handleCancel() {
        if (focusSystem.isMuted()) {
            focusSystem.toggleMute(); // Using toggleMute adjust isMuted as false
        }
        focusSystem.resetTimer();
        gifPanel.setGif("gifs/waiting.gif");
        startButton.setVisible(true);
        breakButton.setVisible(false);
        cancelButton.setVisible(false);
        waitingLabel.setText("Waiting...");
        setButton.setEnabled(true);
    }
    
    /**
     * Opens the {@link FocusRecapFrame}, which provides a recap or summary.
     */
    private void openFocusRecapFrame() {
        FocusRecapFrame recapFrame = new FocusRecapFrame(this, focusSystem);
        recapFrame.setVisible(true);
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
    
    /**
     * Creates a {@link JButton} with specified text, background color, foreground color,
     * and dimensions. The button is styled with a specific font.
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
        return button;
    }
    
    /**
     * Styles a {@link JLabel} with specified foreground color, background color, and font size.
     *
     * @param label    The JLabel to style.
     * @param fgColor  The foreground color in hex format.
     * @param bgColor  The background color in hex format.
     * @param fontSize The font size.
     */
    private void styleLabel(JLabel label, String fgColor, String bgColor, int fontSize) {
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setOpaque(true);
        label.setBackground(Color.decode(bgColor));
        label.setForeground(Color.decode(fgColor));
    }

    // Focus System
}