# Screenshots — clone chạy trên thiết bị thật

Chụp bằng `adb shell screencap` trên **SM-G975U** (1440×3040, density 560), Android, dark mode.
Đây là bản **clone Compose** (`clone-android/`), không phải app Meetup gốc.

| File | Màn | Nguồn trong APK |
|---|---|---|
| `01-onboarding.png` | Onboarding trang 1 — eyebrow peach, title 32sp, gradient hero, dot indicator, CTA peach | `feature.onboarding.NewOnboardingActivity` |
| `04-interests.png` | Interest picker — "Groups for you", chip rail, "Others you might like" | `onboarding_*` strings, `auto_join_groups_title` |
| `10-onboarding.png` | Onboarding trang 1 (bản rebuild sau khi thêm gradient image slot) | — |
| `11-home.png` | Home — location bar, search + bell có chấm đỏ, rail event ngang 300dp, "Going" list | `feature.home.k0` (HomeFragment) |
| `15-home-scroll.png` | Home feed — "Popular near you", badge Online | — |
| `16-home-groups.png` | Home — card full-width, meta row venue | — |
| `17-groups-rail.png` (đã xoá) | — | — |
| `24-group-timeline.png` | Group timeline — cover 200dp, title 32sp, member count, topic chip, Join, 5 tab (Upcoming/Past/Discussions/Photos/Members) | `group_nav_graph` → `TimelineFragment` |
| `12-explore.png` | Explore — title 32sp, search field, filter chip rail, category tile 150dp, feed 2 cột staggered | `feature.explore.NewExploreFragment` |
| `13-notifications.png` | Notifications — unread tô `grey6`, avatar 36dp bo 2dp, timestamp tertiary | `feature.notifications.NotificationsFragment` |
| `14-messages.png` | Messages — avatar 48dp, dot đỏ, title 16sp medium, preview 1 dòng | `feature.legacy.coco.fragment.x` |
| `25-auth.png` | Auth — title 32sp, input radius 4dp, CTA peach 48dp, social row, "or" divider | `base.auth.LoginWebActivity` (WebView → native form) |

## Ghi chú kỹ thuật khi chụp

- **Thiết bị offline** (không DNS, không route). Ảnh remote không tải được → `MeetupImage` vẽ gradient từ `palette_viridian` / `palette_indigo` làm nền, nên khung ảnh vẫn đọc được là slot ảnh chứ không phải ô đen.
- Dark mode là mặc định của máy. Palette dark lấy từ `res/values-night/colors.xml`.
- Bottom bar đo được: chiều cao 48dp + `navigationBarsPadding`, tab hit-box y≈2858–2898 trên màn 3040px.

## Chưa chụp được

- Onboarding trang 2 và 3 — cùng template với trang 1, chỉ khác copy và gradient.
- Trạng thái light mode — cần tắt dark mode trên máy.
- Empty state, loading, error — chưa có nhánh UI nào dựng các trạng thái đó.
