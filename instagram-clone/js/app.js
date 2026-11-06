/* Instagram clone — data, rendering, routing and motion.
   All copy is sample content; all media is synthetic placeholder art. */

/* ---------------- data ---------------- */

const STORIES = [
  { name: "Bạn", me: true, avatar: "assets/img/avatar-01.jpg" },
  { name: "linh.tr", avatar: "assets/img/avatar-02.jpg", seen: false, live: true },
  { name: "minhkhang", avatar: "assets/img/avatar-03.jpg", seen: false },
  { name: "thuha.99", avatar: "assets/img/avatar-04.jpg", seen: false },
  { name: "quanghuy", avatar: "assets/img/avatar-05.jpg", seen: true },
  { name: "ngocdiep", avatar: "assets/img/avatar-06.jpg", seen: false },
  { name: "baotram", avatar: "assets/img/avatar-07.jpg", seen: true },
  { name: "hai.dang", avatar: "assets/img/avatar-08.jpg", seen: false },
];

const POSTS = [
  {
    user: "linh.tr",
    avatar: "assets/img/avatar-02.jpg",
    verified: false,
    location: "Đà Lạt",
    img: "assets/img/post-01.jpg",
    likes: 1284,
    caption:
      "Sáng nay sương xuống muộn, cả đồi chỉ còn tiếng gió. Đứng đây hai tiếng không ai giục.",
    tags: ["#dalat", "#morninglight"],
    comments: 42,
    time: "2 giờ",
  },
  {
    user: "minhkhang",
    avatar: "assets/img/avatar-03.jpg",
    verified: true,
    location: "",
    img: "assets/img/post-02.jpg",
    likes: 20417,
    caption: "Roll cuối của chuyến đi. Không chỉnh màu, chỉ đợi đúng 6 giờ chiều.",
    tags: ["#filmphotography"],
    comments: 316,
    time: "5 giờ",
  },
  {
    user: "thuha.99",
    avatar: "assets/img/avatar-04.jpg",
    verified: false,
    location: "Hội An",
    img: "assets/img/post-03.jpg",
    likes: 873,
    caption: "Đèn lên rồi mới thấy phố đông thật. Mai về.",
    tags: ["#hoian"],
    comments: 18,
    time: "8 giờ",
  },
  {
    user: "quanghuy",
    avatar: "assets/img/avatar-05.jpg",
    verified: false,
    location: "",
    img: "assets/img/post-04.jpg",
    likes: 3412,
    caption:
      "Ba tháng đổi giờ dậy. Không nghĩ lại được nữa — sáng sớm là phần thưởng, không phải hình phạt.",
    tags: [],
    comments: 91,
    time: "11 giờ",
  },
  {
    user: "ngocdiep",
    avatar: "assets/img/avatar-06.jpg",
    verified: true,
    location: "Sài Gòn",
    img: "assets/img/post-05.jpg",
    likes: 56230,
    caption: "Mưa tới trước dự báo. Ướt hết nhưng vẫn kịp chụp.",
    tags: ["#saigon", "#rain"],
    comments: 704,
    time: "1 ngày",
  },
  {
    user: "hai.dang",
    avatar: "assets/img/avatar-08.jpg",
    verified: false,
    location: "",
    img: "assets/img/post-06.jpg",
    likes: 640,
    caption: "Góc này không ai đứng, nên đứng. Xong đi ăn.",
    tags: [],
    comments: 7,
    time: "1 ngày",
  },
];

const NOTES = [
  { name: "minhkhang", avatar: "assets/img/avatar-03.jpg", text: "về rồi" },
  { name: "thuha.99", avatar: "assets/img/avatar-04.jpg", text: "mưa to vãi" },
  { name: "ngocdiep", avatar: "assets/img/avatar-06.jpg", text: "đang ở đâu" },
  { name: "baotram", avatar: "assets/img/avatar-07.jpg", text: "ngủ sớm đi" },
  { name: "hai.dang", avatar: "assets/img/avatar-08.jpg", text: "hẹn 7h" },
];

