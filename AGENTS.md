# rokt-demo-android

## Project Overview

Rokt Demo Android is a sample Android application that showcases the Rokt mobile SDK's functionality. It demonstrates various placement examples across multiple pages, serving as both a showcase for the Rokt SDK features and an internal testing tool for the SDK team. The app is distributed via Firebase App Distribution.

Owned by **sdk-engineering** team. Resident experts: Thomson Thomas and James Newman.

## Architecture

The app follows the **MVVM (Model-View-ViewModel) + Repository** pattern with a single-Activity architecture:

```
┌─────────────────────────────────────────────────────┐
│  View Layer (Jetpack Compose)                       │
│  ┌──────────┐ ┌──────────┐ ┌───────┐ ┌──────────┐  │
│  │ Home     │ │ Demo     │ │Layouts│ │ Settings │  │
│  │          │ │ Library  │ │ (QR)  │ │          │  │
│  └────┬─────┘ └────┬─────┘ └───┬───┘ └────┬─────┘  │
│       │            │           │           │        │
│  ┌────▼────────────▼───────────▼───────────▼─────┐  │
│  │          ViewModels (Hilt-injected)           │  │
│  └────────────────────┬──────────────────────────┘  │
│                       │                             │
│  ┌────────────────────▼──────────────────────────┐  │
│  │   Repositories (About, DemoLibrary, Settings) │  │
│  └────────────────────┬──────────────────────────┘  │
│                       │                             │
│  ┌────────────────────▼──────────────────────────┐  │
│  │  RoktDemoService (Retrofit)                   │  │
│  │  Base URL: https://rokt-demo-app-server.rokt.com │
│  └───────────────────────────────────────────────┘  │
│                                                     │
│  ┌───────────────────────────────────────────────┐  │
│  │  Rokt SDK (RoktExecutor)                      │  │
│  │  - Rokt.init() called in MainActivity         │  │
│  │  - Rokt.execute() called via RoktExecutor     │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
```

**Key architectural decisions:**

- Single `MainActivity` hosts all Compose navigation (Home, Demo, Layouts, About, Settings)
- `MainActivityViewModel` holds the selected tag ID; `Rokt.init()` is called in `MainActivity` each time the tag ID changes (SDK requires an Activity reference)
- `RoktExecutor` centralizes all other Rokt SDK calls
- Data layer uses Retrofit to fetch demo library and about page content from the backend server
- DI via Dagger Hilt (`@HiltAndroidApp`, `@AndroidEntryPoint`, `ApplicationModule`)

## Tech Stack

| Component | Version / Detail |
|---|---|
| Language | Kotlin 1.8.21 |
| JVM Target | 11 |
| Android Min SDK | 21 |
| Android Target/Compile SDK | 35 |
| Gradle | 8.7 |
| Android Gradle Plugin | 8.6.0 |
| Java (CI) | 17 (Microsoft distribution) |
| UI | Jetpack Compose (compiler extension 1.4.7) |
| DI | Dagger Hilt 2.44.2 |
| Networking | Retrofit 2.9.0, OkHttp 4.9.0, Gson converter |
| Image Loading | Coil Compose 2.2.0 |
| QR Scanning | ML Kit barcode scanner 16.1.0 |
| Rokt SDK | `com.rokt:roktsdk:4.14.0` |
| Logging | Timber 4.7.1 |
| Linting | ktlint 0.40.0 (via `org.jlleitschuh.gradle.ktlint` plugin 10.0.0) |
| Testing | JUnit (AndroidX), MockK 1.11.0, Truth 1.1.2, Coroutines Test |
| Firebase | Firebase BOM 33.9.0 (App Distribution) |

## Development Guide

### Prerequisites

- Android Studio (latest version recommended)
- JDK 17
- Android SDK with API level 35 installed
- A physical device or Android emulator (API 21+)
- `google-services.json` file placed in `app/` (required for Firebase; not committed to repo)

### Quick Start

1. Open Android Studio
2. Open the project by selecting the project folder
3. Wait for Gradle sync to finish (may take a while on first run)
4. Select a physical or virtual device from the dropdown
5. Click the green run button

### Using Mock Data

In `ApplicationModule.kt`, change `provideDemoRepository` to return `DemoLibraryRepositoryMockImpl` and `provideAboutRoktRepository` to return `AboutRoktRepositoryMockImpl`.

### Previewing Layouts on Emulator

Use the deeplink scheme to preview mobile layouts:

```bash
adb shell am start -a android.intent.action.VIEW -d "rokt://demo/preview?config=<URL_ENCODED_JSON>"
```

The JSON data is generated from OnePlatform's layout preview QR code and includes fields like `tagId`, `previewId`, `versionId`, `creativeIds`, `language`, and `layoutVariantIds`.

## Build, Test & Lint Commands

| Command | Description |
|---|---|
| `./gradlew assembleDebug` | Build debug APK |
| `./gradlew assembleRelease` | Build release APK (requires signing config env vars) |
| `./gradlew test` | Run all unit tests |
| `./gradlew ktlintCheck` | Run ktlint linting |
| `./gradlew ktlintFormat` | Auto-fix ktlint issues |
| `./gradlew clean` | Clean build outputs |

## CI/CD Pipeline

CI/CD uses **GitHub Actions** with workflows defined in `.github/workflows/`:

