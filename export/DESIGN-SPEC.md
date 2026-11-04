# Design spec — extracted from `meetup-social-events-groups.apkm`

Package `com.meetup`, versionName `2026.10.26.3095`, versionCode `3095`, minSdk 24, targetSdk 36, compileSdk 36.

Everything below is read out of `res/values*`, `res/drawable*`, `res/layout*`, `res/navigation*` after apktool decode. Nothing invented.

---

## 1. Color tokens

Dual palette. Light in `res/values/colors.xml`, dark overrides in `res/values-night/colors.xml`. The app does not use Material's `colorPrimary` for branding — `colorPrimary` and `colorSurface` both resolve to `palette_system_background`. Brand color lives in `colorSecondary` → `palette_viridian`.

| Token | Light | Dark | Role |
|---|---|---|---|
| `palette_system_background` | `#FFFFFFFF` | `#FF121212` | app background, surfaces, sheets |
| `palette_secondary_background` | `#FFF6F7F8` | `#FF232425` | cards, elevated sheets |
| `palette_background_2dp` | `#FF4267B2` | `#FF222222` | 2dp elevation layer |
| `palette_background_4dp` | `@color/color_background` | `#FF262626` | 4dp layer |
| `palette_background_6dp` | — | `#FF2C2C2C` | 6dp layer |
| `palette_background_24dp` | — | `#FF383838` | search chip fill (dark) |
| `palette_system_grey_1` | `#FF8E8E93` | `#FF8E8E93` | tertiary icon |
| `palette_system_grey_3` | `#FFC7C7CC` | `#FF48484A` | disabled control |
| `palette_system_grey_4` | `#FFD1D1D6` | `#FF3A3A3C` | strong border |
| `palette_system_grey_5` | `#FFE5E5EA` | `#FF2C2C2E` | divider / list separator |
| `palette_system_grey_6` | `#FFF2F2F7` | `#FF1C1C1E` | subtle fill, image placeholder |
| `palette_text_primary` | `#FF212121` | `#DEFFFFFF` | body copy |
| `palette_text_secondary` | `#99212121` | `#99FFFFFF` | support copy (60%) |
| `palette_text_tertiary` | `#66212121` | `#66FFFFFF` | hint (40%) |
| `palette_text_primary_inverse` | `#DEFFFFFF` | `#FF212121` | on-brand text |
| `palette_viridian` | `#FF008294` | `#FF008294` | **primary action / link / selected tab** |
| `palette_peach` | `#FFF65858` | `#FFF65858` | SignUp CTA, app icon, date eyebrow |
| `palette_indigo` | `#FF00455D` | `#FF00455D` | deep teal accent |
| `palette_lime` | `#FF32D74B` | `#FF32D74B` | Live badge, success accent |
| `palette_old_gold` | `#FF7D694A` | `#FFD6BE98` | premium / Pro |
| `palette_alert_red` | `#FFC80000` | `#FFC80000` | error |
| `palette_success` | `#FF07B203` | `#FF07B203` | confirmation |
| `palette_text_entry_background` | `#FFEDEDED` | `@color/palette_system_grey_5` | input fill |
| `palette_text_color_link` | `#FF008294` | `#FF0098AB` | inline link |
| `palette_text_color_hint` | `#66212121` | `#66FFFFFF` | placeholder |
| `palette_text_color_highlight` | `#66008294` | `#660098AB` | selection |
| `palette_background_unread` | `#1AFFFFFF` | `@color/palette_system_grey_5` | unread row tint |
| `color_meetup_red` | `#FFFF1154` | — | legacy mark red |
| `app_icon_color` | `#FFF13A59` | — | launcher background |
| `mu_color_border` | `@color/palette_system_grey_6` | same | hairline |

Semantic indirection used by Compose-tolerant code:

```
color_background     -> palette_system_background
color_surface        -> palette_system_background
color_primary        -> palette_system_background
color_secondary      -> palette_viridian
color_on_surface     -> palette_text_primary
color_on_primary     -> palette_text_primary
color_error          -> palette_alert_red
```

