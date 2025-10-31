# Focus States - Productivity Timer Application

**Focus States** is a Java-based desktop productivity application that implements a Pomodoro-style focus timer with gamification elements. The application helps users improve concentration through timed focus sessions while rewarding them with virtual coins that can be used to unlock additional features.

## 🌟 Key Features

- **📅 Customizable Timer**: Set focus sessions from 1-60 minutes (default: 45 minutes)
- **🎬 Visual Feedback**: Animated GIF displays showing current state (waiting/focusing)
- **🎵 Audio Management**: Background music with mute/unmute functionality
- **🪙 Gamification System**: Earn 10 coins for each completed session
- **🔓 Unlockable Features**: 9 different features that can be unlocked with earned coins
- **📊 Session Tracking**: Statistics for total time, completed sessions, and earned coins
- **⏸️ Break Functionality**: Pause and resume focus sessions
- **🎯 Clean GUI**: Intuitive Swing-based user interface

## 🛠️ Technology Stack

- **Language**: Java SE 8+
- **GUI Framework**: Java Swing
- **Audio**: javax.sound.sampled API
- **Development Environment**: BlueJ IDE compatible
- **Build System**: Standard Java compilation

## 🏗️ System Architecture

### Core Components

| Component | Responsibility | Key Methods |
|-----------|----------------|-------------|
| [`FocusSystem`](FocusSystem.java) | Business logic, timer, audio, coins | `startCountdown()`, `toggleMute()`, `unlockFeature()` |
| [`FocusFrame`](FocusFrame.java) | Main GUI window | `handleStart()`, `handleBreak()`, `handleCancel()` |
| [`FocusRecapFrame`](FocusRecapFrame.java) | Statistics and feature unlocking | `handleLockUnlock()`, statistics display |
| [`CircularGIFPanel`](CircularGIFPanel.java) | Visual feedback component | `setGif()`, `paintComponent()` |
| [`FocusSystemListener`](FocusSystemListener.java) | Observer pattern interface | Event callback methods |

### Design Patterns

- **Observer Pattern**: [`FocusSystemListener`](FocusSystemListener.java) interface enables loose coupling between business logic and GUI
- **Model-View-Controller**: Clear separation between [`FocusSystem`](FocusSystem.java) (Model), GUI classes (View), and event handlers (Controller)

## 🚀 Installation & Setup

### System Requirements

- **Java Version**: Java SE 8 or higher
- **Operating System**: Cross-platform (Windows, macOS, Linux)
- **Memory**: Minimum 256 MB RAM
- **Storage**: ~60 MB for application and assets

### Compilation & Running

#### Command Line
```bash
# Navigate to project directory
cd "Focus States x1-preview"

# Compile all Java files
javac *.java

# Run the application
java FocusSystem
```

#### BlueJ IDE
1. Open BlueJ IDE
2. Open project folder containing Java files
3. Right-click [`FocusSystem`](FocusSystem.java) class
4. Select "void main(String[] args)"

## 📖 Usage Guide

### Starting a Focus Session

1. **Launch** the application
2. **Optional**: Click **SET** button to adjust timer (1-60 minutes)
3. Click **START** button to begin focus session
4. Application displays focus animation and starts countdown
5. Background music plays automatically (unless muted)

### Managing Audio

- Click **Mute/Unmute button** (top-right) to toggle background music
- Audio automatically starts during focus sessions
- Mute state persists until manually changed

### Using Break Feature

- Click **BREAK** button during active session to pause
- Click **BREAK** button again to resume
- Or click **CANCEL** to abort session entirely

### Unlocking Features

1. Complete focus sessions to earn coins (10 coins per session)
2. Click **Home button** to access Focus Recap window  
3. Click any **Lock button** to unlock features (10 coins each)
4. Unlocked features display custom icons instead of lock symbol

### Viewing Statistics

- **Total Time**: Cumulative focus time across all completed sessions
- **Total Focus**: Number of successfully completed sessions  
- **Total Coins**: Current coin balance (earned minus spent)