const THREADS = [
  {
    user: "linh.tr",
    avatar: "assets/img/avatar-02.jpg",
    preview: "Bạn gửi: ảnh này ok không",
    time: "2 ph",
    unread: true,
    presence: true,
    verified: false,
    messages: [
      { from: "them", text: "Mai đi sớm không?", t: "08:12" },
      { from: "me", text: "6h được không, sương còn", t: "08:13" },
      { from: "them", text: "6h hơi trễ, 5r30 đi", t: "08:13" },
      { from: "them", text: "mà thôi 6h cũng được, mang áo khoác", t: "08:14" },
      { from: "me", text: "ok chốt", t: "08:15" },
      { from: "them", text: "nay trời trong lắm", t: "09:02" },
    ],
  },
  {
    user: "minhkhang",
    avatar: "assets/img/avatar-03.jpg",
    preview: "gửi lại file raw nhé",
    time: "18 ph",
    unread: true,
    presence: false,
    verified: true,
    messages: [
      { from: "them", text: "ảnh hôm qua anh chỉnh nhẹ thôi đúng không", t: "19:40" },
      { from: "me", text: "đúng rồi, chỉ crop", t: "19:41" },
      { from: "them", text: "gửi lại file raw nhé", t: "19:44" },
    ],
  },
  {
    user: "thuha.99",
    avatar: "assets/img/avatar-04.jpg",
    preview: "Đã thả ❤️ vào tin của bạn",
    time: "1 giờ",
    unread: false,
    presence: true,
    verified: false,
    messages: [
      { from: "them", text: "đèn lồng đẹp thật", t: "20:02" },
      { from: "me", text: "phố đông mà vẫn chụp được", t: "20:05" },
      { from: "them", text: "mai về sớm không", t: "20:09" },
    ],
  },
  {
    user: "quanghuy",
    avatar: "assets/img/avatar-05.jpg",
    preview: "Bạn: mai chạy 5km không",
    time: "3 giờ",
    unread: false,
    presence: false,
    verified: false,
    messages: [
      { from: "me", text: "mai chạy 5km không", t: "05:31" },
      { from: "them", text: "5r30 nhé", t: "05:44" },
    ],
  },
  {
    user: "ngocdiep",
    avatar: "assets/img/avatar-06.jpg",
    preview: "mưa tới trước dự báo luôn",
    time: "5 giờ",
    unread: false,
    presence: true,
    verified: true,
    messages: [
      { from: "them", text: "mưa tới trước dự báo luôn", t: "16:20" },
      { from: "me", text: "ướt hết chưa", t: "16:22" },
      { from: "them", text: "ướt nhưng vẫn kịp chụp", t: "16:23" },
    ],
  },
  {
    user: "baotram",
    avatar: "assets/img/avatar-07.jpg",
    preview: "Đã gửi một ảnh",
    time: "1 ngày",
    unread: false,
    presence: false,
    verified: false,
    messages: [
      { from: "them", text: "này đẹp nè", t: "21:10" },
      { from: "me", text: "gửi anh bản gốc", t: "21:12" },
    ],
  },
  {
    user: "hai.dang",
    avatar: "assets/img/avatar-08.jpg",
    preview: "hẹn 7h ở quán cũ",
    time: "2 ngày",
    unread: false,
    presence: false,
    verified: false,
    messages: [
      { from: "them", text: "hẹn 7h ở quán cũ", t: "18:00" },
      { from: "me", text: "ok", t: "18:04" },
    ],
  },
];

const REPLIES = [
  "ừ để xem đã",
  "nghe được đó",
  "ok chốt luôn",
  "mai nói tiếp nhé",
  "haha đúng rồi",
  "gửi ảnh đi",
  "đang ở ngoài, lát trả lời",
];

/* ---------------- helpers ---------------- */

const $ = (sel, root = document) => root.querySelector(sel);
const $$ = (sel, root = document) => Array.from(root.querySelectorAll(sel));

function el(tag, cls, html) {
  const node = document.createElement(tag);
  if (cls) node.className = cls;
  if (html != null) node.innerHTML = html;
  return node;
}

function fmtLikes(n) {
  if (n >= 1000000) return (n / 1000000).toFixed(1).replace(".0", "") + " Tr";
  if (n >= 1000) return (n / 1000).toFixed(1).replace(".0", "") + " N";
  return String(n);
}

function icon(name, cls) {
  return '<i class="ic ic--' + name + (cls ? " " + cls : "") + '"></i>';
}

/* ---------------- toast ---------------- */

let toastTimer = null;
function toast(msg) {
  const t = $("#toast");
  if (!t) return;
  t.textContent = msg;
  t.classList.add("is-on");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => t.classList.remove("is-on"), 1700);
}

/* ---------------- feed ---------------- */

function renderStories() {
  const rail = $("#stories");
  rail.innerHTML = "";

  STORIES.forEach((s, i) => {
    const btn = el("button", "story" + (s.me ? " story--me" : ""));
    btn.type = "button";
    btn.style.setProperty("--i", i);
    btn.dataset.story = String(i);

    const ring = el(
      "div",
      "story-ring" + (s.seen ? " is-seen" : "") + (s.live ? " is-live" : "")
    );
    const img = el("img");
    img.src = s.avatar;
    img.alt = "";
    ring.appendChild(img);

    if (s.me) {
      const add = el("div", "add", icon("plus"));
      ring.appendChild(add);
    }

    const nm = el("span", "story-name");
    nm.textContent = s.name;

    btn.append(ring, nm);
    rail.appendChild(btn);
  });
}

