# CHANGES & Reorganization Notes

This file documents every change made to the original NetBeans project
**for GitHub submission purposes only**. The source code logic was NOT modified.

---

## What Was Changed

### 1. Repository Structure (New)
**Original:** All three `.java` files were flat inside `src/javafxapplication1/`.

**Changed to:** Files reorganized into separate package folders matching the
recommended structure from the course cover sheet:

```
src/
├── model/       → MyProcess.java      (was: src/javafxapplication1/MyProcess.java)
├── scheduler/   → Scheduler.java      (was: src/javafxapplication1/Scheduler.java)
├── gui/         → MainUI.java         (was: src/javafxapplication1/MainUI.java)
├── metrics/     → (reserved folder — metrics logic is inside Scheduler.java)
└── util/        → (reserved folder — no utility class needed currently)
```

> **Note:** The Java `package` declaration inside the files still reads
> `package javafxapplication1;` — this is correct for NetBeans compilation.
> The folder reorganization is for GitHub readability only.

---

### 2. Files Added (New — Did Not Exist in Original)

| File | Purpose |
|------|---------|
| `README.md` | Project description, requirements, build/run instructions, team info |
| `.gitignore` | Excludes compiled `.class` files, NetBeans build output, IDE folders |
| `test-cases/test-scenarios.md` | Four documented test scenarios with inputs and expected outputs |
| `screenshots/` | Placeholder folder — add screenshots before final submission |
| `assets/` | Placeholder folder — for any icons or resources |
| `CHANGES.md` | This file — documents all reorganization decisions |

---

### 3. Files Excluded from Repository

The following original files were excluded (via `.gitignore`) as they are
auto-generated and not part of the source submission:

- `build/` — compiled `.class` files
- `nbproject/private/` — local NetBeans configuration (machine-specific paths)
- `nbproject/Makefile-*.mk`, `nbproject/Package-*.bash` — generated build scripts

The following are **kept** because they are needed to open/build in NetBeans:
- `nbproject/project.xml`
- `nbproject/project.properties`
- `nbproject/build-impl.xml`
- `nbproject/jfx-impl.xml`
- `build.xml`
- `manifest.mf`

---

## What Was NOT Changed

- `MainUI.java` — zero logic changes; UI, event handlers, and Gantt drawing unchanged
- `Scheduler.java` — zero logic changes; RR, SJF Preemptive, SJF Non-Preemptive algorithms unchanged
- `MyProcess.java` — zero changes; data model and getters/setters unchanged

---

## Submission Checklist Status

| Item | Status | Notes |
|------|--------|-------|
| GitHub repository link | ☐ | Add after pushing |
| README included | ✅ | `README.md` created |
| Source code included | ✅ | All 3 `.java` files present |
| Run instructions | ✅ | In `README.md` |
| Screenshots | ☐ | Add to `screenshots/` before submitting |
| Test scenarios (3+) | ✅ | `test-cases/test-scenarios.md` has 4 scenarios |
| Project Submission Form | ☐ | Complete separately and attach to submission |
