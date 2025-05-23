/**
 * The {@code FocusSystemListener} interface defines a set of callback methods used by the {@link FocusSystem}
 * to communicate with other components, such as the GUI. Implementers of this interface can respond to events
 * like time updates, coin updates, lock status changes, timer completion, errors, and mute status changes.
 * This allows for decoupled communication between the focus system's logic and the user interface.
 */
public interface FocusSystemListener {

    /**
     * Called when the focus system updates the remaining time during a focus session.
     * Implementers can use this method to update time displays or progress bars.
     *
     * @param formattedTime The formatted time string representing the time left, typically in "MM:SS" format.
     */
    void onTimeUpdate(String formattedTime);

    /**
     * Called when the number of coins has changed.
     * This can occur when coins are earned or spent.
     *
     * @param coins The current total number of coins the user has.
     */
    void onCoinsUpdate(int coins);

    /**
     * Called when a feature or lock is updated, indicating whether it has been unlocked.
     * Implementers can use this to update the state of UI elements representing locked features.
     *
     * @param lockNumber  The identifier for the lock or feature (e.g., 1-9).
     * @param isUnlocked  {@code true} if the feature is now unlocked; {@code false} otherwise.
     */
    void onLockUpdate(int lockNumber, boolean isUnlocked);

    /**
     * Called when the countdown timer has finished, signaling the end of a focus session.
     * Implementers can use this to perform actions like rewarding the user or resetting the UI.
     */
    void onTimerFinished();

    /**
     * Called when an error or important message needs to be conveyed to the user.
     * This can be used for displaying alerts or logging errors.
     *
     * @param message A string describing the error or message.
     */
    void onError(String message);

    /**
     * Called when the mute status of the application's audio changes.
     * Implementers can update mute buttons or audio indicators accordingly.
     *
     * @param isMuted {@code true} if the audio is currently muted; {@code false} otherwise.
     */
    void onMuteStatusChanged(boolean isMuted);
}