function postNode(p, i) {
  const wrap = el("article", "post");
  wrap.style.setProperty("--i", i);

  const head = el("div", "post-head");
  const ring = el("div", "avatar-ring");
  const av = el("img", "avatar avatar--32");
  av.src = p.avatar;
  av.alt = "";
  ring.appendChild(av);

  const who = el("div", "post-user");
  const nm = el("span", "name");
  nm.textContent = p.user;
  who.appendChild(nm);
  if (p.verified) who.appendChild(el("i", "ic ic--verified"));
  if (p.location) {
    who.appendChild(el("span", "dot", "•"));
    who.appendChild(el("span", "time", p.location));
  }

  const moreBtn = el("button", "iconbtn", icon("more"));
  moreBtn.dataset.act = "more";
  moreBtn.setAttribute("aria-label", "Tuỳ chọn bài viết");

  head.append(ring, who, moreBtn);

  const media = el("div", "post-media");
  const img = el("img");
  img.src = p.img;
  img.alt = "";
  img.loading = i < 2 ? "eager" : "lazy";
  const burst = el("div", "burst", icon("heart-on"));
  media.append(img, burst);

  const acts = el("div", "post-actions");
  acts.innerHTML = `
    <button class="iconbtn" data-act="like" aria-label="Thích">${icon("heart")}</button>
    <button class="iconbtn" data-act="comment" aria-label="Bình luận">${icon("comment")}</button>
    <button class="iconbtn" data-act="share" aria-label="Chia sẻ">${icon("share")}</button>
    <span class="spacer"></span>
    <button class="iconbtn" data-act="save" aria-label="Lưu">${icon("save")}</button>
  `;

  const likes = el("div", "likes");
  likes.dataset.base = String(p.likes);
  likes.textContent = fmtLikes(p.likes) + " lượt thích";

  const cap = el("div", "caption");
  const capName = el("span", "name");
  capName.textContent = p.user;
  cap.appendChild(capName);
  cap.appendChild(document.createTextNode(p.caption + " "));
  for (const t of p.tags) {
    const a = el("span", "tag");
    a.textContent = t + " ";
    cap.appendChild(a);
  }

  const cta = el("button", "comments-link");
  cta.type = "button";
  cta.dataset.act = "comment";
  cta.textContent = "Xem tất cả " + p.comments + " bình luận";

  const ts = el("div", "timestamp");
  ts.textContent = p.time + " trước";

  wrap.append(head, media, acts, likes, cap, cta, ts);
  return wrap;
}

function renderPosts() {
  const list = $("#posts");
  list.innerHTML = "";
  POSTS.forEach((p, i) => list.appendChild(postNode(p, i)));

  $$(".post").forEach((post) => {
    const media = post.querySelector(".post-media");
    let lastTap = 0;
    media.addEventListener("pointerup", () => {
      const now = Date.now();
      if (now - lastTap < 300) {
        likePost(post, true);
        runBurst(media);
        lastTap = 0;
        return;
      }
      lastTap = now;
    });
  });
}

function runBurst(media) {
  const burst = media.querySelector(".burst");
  if (!burst) return;
  burst.classList.remove("run");
  void burst.offsetWidth;
  burst.classList.add("run");
}

function setLiked(post, on) {
  const btn = post.querySelector('[data-act="like"]');
  const ico = btn.querySelector(".ic");
  ico.classList.toggle("ic--heart-on", on);
  ico.classList.toggle("ic--heart", !on);
  post.classList.toggle("is-liked", on);
  btn.classList.remove("pop");
  void btn.offsetWidth;
  btn.classList.add("pop");
}

function likePost(post, force) {
  const btn = post.querySelector('[data-act="like"]');
  const liked = btn.querySelector(".ic").classList.contains("ic--heart-on");
  if (force && liked) return;
  const on = force ? true : !liked;
  setLiked(post, on);

  const likes = post.querySelector(".likes");
  const base = parseInt(likes.dataset.base, 10);
  const now = base + (on ? 1 : -1);
  likes.dataset.base = String(now);
  likes.textContent = fmtLikes(now) + " lượt thích";
  likes.classList.remove("flash");
  void likes.offsetWidth;
  likes.classList.add("flash");

  post.querySelector(".post-media").classList.remove("is-liked");
  if (on) void post.querySelector(".post-media").offsetWidth;
  post.querySelector(".post-media").classList.toggle("is-liked", on);
}

function bindFeed() {
  const list = $("#posts");

  list.addEventListener("click", (ev) => {
    const btn = ev.target.closest("[data-act]");
    if (!btn) return;
    const act = btn.dataset.act;
    const post = btn.closest(".post");

    if (act === "like") return likePost(post, false);

    if (act === "save") {
      const ico = btn.querySelector(".ic");
      const on = ico.classList.toggle("ic--save-on");
      ico.classList.toggle("ic--save", !on);
      btn.classList.remove("pop");
      void btn.offsetWidth;
      btn.classList.add("pop");
      toast(on ? "Đã lưu bài viết" : "Bỏ lưu bài viết");
      return;
    }

    if (act === "comment") return openComments(post);

    if (act === "share") return openShare(post);

    if (act === "more") return openMore(post);
  });

  $("#stories").addEventListener("click", (ev) => {
    const btn = ev.target.closest("[data-story]");
    if (!btn) return;
    const idx = Number(btn.dataset.story);
    if (STORIES[idx].me) return toast("Tạo tin mới");
    openViewer(idx);
  });
}

/* ---------------- story viewer ---------------- */

const viewer = {
  idx: 0,
  timer: null,
  items: [],
};

function openViewer(startIdx) {
  viewer.items = STORIES.filter((s) => !s.me);
  const i = Math.max(0, viewer.items.findIndex((s) => s === STORIES[startIdx]));
  viewer.idx = i < 0 ? 0 : i;

  const v = $("#viewer");
  v.classList.add("is-on");
  v.setAttribute("aria-hidden", "false");
  paintViewer();
}

