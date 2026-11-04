# Meetup UI/UX clone — full extraction

Source: `meetup-social-events-groups.apkm` (APKMirror bundle, 125 MB)
Package `com.meetup` · versionName `2026.10.26.3095` · versionCode `3095` · minSdk 24 · targetSdk 36

Two deliverables here:

1. **`export/`** — every artefact pulled out of the APK, structured for design work.
2. **`clone-android/`** — a working Jetpack Compose app that rebuilds the four root tabs, event detail, and the design system, using the extracted tokens verbatim.

---

## What was done

| Step | Tool | Result |
|---|---|---|
| Unpack `.apkm` (zip of APKs) | python `zipfile` | `raw/` — 1 base + 36 config splits |
| Decode resources + manifest | apktool 2.11.1 | `apktool/base/` + 36 `apktool/split_*` |
| Decompile to Java | jadx 1.5.1 | `jadx/base/sources/` — 68k Java files, 3.6k under `com.meetup` |
| Smoke-test on device | adb, SM-G975U | installed, launched, confirmed running |
| Token extraction | python | `export/design/*.json` |
| Asset harvest | python + robocopy | `export/assets/` |

apktool gave 408 layouts, 618 drawables, 80 `res/` bucket variants, 102,355 smali classes across 13 dex.
jadx gave readable structure under `com.meetup` but mangled names everywhere else (`a0`, `zq`, …) — R8 with package renaming, so the app's own packages survived by design, SDKs did not.

---

## `export/` — layout

```
export/
├── DESIGN-SPEC.md              ← read this first. colors, type, spacing, nav, stack.
├── design/
│   ├── tokens.json             24 semantic color tokens, light + dark, with source name
│   ├── typography.json         Meetup.Text.* ladder fully resolved (parent chain walked)
│   ├── dimens_tokens.json      33 spacing/size tokens
│   ├── components.json         43 own component styles with every item
│   ├── palette.csv             token,light,dark
│   ├── colors_light.xml        verbatim values/colors.xml
│   ├── colors_dark.xml         verbatim values-night/colors.xml
│   ├── styles.xml              verbatim values/styles.xml (1542 styles)
│   ├── dimens.xml              verbatim values/dimens.xml
│   ├── strings.xml             verbatim values/strings.xml (3,795 strings)
│   ├── attrs.xml               custom attrs
│   ├── ids.xml                 own ids
│   ├── ids_public.csv          full 1,808 public id table
│   └── dependencies.tsv        161 artifacts + versions from META-INF/*.version
├── ui/
│   ├── layouts/                408 layout XMLs
│   ├── navigation/             13 nav graphs
│   ├── navigation_graphs.json  fragments, args, actions, per graph
│   ├── fonts/                  36 font files (Graphik, Grandstander, Neue Sans, Pacifico)
│   ├── anim/ animator/ interpolator/ menu/
├── manifest/
│   ├── AndroidManifest.xml     decoded
│   ├── components_full.json    217 components with intent filters
│   ├── screens.json             activities + aliases with theme/label
│   ├── permissions.txt         32
│   ├── deeplinks.json          18 entries, scheme+host+path+target
│   └── components.tsv
├── assets/
│   ├── icons_vector/           138 own vector icons
│   ├── drawable/               618 drawables
│   ├── drawable-xxhdpi/
│   ├── mipmap-*/               112 launcher variants + night
│   ├── lottie/                 confetti.lottie
│   ├── reactions_sample.json   Meetup reaction emoji set
│   ├── app_assets/             full assets/ tree
│   └── native_libs/            arm64 .so files
└── strings/
    ├── _index.tsv              404 prefixes by count
    └── *.json                  170 per-feature string bundles
```

### Headline numbers

- **Brand color is `#008294`** (`palette_viridian`), not the red you'd guess from the logo. `#F65858` peach is the SignUp CTA and the app-icon background.
- **Type is Graphik** at 16 / 14 / 12 body and 32 / 20 / 16 title. `lineSpacingExtra` 6sp on body, multiplier 1.2 on titles.
- **Spacing ladder is named**: xtiny 6, tiny 8, small 12, middling 14, normal 16, stripe 24, table 32, large 40, xlarge 80.
- **Four root tabs**: Home, Explore, Notifications, Messages. Confirmed from the `RootTab` enum in decompiled source, not guessed.
- **Cards are flat.** 1dp `#E5E5EA` hairline, 8dp radius, zero elevation. Elevation only on the sticky RSVP bar.
- **The whole shell is Compose** — `activity_root.xml` is a `FrameLayout` with two `ComposeView`s anchored bottom (`@id/bottom_nav_compose`, `@id/compose_view`). Fragments underneath are legacy.

---

## `clone-android/` — the Compose rebuild

