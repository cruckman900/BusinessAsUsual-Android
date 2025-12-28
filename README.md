# Business As Usual — Mobile

Native Android + iOS implementation of the BAU platform using:

- Clean Architecture
- MVVM
- Jetpack Compose (Android)
- SwiftUI (iOS)
- Modular domain-driven design
- Shared design system across Web, Android, and iOS

# 🧱 Business As Usual — Clean Architecture (Android + Koin)

```
                   ┌──────────────────────────────────────────┐
                   │                  UI Layer                │
                   │        (Jetpack Compose + ViewModels)    │
                   └──────────────────────────────────────────┘
                                      │
                                      ▼
                   ┌──────────────────────────────────────────┐
                   │                App Module                │
                   │     - ViewModels                         │
                   │     - Navigation                         │
                   │     - Koin appModule                     │
                   └──────────────────────────────────────────┘
                                      │
                                      ▼
                   ┌──────────────────────────────────────────┐
                   │               Domain Layer               │
                   │     - Entities (Module, HrAction, etc.)  │
                   │     - Repository Interfaces              │
                   │     - Use Cases                          │
                   │     - Koin domainModule                  │
                   └──────────────────────────────────────────┘
                                      │
                                      ▼
                   ┌──────────────────────────────────────────┐
                   │                Data Layer                │
                   │     - Data Sources (Fake/Real)           │
                   │     - Repository Implementations         │
                   │     - Koin dataModule                    │
                   └──────────────────────────────────────────┘
                                      │
                                      ▼
                   ┌──────────────────────────────────────────┐
                   │             External Systems             │
                   │     - REST API (future)                  │
                   │     - Local DB (future)                  │
                   │     - Caching (future)                   │
                   └──────────────────────────────────────────┘
```

## 🔍 Layer Responsibilities

### **UI Layer**
- Jetpack Compose screens
- ViewModels (state holders)
- No business logic
- Talks only to **Use Cases**

### **Domain Layer**
- Pure Kotlin
- No Android dependencies
- Business rules
- Repository interfaces
- Use cases

### **Data Layer**
- Implements repository interfaces
- Provides data sources (Fake, Local, Remote)
- Converts raw data → domain models

### **Dependency Injection (Koin)**
- `appModule` → ViewModels
- `domainModule` → Use Cases
- `dataModule` → Repositories + Data Sources