Note the oddity: `color_on_primary` is **not** white. On a viridian button the label uses `palette_text_primary_inverse` via the button style, not the theme's `onPrimary`.

---

## 2. Typography

Font: **Graphik** (Commercial Type) — `graphik_regular`, `graphik_medium`, `graphik_semibold`, `graphik_bold`, each with italic variants, shipped as `.ttf` in `res/font`. Display accent font `grandstander_semibold.ttf` (Google Fonts, OFL) and legacy `neu_sans_*` (Neue Sans) also present.

Style ladder (`res/values/styles.xml`):

| Style | Size | Font | Extras |
|---|---|---|---|
| `Meetup.Text.Title1` | 32sp | graphik_semibold | lineSpacingExtra 6sp, multiplier 1.2 |
| `Meetup.Text.Title3` | 20sp | graphik_semibold | multiplier 1.2 |
| `Meetup.Text.Title` (base) | 16sp | graphik_regular | multiplier 1.2 |
| `Meetup.Text.Body` | 16sp | graphik_regular | lineSpacingExtra 6sp |
| `Meetup.Text.Body.Medium` | 16sp | graphik_medium | — |
| `Meetup.Text.Small` | 14sp | graphik_regular | — |
| `Meetup.Text.Small.Medium` | 14sp | graphik_medium | — |
| `Meetup.Text.Tiny` | 12sp | graphik_regular | — |
| `Meetup.Text.Tiny.Bold` | 12sp | graphik_medium | — |

Color variants: `.Secondary` → `textColorSecondary`, `.Tertiary` → `textColorTertiary`, `.Inverse` → `textColorPrimaryInverse`, `.Link` → `textColorLink`, `.Gold` → `palette_old_gold`.

Base text size on `BaseTheme` is `16sp`. `android:textColorHighlight`, `textColorHint`, `textColorLink` are all set at theme level.

---

## 3. Spacing scale

`res/values/dimens.xml` — a named ladder, not a 4/8/12 grid:

```
space_xtiny    6dp
space_tiny     8dp
space_small    12dp
space_middling 14dp
space_normal   16dp
space_separator 4dp
space_stripe   24dp
space_table    32dp
space_large    40dp
space_xlarge   80dp
```

## 4. Size tokens

```
avatar_tiny    26dp
avatar_small   36dp
avatar_medium  48dp
avatar_large   72dp
avatar_small_inset 1dp
overlapping_member_gallery_overlap  -8dp
icon_medium    24dp
tab_bar_height 48dp
height_normal  48dp
divider_default_height 1dp
settings_image_size 48dp
your_group_card_width 120dp
home_category_target_width 150dp
event_preview_row_height 200dp
event_preview_image_width 600dp
group_banner_image_width 600dp
```

## 5. Shape

```
text_entry_corner_radius  4dp
rounded_icon_corner_radius 2dp
Meetup.Button cornerRadius 8dp
badge / chip pill         4dp
```

No card elevation anywhere in list surfaces. Cards are flat with a 1dp `palette_system_grey_5` hairline. Elevation is reserved for the sticky RSVP bar and the bottom sheet.

---

## 6. Component styles

| Style | Parent | Key items |
|---|---|---|
| `BaseTheme` | `Theme.MaterialComponents.DayNight` | `fontFamily=graphik_regular`, `textSize=16sp`, statusBar `deprecated_black`, `windowLightStatusBar=true`, `actionBarStyle=Toolbar` |
| `Meetup` | `BaseTheme` | statusBar `?android:colorBackground` |
| `Meetup.NoActionBar` | `Meetup` | — |
| `Meetup.NoActionBarSplashScreen` | `Theme.SplashScreen` | — |
| `Meetup.Button` | `Widget.MaterialComponents.Button` | cornerRadius 8dp, padding 16/24, backgroundTint `mu_color_default_button_grey`, text `mu_color_default_button_text` |
| `Meetup.Button.Important` | `Meetup.Button` | backgroundTint `enable_or_disable_color_selector`, text `mu_color_fixed_light_text_primary` |
| `Meetup.Button.SignUp` | `Meetup.Button` | backgroundTint `palette_peach`, text `primary_button_text_color` |
| `Meetup.Button.Text` | `Widget.MaterialComponents.Button.TextButton` | fill_parent, cornerRadius 8dp |
| `Meetup.Button.Text.Small` | `Meetup.Button.Text` | padding 8dp, wrap_content |
| `Meetup.Alert.Dialog` | `Theme.MaterialComponents.DayNight.Dialog.Alert` | — |
| `Meetup.Dialog` | `Theme.MaterialComponents.DayNight.Dialog` | date/time picker theme |
| `Meetup.BottomNavigationView` | `Widget.MaterialComponents.BottomNavigationView.Colored` | — |
| `Meetup.Badge` | `Widget.MaterialComponents.Badge` | — |