| Workflow | Trigger | Description |
|---|---|---|
| `pull-request.yml` | Push to `main`, PRs | Runs ktlint, unit tests, and debug APK assembly (3 parallel jobs) |
| `release-from-main.yml` | Push to `main` | Builds release APK, uploads to Firebase App Distribution, creates GitHub release (skipped for SNAPSHOT versions) |
| `distribute-to-firebase.yml` | Manual dispatch | Builds release APK and uploads to Firebase App Distribution |
| `trigger-release-to-firebase.yml` | `repository_dispatch: release-build` | Updates Rokt SDK version in `gradle.properties` and `dependencies.kt`, creates a PR |
| `trigger-snapshot-to-firebase.yml` | `repository_dispatch: snapshot-build` | Updates SDK version inline (no PR), builds and uploads snapshot APK to Firebase |
| `pr-notification.yml` | PR opened/reopened | Notifies Google Chat via shared workflow |

### Composite Actions

- `.github/actions/setup-java/` — Sets up JDK 17 (Microsoft distribution)
- `.github/actions/build-apk/` — Configures Google Services, keystore, and builds release APK
- `.github/actions/upload-apk/` — Authenticates with Google Cloud (Workload Identity Federation) and uploads APK via Firebase CLI

### Release Process

1. Update `version_code` and `version_name` in `gradle.properties`
2. Commit and push to `main`
3. The `release-from-main.yml` workflow automatically builds, uploads to Firebase, and creates a GitHub release

For manual release: dispatch the `Distribute to Firebase` workflow from the Actions tab.

SDK version updates are automated via `repository_dispatch` events from the SDK release pipeline.

## Environment Variables

| Variable | Description | Used In |
|---|---|---|
| `STORE_PASSWORD` | Android keystore store password | Release signing (`app/build.gradle`) |
| `KEY_ALIAS` | Android keystore key alias | Release signing (`app/build.gradle`) |
| `KEY_PASSWORD` | Android keystore key password | Release signing (`app/build.gradle`) |
| `GOOGLE_SERVICES_JSON` | Base64-encoded `google-services.json` | CI workflows (decoded to `app/google-services.json`) |
| `ANDROID_KEYSTORE_FILE` | Base64-encoded keystore file | CI workflows (decoded to `app/keystore.jks`) |

## Project Structure

```
rokt-demo-android/
├── app/
│   ├── build.gradle                    # App module build config
│   ├── proguard-rules.pro              # ProGuard/R8 rules
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml     # App manifest, deeplink config
│       │   ├── java/com/rokt/roktdemo/
│       │   │   ├── MainActivity.kt         # Single activity, Rokt.init()
│       │   │   ├── MainActivityViewModel.kt # Tag ID management, SDK config
│       │   │   ├── RoktDemoApplication.kt  # Hilt app entry, Timber init
│       │   │   ├── data/                   # Repositories and services
│       │   │   │   ├── about/              # About Rokt data
│       │   │   │   ├── library/            # Demo library data
│       │   │   │   ├── service/            # RoktDemoService (Retrofit API)
│       │   │   │   ├── settings/           # SharedPreferences settings
│       │   │   │   └── validate/           # Input validation
│       │   │   ├── di/                     # Hilt DI modules
│       │   │   ├── model/                  # Data classes (AboutRokt, DemoLibrary)
│       │   │   ├── ui/                     # Compose UI
│       │   │   │   ├── home/               # Home screen
│       │   │   │   ├── demo/               # Demo library pages + RoktExecutor
│       │   │   │   ├── layouts/            # Layout preview + QR scanning
│       │   │   │   ├── about/              # About Rokt page
│       │   │   │   ├── settings/           # Settings page
│       │   │   │   ├── theme/              # App theme
│       │   │   │   └── common/             # Shared UI components
│       │   │   └── utils/                  # Extensions, image utilities
│       │   └── res/                        # Android resources
│       ├── debug/                          # Debug build variant overrides
│       └── test/                           # Unit tests
├── buildSrc/
│   └── src/main/java/dependencies.kt  # Centralized dependency versions
├── build.gradle                        # Root build config, ktlint plugin
├── settings.gradle                     # Module config, Maven repositories
├── gradle.properties                   # Version code/name, Gradle settings
├── gradle/wrapper/                     # Gradle wrapper (8.7)
├── .cortex/catalog/                    # Cortex service catalog metadata
├── .github/
│   ├── workflows/                      # CI/CD workflow definitions
│   ├── actions/                        # Composite actions (build-apk, setup-java, upload-apk)
│   ├── CODEOWNERS                      # @ROKT/sdk-engineering
│   └── pull_request_template.md        # PR template
└── LICENSE                             # Rokt SDK License 2.0
```

## Observability

- **Cortex tag:** `native-demo-android`
- **Service tier:** 3
- **On-call:** Mobile Integrations schedule (OpsGenie)
- **Dashboard:** [Mobile SDK Detailed Error View](https://rokt.datadoghq.com/dashboard/nsi-c8c-gtd)

## Maintaining This Document

When making changes to this repository that affect the information documented here
(build commands, dependencies, architecture, deployment configuration, etc.),
please update this document to keep it accurate. This file is the primary reference
for AI coding assistants working in this codebase.