function paintViewer() {
  const s = viewer.items[viewer.idx];
  if (!s) return closeViewer();

  $("#viewer-img").src = s.avatar.replace(/avatar-(\d+)/, "post-0" + (1 + (viewer.idx % 6)));
  $("#viewer-avatar").src = s.avatar;
  $("#viewer-name").textContent = s.name;
  $("#viewer-time").textContent = viewer.idx === 0 ? "vừa xong" : viewer.idx + " giờ";

  const bars = $("#viewer-bars");
  bars.innerHTML = "";
  viewer.items.forEach((_, i) => {
    const bar = el("div", "bar" + (i < viewer.idx ? " done" : i === viewer.idx ? " active" : ""));
    bar.appendChild(el("i"));
    bars.appendChild(bar);
  });

  const img = $("#viewer-img");
  img.style.animation = "none";
  void img.offsetWidth;
  img.style.animation = "";

  clearTimeout(viewer.timer);
  viewer.timer = setTimeout(viewerNext, 5000);
}

function viewerNext() {
  if (viewer.idx + 1 >= viewer.items.length) return closeViewer();
  viewer.idx += 1;
  paintViewer();
}

function viewerPrev() {
  if (viewer.idx === 0) return paintViewer();
  viewer.idx -= 1;
  paintViewer();
}

function closeViewer() {
  clearTimeout(viewer.timer);
  const v = $("#viewer");
  v.classList.remove("is-on");
  v.setAttribute("aria-hidden", "true");
}

/* ---------------- bottom sheet ---------------- */

let sheetOpen = false;

function openSheet(title, rows) {
  const sheet = $("#sheet");
  const scrim = $("#sheet-scrim");
  $("#sheet-head").textContent = title;
  const body = $("#sheet-body");
  body.innerHTML = "";

  rows.forEach((r) => {
    const b = el("button", "sheet-row" + (r.danger ? " danger" : ""));
    b.type = "button";
    b.innerHTML = icon(r.icon) + "<span>" + r.label + "</span>";
    b.addEventListener("click", () => {
      closeSheet();
      if (r.toast) setTimeout(() => toast(r.toast), 180);
    });
    body.appendChild(b);
  });

  sheet.classList.add("is-on");
  sheet.setAttribute("aria-hidden", "false");
  scrim.classList.add("is-on");
  sheetOpen = true;
  bindSheetDrag();
}

function closeSheet() {
  const sheet = $("#sheet");
  $("#sheet").classList.remove("is-on");
  sheet.setAttribute("aria-hidden", "true");
  $("#sheet-scrim").classList.remove("is-on");
  sheetOpen = false;
}

function bindSheetDrag() {
  const sheet = $("#sheet");
  if (sheet.dataset.dragBound) return;
  sheet.dataset.dragBound = "1";

  let startY = 0;
  let dy = 0;
  let dragging = false;

  const down = (ev) => {
    if (!sheetOpen) return;
    dragging = true;
    startY = ev.clientY;
    dy = 0;
    sheet.classList.add("dragging");
  };
  const move = (ev) => {
    if (!dragging) return;
    dy = Math.max(0, ev.clientY - startY);
    sheet.style.transform = "translateY(" + dy + "px)";
  };
  const up = () => {
    if (!dragging) return;
    dragging = false;
    sheet.classList.remove("dragging");
    sheet.style.transform = "";
    if (dy > 110) closeSheet();
  };

  sheet.addEventListener("pointerdown", down);
  window.addEventListener("pointermove", move);
  window.addEventListener("pointerup", up);
}

function openComments(post) {
  const user = post.querySelector(".post-user .name").textContent;
  const samples = [
    ["thuha.99", "assets/img/avatar-04.jpg", "góc này đẹp thật, chụp máy gì vậy"],
    ["quanghuy", "assets/img/avatar-05.jpg", "sương lên rồi đứng đây là chuẩn"],
    ["baotram", "assets/img/avatar-07.jpg", "lưu lại để đi"],
  ];
  const rows = samples.map(([n, av, txt]) => ({
    icon: "",
    label: "",
    raw: { n, av, txt },
  }));

  openSheet("Bình luận", []);

  const body = $("#sheet-body");
  body.innerHTML = "";
  body.append(
    (() => {
      const head = el("div", "comment-head");
      head.style.cssText =
        "display:flex;gap:10px;padding:12px 16px;border-bottom:1px solid var(--gray-02)";
      head.innerHTML =
        '<img class="avatar avatar--32" src="' +
        post.querySelector(".avatar").src +
        '" alt="">' +
        '<div style="font-size:13px;line-height:17px"><b>' +
        user +
        "</b> " +
        post.querySelector(".caption").textContent.replace(user, "").trim() +
        "</div>";
      return head;
    })()
  );

  samples.forEach(([n, av, txt]) => {
    const row = el("div");
    row.style.cssText = "display:flex;gap:10px;padding:10px 16px;font-size:13px;line-height:18px";
    row.innerHTML =
      '<img class="avatar avatar--32" src="' +
      av +
      '" alt=""><div><b>' +
      n +
      "</b> " +
      txt +
      '<div style="color:var(--text-2);font-size:11px;margin-top:3px">' +
      "1 giờ · Trả lời · Thích" +
      "</div></div>";
    body.appendChild(row);
  });

  const input = el("div");
  input.style.cssText =
    "display:flex;gap:10px;align-items:center;padding:10px 16px;border-top:1px solid var(--gray-02)";
  input.innerHTML =
    '<img class="avatar avatar--32" src="assets/img/avatar-01.jpg" alt="">' +
    '<input placeholder="Thêm bình luận..." style="flex:1;border:0;outline:none;font-size:13px">' +
    '<button style="border:0;background:none;color:var(--blue);font-weight:600;font-size:13px">Đăng</button>';
  body.appendChild(input);
}

