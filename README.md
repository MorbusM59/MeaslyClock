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
├── data/
│   ├── Models.kt                         # AlarmSet, Alarm, CycleGroup data models
│   └── SampleData.kt                     # In-memory sample data
└── ui/
    ├── theme/                            # Material 3 theme (Color, Theme, Type)
    └── dashboard/
        ├── DashboardViewModel.kt         # State management (StateFlow)
        └── DashboardScreen.kt            # Dashboard Composables
```

## Current status

### Implemented
- Data models: `AlarmSet`, `Alarm`, `CycleGroup`, `SetType`
- Dashboard screen with 80/20 two-button row layout per alarm set
- Cycle logic: standalone (ON/OFF) and grouped (A/B/…/None)
- Per-set color support (displayed as button background)
- Active alarms list below each set (filtered by today's day of week)
- In-memory sample data with 4 sets (Daily, Medication, Office, Home Office)

### Planned / TODO
- Room database persistence
- AlarmManager scheduling for actual alarm ringing
- BroadcastReceiver for alarm trigger
- Boot reschedule receiver
- Calendar planner screen for scheduling sets by date
- Add/edit alarm set and alarm screens
- Color picker UI for alarm sets
- Snooze / dismiss flows