## 📁 Project Structure

```
Focus States x1-preview/
├── FocusSystem.java          # Core business logic and main method
├── FocusFrame.java           # Main GUI window
├── FocusRecapFrame.java      # Statistics and unlocking window  
├── CircularGIFPanel.java     # Custom GIF display component
├── FocusSystemListener.java  # Observer interface
├── images/                   # UI buttons and icons (required)
│   ├── LOGO.png
│   ├── StartBTN.png
│   ├── BreakBTN.png
│   ├── CancelBTN.png
│   ├── Lock.png
│   ├── Unlock1-9.png
│   └── ...
├── gifs/                     # Animation assets (required)
│   ├── waiting.gif
│   └── penguin.gif
├── musics/                   # Audio files (required)
│   └── M5.wav
└── README.md                 # This file
```

### Asset Dependencies

⚠️ **Important**: The application requires specific asset files to function properly:

- **Images**: 22+ PNG files for buttons, icons, and unlockables
- **GIFs**: 2 animation files for visual feedback  
- **Audio**: 1 WAV file for background music
- **Missing Assets**: Application will display error messages if files not found

## 🎮 Gamification System

### Coin Economy

- **Earning Rate**: 10 coins per completed focus session
- **Spending**: 10 coins per feature unlock
- **Features**: 9 unlockable features (IDs 1-9)
- **Persistence**: In-memory storage (resets on application restart)

### Unlockable Features

| Feature ID | Unlock Cost | Visual Asset | Status |
|------------|-------------|--------------|--------|
| 1-9 | 10 coins each | `Unlock{ID}.png` | Tracked in boolean array |

## 🧪 Testing

### Manual Testing Checklist

#### Timer Functionality
- [ ] Timer displays correct initial value (45:00)
- [ ] Timer counts down every second when started
- [ ] Timer stops at 00:00 and shows completion message
- [ ] Custom time setting (1-60 minutes) works correctly
- [ ] Timer resets properly after completion or cancellation

#### Audio System
- [ ] Background music plays during focus sessions
- [ ] Mute button toggles audio on/off
- [ ] Audio stops when session ends or is cancelled
- [ ] Mute button icon updates correctly

#### UI Interactions  
- [ ] All buttons respond to clicks
- [ ] Button visibility changes correctly based on state
- [ ] GIF animations switch between waiting and focus states
- [ ] Coin counter updates when sessions complete
- [ ] Focus Recap window opens and closes properly

#### Feature Unlocking
- [ ] Lock buttons display correctly (locked/unlocked states)
- [ ] Unlock confirmation dialog appears
- [ ] Coins deduct properly when features unlocked
- [ ] Unlocked features show custom icons
- [ ] Insufficient coins shows error message

## ⚠️ Known Issues & Limitations

### Current Limitations

- **Data Persistence**: Application state resets on restart (coins, unlocked features)
- **Asset Dependencies**: Hard-coded file paths may cause issues if assets moved
- **Audio Format**: Limited to WAV format for background music
- **Single Session Type**: Only focus sessions, no break timer implementation

### Error Scenarios

- **Missing Assets**: Application displays error messages but continues running
- **Audio Initialization**: Fails gracefully if audio system unavailable
- **Timer Precision**: May drift slightly over very long sessions

## 🔮 Future Enhancements

- **Data Persistence**: Implement file-based or database storage
- **Multiple Timers**: Add break timer and long break functionality
- **Customization**: User-selectable themes, sounds, and animations
- **Statistics Enhancement**: Historical data tracking and charts
- **Notification System**: System notifications for session completion
- **Keyboard Shortcuts**: Hotkey support for common actions

## 👥 Authors

**Pomodoro Timer App | Focus States © 2024**
- **Kongphob Khanisan** (1660702109)
- **Chanon Doksanthia** (1660702372)
- **Version**: 1.0, 15/11/24

## 📄 License

This project is part of an academic assignment and is intended for educational purposes.

---

**Get focused, stay productive! 🎯**