function openShare() {
  openSheet("Chia sẻ", [
    { icon: "direct", label: "Gửi bằng tin nhắn", toast: "Chọn người nhận" },
    { icon: "link", label: "Sao chép liên kết", toast: "Đã sao chép liên kết" },
    { icon: "share", label: "Chia sẻ ra ngoài", toast: "Chưa nối ứng dụng ngoài" },
    { icon: "save", label: "Lưu vào bộ sưu tập", toast: "Đã lưu" },
  ]);
}

function openMore() {
  openSheet("Tuỳ chọn", [
    { icon: "save", label: "Lưu bài viết", toast: "Đã lưu bài viết" },
    { icon: "bell", label: "Bật thông báo cho bài viết", toast: "Đã bật thông báo" },
    { icon: "qr", label: "Mã QR của bài viết", toast: "Chưa dựng màn QR" },
    { icon: "flag", label: "Báo cáo", danger: true, toast: "Chưa dựng luồng báo cáo" },
  ]);
}

/* ---------------- DM inbox ---------------- */

function renderNotes() {
  const wrap = $("#notes");
  wrap.innerHTML = "";
  const row = el("div", "notes-row");

  NOTES.forEach((n) => {
    const b = el("button", "note");
    b.type = "button";
    b.dataset.toast = "Trả lời tin của " + n.name;
    b.innerHTML =
      '<span class="bubble">' +
      n.text +
      '</span><img class="avatar avatar--56" src="' +
      n.avatar +
      '" alt=""><span class="nm">' +
      n.name +
      "</span>";
    row.appendChild(b);
  });

  wrap.appendChild(row);
}

function threadRow(t, i) {
  const b = el("button", "thread-row" + (t.unread ? " unread" : ""));
  b.type = "button";
  b.style.setProperty("--i", i);
  b.dataset.thread = t.user;

  const av = el("div", "avatar-wrap");
  av.innerHTML =
    '<img class="avatar avatar--56" src="' +
    t.avatar +
    '" alt="">' +
    (t.presence ? '<span class="presence"></span>' : "");
  av.firstChild.classList.add("avatar--56");

  const body = el("div", "body");
  const line1 = el("div", "line1");
  line1.innerHTML =
    '<span class="who">' +
    t.user +
    "</span>" +
    (t.verified ? icon("verified") : "") +
    '<span class="meta">' +
    t.time +
    "</span>";

  const line2 = el("div", "line2");
  line2.innerHTML =
    (t.unread ? '<span class="unread-dot"></span>' : "") +
    '<span class="txt">' +
    t.preview +
    "</span>";

  body.append(line1, line2);
  b.append(av, body);
  return b;
}

function renderThreads(filter) {
  const list = $("#threads");
  list.innerHTML = "";
  const q = (filter || "").trim().toLowerCase();
  THREADS.filter((t) => !q || t.user.toLowerCase().includes(q)).forEach((t, i) => {
    list.appendChild(threadRow(t, i));
  });
}

function bindDm() {
  $("#threads").addEventListener("click", (ev) => {
    const row = ev.target.closest("[data-thread]");
    if (!row) return;
    const t = THREADS.find((x) => x.user === row.dataset.thread);
    if (t) openThread(t);
  });

  $("#dm-filter").addEventListener("input", (ev) => renderThreads(ev.target.value));
}

/* ---------------- thread ---------------- */

let activeThread = THREADS[0];

function openThread(t) {
  activeThread = t;
  t.unread = false;

  $("#th-avatar").src = t.avatar;
  $("#th-name").textContent = t.user;
  $("#th-status").textContent = t.presence ? "Đang hoạt động" : "Hoạt động 1 giờ trước";

  renderThreadBody();
  go("thread", { push: true });
  renderThreads($("#dm-filter").value);
}

function renderThreadBody() {
  const body = $("#thread-body");
  body.innerHTML = "";

  const sep = el("div", "day-sep");
  sep.textContent = "Hôm nay";
  body.appendChild(sep);

  activeThread.messages.forEach((m, i) => body.appendChild(msgNode(m, i)));
  scrollThread();
}

