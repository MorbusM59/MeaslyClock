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
- JDK 11+
- Internet access to resolve Google Maven dependencies

### Clone and build

```bash
git clone https://github.com/MorbusM59/MeaslyClock.git
cd MeaslyClock
./gradlew assembleDebug
```

The APK will be output to `app/build/outputs/apk/debug/app-debug.apk`.

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