Total own styles in `styles.xml`: 1542 entries, of which 43 are the `Meetup.*` / `Widget.Meetup.*` family.

---

## 7. Navigation architecture

### Root tabs — `com.meetup.feature.legacy.activity.RootTab` (enum, source-confirmed)

| Ordinal | Name | Fragment | Analytics tag |
|---|---|---|---|
| 0 | `HOME` | `com.meetup.feature.home.k0` (HomeFragment) | `Android_Home_Screen` |
| 1 | `NEW_EXPLORE` | `com.meetup.feature.explore.NewExploreFragment` | `Android_Explore_Screen` |
| 2 | `NOTIFICATIONS` | `com.meetup.feature.notifications.NotificationsFragment` | `Android_Notifications_Screen` |
| 3 | `MESSAGES` | `com.meetup.feature.legacy.coco.fragment.x` | `Android_Messages_Screen` |

Four tabs, in that order. No fifth. The bottom bar is a `ComposeView` with id `@id/bottom_nav_compose` anchored `layout_gravity="bottom"` inside `@layout/activity_root` — the entire shell is a `FrameLayout` → `CoordinatorLayout` → `FragmentContainerView` with the Compose bar overlaid.

### Feature nav graphs (`res/navigation/`)

| Graph | Start | Destinations |
|---|---|---|
| `auth_nav_graph` | `introFragment` | Intro, IntegrityFailed |
| `events_nav_graph` | `eventFragment` | Event, Sponsors, Ticket (QR), Going/RSVP |
| `group_nav_graph` | `groupTimelineFragment` | Timeline |
| `group_search_nav_graph` | `groupSearchResultFragment` | Search results |
| `create_event_nav_graph` | `startCreateEventFragment` | 13 steps: Start, Create, Topics, Link, Location, Hosts, Description, Question, Welcome Message, Event Chat Settings, Payment Options, Promo Code, Discount |
| `settings_nav_graph` | `settingsFragment` | Settings, Notification guide, Subscription, App icon, Notification settings |
| `confirmation_nav_graph` | `confirmationFragment` | — |
| `about_meetup_nav_graph` | `aboutMeetupFragment` | — |
| `resubscribe_nav_graph` | `resubscribeFragment` | — |
| `step_up_nav_graph` | `stepUpFragment` | — |
| `terms_nav_graph` | `termsFragment` | — |
| `debug_nav_graph` | `debugMenuFragment` | Debug, Home, Browse plans |
| `nav_graph_secret_menu_lib` | `secretMenuFragment` | Secret menu (Bending Spoons lib) |

`events_nav_graph` `eventFragment` takes **18** arguments — the deepest arg list, and the map of everything the event screen renders: `eventId, origin, groupUrlname, startAttend, subOrigin, campaign, dispatchId, categoryId, categoryName, date, distance, venue, timeOfDay, recommendationId, recommendationSource, searchId`. That tells you the detail screen is fully deep-link-driven and stateless on entry.

`goingFragment` (the post-RSVP screen) takes 20 args, including `eventType`, `eventPaymentType`, `isHybridOnline`, `isWaitlisted`, `showWaitlistCta`, `attendees` (typed `com.meetup.feature.event.model.Attendees`).

---

## 8. Manifest inventory

152 activities, 5 activity-aliases (launcher icon variants: default, SummerSunset, Doodle, CottonCandy, Confetti — all disabled except `BootstrapActivity`), 24 services, 22 receivers, 14 providers, 32 permissions.

