# MeaslyClock

An Android alarm-clock app built with Kotlin, Jetpack Compose, and Material 3.

## Concept

MeaslyClock is built around **alarm sets** — named collections of alarms that can be activated independently or cycled as a group:

- **Standalone sets** toggle `OFF → ON → OFF`.
- **Grouped sets** cycle through participating sets and `none` (e.g. `Office → Home Office → None`).
- The **dashboard** lists all sets with a two-button row layout (80% name button / 20% action button).
- Below each set row, today's active alarms are shown based on the set state and alarm weekday rules.

## Tech stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architecture:** ViewModel + StateFlow
- **minSdk:** 28 / **targetSdk:** 35
- **Build:** Android Gradle Plugin 8.7.3 + Gradle 8.9

## Building

### Prerequisites

- Android Studio Hedgehog or later (or Android SDK command-line tools)
- JDK 17+ (Android Studio bundles one; you do not need to install it separately)
- Internet access to resolve Google Maven dependencies

### Clone and build

```bash
git clone https://github.com/MorbusM59/MeaslyClock.git
cd MeaslyClock
./gradlew assembleDebug
```

The APK will be output to `app/build/outputs/apk/debug/app-debug.apk`.

## Running in the emulator (Android Studio)

If Android Studio hangs on **"Initializing Gradle Language Server"** or the
Gradle sync spinner never disappears, follow the steps below.

### 1. Open the project

1. Launch **Android Studio**.
2. Choose **File → Open…** and select the **`MeaslyClock`** root folder (the one that
   contains `settings.gradle.kts`).  Do **not** open the `app/` sub-folder.
3. Wait for the initial Gradle sync to finish (one-time download; ~2 min on a
   fast connection).

### 2. Fix a stuck "Initializing Gradle Language Server" spinner

If the spinner does not resolve within 3–5 minutes:

| Step | What to do |
|------|-----------|
| 1 | **File → Invalidate Caches…** → check all boxes → **Invalidate and Restart** |
| 2 | After restart, let indexing finish (bottom status bar) |
| 3 | If still stuck, open a terminal and run `./gradlew tasks` to verify the Gradle wrapper downloads correctly |

> **Root cause (now fixed):** The repository previously set
> `org.gradle.configuration-cache=true` in `gradle.properties`.  Configuration
> cache can cause the IDE's Gradle tooling-API to hang on the first sync when
> there is no pre-existing cache.  That flag has been removed.

### 3. Create or start an emulator

1. Open **Device Manager** (right toolbar or **View → Tool Windows → Device Manager**).
2. Click **＋ Create Virtual Device**.
3. Choose a phone profile (e.g. **Pixel 8**) → **Next**.
4. Download an API **28–35** system image (e.g. **API 35 "VanillaIceCream"**) → **Next** → **Finish**.
5. Press **▶** next to the new device to boot it.

### 4. Run the app

1. Select the **`app`** run configuration from the toolbar drop-down
   (the `.run/app.run.xml` file committed to this repo makes it available
   immediately without waiting for full project indexing).
2. Press **Shift+F10** (or the green **▶ Run** button).
3. Android Studio builds a debug APK and deploys it to the emulator.
4. The MeaslyClock dashboard appears — sample alarm sets are seeded automatically
   on the first launch.

## Project structure

```
app/src/main/java/com/measlyclock/
├── MainActivity.kt                       # Entry point
├── MeaslyClockApp.kt                     # Application class (DB + Repository init)
├── data/
│   ├── Models.kt                         # AlarmSet, Alarm, CycleGroup domain models
│   ├── Repository.kt                     # Data access layer (seeding, CRUD)
│   ├── SampleData.kt                     # Seed data for first launch
│   └── db/
│       ├── AppDatabase.kt                # Room database singleton
│       ├── Converters.kt                 # Type converters (Color, SetType, DayOfWeek)
│       ├── AlarmSetEntity.kt             # Room entity for alarm sets
│       ├── AlarmEntity.kt                # Room entity for individual alarms
│       ├── CycleGroupEntity.kt           # Room entity for cycle groups
│       ├── AlarmSetWithAlarms.kt         # @Relation join DTO
│       ├── AlarmSetDao.kt                # DAO: observe, upsert, delete, toggle active
│       ├── AlarmDao.kt                   # DAO: upsert, delete alarms
│       └── CycleGroupDao.kt              # DAO: observe, upsert, set active member
└── ui/
    ├── theme/                            # Material 3 theme (Color, Theme, Type)
    └── dashboard/
        ├── DashboardViewModel.kt         # State management (Room flows → StateFlow)
        ├── DashboardScreen.kt            # Dashboard + FAB + swipe-to-dismiss
        └── AddAlarmSetDialog.kt          # Dialog: name, color picker, type selector
```

## Current status

### Implemented
- Data models: `AlarmSet`, `Alarm`, `CycleGroup`, `SetType`
- Dashboard screen with 80/20 two-button row layout per alarm set
- Cycle logic: standalone (ON/OFF) and grouped (A/B/…/None)
- Per-set color support (displayed as button background)
- Active alarms list below each set (filtered by today's day of week)
- **Room database** persistence (alarm sets, alarms, cycle groups survive restarts)
- **Sample data seed** on first launch (Daily, Medication, Office, Home Office sets)
- **Add alarm set** via FAB → dialog (name + color picker + type selector)
- **Delete alarm set** via swipe-left gesture on any row

### Planned / TODO
- AlarmManager scheduling for actual alarm ringing
- BroadcastReceiver for alarm trigger
- Boot reschedule receiver
- Add/edit individual alarms within a set
- Calendar planner screen for scheduling sets by date
- Color picker UI improvements (custom color input)
- Snooze / dismiss flows
