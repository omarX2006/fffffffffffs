# Test Cases – OS Scheduler: RR vs SJF

All test cases can be loaded directly from the application using the Scenario buttons (A–D),
or entered manually using the input panel.

---

## Test Case 1 – Scenario A: Mixed Burst Times (RR Quantum = 2)

### Input

| Process | Arrival Time | Burst Time |
|---------|-------------|------------|
| P1      | 0           | 5          |
| P2      | 1           | 3          |
| P3      | 2           | 8          |
| P4      | 3           | 2          |

**Time Quantum:** 2

### Expected Output

| Algorithm   | Avg WT | Avg TAT | Avg RT |
|-------------|--------|---------|--------|
| Round Robin | ~7.00  | ~11.25  | ~2.00  |
| SJF (SRTF)  | ~3.00  | ~7.25   | ~1.75  |

**Conclusion:** SJF Preemptive outperforms RR in this scenario due to shorter burst prioritization.

---

## Test Case 2 – Scenario B: Many Short Processes (RR Quantum = 2)

### Input

| Process | Arrival Time | Burst Time |
|---------|-------------|------------|
| P1      | 0           | 1          |
| P2      | 1           | 2          |
| P3      | 2           | 1          |
| P4      | 3           | 3          |
| P5      | 4           | 1          |

**Time Quantum:** 2

### Expected Output

| Algorithm   | Avg WT | Avg TAT | Avg RT |
|-------------|--------|---------|--------|
| Round Robin | ~2.40  | ~4.00   | ~1.20  |
| SJF (SRTF)  | ~1.60  | ~3.20   | ~1.20  |

**Conclusion:** Both perform similarly for short processes; SJF slightly better in WT.

---

## Test Case 3 – Scenario D: Long Job + Short Arrivals (RR Quantum = 3)

### Input

| Process | Arrival Time | Burst Time |
|---------|-------------|------------|
| P1      | 0           | 20         |
| P2      | 2           | 3          |
| P3      | 4           | 1          |
| P4      | 6           | 2          |

**Time Quantum:** 3

### Expected Output

| Algorithm   | Avg WT | Avg TAT | Avg RT |
|-------------|--------|---------|--------|
| Round Robin | ~8.25  | ~13.75  | ~2.25  |
| SJF (SRTF)  | ~2.00  | ~7.50   | ~0.75  |

**Conclusion:** SJF dramatically reduces waiting time for short jobs arriving later, while RR distributes time more evenly but at the cost of total efficiency.

---

## Test Case 4 – Scenario E: Validation Cases

This scenario tests input validation. The following inputs should produce error alerts:

| Invalid Input              | Expected Error Message                      |
|----------------------------|---------------------------------------------|
| Empty Process ID           | "Process ID is missing!"                    |
| Burst Time = 0             | "Burst Time must be greater than 0!"        |
| Arrival Time = -1          | "Arrival Time cannot be negative!"          |
| Duplicate Process ID (P1)  | "Duplicate Process ID!"                     |
| Quantum = 0                | "Quantum must be greater than 0!"           |
