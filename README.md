# 🧩 Minesweeper Java Game

## 📚 Course Project: Data Structures & Algorithms  
**Instructor**: Mr. Thai Trung Tin  
**School**: International University - Vietnam National University, HCM City  
**Semester**: II, 2024–2025  

---

## 👥 Team Members

| Name              | GitHub           | Student ID     | Contribution |
|-------------------|------------------|----------------|--------------|
| Phan Thế Thiện    | [SunSu150](https://github.com/SunSu150)         | ITDSIU20084     | 25%          |
| Phạm Quang Tường  | [qtuong180402](https://github.com/qtuong180402) | ITDSIU20108     | 25%          |
| Hồ Ngọc An        | [KyungUwU](https://github.com/KyungUwU)         | ITITIU21146     | 25%          |
| Hoàng Tuấn Kiệt   | [meiskiet](https://github.com/meiskiet)         | ITDSIU21055     | 25%          |

---

## 🎮 About the Game

Minesweeper is a classic single-player logic-based game. Players reveal squares on a grid, attempting to avoid hidden mines. Each revealed square may contain a number indicating how many mines surround it. The objective is to reveal all safe squares without detonating any bombs.

---

## 🚀 Features

- Follows classic Minesweeper rules with new UI elements.
- Multiple difficulty levels and custom mode.
- Flagging system for suspected bombs.
- Game timer with win/loss detection.
- Replay system to review player moves.
- Hidden Easter egg on game over prompt.
- Designed with modular OOP structure in Java.

---

## 🛠️ Technologies Used

- **Programming Language**: Java  
- **Libraries**:  
  - Java Swing (GUI components: `JFrame`, `JButton`, etc.)  
  - AWT Event Model (`ActionEvent`, `ActionListener`)  
- **Development Tools**: VS Code, NetBeans

---

## 🧱 System Architecture

| Class Name      | Responsibility |
|------------------|----------------|
| `StartWindow`    | Launches the game and main menu |
| `Menu`           | Handles difficulty selection |
| `GameBoard`      | Manages the grid and game logic |
| `GameSquare`     | Represents each square on the board |
| `SmartSquare`    | Handles right-click events |
| `FansyButton`    | Custom buttons with enhanced UI |
| `Bomb`           | Bomb placement logic |
| `ProduceBomb`    | Random bomb generation |
| `CheckSquare`    | Handles recursive reveal logic |
| `TimeChecker`    | Game timer tracking |
| `GameMove`       | Stores move history |
| `ReplayManager`  | Replays previous moves |

---

## 📋 Functional Requirements

- Start menu for game and difficulty selection.
- Grid with randomly placed bombs.
- Reveal/flag squares via left/right-click.
- Recursive reveal of blank zones.
- Timer starts at game start and stops on end.
- Win when all non-bomb squares are revealed.
- Lose when clicking on a bomb.

---

## 💡 Non-Functional Requirements

- Fast response time (< 100ms per interaction).
- Board setup within 1 second.
- Portable across OSes supporting Java (Windows/macOS/Linux).
- Clean, modular code with comments and reusable classes.

---

## 📈 Data Structures & Algorithms

- **Grid**: 2D array to represent board.
- **Recursion**: For revealing all contiguous empty squares.
- **Stack**: To manage game move history and replay.
- **Sorting algorithms**: Used for score or data management (optional).

---

## 📷 Demo

[📺 Insert screenshots or GIFs here]  
[🎬 You can also include a YouTube link if recorded]

---

## 🧪 Use Case Scenario

**Main Menu (`StartWindow`)**:
- Start Game → `Menu`
- Exit

**Menu**:
- Difficulty Selection: Beginner, Intermediate, Advanced, Custom
- Custom: Enter custom rows, columns, bombs
- Start Game → Launch `GameBoard`

---

## 🧠 Experience Gained

- Strengthened understanding of Java Swing & AWT.
- Applied DSA principles: recursion, stack, sorting.
- Improved teamwork, project planning, and Git collaboration.
- Practical application of modular design and OOP principles.

---

## 🔮 Future Improvements

- **Advanced UI**: Animations, improved visuals, smoother transitions.
- **High Score Tracker**: Save best times per difficulty.
- **Save & Load Replays**: Watch or share past games.
- **Mobile Portability**: JavaFX or Android support for broader reach.

---

## 🔗 References

- [Classic Minesweeper Rules](https://en.wikipedia.org/wiki/Minesweeper_(video_game))

---

## 📦 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-team/minesweeper-java.git
