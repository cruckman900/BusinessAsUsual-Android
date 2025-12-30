A guided overview for new contributors entering the BAU Mobile ecosystem
Welcome to Business As Usual Mobile — a modular, theme‑driven, multi‑tenant application built with Jetpack Compose, Material 3, and a clean, scalable architecture.
This summary gives new developers the context they need to understand the UI shell, theming system, navigation structure, and component patterns introduced in the v0.4.0 Renaissance update.

🏛️ Architecture Overview
BAU Mobile’s UI is built around three core pillars:
1. BAUScreenScaffold
   The universal layout wrapper for all screens.
   It provides:
- TopAppBar (themed)
- Action Bar integration (themed)
- Bottom breadcrumb bar (themed)
- Navigation drawer
- Theme drawer
- Safe insets + unified layout
- Content slot for screen-specific UI
  Every screen uses:
  BAUScreenScaffold(
  navController = navController,
  title = "Screen Title",
  onNavigate = onNavigate,
  themeName = themeName,
  darkTheme = darkTheme,
  onThemeChange = onThemeChange,
  onDarkThemeChange = onDarkThemeChange,
  breadcrumbs = listOf("Dashboard", "Module", "Detail")
  ) {
  // Screen content here
  }

This ensures consistency across the entire app.

🎨 2. Theme Engine
Themes live in:
ui/theme/ 
BAU.kt
Hazard.kt
Midnight.kt
Armada.kt
Ocean.kt
Steel.kt
Sunset.kt
Emerald.kt
Purple.kt
Brownstone.kt
ThemeRegistry.kt

Each theme file defines:
- Brand colors
- Light color scheme
- Dark color scheme
  ThemeRegistry.kt maps theme names to color schemes:

val colors = resolveTheme(themeName, darkTheme)

BAUTheme applies:
- MaterialTheme colorScheme
- Typography
- Shapes
- System UI styling (status bar + navigation bar)
  This ensures the entire app responds to theme changes instantly.

🧱 3. Navigation Structure
Navigation is powered by:
- NavHostController
- AppNavHost
- Route-based navigation
- Module-aware routing (e.g., module/hr, module/finance)
  Breadcrumbs are passed from each screen:
  breadcrumbs = listOf("Dashboard", "HR", "Employee Details")


And BAUScreenScaffold handles:
onBreadcrumbNavigate(index)


This creates a consistent, web-like navigation experience.

🧩 4. Component Library
All major components are theme-aware:
Cards
- Use surface and onSurface
- Primary-tinted icons
- Themed ripple using ripple()
- Optional outline borders
  Buttons
- Primary, outlined, and text variants use theme colors
  Drawers
- Navigation drawer and theme drawer use:
- surface
- onSurface
- surfaceVariant
- primary for selected states
  Dividers
- Use outline with reduced alpha
  Lists
- Background = colorScheme.background
- Items = colorScheme.surface
  Module Pages
- Use theme accents for headers and section titles

📱 5. System UI Integration
Using rememberSystemUiController():
- Status bar color = primary
- Navigation bar color = primary
- Icon contrast auto-calculated via luminance
  This ensures:
- Hazard → black icons
- Midnight → white icons
- Armada → white icons
- Ocean → white icons
- etc.
  The result is a fully immersive, branded experience.

🧩 6. Folder Structure (UI Layer)
ui/
components/
BAUScreenScaffold.kt
BAUTopBar.kt
BreadcrumbBar.kt
BAUNavigationDrawer.kt
ThemeDrawer.kt

dashboard/
DashboardScreen.kt

theme/
(all theme files)


This structure keeps:
- Shell components centralized
- Screens modular
- Themes isolated
- Navigation clean

🚀 7. How to Add a New Screen
- Create a new screen file in ui/<module>/
- Add a route in AppNavHost
- Wrap the screen in BAUScreenScaffold
- Provide breadcrumbs
- Use theme-aware components
  Example:
  BAUScreenScaffold(
  navController,
  title = "Finance",
  onNavigate,
  themeName,
  darkTheme,
  onThemeChange,
  onDarkThemeChange,
  breadcrumbs = listOf("Dashboard", "Finance")
  ) {
  FinanceContent()
  }



🧭 8. How to Add a New Theme
- Create a new file in ui/theme/
- Define light + dark color schemes
- Add it to ThemeRegistry.kt
- Add it to the ThemeDrawer list
  Done.

🧠 9. Mental Model for Contributors
Think of BAU Mobile as:
- A shell (BAUScreenScaffold)
- A theme engine (BAUTheme + ThemeRegistry)
- A navigation system (NavHost + breadcrumbs)
- A component library (cards, drawers, bars)
- A set of screens (Dashboard, HR, Finance, etc.)
  Each layer is cleanly separated and easy to extend.

🎉 You’re Ready to Contribute
With this summary, new developers can:
- Understand the architecture
- Navigate the codebase
- Add screens
- Add themes
- Extend components
- Maintain consistency
  This is the foundation of a scalable, multi-tenant BAU Mobile platform.


