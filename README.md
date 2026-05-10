# OS Scheduler: Round Robin vs SJF Comparison

A JavaFX desktop application that simulates and compares two CPU scheduling algorithms:
- **Round Robin (RR)** — time-sharing with configurable quantum
- **Shortest Job First (SJF)** — both Preemptive (SRTF) and Non-Preemptive modes

---

## Project Description

This project is an Operating Systems course submission that visualizes CPU scheduling using a graphical interface. Users can enter custom processes or load predefined test scenarios, then observe the Gantt charts and metrics (Waiting Time, Turnaround Time, Response Time) for both algorithms side by side, along with an automated analysis and conclusion.

---

## Team Information

| Field | Value |
|-------|-------|
| Course | Operating Systems |
| Project | CPU Scheduling Comparison |

---

## Requirements

- **Java** 11 or higher
- **JavaFX** 11 or higher (included in most NetBeans bundles)
- **NetBeans IDE** 12+ (recommended) — or any IDE with JavaFX support

---

## Build & Run Instructions

### Option 1: NetBeans IDE (Recommended)

1. Clone or download the repository.
2. Open NetBeans → **File → Open Project** → select the `JavaFXApplication1` folder.
3. Right-click the project → **Run** (or press `F6`).

### Option 2: Command Line (with JavaFX SDK)

```bash
# Set JAVAFX_HOME to your JavaFX SDK path
javac --module-path $JAVAFX_HOME/lib --add-modules javafx.controls,javafx.fxml \
  -d out src/gui/MainUI.java src/scheduler/Scheduler.java src/model/MyProcess.java

java --module-path $JAVAFX_HOME/lib --add-modules javafx.controls \
  -cp out gui.MainUI
```

---

## How to Use

1. **Add Processes manually** — enter Process ID, Arrival Time, and Burst Time, then click **Add Process**.
2. **Set Time Quantum** for Round Robin.
3. Click **Run Comparison & Analysis** to see results.
4. Use the **SJF: Preemptive / Non-Preemptive toggle** to switch SJF mode.
5. Use **Scenario A–D** buttons to load predefined test cases instantly.
6. Use **Scenario E** to test input validation.
7. Click **Reset** to clear everything.

---

## Project Structure

```
os-scheduler-project/
├── src/
│   ├── model/          → MyProcess.java       (process data model)
│   ├── scheduler/      → Scheduler.java       (RR, SJF-P, SJF-NP algorithms)
│   ├── gui/            → MainUI.java          (JavaFX UI, Gantt chart, tables)
│   ├── metrics/        → (metrics calculation is embedded in Scheduler.java)
│   └── util/           → (utility helpers, reserved for future use)
├── assets/             → icons and resources
├── screenshots/        → UI and Gantt chart screenshots
├── test-cases/         → documented test scenarios with expected output
├── README.md
└── .gitignore
```

---

## Screenshots

See the [`screenshots/`](screenshots/) folder for UI screenshots and Gantt chart examples.

---

## Test Scenarios

See the [`test-cases/`](test-cases/) folder for three documented scenarios with input and expected output.

---

## Algorithms Implemented

### Round Robin (RR)
- Processes executed in cyclic order with a fixed time quantum.
- Fair CPU distribution; suitable for interactive/multi-user systems.

### SJF Preemptive (SRTF)
- Always runs the process with the shortest **remaining** burst time.
- Optimal for minimizing average waiting time.

### SJF Non-Preemptive
- Selects the shortest burst process from available queue; runs to completion.
- No preemption once a process starts.