```
clone-android/
├── app/src/main/java/com/clone/meetup/
│   ├── MainActivity.kt                     NavHost + root shell, 5 routes
│   ├── nav/MeetupBottomBar.kt              RootTab enum mirrors the real one, 4 tabs
│   ├── ui/theme/Theme.kt                   tokens + typography + spacing + shapes, from the APK
│   ├── ui/components/Core.kt               Button, Avatar, Image, Chip, Badge, Divider, SectionHeader
│   ├── ui/components/Cards.kt              EventCard, EventRow, YourGroupCard, GroupRow, CategoryTile
│   ├── ui/screens/OnboardingScreen.kt      3-page pager + interest picker
│   ├── ui/screens/AuthScreen.kt            native email/password form (real app is a WebView)
│   ├── ui/screens/HomeScreen.kt            app bar, going rail, groups rail, start-a-group, feed
│   ├── ui/screens/ExploreScreen.kt         search + filter chips + category tiles + 2-col feed
│   ├── ui/screens/MessagesScreen.kt        messages + notifications lists
│   ├── ui/screens/GroupTimelineScreen.kt   cover, Join, 5 tabs (Upcoming..Members)
│   ├── ui/screens/EventDetailScreen.kt     hero, meta rows, about, attendees, sticky RSVP bar
│   └── data/SampleData.kt                  seeded content
└── app/src/main/res/
    ├── font/                        Graphik regular/medium/semibold/bold + Grandstander
    ├── drawable/                    138 vector icons from the APK
    ├── mipmap-*/                    launcher icons from the APK
    ├── values/  values-night/       palette, theme, icon_colors.xml, attrs.xml
```

### Routes

| Route | Screen | Transition |
|---|---|---|
| `onboarding` (start) | OnboardingScreen | fade |
| `auth` | AuthScreen | fade |
| `root` | 4-tab shell | fade |
| `event/{eventId}` | EventDetailScreen | slide 260/240 ms |
| `group/{groupId}` | GroupTimelineScreen | slide 260/240 ms |

Verified on device: 11 screenshots in `export/screenshots/`, index in that folder's README.
Build: `BUILD SUCCESSFUL`, APK 20 MB, `adb install` clean, zero runtime crashes across every route.

### Two adaptations worth knowing

- **Icons.** The 138 harvested `ic_*` vectors reference `?colorControlNormal`, `?colorSecondary`,
  `@color/m3_chip_*`, `@color/enable_or_disable_color_selector` and a few Meetup aliases. The real
  app inherits those from AppCompat/MaterialComponents; this clone is standalone, so they are
  declared in `res/values/attrs.xml` + `res/values/icon_colors.xml` rather than editing the extracted
  XML. The icon files stay byte-identical to the APK.
- **Images.** No bundled photography ships (nothing licensed to redistribute), so every image is a
  remote URL. `MeetupImage` paints a `palette_viridian` → `palette_indigo` gradient behind the slot,
  seeded by URL hash, so the layout reads correctly with or without network. The test device was
  offline — Coil never resolved — so the gradients are what the screenshots show.
- **JDK.** Build needs JDK 17–21. Android Studio's bundled JBR is 25.0.3 and breaks the Kotlin
  compiler with `IllegalArgumentException: 25.0.3`; set `JAVA_HOME=C:\Java\jdk-21`.

### Build

```powershell
cd "E:\Clone UIUX\clone-android"
.\gradlew.bat assembleDebug
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Stack matches the real app as closely as it matters: AGP 8.13, Kotlin 2.2.20, Compose 1.10.4 BOM-equivalent pins, Material3 1.4.0, Navigation 2.9.7, Coil for images. `compileSdk 36`, `minSdk 24` — same as the APK.

### Fidelity notes

- Colors, type sizes, spacing, radii, avatar sizes, tab bar height — all copied from `res/values`, none invented.
- `MeetupSpacing` reproduces the real names (`xtiny/tiny/small/middling/normal/stripe/table/large/xlarge`) so a designer reading the spec and a dev reading the code use the same words.
- Bottom bar: 48dp + `navigationBarsPadding`, 1dp top hairline, selected tint `#008294`, unselected at 62% alpha, icon 22dp, label 12sp.
- Event detail keeps the sticky bottom RSVP bar with `shadowElevation = 8.dp` and a hairline above it — that's the app's signature move.
- 16:9 hero on cards, 3:2 category tiles at 150dp wide, 120dp group tiles — all from `dimens.xml`.
- Light + dark both wired, dark palette from `values-night`.

---

## License reality check on the assets

Copied verbatim from the APK and therefore **not yours to ship**:

- Graphik `.ttf` — Commercial Type, paid license, per-app
- The `ic_*` vector icons — Meetup's own, drawn in-house
- Launcher icon + `color_meetup_red` mark
- 3,795 strings — Meetup's copy
- `com.meetup` namespace

Safe to reuse if you're rebuilding under your own identity: the spacing ladder, type ladder, tab order, nav graph structure, arg maps, interaction model, sticky-bar pattern, hairline-over-shadow convention. Those are idea and function, not protected expression.

If this ships publicly, swap Graphik for Inter or Manrope, redraw the icons, rewrite the copy, pick a new namespace and palette hue.

---

## Reproduce it

```powershell
# 1. unpack
python -c "import zipfile;zipfile.ZipFile('app.apkm').extractall('raw')"

# 2. decode resources
java -jar apktool.jar d raw/base.apk -o apktool/base -f

# 3. decode every split
Get-ChildItem raw/split_*.apk | % { java -jar apktool.jar d $_.FullName -o ("apktool/split_" + ($_.BaseName -replace 'split_config\.','')) -f }

# 4. decompile to Java (long: ~1h for 220 MB base, 13 dex)
jadx/bin/jadx.bat -d jadx/base --no-res --threads-count 8 raw/base.apk

# 5. re-run the extractors
python scan4.py   # typography, dimens, components
python scan6.py   # nav graphs, strings by prefix
python scan7.py   # manifest components, deeplinks, permissions
python scan8.py   # vector icons, lottie
python scan9.py   # semantic color tokens
```