function msgNode(m, i) {
  const wrap = el("div", "msg " + (m.from === "me" ? "me" : "them"));
  wrap.style.setProperty("--i", i);

  if (m.from !== "me") {
    const img = el("img", "avatar avatar--24");
    img.src = activeThread.avatar;
    img.alt = "";
    wrap.appendChild(img);
  }

  const bubble = el("div", "bubble");
  bubble.textContent = m.text;
  if (/^\p{Emoji}+$/u.test(m.text)) bubble.classList.add("emoji-only");
  bubble.dataset.msg = String(i);
  if (m.liked) {
    const badge = el("span", "liked", icon("heart-on"));
    bubble.appendChild(badge);
  }
  wrap.appendChild(bubble);
  return wrap;
}

function scrollThread() {
  const body = $("#thread-body");
  requestAnimationFrame(() => {
    body.scrollTop = body.scrollHeight;
  });
}

function appendMessage(m) {
  const body = $("#thread-body");
  const node = msgNode(m, activeThread.messages.length - 1);
  node.style.animationDelay = "0ms";
  body.appendChild(node);
  scrollThread();
  return node;
}

function showTyping() {
  const body = $("#thread-body");
  const t = el("div", "typing");
  t.id = "typing";
  t.innerHTML =
    '<img class="avatar avatar--24" src="' +
    activeThread.avatar +
    '" alt=""><span class="bubble"><i></i><i></i><i></i></span>';
  body.appendChild(t);
  scrollThread();
  return t;
}

function hideTyping() {
  const t = $("#typing");
  if (t) t.remove();
}

function sendMessage(text) {
  const clean = text.trim();
  if (!clean) return;

  const msg = { from: "me", text: clean, t: "now" };
  activeThread.messages.push(msg);

  const node = appendMessage(msg);
  node.querySelector(".bubble").classList.add("sending");
  scrollThread();

  setTimeout(() => node.querySelector(".bubble").classList.remove("sending"), 420);

  setTimeout(() => {
    const typing = showTyping();
    setTimeout(() => {
      typing.remove();
      const reply = {
        from: "them",
        text: REPLIES[Math.floor(Math.random() * REPLIES.length)],
        t: "now",
      };
      activeThread.messages.push(reply);
      appendMessage(reply);
    }, 1100 + Math.random() * 700);
  }, 700);
}

function bindThread() {
  const input = $("#dm-input");
  const composer = $("#composer");
  const sent = () => {
    sendMessage(input.value);
    input.value = "";
    composer.classList.remove("has-text");
    input.focus();
  };

  input.addEventListener("input", () => {
    composer.classList.toggle("has-text", input.value.trim().length > 0);
  });

  input.addEventListener("keydown", (ev) => {
    if (ev.key === "Enter" && !ev.shiftKey) {
      ev.preventDefault();
      sent();
    }
  });

  $("#dm-send").addEventListener("click", sent);

  // double tap a bubble to like it, long press for the reaction bar
  const body = $("#thread-body");
  let lastTap = 0;
  let pressTimer = null;

  body.addEventListener("pointerdown", (ev) => {
    const bubble = ev.target.closest(".bubble");
    if (!bubble) return;
    pressTimer = setTimeout(() => {
      showReactions(bubble);
    }, 430);
  });

  body.addEventListener("pointerup", (ev) => {
    clearTimeout(pressTimer);
    const bubble = ev.target.closest(".bubble");
    if (!bubble) return;
    const now = Date.now();
    if (now - lastTap < 300) {
      toggleMsgLike(bubble);
      lastTap = 0;
      return;
    }
    lastTap = now;
  });

  body.addEventListener("pointercancel", () => clearTimeout(pressTimer));
}

function toggleMsgLike(bubble) {
  let badge = bubble.querySelector(".liked");
  if (badge) {
    badge.remove();
    return;
  }
  badge = el("span", "liked", icon("heart-on"));
  bubble.appendChild(badge);
  badge.animate(
    [
      { transform: "scale(0.4)", opacity: 0 },
      { transform: "scale(1.16)", opacity: 1 },
      { transform: "scale(1)", opacity: 1 },
    ],
    { duration: 320, easing: "cubic-bezier(0.18,1.22,0.4,1)" }
  );
}

const REACTIONS = ["❤️", "😂", "😮", "😢", "🔥", "👏"];

function showReactions(bubble) {
  const bar = $("#reaction-bar");
  bar.innerHTML = "";
  REACTIONS.forEach((r) => {
    const b = el("button");
    b.type = "button";
    b.textContent = r;
    b.addEventListener("click", () => {
      hideReactions();
      let badge = bubble.querySelector(".liked");
      if (badge) badge.remove();
      badge = el("span", "liked");
      badge.textContent = r;
      bubble.appendChild(badge);
      badge.animate(
        [
          { transform: "scale(0.4)", opacity: 0 },
          { transform: "scale(1.2)", opacity: 1 },
          { transform: "scale(1)", opacity: 1 },
        ],
        { duration: 300, easing: "cubic-bezier(0.18,1.22,0.4,1)" }
      );
    });
    bar.appendChild(b);
  });

  // anchor the bar just above the pressed bubble, clamped inside the screen.
  // the device frame is CSS-scaled on short viewports, so convert the measured
  // (scaled) rects back into the screen's own layout pixels before positioning.
  const screenEl = $("#screen-thread");
  const screen = screenEl.getBoundingClientRect();
  const rect = bubble.getBoundingClientRect();
  const scale = screen.width / screenEl.offsetWidth || 1;
  const barW = bar.offsetWidth || 240;
  const barH = bar.offsetHeight || 44;
  const pad = 8;

  const bubbleTop = (rect.top - screen.top) / scale;
  const bubbleLeft = (rect.left - screen.left) / scale;
  const bubbleW = rect.width / scale;

  let top = bubbleTop - barH - pad;
  top = Math.max(pad, Math.min(top, screenEl.offsetHeight - barH - pad));

  let left = bubbleLeft + bubbleW / 2 - barW / 2;
  left = Math.max(pad, Math.min(left, screenEl.offsetWidth - barW - pad));

  bar.style.left = Math.round(left) + "px";
  bar.style.top = Math.round(top) + "px";
  bar.style.bottom = "auto";

  bar.classList.add("is-on");
  clearTimeout(showReactions._t);
  showReactions._t = setTimeout(hideReactions, 3200);
}

