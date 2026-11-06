# KakaoStory UI clone

Dựng lại giao diện KakaoStory Android (`KakaoStory_7.2.5_APKPure.apk`, `com.kakao.story`
versionCode 26038, versionName 7.2.5, minSdk 26, targetSdk 36) bằng HTML/CSS/JS thuần —
mở file là chạy, không cần build, không cần mạng.

```
kakaostory-clone/
├── index.html            # 8 màn + overlay (emo popup, flyer, sheet, toast)
├── styles/app.css        # token + layout + toàn bộ keyframes
├── js/app.js             # data mẫu, render, router, long-press rail, pull-to-refresh
├── export/
│   ├── serve.js          # static server cho lúc chụp ảnh
│   ├── capture.py        # headless Chrome → screenshots/*.png
│   └── make_media.py     # sinh ảnh placeholder bằng PIL
├── screenshots/          # 23 ảnh 780×1688
└── assets/
    ├── fonts/            # KakaoOTFBold.otf (lấy thật từ APK)
    ├── icons/            # 42 PNG export từ drawable trong APK
    └── img/              # 23 ảnh placeholder tự sinh (không phải asset Kakao)
```

## Mở

Cách nhanh nhất — double-click `index.html`. Font và icon nạp qua đường dẫn
tương đối nên `file://` vẫn chạy ở Chrome/Edge/Firefox.

Có server thì sạch hơn (tránh cache CSS khi đang sửa, và cần cho script chụp):

```bash
cd "E:\Clone UIUX\kakaostory-clone"
node export/serve.js 8791
# http://127.0.0.1:8791/index.html
```

## Route

State-driven, không dùng hash. Chỉ `.screen.is-active` hiện, các màn còn lại
`display:none` + `aria-hidden`.

| Route | Màn |
| --- | --- |
| `splash` | Khởi động — nền tím, `logo_txt.png` + `logo_comma.png`, hold 1.5s rồi tự vào feed |
| `home` | Trang chủ — actionbar, story rail, feed card, FAB |
| `feed` | Feed đầy đủ — header + chồng card |
| `detail` | Chi tiết bài — hero, body, like rail, bình luận |
| `noti` | Trung tâm thông báo |
| `explore` | Khám phá — chip row, grid masonry |
| `friends` | Bạn bè — khối profile, tab row, grid/list |
| `write` | Viết bài — dải media, body, hàng emotion |

Trên desktop có thanh route phía trên khung máy; `?shot=1` thì thanh đó, legend
và bezel bị bỏ hết để ảnh chụp đúng 390×844.

Tham số query cho lúc chụp/soi: `route`, `clock`, `post`, `stab`, `ntab`, `chip`,
`scroll`, `ui`, `msg`, `text`. `ui` nhận `emo`, `sheet`, `share`, `toast`, `pull`,
`fly`, `newfeed`, `comment`.

## Tương tác đã dựng

- **Tap để like** — tap trái tim trên card là toggle reaction, số đếm nhảy.
- **Long-press** — giữ 450ms trên nút like mở rail 5 emotion
  (`like / good / pleasure / sad / cheerup`). Có cờ chặn click để rail không
  giết luôn cái toggle vừa mở.
- **Emotion flyer** — chọn trong rail thì sticker bay từ nút like sang ô reaction
  của card, đáp xuống với scale overshoot.
- **Pull-to-refresh** — kéo feed xuống, qua 64px thì spinner arm, thả ra danh sách
  bật về và pill "Bài mới" trượt vào.
- **Bình luận** — gõ vào ô soạn ở detail, gửi thì row mới chèn vào, đếm 4 → 5,
  scroll tới row vừa thêm.
- **Bottom sheet** — FAB hoặc nút overflow của card mở sheet 5 mục, có scrim,
  trượt lên; chọn mục thì toast rồi đóng.
- **Tab** — bottom bar đổi màn gốc; actionbar của home có tab riêng (`stab`),
  friends có grid/list (`stab`).
- **Chip lọc** — chip ở thông báo (`chip=all|story|like|comment`) render lại list.
- Toàn bộ animation tắt theo `prefers-reduced-motion`.

## Token lấy từ APK

| Token | Giá trị | Nguồn |
| --- | --- | --- |
| `--purple` | `#ff6552de` | `colors.xml → purple` |
| `--light_purple` | `#ff6a5cff` | `colors.xml → light_purple` |
| `--text_type0` | `#ff1a1a1a` | `colors.xml → text_type0` |
| `--text_type1` | `#ff333333` | `colors.xml → text_type1` |
| `--text_type2` | `#ff666666` | `colors.xml → text_type2` |
| `--text_type3` | `#ff999999` | `colors.xml → text_type3` |
| `--text_type4` | `#ffcccccc` | `colors.xml → text_type4` |
| `--stroke_type1` | `#ffdddddd` | `colors.xml → stroke_type1` |
| `--stroke_type2` | `#ffe5e5e5` | `colors.xml → stroke_type2` |
| `--stroke_type3` | `#ffeeeeee` | `colors.xml → stroke_type3` |
| `--stroke_type4` | `#fff7f7f7` | `colors.xml → stroke_type4` |
| `--light_gray` | `#fff7f7f7` | `colors.xml → light_gray` |
| `--orange_red` | `#fff53a3a` | `colors.xml → orange_red` |
| `--actionbar_height` | `48px` | `dimens.xml → actionbar_height` |
| `--feed_card_padding` | `16px` | `dimens.xml → feed_card_padding` |
| `--story_home_badge_height` | `49px` | `dimens.xml → story_home_badge_view_height` |
| `--gnb_height` | `50px` | `dimens.xml → main_gnb_tab_plus_progress_height` |
| `--profile_large_height` | `240px` | `dimens.xml → profile_large_height` |
| `--emotion_item_large` | `53px` | `dimens.xml → emotion_popup_item_large_size` |
| `--thumbnail_profile` | `36px` | `dimens.xml → thumbnail_profile_width` |
| `--card_radius` | `15px` | `styles.xml → FeedCardViewStyle` |

