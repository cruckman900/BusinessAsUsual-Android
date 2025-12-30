# Changelog

## [0.4.0] — The Great BAU Mobile Renaissance
### Added
- Full multi-theme engine with 10 expressive themes:
    - BAU, Hazard, Midnight, Armada, Ocean, Steel, Sunset, Emerald, Purple, Brownstone
    - Light and dark variants for each theme
- Unified header system:
    - Action Bar, Status Bar, and TopAppBar now share a seamless primary background
    - Dynamic runtime theming for Action Bar logo/title
    - Removed elevation seams and visual breaks
- Themed Android system navigation bar (gesture bar + icons)
- Bottom breadcrumb bar:
    - Fully theme-aware
    - Clickable breadcrumb navigation
    - Matches top bar for a unified brand band
- Navigation drawer:
    - Themed surfaces, icons, and selected states
    - Integrated with new theme engine
- Theme drawer:
    - Updated to use surface/onSurface colors
    - Supports dark mode toggle and theme switching
- Card component overhaul:
    - Surface-aware backgrounds
    - Primary-tinted icons
    - OnSurface text with proper contrast
    - Themed ripple using new Material 3 ripple API
    - Outline borders for dark themes
- Global background theming:
    - All screens now use colorScheme.background
    - Module pages and lists updated for consistency
- Button theming:
    - Primary, outlined, and text buttons now use theme colors
- Divider theming:
    - Uses outline color with reduced alpha for subtle separation

### Changed
- Rebuilt BAUScreenScaffold:
    - Added breadcrumb support
    - Added theme-aware bottom bar
    - Removed bottom inset gutter
    - Improved navigation logic
- Moved Action Bar theming into Compose-safe LaunchedEffect
- Updated ripple system to use new Material 3 ripple() API

### Fixed
- Inconsistent bottom gutter under bottom bar
- Action Bar background not filling full width
- Deprecated ripple API warnings
- Drawer content using default Material colors

### Removed
- Legacy ripple implementation
- Hardcoded card colors and elevations