function hideReactions() {
  $("#reaction-bar").classList.remove("is-on");
}

/* ---------------- auth ---------------- */

function bindFloatLabels() {
  $$(".field input").forEach((input) => {
    const field = input.closest(".field");
    const sync = () => field.classList.toggle("is-filled", input.value.length > 0);
    input.addEventListener("input", sync);
    input.addEventListener("blur", sync);
    sync();
  });
}

function bindReveal() {
  $$("[data-reveal]").forEach((btn) => {
    btn.addEventListener("click", () => {
      const input = btn.parentElement.querySelector("input");
      const ico = btn.querySelector(".ic");
      const hide = input.type === "password";
      input.type = hide ? "text" : "password";
      ico.classList.toggle("ic--eye", !hide);
      ico.classList.toggle("ic--eye-off", hide);
      btn.setAttribute("aria-label", hide ? "Ẩn mật khẩu" : "Hiện mật khẩu");
    });
  });
}

function shakeField(input) {
  const field = input.closest(".field");
  field.classList.remove("shake");
  void field.offsetWidth;
  field.classList.add("shake");
}

function bindAuth() {
  const loginForm = $("#login-form");
  const loginErr = $("#login-error");
  const loginBtn = $("#login-submit");

  loginForm.addEventListener("submit", (ev) => {
    ev.preventDefault();
    const fd = new FormData(loginForm);
    const user = String(fd.get("username") || "").trim();
    const pass = String(fd.get("password") || "");

    if (!user) {
      loginErr.textContent = "Nhập tên người dùng.";
      loginErr.classList.add("is-on");
      return shakeField(loginForm.querySelector('[name="username"]'));
    }
    if (pass.length < 6) {
      loginErr.textContent = "Mật khẩu phải có ít nhất 6 ký tự.";
      loginErr.classList.add("is-on");
      return shakeField(loginForm.querySelector('[name="password"]'));
    }
    loginErr.classList.remove("is-on");
    loginBtn.classList.add("busy");
    setTimeout(() => {
      loginBtn.classList.remove("busy");
      toast("Đăng nhập thành công (demo)");
      go("feed");
    }, 780);
  });

  const regForm = $("#register-form");
  const regErr = $("#register-error");
  const regBtn = $("#register-submit");

  regForm.addEventListener("submit", (ev) => {
    ev.preventDefault();
    const fd = new FormData(regForm);
    const email = String(fd.get("email") || "").trim();
    const pass = String(fd.get("password") || "");
    const fullname = String(fd.get("fullname") || "").trim();
    const user = String(fd.get("username") || "").trim();

    const missing =
      !email
        ? '[name="email"]'
        : pass.length < 6
        ? '[name="password"]'
        : !fullname
        ? '[name="fullname"]'
        : !user
        ? '[name="username"]'
        : null;

    if (missing) {
      regErr.textContent =
        missing === '[name="password"]'
          ? "Mật khẩu phải có ít nhất 6 ký tự."
          : "Điền đủ các trường để tạo tài khoản.";
      regErr.classList.add("is-on");
      return shakeField(regForm.querySelector(missing));
    }

    regErr.classList.remove("is-on");
    regBtn.classList.add("busy");
    setTimeout(() => {
      regBtn.classList.remove("busy");
      toast("Đã tạo tài khoản (demo)");
      go("feed");
    }, 780);
  });
}

/* ---------------- routing ---------------- */

const DEPTH = { feed: 0, dm: 1, thread: 2, login: 0, register: 0 };
let current = "feed";
let stack = ["feed"];
let animating = false;

function screenOf(route) {
  return document.getElementById("screen-" + route);
}

