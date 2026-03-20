# Contributing to MeaslyClock

Welcome! This guide explains how to set up your development environment and contribute using **VS Code** and **GitHub Desktop**.

---

## Prerequisites

| Tool | Version | Notes |
|------|---------|-------|
| **Android Studio** | Hedgehog (2023.1) or later | Required to run/debug on device or emulator. VS Code handles editing. |
| **JDK** | 17 (Temurin recommended) | Required by the Android Gradle Plugin. |
| **VS Code** | Latest stable | Primary editor. |
| **GitHub Desktop** | Latest stable | Handles branches, commits, and pull requests. |
| **Git** | Latest | Installed automatically with GitHub Desktop. |

> **Android Studio vs VS Code:** You can write code in VS Code and use Android Studio only to run the emulator. Alternatively, open the project in Android Studio directly — both workflows work.

---

## One-time setup

### 1. Clone the repo

In **GitHub Desktop**: `File → Clone Repository → MorbusM59/MeaslyClock` and choose a local path.

Or via terminal:

```bash
git clone https://github.com/MorbusM59/MeaslyClock.git
cd MeaslyClock
```

### 2. Open in VS Code

```bash
code .
```

VS Code will prompt you to install the **recommended extensions** (from `.vscode/extensions.json`). Accept the prompt — they add Kotlin syntax highlighting, Gradle support, and XML editing.

### 3. Point Android Studio at the project

Open Android Studio → `Open an Existing Project` → select the `MeaslyClock` folder. Android Studio will:
- Download the Android Gradle Plugin and all dependencies (requires internet).
- Generate `local.properties` with your SDK path automatically.
- Sync the project.

> `local.properties` is excluded from Git (`.gitignore`) because the SDK path differs per machine.

### 4. Verify the build

In a terminal (or Android Studio's Terminal panel):

```bash
./gradlew assembleDebug
```

A successful build outputs the APK at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Day-to-day workflow

```
Edit code in VS Code  →  Build/run in Android Studio  →  Commit in GitHub Desktop  →  Push & open PR
```

### Step-by-step

1. **Create a branch** in GitHub Desktop: `Branch → New Branch` (name it `feature/your-feature` or `fix/your-fix`).

2. **Edit code** in VS Code. The Kotlin extension provides syntax highlighting and basic code intelligence.

3. **Build & run** in Android Studio (or `./gradlew assembleDebug` in the terminal).

4. **Run unit tests** locally before committing:
   ```bash
   ./gradlew test
   ```

5. **Commit** in GitHub Desktop: review changed files in the diff view, write a clear commit message, and click **Commit to `your-branch`**.

6. **Push** with the **Publish Branch** / **Push origin** button.

7. **Open a Pull Request** via the GitHub Desktop prompt or on GitHub.com. The CI workflow will automatically:
   - Run unit tests.
   - Build the debug APK.
   - Upload the APK as a downloadable artifact.

8. Once the CI is green and the PR is reviewed, **merge** into `main`.

---

## Project structure

```
MeaslyClock/
├── .github/
│   └── workflows/
│       └── android.yml          # GitHub Actions CI (build + test)
├── .vscode/
│   ├── extensions.json          # Recommended VS Code extensions
│   └── settings.json            # Editor settings for this project
├── app/
│   └── src/main/
│       ├── java/com/measlyclock/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   │   ├── Models.kt    # AlarmSet, Alarm, CycleGroup
│       │   │   └── SampleData.kt
│       │   └── ui/
│       │       ├── theme/       # Material 3 theme
│       │       └── dashboard/   # DashboardScreen + DashboardViewModel
│       └── res/                 # Android resources (strings, themes, icons)
├── gradle/
│   ├── libs.versions.toml       # Centralized dependency versions
│   └── wrapper/                 # Gradle wrapper (pin exact Gradle version)
├── build.gradle.kts             # Root build script
├── settings.gradle.kts          # Module declarations + repository config
└── README.md
```

---

## Code style

- **Language:** Kotlin (official code style — enforced by `kotlin.code.style=official` in `gradle.properties`).
- **Indentation:** 4 spaces (no tabs).
- **Line length:** 120 characters (shown as a ruler in VS Code).
- **Formatting:** `editor.formatOnSave` is enabled in `.vscode/settings.json`.

---

## CI / Continuous Integration

Every push and pull request triggers the **Android CI** workflow (`.github/workflows/android.yml`), which:

1. Checks out the code.
2. Sets up JDK 17.
3. Runs `./gradlew test` (unit tests).
4. Runs `./gradlew assembleDebug` (full build).
5. Uploads the debug APK as a GitHub Actions artifact (available for 14 days).

A green CI status is required before merging to `main`.

---

## Planned next features

See the [README](./README.md#planned--todo) for the feature backlog. Good first contributions:

- Add a simple alarm-set edit dialog (rename + color picker).
- Wire up Room for alarm persistence.
- Implement AlarmManager scheduling.
- Add a calendar planner screen.
