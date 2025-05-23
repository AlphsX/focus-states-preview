/**
 * @details Pomodoro Timer App | Focus States © 2024
 * @author  Kongphob Khanisan / Chanon Doksanthia
 * @code    1660702109 / 1660702372
 * @version 1.0, 15/11/24
 * @since   1.0
 */
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

/**
 * The {@code FocusSystem} class manages the core functionality of the focus application.
 * It handles the countdown timer, audio playback, coin management, and communicates with the GUI
 * through the {@link FocusSystemListener} interface.
 */
public class FocusSystem {
    /** Initial time left in seconds (default is 45 minutes). */
    private int initialTimeLeft = 45 * 60; // 45 minutes in seconds

    /** Current time left in seconds. */
    private int timeLeft = 45 * 60;

    /** Number of coins the user has earned. */
    private int coins = 0;

    /** Total accumulated focus time in seconds. */
    private int accumulatedTime = 0;

    /** Number of completed focus sessions. */
    private int completedFocusCount = 0;

    /** Indicates whether the audio is muted. */
    private boolean isMuted = false;

    /** Array to track the unlocked features; index corresponds to feature number minus one. */
    private boolean[] isUnlocked = new boolean[9];

    /** The audio clip used for background music or sounds. */
    private Clip audioClip;

    /** The countdown timer for the focus session. */
    private javax.swing.Timer timer;

    /** Listener interface for communicating with the GUI components. */
    protected FocusSystemListener listener;
    
    /**
     * Constructs a new {@code FocusSystem} with the specified listener.
     *
     * @param listener The listener interface to communicate with the GUI.
     */
    public FocusSystem(FocusSystemListener listener) {
        this.listener = listener;
    }

    /**
     * Checks if the audio is currently muted.
     *
     * @return {@code true} if muted; {@code false} otherwise.
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Retrieves the current time left in the countdown timer.
     *
     * @return The time left in seconds.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

     /**
     * Starts the countdown timer for the focus session.
     * If the timer is already running, it does nothing.
     * Updates the GUI with the remaining time every second.
     * When the timer reaches zero, stops the timer, rewards coins,
     * updates accumulated time and completed focus count, and notifies the listener.
     */
    public void startCountdown() {
        if (timer != null && timer.isRunning()) return;

        timer = new javax.swing.Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                listener.onTimeUpdate(formatTime(timeLeft));
            } else {
                timer.stop();
                stopAudio();
                coins += 10; // Reward coins
                accumulatedTime += initialTimeLeft;
                completedFocusCount++;
                listener.onCoinsUpdate(coins);
                listener.onTimerFinished();
            }
        });
        timer.start();
    }

    /**
     * Stops the countdown timer if it is running.
     */
    public void stopCountdown() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Resets the countdown timer to the initial time left.
     * Stops any running timer and updates the GUI with the reset time.
     */
    public void resetTimer() {
        stopCountdown();
        timeLeft = initialTimeLeft;
        listener.onTimeUpdate(formatTime(timeLeft));
    }


    /**
     * Sets a new initial time for the focus session.
     * Updates the current time left and notifies the GUI.
     *
     * @param minutes The new initial time in minutes.
     */
    public void setInitialTime(int minutes) {
        initialTimeLeft = minutes * 60;
        timeLeft = initialTimeLeft;
        listener.onTimeUpdate(formatTime(timeLeft));
    }

    /**
     * Toggles the mute status of the audio.
     * If currently muted, unmutes by stopping the audio.
     * If not muted, mutes by playing the background sound.
     * Notifies the listener of the mute status change.
     */
    public void toggleMute() {
        if (isMuted) {
            stopAudio();
            isMuted = false;
            listener.onMuteStatusChanged(isMuted); 
        } else {
            playSound("musics/M5.wav");
            isMuted = true;
            listener.onMuteStatusChanged(isMuted); 
        }
    }

    /**
     * Plays a background sound from the specified file path.
     * Loops the audio continuously.
     *
     * @param filePath The path to the audio file.
     */
    private void playSound(String filePath) {
        try {
            if (audioClip != null && audioClip.isRunning()) {
                return;
            }

            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                listener.onError("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            listener.onError("Unable to play sound: " + e.getMessage());
        }
    }

    /**
     * Stops the background audio if it is playing.
     * Releases audio resources.
     */
    private void stopAudio() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
            audioClip = null;
        }
    }

    /**
     * Handles the break functionality during a focus session.
     * If the timer is running, stops the countdown and audio, and unmutes.
     * If the timer is not running, starts the countdown and mutes.
     * Notifies the listener of the mute status change.
     */
    public void handleBreak() {
        if (timer != null && timer.isRunning()) {
            stopCountdown();
            stopAudio();
            isMuted = false;
            listener.onMuteStatusChanged(isMuted); 
        } else {
            startCountdown();
            if (!isMuted) {
                playSound("musics/M5.wav");
                isMuted = true;
                listener.onMuteStatusChanged(isMuted); 
            }
        }
    }
    
    /**
     * Formats the time in seconds to a string in "MM:SS" format.
     *
     * @param seconds The time in seconds.
     * @return A formatted time string.
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        return String.format("%02d:%02d", minutes, seconds % 60);
    }

    /**
     * Attempts to unlock a feature identified by the lock number.
     * If the feature is already unlocked or the lock number is invalid, does nothing.
     * If the user has enough coins, deducts coins, unlocks the feature,
     * and notifies the listener.
     * If not enough coins, notifies the listener of the error.
     *
     * @param lockNumber The lock number of the feature (1-9).
     */
    public void unlockFeature(int lockNumber) {
        if (lockNumber < 1 || lockNumber > 9) return;

        int index = lockNumber - 1;
        if (isUnlocked[index]) {
            // Unlock
            return;
        }

        if (coins >= 10) {
            coins -= 10;
            listener.onCoinsUpdate(coins);
            isUnlocked[index] = true;
            listener.onLockUpdate(lockNumber, true);
        } else {
            listener.onError("Not enough coins!");
        }
    }
    
    /**
     * Retrieves the total accumulated focus time.
     *
     * @return The accumulated time in seconds.
     */
    public int getAccumulatedTime() {
        return accumulatedTime;
    }
    
    /**
     * Retrieves the number of completed focus sessions.
     *
     * @return The count of completed focus sessions.
     */
    public int getCompletedFocusCount() {
        return completedFocusCount;
    }
    
    /**
     * Retrieves the current number of coins the user has.
     *
     * @return The number of coins.
     */
    public int getCoins() {
        return coins;
    }
    
    /**
     * Checks if a feature identified by the lock number is unlocked.
     *
     * @param lockNumber The lock number of the feature (1-9).
     * @return {@code true} if the feature is unlocked; {@code false} otherwise.
     */
    public boolean isFeatureUnlocked(int lockNumber) {
        if (lockNumber < 1 || lockNumber > 9) return false;
        return isUnlocked[lockNumber - 1];
    }

    /**
     * The main method to launch the focus application.
     * Creates and displays the {@link FocusFrame}.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FocusFrame().setVisible(true));
    }
}