Thang chữ là `text_0`…`text_22` trong `dimens.xml`: `11 / 12 / 13 / 14 / 15 / 16 /
17 / 18 / 20 / 22 / 24 / 26 / 28 / 30`. Title `17sp / 2 dòng`
(`FeedContentsText.Title`), body `15sp`, meta `13sp`.

## Motion

Mọi timing dưới đây copy nguyên từ APK, không tự đặt easing.

| Animation | File nguồn | Giá trị |
| --- | --- | --- |
| Chuyển activity | `res/values/integers.xml → activity_transition_time` | `150ms` |
| Splash logo pop | `res/anim/zoom_fade_in.xml` | scale `0.9 → 1.0`, alpha `0.8 → 1.0`, 300ms |
| Emotion pop-up | `res/anim/pop_up.xml` | scale `0.5 → 1.0` có overshoot, pivot `20%` |
| Scale + fade in | `res/anim/scale_fade_in.xml` | scale `0.9 → 1.0`, alpha `0 → 1`, `150ms`, pivot `20%` |
| Composer trượt lên | `res/anim/slide_up_in_fast_full.xml` | translateY `92% → 0`, `150ms`, `linear` |
| Sheet detail | `res/anim/details_slide_in_from_bottom.xml` | translateY `100% → 0`, `200ms`, `decelerate` |
| Trung tâm thông báo | `res/anim/notification_center_in.xml` | translateX `100% → 0` |

Mỗi cái nằm trong `styles/app.css` dưới dạng một block `@keyframes` cộng một class
ngắn, ví dụ `.emo-rail.is-open { animation: popUp 180ms cubic-bezier(.34,1.56,.64,1); }`.

## Ảnh chụp

`python export/capture.py` — cần `node export/serve.js 8791` chạy trước. Script đọc
`PLAN` (tên → route + query + delay), chụp bằng Chrome headless với
`--force-device-scale-factor=2` ở viewport 390×844, ra PNG 780×1688 trong
`screenshots/`. Chạy một phần: `python export/capture.py noti explore`.

| File | Nội dung |
| --- | --- |
| `01-splash` | Khởi động |
| `02-home` | Trang chủ |
| `03-feed` | Feed |
| `04-detail` | Chi tiết bài |
| `05-noti` | Thông báo |
| `06-explore` | Khám phá |
| `07-friends` | Bạn bè |
| `08-write` | Viết bài |
| `09-home-feed` | Home, tab feed |
| `10-home-discovery` | Home, tab discovery |
| `11-noti-message` | Thông báo, tab message |
| `12-explore-chip` | Khám phá, chip thứ 2 |
| `13-detail-newcomment` | Bình luận vừa gửi |
| `14-motion-emotions` | Rail 5 emotion đang mở |
| `15-motion-emotions-detail` | Rail emotion ở detail |
| `16-motion-flyer` | Sticker đang bay |
| `17-motion-sheet` | Bottom sheet |
| `18-motion-sheet-share` | Sheet chia sẻ |
| `19-motion-pull` | Pull-to-refresh |
| `20-motion-newfeed` | Pill "Bài mới" |
| `21-motion-toast` | Toast |
| `22-detail-scroll` | Detail đã scroll |
| `23-home-me` | Home, tab me |

## Về ảnh và nội dung

Mọi ảnh trong `assets/img/` do `export/make_media.py` sinh bằng PIL — gradient nhiều
lớp cho ảnh feed, silhouette bust cho avatar. Không có ảnh, video hay nội dung nào
lấy từ APK. Tên người, caption, bình luận đều là dữ liệu mẫu tiếng Việt tự viết.

Icon thì lấy thật từ APK: 42 PNG trong `res/drawable-*dpi/`, gồm bộ
`btn_mystory_menu_*`, `ico_badge_*`, icon tabbar và emotion. Badge gốc 72px được
CSS cap xuống 14px khi hiển thị inline.

## Sửa nhanh

- Đổi dữ liệu: `USERS`, `EMO`, `POSTS`, `NOTIS`, `FRIENDS`, `EXPLORE`, `TABBAR` ở
  đầu `js/app.js`.
- Đổi/thêm ảnh: sửa `export/make_media.py` rồi chạy `python export/make_media.py`.
- Đổi kích thước khung máy: `.device` trong `styles/app.css` (`390 × 844`, tự scale
  xuống khi cửa sổ thấp).
- Chụp lại ảnh sau khi sửa CSS: chạy lại `python export/capture.py`.

## Ghi chú

- `.scroll` cần `min-height: 0` trong flex parent — thiếu dòng đó là feed bị clip
  thay vì scroll.
- `setMyTab()` lưu đường dẫn icon gốc vào `dataset.base`, nếu không thì đổi tab
  nhiều lần sẽ làm hỏng `src`.
- `renderMyGrid()` dùng `rows` cho mọi mode trừ `grid` (grid là đường masonry).
- Emotion key: `like / good / pleasure / sad / cheerup` — nhãn `Like / Cool / Happy /
  Sad / Cheer Up`.