function go(route, opts) {
  const push = opts && opts.push;
  if (!screenOf(route) || animating) return;
  if (route === current) {
    if (push) stack.push(route);
    return;
  }

  const from = screenOf(current);
  const to = screenOf(route);
  const dir =
    DEPTH[route] > DEPTH[current] ? "push" : DEPTH[route] < DEPTH[current] ? "pop" : "fade";

  animating = true;

  from.classList.remove("is-active", "anim-push", "anim-pop", "anim-fade");
  to.classList.add("is-active", "anim-" + dir);
  to.scrollTop = 0;

  const done = () => {
    to.classList.remove("anim-push", "anim-pop", "anim-fade");
    animating = false;
  };
  to.addEventListener("animationend", done, { once: true });
  setTimeout(done, 420);

  if (push) {
    stack.push(route);
  } else {
    const i = stack.indexOf(route);
    if (i >= 0) stack = stack.slice(0, i + 1);
    else stack = [route];
  }

  $$(".routebar button").forEach((b) => {
    b.setAttribute("aria-selected", String(b.dataset.route === route));
  });

  current = route;
  if (location.hash.slice(1) !== route) history.replaceState(null, "", "#" + route);
}

function back() {
  if (stack.length > 1) {
    stack.pop();
    go(stack[stack.length - 1]);
    return;
  }
  go("feed");
}

function bindNav() {
  $$(".routebar button").forEach((b) => {
    b.addEventListener("click", () => {
      go(b.dataset.route);
      closeRouteMenu();
    });
  });

  const fab = $("#routefab");
  const bar = $("#routebar");
  if (fab && bar) {
    fab.addEventListener("click", () => {
      const open = bar.classList.toggle("is-open");
      fab.setAttribute("aria-expanded", String(open));
    });
    $$(".routebar button").forEach((b) =>
      b.addEventListener("click", closeRouteMenu)
    );
  }

  document.addEventListener("click", (ev) => {
    const backBtn = ev.target.closest("[data-back]");
    if (backBtn) return back();

    const link = ev.target.closest("[data-route]");
    if (link) {
      ev.preventDefault();
      return go(link.dataset.route, { push: true });
    }

    const t = ev.target.closest("[data-toast]");
    if (t) {
      if (t.tagName === "A") ev.preventDefault();
      return toast(t.dataset.toast);
    }
  });

  window.addEventListener("keydown", (ev) => {
    if (ev.key === "Escape") {
      if (viewerOpen()) return closeViewer();
      if (sheetOpen) return closeSheet();
      hideReactions();
    }
  });

  $("#sheet-scrim").addEventListener("click", closeSheet);

  $("#viewer").addEventListener("click", (ev) => {
    if (ev.target.closest("[data-viewer-close]")) return closeViewer();
    if (ev.target.closest("[data-viewer-next]")) return viewerNext();
    if (ev.target.closest("[data-viewer-prev]")) return viewerPrev();
  });

  history.replaceState(null, "", "#" + current);
}

function viewerOpen() {
  return $("#viewer").classList.contains("is-on");
}

function closeRouteMenu() {
  const bar = $("#routebar");
  const fab = $("#routefab");
  if (!bar || !fab) return;
  bar.classList.remove("is-open");
  fab.setAttribute("aria-expanded", "false");
}

/* ---------------- device fit ---------------- */

function fitDevice() {
  const device = $(".device");
  const wrap = $(".wrap");
  if (!device || !wrap) return;

  if (window.matchMedia("(max-width: 640px)").matches) {
    device.style.transform = "";
    wrap.style.minHeight = "";
    return;
  }

  device.style.transform = "none";
  wrap.style.minHeight = "";
  const avail = window.innerHeight - 24 - 32 - 44 - 16;
  const scale = Math.min(1, avail / device.offsetHeight);
  device.style.transform = scale < 1 ? "scale(" + scale.toFixed(3) + ")" : "";
  wrap.style.minHeight = scale < 1 ? window.innerHeight / scale + "px" : "";
}

/* ---------------- clock ---------------- */

function tickClock() {
  const n = new Date();
  const hh = String(n.getHours()).padStart(2, "0");
  const mm = String(n.getMinutes()).padStart(2, "0");
  const c = $("#sb-clock");
  if (c) c.textContent = hh + ":" + mm;
}

/* ---------------- boot ---------------- */

function boot() {
  const initial = (location.hash || "").replace("#", "");

  renderStories();
  renderPosts();
  renderNotes();
  renderThreads("");
  bindFeed();
  bindDm();
  bindThread();
  bindFloatLabels();
  bindReveal();
  bindAuth();
  bindNav();
  fitDevice();
  tickClock();
  setInterval(tickClock, 20000);
  window.addEventListener("resize", fitDevice);

  if (initial === "thread") {
    openThread(THREADS[0]);
    return;
  }
  if (DEPTH[initial] !== undefined && initial !== "feed") {
    current = "feed";
    stack = ["feed"];
    go(initial);
  }

  // capture hooks: ?fx=viewer|sheet|share|more keeps a demo overlay open on load
  const fx = new URLSearchParams(location.search).get("fx");
  if (fx === "viewer") openViewer(1);
  if (fx === "sheet") openComments($$(".post")[0]);
  if (fx === "share") openShare();
  if (fx === "more") openMore();
  if (fx === "reaction" || fx === "typing") {
    openThread(THREADS[0]);
    if (fx === "reaction") {
      setTimeout(() => {
        const bubble = $("#thread-body .msg .bubble");
        if (bubble) showReactions(bubble);
      }, 380);
    } else {
      setTimeout(showTyping, 300);
    }
  }
}

document.addEventListener("DOMContentLoaded", boot);