Deep links: schemes `https, http, meetup, myapp, fbconnect, mailto, sms, market`. Hosts: `meetup.com`, `www.meetup.com`, `secure.meetup.com`, `meet.meetup.com`, `clicks.meetup.com`, `email-analytics.meetup.com`, `meetu.ps`, `cct.com.meetup`. 18 distinct deep-link entries, all funneling through `com.meetup.feature.legacy.deeplinks.DeepLinkDispatcherActivity` (`singleInstance`, `noHistory`, `excludeFromRecents`).

Notable permissions: `CAMERA`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `READ_CALENDAR`, `WRITE_CALENDAR`, `POST_NOTIFICATIONS`, `com.android.vending.BILLING`, `AD_ID`, `FOREGROUND_SERVICE`, `WAKE_LOCK`, `VIBRATE`, `com.google.android.c2dm.permission.RECEIVE`.

`network_security_config.xml` sets `cleartextTrafficPermitted="true"` on the base config — worth noting, since it is not best practice.

---

## 9. Stack

From `META-INF/*.version` (161 artifacts):

```
Kotlin coroutines   1.10.2
Compose UI / runtime / foundation / animation  1.10.4
Compose Material3   1.4.0
Compose Material    1.10.2
material-icons-extended 1.7.8
Navigation (compose + fragment) 2.9.7
Hilt                2.57.2
Room                2.7.1
Paging              3.4.0
Coil                (present)
OkHttp3 / Retrofit2 / Moshi (present, obfuscated)
Braze, AppLovin/MAX, Fyber, Smaato, Vungle, InMobi, Rokt, Zendesk, OneTrust, mParticle, Sift
Facebook SDK, Google Play Billing, ML Kit barcode, Credential Manager
```

Architecture: single-activity-ish (`RootActivity` + `BootstrapActivity` alias), Fragments for legacy screens, Compose for new ones (`HomeFragment` tab content, settings, explore, bottom nav). Navigation via Jetpack Navigation graphs for both layers. 13 dex files, 102,355 smali classes — heavy obfuscation (R8 + renamed packages `a0`…`zq`, so jadx output is mostly unreadable class names but structure survives).

---

## 10. String corpus

3,795 string resources, 404 prefixes. Biggest buckets — this is your copy deck, and it maps almost 1:1 to screens:

```
event        466
group        209
top          173
profile      166
edit         102
explore       91
messages      89
member        86
attendee      85
onboarding    85
shared        71
attribution   65
subscription  65
settings      63
photo         45
rsvp          31
qr            30
```

Split by prefix into `export/strings/<prefix>.json`.

---

## 11. Asset inventory

| Path | Count | What |
|---|---|---|
| `res/layout/*.xml` | 408 total, 3 app-owned | the app is Compose-first; layouts are mostly SDK |
| `res/drawable*` | 618 | 138 own vector icons (`ic_*`, `mu_*`) + raster |
| `res/font/` | 36 | Graphik family + Grandstander + Neue Sans + Pacifico + rokt icons |
| `res/mipmap-*` | 112 in xxxhdpi | launcher variants, night variants |
| `res/raw/` | 17 | `confetti.lottie` (10 KB), CSV/JSON config |
| `assets/emoji/meetup-reactions-emoji-v1.json` | 390 KB | reaction emoji set |
| `assets/fontawesome-webfont.ttf` | 122 KB | SDK shipped |
| `lib/arm64-v8a/` | 6 `.so` | barhopper, applovin crash reporter, path, datastore, mlkit |

Native libs are almost entirely third-party — the app itself is Java/Kotlin, no custom `.so`.

---

## 12. What to keep vs change if you rebuild this

Keep (idea/function — not copyrightable):
IA, tab order, arg maps, nav graphs, spacing ladder, type ladder, interaction model, sticky RSVP bar, hairline-over-shadow card convention.

Change (protected):
Graphik font (commercial license), the `ic_*` icon set as-is, launcher icon, `color_meetup_red` mark, 3,795 verbatim strings, the `com.meetup` package namespace, the exact screenshot compositions on the store listing.
