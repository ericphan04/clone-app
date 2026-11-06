# Instagram UI clone

Dựng lại giao diện Instagram Android (xapk 454.0.0.54.77) bằng HTML/CSS/JS thuần —
mở file là chạy, không cần build, không cần mạng.

```
instagram-clone/
├── index.html            # 5 màn + overlay (viewer, sheet, toast)
├── styles/app.css        # token + layout + toàn bộ keyframes
├── js/app.js             # data mẫu, render, router, story viewer, thread
└── assets/
    ├── fonts/            # Prism Sans (3 weight) + instagram_signature.otf
    ├── icons/            # 62 SVG export từ vector drawable trong APK
    └── img/              # 16 ảnh placeholder tự sinh (không phải asset IG)
```

## Mở

Cách nhanh nhất — double-click `index.html`. Font và icon nạp qua đường dẫn
tương đối nên `file://` vẫn chạy ở Chrome/Edge/Firefox.

Có server thì sạch hơn (tránh cache CSS khi đang sửa):

```bash
cd "E:\Clone UIUX\instagram-clone"
python -m http.server 8753
# http://127.0.0.1:8753/index.html
```

## Route

Hash trên URL, mở thẳng được:

| URL | Màn |
| --- | --- |
| `#feed` | Feed — story rail, post, double-tap like, sheet bình luận/chia sẻ/tuỳ chọn |
| `#dm` | Hộp thư — search lọc, notes rail, 7 thread (unread, presence, verified) |
| `#thread` | Hội thoại — bubble, typing indicator, gửi tin + reply tự động, reaction |
| `#login` | Đăng nhập — float label, reveal password, busy spinner, shake lỗi |
| `#register` | Đăng ký — 4 field, validate, busy, chuyển về feed |

Trên desktop có thanh `Feed / Tin nhắn / Login / Register` phía trên khung máy.
Dưới 640px thanh đó thu thành nút nhỏ ở mép phải (`#routefab`) để không chiếm
chiều rộng màn hình.

Tham số `?fx=` mở sẵn một overlay để chụp/soi: `viewer`, `sheet`, `share`,
`more`, `reaction`, `typing`.

## Tương tác đã dựng

- **Feed** — double-tap ảnh để like (burst heart + rung nhẹ ảnh + đếm like nhảy),
  tap trái tim để toggle, save pop, sheet bình luận (kèm ô nhập), sheet chia sẻ,
  sheet tuỳ chọn có mục "Báo cáo" màu đỏ.
- **Story** — ring gradient, ring xoay cho story live, tap mở viewer full-screen:
  5 giây mỗi story, thanh tiến trình, ken burns, tap trái/phải để lùi/tiến,
  ô trả lời, `Esc` để đóng.
- **DM** — gõ vào ô search là lọc thread ngay; mở thread thì mất chấm unread.
- **Thread** — gõ là nút gửi hiện lên (scale + fade), gửi xong bubble hiện mờ
  rồi sáng, đối phương hiện typing 3 chấm rồi trả lời; long-press bubble mở
  thanh reaction 6 emoji; double-tap để thả tim.
- **Auth** — float label khi có chữ, reveal/ẩn mật khẩu, submit thiếu field thì
  lắc field + hiện lỗi đỏ, submit đủ thì spinner 0.78s rồi vào feed.
- **Chuyển màn** — hướng animation theo độ sâu route (push sang trái, pop về phải),
  list stagger-in, sheet trượt lên có spring và kéo xuống để đóng.
- Toàn bộ animation tắt theo `prefers-reduced-motion`.

## Token lấy từ APK

| Token | Giá trị | Nguồn |
| --- | --- | --- |
| `--blue` | `#0095f6` | `igds_prism_blue_05` |
| `--red` | `#ff3040` | `igds_prism_red_05` |
| `--text` | `#0c1014` | `igds_prism_gray_10` / black |
| `--text-2` | `#6f7680` | `igds_prism_gray_04` |
| `--line` | `#dbdbdb` | separator mặc định |
| `--button-height` | `46px` | `dimen/button_height` |
| `--auth-title` | `15px` | `auth_title_text_size` |
| `--auth-credential-title` | `17px` | `auth_credential_title_text_size` |
| `--nav-h` / `--tabbar-h` | `44px` / `48px` | nav + tab bar |

Font: `IGUIBetav9_Regular/SemiBold/Bold.ttf` (Prism Sans) và
`instagram_signature.otf` cho wordmark.

## Về ảnh và nội dung

Mọi ảnh trong `assets/img/` do `../ig/make_placeholders.py` sinh bằng PIL —
gradient phong cảnh nhiều lớp cho post, silhouette bust cho avatar. Không có
ảnh, video hay nội dung nào lấy từ APK. Tên người dùng, caption, tin nhắn đều là
dữ liệu mẫu tiếng Việt tự viết.

Icon thì lấy thật từ APK: các vector drawable `threads_icons_cotton_*`,
`basel_icons_svg_*`, `hatch_icon_*`, `ic_call_*`, `arrow_left_outline_24`,
`hatch_permission_microphone_*`. Script `../ig/extract_icons.py` đọc `pathData`
và ghi SVG tô `currentColor` để dùng được qua CSS mask.

## Sửa nhanh

- Đổi dữ liệu: `STORIES`, `POSTS`, `NOTES`, `THREADS`, `REPLIES` ở đầu `js/app.js`.
- Thêm icon: thêm dòng vào dict `WANTED` trong `ig/extract_icons.py` rồi chạy
  `python extract_icons.py`, sau đó thêm `.ic--ten { --icon: url(...) }` vào CSS.
- Đổi kích thước khung máy: `.device` trong `styles/app.css`
  (`390 × 844`, tự scale xuống khi cửa sổ thấp).

## Ghi chú

App gốc không chạy được trên thiết bị test (SM-G975U): inflate chết ở
`layout_activity_main_coordinator_layout_viewpager2` — `Failed to resolve
attribute at index 6`, nên không có screenshot tham chiếu. Hình dựng theo token
màu/dimen bóc được cộng với hình học đọc từ các vector và file layout còn đọc được.
