/* KakaoStory clone — data, rendering, routing, motion.
   All copy is sample content. Media is generated placeholder art (export/make_media.py).
   Motion timings mirror res/anim + res/integers.xml: activity_transition_time = 150. */

/* ---------------------------------------------------------------- data */

const USERS = {
  me: { name: "Linh Trần", avatar: "assets/img/me.jpg", badge: "birth" },
  jieun: { name: "Park Ji-eun", avatar: "assets/img/avatar-01.jpg", badge: "teller" },
  minho: { name: "Kim Min-ho", avatar: "assets/img/avatar-02.jpg" },
  seoah: { name: "Seo-ah Yoon", avatar: "assets/img/avatar-03.jpg", badge: "teller" },
  hana: { name: "Hana Lee", avatar: "assets/img/avatar-04.jpg" },
  dokyung: { name: "Do-kyung Nam", avatar: "assets/img/avatar-05.jpg", badge: "channel" },
  yuna: { name: "Choi Yu-na", avatar: "assets/img/avatar-06.jpg" },
  taehyun: { name: "Tae-hyun Oh", avatar: "assets/img/avatar-07.jpg" },
  sora: { name: "Bae So-ra", avatar: "assets/img/avatar-08.jpg" },
  jae: { name: "Jae-won Lim", avatar: "assets/img/avatar-09.jpg" },
  noor: { name: "Noor Haddad", avatar: "assets/img/avatar-10.jpg" },
};

const EMO = [
  { key: "like", label: "Like", img: "assets/icons/ico_emotion_s_like.png" },
  { key: "good", label: "Cool", img: "assets/icons/ico_emotion_s_cool.png" },
  { key: "pleasure", label: "Happy", img: "assets/icons/ico_emotion_s_happy.png" },
  { key: "sad", label: "Sad", img: "assets/icons/ico_emotion_s_sad.png" },
  { key: "cheerup", label: "Cheer Up", img: "assets/icons/ico_emotion_s_cheer.png" },
];

const POSTS = [
  {
    id: "p1",
    user: "jieun",
    time: "12 phút",
    scope: "Bạn bè",
    img: "assets/img/post-01.jpg",
    wide: false,
    count: 3,
    title: "Sương xuống muộn, cả đồi chỉ còn tiếng gió",
    body: "Đứng ở mép đồi chờ trời sáng. Không ai nói gì, cũng không cần nói. #dalat #morninglight",
    tags: ["#dalat", "#morninglight"],
    like: 1284,
    comment: 42,
    reactions: ["like", "pleasure", "good"],
    picked: null,
    latest: { user: "minho", text: "Cái này chụp lúc mấy giờ vậy?" },
  },
  {
    id: "p2",
    user: "dokyung",
    time: "48 phút",
    scope: "Công khai",
    img: "assets/img/post-02.jpg",
    wide: true,
    count: 1,
    title: "Roll cuối của chuyến đi",
    body: "Không chỉnh màu. Chỉ đợi đúng 6 giờ chiều. #filmphotography",
    tags: ["#filmphotography"],
    like: 20417,
    comment: 316,
    reactions: ["like", "cheerup", "pleasure", "good"],
    picked: null,
    latest: { user: "seoah", text: "Tone này là Portra hả anh?" },
  },
  {
    id: "p3",
    user: "hana",
    time: "2 giờ",
    scope: "Bạn bè",
    img: "assets/img/post-03.jpg",
    wide: false,
    count: 2,
    title: "",
    body: "Ăn lần rồi mới thấy phở đúng thật. Mai về.",
    tags: [],
    like: 873,
    comment: 18,
    reactions: ["like", "pleasure"],
    picked: "like",
    latest: { user: "yuna", text: "Địa chỉ đi, đừng giấu." },
  },
  {
    id: "p4",
    user: "taehyun",
    time: "5 giờ",
    scope: "Công khai",
    img: "assets/img/post-04.jpg",
    wide: false,
    count: 4,
    title: "Ba tháng đợi giờ dậy",
    body: "Không nghĩ lại được nữa — sáng sớm là phần thưởng, không phải hình phạt.",
    tags: [],
    like: 3412,
    comment: 91,
    reactions: ["like", "good", "cheerup"],
    picked: "cheerup",
    latest: { user: "noor", text: "Giờ này tôi còn đang ngủ." },
  },
  {
    id: "p5",
    user: "yuna",
    time: "1 ngày",
    scope: "Bạn bè",
    img: "assets/img/post-05.jpg",
    wide: true,
    count: 1,
    title: "Mưa tới trước dự báo",
    body: "Ướt hết nhưng vẫn kịp chụp.",
    tags: ["#saigon", "#rain"],
    like: 56230,
    comment: 704,
    reactions: ["like", "sad", "pleasure", "good", "cheerup"],
    picked: null,
    latest: { user: "jae", text: "Máy ảnh có sao không?" },
  },
  {
    id: "p6",
    user: "sora",
    time: "1 ngày",
    scope: "Bạn bè",
    img: "assets/img/post-06.jpg",
    wide: false,
    count: 1,
    title: "",
    body: "Góc này không ai đứng, nên đứng. Xong đi ăn.",
    tags: [],
    like: 640,
    comment: 7,
    reactions: ["like"],
    picked: null,
    latest: null,
  },
];

const NOTIS = [
  { id: 1, user: "minho", msg: "<b>Kim Min-ho</b> đã để lại cảm xúc <b>Like</b>", content: "Sương xuống muộn, cả đồi chỉ còn tiếng gió", type: "Trên story của bạn", when: "3 phút", thumb: "img", thumbSrc: "assets/img/post-01.jpg", unread: true, kind: "activity" },
  { id: 2, user: "seoah", msg: "<b>Seo-ah Yoon</b> đã bình luận", content: "Tone này là Portra hả anh?", type: "Bình luận", when: "12 phút", thumb: "img", thumbSrc: "assets/img/post-02.jpg", unread: true, kind: "activity" },
  { id: 3, user: "hana", msg: "<b>Hana Lee</b> đã gửi lời mời kết bạn", content: "Bạn và Hana có 12 người bạn chung", type: "Lời mời", when: "26 phút", thumb: "req", unread: true, kind: "activity" },
  { id: 4, user: "jae", msg: "<b>Jae-won Lim</b> đã chia sẻ story của bạn", content: "“Ba tháng đợi giờ dậy”", type: "Chia sẻ", when: "1 giờ", thumb: "img", thumbSrc: "assets/img/post-04.jpg", unread: false, kind: "activity" },
  { id: 5, user: "yuna", msg: "<b>Choi Yu-na</b> đã thả <b>Happy</b>", content: "Mưa tới trước dự báo", type: "Story", when: "2 giờ", thumb: "img", thumbSrc: "assets/img/post-05.jpg", unread: false, kind: "activity" },
  { id: 6, user: "noor", msg: "<b>Noor Haddad</b> đã trả lời bình luận", content: "“Giờ này tôi còn đang ngủ.”", type: "Trả lời", when: "5 giờ", thumb: "img", thumbSrc: "assets/img/post-04.jpg", unread: false, kind: "activity" },
  { id: 7, user: "dokyung", msg: "<b>Do-kyung Nam</b>", content: "Mai đi sớm không? Sương còn dày.", type: "Tin nhắn", when: "8 phút", thumb: "text", thumbText: "ok chốt", unread: true, kind: "message" },
  { id: 8, user: "jieun", msg: "<b>Park Ji-eun</b>", content: "Gửi tôi cái roll hôm qua với.", type: "Tin nhắn", when: "40 phút", thumb: "text", thumbText: "đang chọn", unread: false, kind: "message" },
  { id: 9, user: "sora", msg: "<b>Bae So-ra</b>", content: "Ảnh đẹp quá, chỉnh màu gì thế?", type: "Tin nhắn", when: "3 giờ", thumb: "text", thumbText: "không chỉnh", unread: false, kind: "message" },
];

const FRIENDS = [
  { user: "jieun", status: "오늘도 좋은 하루", close: true },
  { user: "minho", status: "출근 중 · 08:12", close: true },
  { user: "seoah", status: "필름 카메라 다시 시작", close: true },
  { user: "hana", status: "Đà Lạt · 3 ngày", close: false },
  { user: "dokyung", status: "부산", close: false },
  { user: "yuna", status: "비 오는 날엔 사진", close: true },
  { user: "taehyun", status: "새벽 5시 러닝", close: false },
  { user: "sora", status: "", close: false },
  { user: "jae", status: "요즘 커피에 빠짐", close: false },
  { user: "noor", status: "Amman → Seoul", close: false },
];

const EXPLORE = [
  { img: "assets/img/post-03.jpg", user: "hana" },
  { img: "assets/img/post-05.jpg", user: "yuna" },
  { img: "assets/img/post-01.jpg", user: "jieun" },
  { img: "assets/img/post-07.jpg", user: "taehyun" },
  { img: "assets/img/post-02.jpg", user: "dokyung" },
  { img: "assets/img/post-04.jpg", user: "taehyun" },
  { img: "assets/img/post-06.jpg", user: "sora" },
  { img: "assets/img/post-08.jpg", user: "jae" },
  { img: "assets/img/post-01.jpg", user: "noor" },
  { img: "assets/img/post-05.jpg", user: "seoah" },
  { img: "assets/img/post-03.jpg", user: "minho" },
  { img: "assets/img/post-07.jpg", user: "hana" },
];

const TABBAR = [
  { tab: "home", route: "home", img: "assets/icons/btn_mystory_menu_thumbview.png" },
  { tab: "feed", route: "feed", img: "assets/icons/btn_mystory_menu_feed.png" },
  { tab: "write", route: "write", img: "assets/icons/write_normal.png" },
  { tab: "explore", route: "explore", img: "assets/icons/btn_mystory_menu_friends.png" },
  { tab: "friends", route: "friends", img: "assets/icons/btn_mystory_menu_me.png" },
];

/* ---------------------------------------------------------------- helpers */

const $ = (sel, root = document) => root.querySelector(sel);
const $$ = (sel, root = document) => [...root.querySelectorAll(sel)];
const el = (tag, cls, html) => {
  const n = document.createElement(tag);
  if (cls) n.className = cls;
  if (html != null) n.innerHTML = html;
  return n;
};
const emoImg = (key) => (EMO.find((e) => e.key === key) || EMO[0]).img;
const compact = (n) =>
  n >= 10000 ? `${Math.floor(n / 1000) / 10}만` : n >= 1000 ? `${(n / 1000).toFixed(1)}k` : `${n}`;
const badgeFor = (user) => {
  const b = USERS[user]?.badge;
  return b ? `<img src="assets/icons/ico_badge_${b}.png" alt="" />` : "";
};

/* ---------------------------------------------------------------- render: feed */

function feedCard(post, index) {
  const u = USERS[post.user];
  const card = el("article", "fcard");
  card.style.setProperty("--i", index);
  card.dataset.post = post.id;

  const bodyHtml = post.body
    ? post.body.replace(
        /(#[^\s]+)/g,
        '<span class="tag">$1</span>'
      )
    : "";

  card.innerHTML = `
    <div class="fhead">
      <img class="favatar" src="${u.avatar}" alt="" data-profile="${post.user}" />
      <div class="fnames">
        <div class="fname">${u.name}${badgeFor(post.user)}</div>
        <div class="ftime">${post.time}<span class="scope">${post.scope}</span></div>
      </div>
      <button class="fmenu" data-menu="${post.id}" aria-label="Tuỳ chọn">
        <img src="assets/icons/ico_more_darkgray.png" alt="" />
      </button>
    </div>
    ${
      post.img
        ? `<div class="fmedia-wrap">
             <img class="fmedia${post.wide ? " is-wide" : ""}" src="${post.img}" alt="" data-open="${post.id}" />
             ${post.count > 1 ? `<span class="fcount">1 / ${post.count}</span>` : ""}
           </div>`
        : ""
    }
    ${post.title ? `<h2 class="ftitle">${post.title}</h2>` : ""}
    ${bodyHtml ? `<p class="fbody"${post.title ? "" : ' style="padding-top:16px"'}>${bodyHtml}</p>` : ""}
    <div class="fbar">
      <button data-comment="${post.id}">
        <img src="assets/icons/ico_emotion_s_like.png" alt="" />
        <span class="fcount-n">${compact(post.like)}</span>
      </button>
      <button data-comment="${post.id}">
        <span>${post.comment} bình luận</span>
      </button>
      <span class="react" data-react="${post.id}">
        ${post.reactions.slice(0, 3).map((k) => `<img src="${emoImg(k)}" alt="" />`).join("")}
        ${post.reactions.length > 3 ? `<span>+${post.reactions.length - 3}</span>` : ""}
      </span>
    </div>
    ${
      post.latest
        ? `<div class="flatest">
             <img src="${USERS[post.latest.user].avatar}" alt="" />
             <div class="flatest-body">
               <b>${USERS[post.latest.user].name}</b>${post.latest.text}
             </div>
           </div>`
        : ""
    }
    <div class="factions">
      <button class="f-like${post.picked ? " emo-on" : ""}" data-like="${post.id}">
        <img src="${post.picked ? emoImg(post.picked) : "assets/icons/ico_emotion_s_like.png"}" alt="" />
        ${post.picked ? EMO.find((e) => e.key === post.picked).label : "Cảm xúc"}
      </button>
      <button data-comment="${post.id}">
        <img src="assets/icons/ico_emotion_s_up.png" alt="" />Bình luận
      </button>
      <button data-share="${post.id}">
        <img src="assets/icons/ico_feed_share.png" alt="" />Chia sẻ
      </button>
    </div>`;
  return card;
}

function renderFeed() {
  const list = $("#feedlist");
  list.innerHTML = "";
  POSTS.forEach((p, i) => list.appendChild(feedCard(p, i)));
}

function renderMyGrid(mode = "grid") {
  const wrap = $("#mygrid");
  wrap.innerHTML = "";
  wrap.className = "gridwrap" + (mode === "grid" ? "" : " rows");

  if (mode === "grid") {
    POSTS.map((p, i) => ({ p, i })).forEach(({ p, i }) => {
      const cell = el("div", "gcell");
      cell.innerHTML = `
        <img src="${p.img}" alt="" data-open="${p.id}" />
        <span class="gmeta"><img src="assets/icons/ico_emotion_s_like.png" alt="" />${compact(p.like)}</span>`;
      cell.style.setProperty("--i", i);
      wrap.appendChild(cell);
    });
  } else if (mode === "feed") {
    POSTS.slice(0, 4).forEach((p, i) => {
      const row = el("div", "grow");
      const u = USERS[p.user];
      row.innerHTML = `
        <div class="gp">
          <img src="${u.avatar}" alt="" />
          <div>
            <div class="gn">${u.name}</div>
            <div class="gd">${p.time} · ${p.scope}</div>
          </div>
        </div>
        ${p.title ? `<div class="gt" style="font-weight:700;margin-bottom:4px">${p.title}</div>` : ""}
        <div class="gt">${p.body}</div>
        <div class="gimgs">${[p.img, "assets/img/post-0" + (((i + 3) % 8) + 1) + ".jpg"]
          .map((s) => `<img src="${s}" alt="" />`)
          .join("")}</div>`;
      wrap.appendChild(row);
    });
  } else if (mode === "discovery") {
    const head = el("div", "fsection", "Suggested Stories");
    wrap.appendChild(head);
    FRIENDS.slice(0, 4).forEach((f) => {
      const u = USERS[f.user];
      const row = el("div", "grow");
      row.innerHTML = `
        <div class="gp">
          <img src="${u.avatar}" alt="" />
          <div style="flex:1">
            <div class="gn">${u.name}${badgeFor(f.user)}</div>
            <div class="gd">${f.status || "Story mới · 2 giờ"}</div>
          </div>
          <button class="fmini" data-follow="${f.user}">Theo dõi</button>
        </div>`;
      wrap.appendChild(row);
    });
  } else {
    wrap.appendChild(
      el(
        "div",
        "grow intro",
        `<div class="gt">Đà Lạt, Việt Nam<br />Film · 35mm · Portra 400<br />daydreaming.story</div>`
      )
    );
    wrap.appendChild(el("div", "fsection", "Album"));
    const album = el("div", "galbum");
    album.innerHTML = ["post-02", "post-04", "post-06", "post-08"]
      .map((p) => `<img src="assets/img/${p}.jpg" alt="" />`)
      .join("");
    wrap.appendChild(album);
    wrap.appendChild(el("div", "fsection", "Liên kết"));
    wrap.appendChild(
      el("div", "grow", `<div class="gt link-row">daydreaming.story<span>blog</span></div>`)
    );
  }
}

/* ---------------------------------------------------------------- render: detail */

function renderDetail(postId) {
  const post = POSTS.find((p) => p.id === postId) || POSTS[0];
  const u = USERS[post.user];
  const host = $("#detailbody");

  host.innerHTML = `
    <div class="fcard">
      <div class="fhead" style="padding-top:14px">
        <img class="favatar" src="${u.avatar}" alt="" />
        <div class="fnames">
          <div class="fname">${u.name}${badgeFor(post.user)}</div>
          <div class="ftime">${post.time}<span class="scope">${post.scope}</span></div>
        </div>
        <button class="fmenu" data-menu="${post.id}">
          <img src="assets/icons/ico_more_darkgray.png" alt="" />
        </button>
      </div>
      ${
        post.title
          ? `<h2 class="ftitle" style="padding-top:14px">${post.title}</h2>`
          : ""
      }
      <div class="detail-lead">
        <p class="fbody" style="padding:6px 0 10px">${post.body.replace(
          /(#[^\s]+)/g,
          '<span class="tag">$1</span>'
        )}</p>
      </div>
      <div class="detail-images">
        <img src="${post.img}" alt="" data-open-src="${post.img}" />
      </div>
      <div class="fbar" style="margin:0 16px">
        <button data-comment="${post.id}">
          <img src="assets/icons/ico_emotion_s_like.png" alt="" />
          <span class="fcount-n">${compact(post.like)}</span>
        </button>
        <button><span>${post.comment} bình luận</span></button>
        <span class="react">
          ${post.reactions
            .slice(0, 3)
            .map((k) => `<img src="${emoImg(k)}" alt="" />`)
            .join("")}
        </span>
      </div>
      <div class="factions" style="margin:6px 16px 10px;border-top:0;border-radius:12px;background:var(--light-gray)">
        <button class="f-like${post.picked ? " emo-on" : ""}" data-like="${post.id}">
          <img src="${post.picked ? emoImg(post.picked) : "assets/icons/ico_emotion_s_like.png"}" alt="" />
          ${post.picked ? EMO.find((e) => e.key === post.picked).label : "Cảm xúc"}
        </button>
        <button data-comment="${post.id}">
          <img src="assets/icons/ico_emotion_s_up.png" alt="" />Bình luận
        </button>
        <button data-share="${post.id}">
          <img src="assets/icons/ico_feed_share.png" alt="" />Chia sẻ
        </button>
      </div>
    </div>`;

  const seed = [
    { user: "minho", text: "Cái này chụp lúc mấy giờ vậy?", when: "2 phút", likes: 3 },
    { user: "seoah", text: "Tone màu này là gì thế? Trông như film thật.", when: "8 phút", likes: 12 },
    { user: "hana", text: "Chỗ này tôi cũng đứng rồi, sáng sớm rất yên.", when: "21 phút", likes: 1 },
    { user: "noor", text: "Đẹp quá, lưu lại để lên lịch đi.", when: "44 phút", likes: 5 },
  ];
  const box = $("#comments");
  box.innerHTML = "";
  seed.forEach((c, i) => {
    const u2 = USERS[c.user];
    const row = el("div", "comment");
    row.style.animationDelay = i * 30 + "ms";
    row.innerHTML = `
      <img class="cav" src="${u2.avatar}" alt="" />
      <div class="cbody">
        <div class="cname">${u2.name}${badgeFor(c.user)}</div>
        <div class="ctext">${c.text}</div>
        <div class="cmeta">
          <span>${c.when}</span><span class="dot"></span>
          <span>Thích · ${c.likes}</span><span class="dot"></span>
          <span>Trả lời</span>
        </div>
      </div>
      <button class="clike" data-clike aria-label="Thích bình luận">
        <img src="assets/icons/ico_emotion_s_like.png" alt="" />
      </button>`;
    box.appendChild(row);
  });

  $("#detailscroll").scrollTop = 0;
  $("#detailscroll").dataset.post = post.id;
  syncCommentPost();
}

function syncCommentPost() {
  const id = $("#detailscroll").dataset.post;
  const post = POSTS.find((p) => p.id === id);
  const btn = $("#cbPost");
  if (btn) btn.disabled = !$("#cbInput").value.trim();
  const emo = $("#cbEmo img");
  if (emo) emo.src = post?.picked ? emoImg(post.picked) : "assets/icons/ico_emotion_s_like.png";
}

/* ---------------------------------------------------------------- render: noti */

let notiTab = "activity";

function renderNoti() {
  const list = $("#notilist");
  list.innerHTML = "";
  const items = NOTIS.filter((n) => n.kind === notiTab);
  const unread = items.filter((n) => n.unread).length;
  $("#ncount").textContent = unread || "";
  $("#ncount").style.display = unread ? "" : "none";

  items.forEach((n, i) => {
    const u = USERS[n.user];
    const row = el("div", "nrow" + (n.unread ? "" : " is-read"));
    row.style.setProperty("--i", i);
    row.innerHTML = `
      <div class="nav-wrap">
        <img class="av" src="${u.avatar}" alt="" />
        <span class="ring"></span>
        ${n.unread ? `<span class="unread"></span>` : ""}
      </div>
      <div class="nbody">
        <div class="nmsg">${n.msg}</div>
        <div class="ncontent">${n.content}</div>
        <div class="nmeta"><span class="t">${n.type}</span><span class="dot"></span><span>${n.when}</span></div>
      </div>
      <div class="nthumb">${
        n.thumb === "img"
          ? `<img src="${n.thumbSrc}" alt="" />`
          : n.thumb === "text"
          ? `<div class="ntext">${n.thumbText}</div>`
          : `<img src="assets/icons/ico_mystory_accept.png" style="padding:6px;opacity:.6" alt="" />`
      }</div>`;
    row.addEventListener("click", () => {
      if (n.unread) {
        n.unread = false;
        renderNoti();
      }
    });
    list.appendChild(row);
  });

  if (!items.length) {
    list.appendChild(el("div", "list-end", "Không có mục nào"));
  }
}

/* ---------------------------------------------------------------- render: explore */

function renderExplore() {
  const g = $("#exgrid");
  g.innerHTML = "";
  EXPLORE.forEach((t, i) => {
    const u = USERS[t.user];
    const cell = el("div", "extile");
    cell.style.setProperty("--i", i);
    cell.innerHTML = `<img src="${t.img}" alt="" /><span class="badge"><img src="${u.avatar}" alt="" />${u.name}</span>`;
    cell.addEventListener("click", () => openDetail("p" + (((i % 6) + 1))));
    g.appendChild(cell);
  });
}

/* ---------------------------------------------------------------- render: friends */

function renderFriends() {
  const wrap = $("#friendlist");
  wrap.innerHTML = "";
  wrap.appendChild(el("div", "fsection", "Bạn thân"));
  FRIENDS.filter((f) => f.close).forEach((f, i) => wrap.appendChild(friendRow(f, i)));
  wrap.appendChild(el("div", "fsection", "Tất cả bạn bè"));
  FRIENDS.filter((f) => !f.close).forEach((f, i) => wrap.appendChild(friendRow(f, i + 10)));
}

function friendRow(f, i) {
  const u = USERS[f.user];
  const row = el("div", "frow");
  row.style.animation = `card-in 240ms var(--ease-decel) both`;
  row.style.animationDelay = i * 24 + "ms";
  row.innerHTML = `
    <img class="av" src="${u.avatar}" alt="" />
    <div class="finfo">
      <div class="fnn">${u.name}${badgeFor(f.user)}</div>
      ${f.status ? `<div class="fss">${f.status}</div>` : ""}
    </div>
    <button class="fmini${f.close ? " is-on" : ""}" data-follow="${f.user}">${f.close ? "Bạn thân" : "Theo dõi"}</button>`;
  return row;
}

/* ---------------------------------------------------------------- render: write */

function renderWrite() {
  const box = $("#writeImgs");
  box.innerHTML = "";
  ["post-01", "post-03", "post-05", "post-07"].forEach((p) => {
    const img = el("img");
    img.src = `assets/img/${p}.jpg`;
    img.alt = "";
    img.addEventListener("click", () => {
      img.classList.toggle("is-off");
      syncWrite();
    });
    box.appendChild(img);
  });
  $("#writeText").value = "";
  syncWrite();
}

function syncWrite() {
  const on = $$("#writeImgs img:not(.is-off)").length;
  $("#writeDone").disabled = !(on > 0 || $("#writeText").value.trim());
}

/* ---------------------------------------------------------------- tabbar */

function mountTabbars() {
  $$("[data-tabbar]").forEach((bar) => {
    bar.innerHTML = TABBAR.map(
      (t) =>
        `<button data-tab="${t.tab}" data-route="${t.route}"><img src="${t.img}" alt="${t.tab}" /></button>`
    ).join("");
  });
  syncTabbars();
}

function syncTabbars() {
  const active = currentRoute === "detail" ? "feed" : currentRoute;
  $$("[data-tabbar] button").forEach((b) => {
    b.classList.toggle("is-on", b.dataset.tab === active);
  });
}

/* ---------------------------------------------------------------- routing */

const ROUTES = {
  splash: { anim: "anim-zoom" },
  home: { anim: "anim-zoom" },
  feed: { anim: "anim-zoom" },
  detail: { anim: "anim-push", h: 44 },
  noti: { anim: "anim-push" },
  explore: { anim: "anim-zoom" },
  friends: { anim: "anim-zoom" },
  write: { anim: "anim-rise" },
};

let currentRoute = "feed";
let history = [];
let holdSplash = false;

function go(route, opts = {}) {
  if (!ROUTES[route]) return;
  if (route === currentRoute && !opts.force) return;

  const prev = $(`#screen-${currentRoute}`);
  const next = $(`#screen-${route}`);
  if (!next) return;

  if (!opts.replace) history.push(currentRoute);
  currentRoute = route;
  const dev = $(".device");
  if (dev) dev.classList.toggle("is-splash", route === "splash");

  if (prev && prev !== next) {
    prev.classList.remove("is-active", "anim-zoom", "anim-push", "anim-up", "anim-rise", "anim-out-zoom");
    prev.classList.add("anim-out-zoom");
    setTimeout(() => prev.classList.remove("anim-out-zoom"), 130);
  }

  next.classList.remove("anim-zoom", "anim-push", "anim-up", "anim-rise", "anim-out-zoom");
  void next.offsetWidth;
  next.classList.add("is-active", ROUTES[route].anim);

  if (route === "splash" && !holdSplash) setTimeout(() => go("feed", { replace: true }), 1500);
  if (route === "noti") renderNoti();
  if (route === "explore") renderExplore();
  if (route === "friends") renderFriends();
  if (route === "write") {
    renderWrite();
    setTimeout(() => $("#writeText").focus(), 260);
  }
  if (route === "home") renderMyGrid(currentMyTab);

  syncRouteBar();
  syncTabbars();
}

function back() {
  const prev = history.pop();
  if (prev) go(prev, { replace: true });
  else go("feed", { replace: true });
}

function syncRouteBar() {
  $$(".routebar button").forEach((b) => {
    b.setAttribute("aria-selected", b.dataset.route === currentRoute ? "true" : "false");
  });
}

/* ---------------------------------------------------------------- overlays */

function openDetail(postId) {
  renderDetail(postId);
  go("detail");
}

let toastTimer;
function toast(msg) {
  const t = $("#toast");
  t.textContent = msg;
  t.classList.add("is-shown");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => t.classList.remove("is-shown"), 1500);
}

/* emotion popup anchored near a like button */
let emoTarget = null;

function openEmoPopup(anchor, postId) {
  const pop = $("#emopop");
  const inner = $("#emopopInner");
  const post = POSTS.find((p) => p.id === postId);

  inner.innerHTML = `
    <div class="emopop-row">
      ${EMO.map(
        (e) =>
          `<button data-pick="${e.key}" class="${post?.picked === e.key ? "is-picked" : ""}">
             <img src="${e.img}" alt="" /><span>${e.label}</span>
           </button>`
      ).join("")}
    </div>
    <div class="emopop-hint">Giữ để đổi cảm xúc · thả để gửi</div>`;

  const rect = anchor.getBoundingClientRect();
  const host = $("#viewport").getBoundingClientRect();
  const top = rect.top - host.top - 108;
  inner.style.top = Math.max(8, Math.min(top, host.height - 190)) + "px";
  inner.style.left = Math.max(10, Math.min(rect.left - host.left - 12, host.width - 330)) + "px";

  emoTarget = { postId, anchor };
  pop.classList.add("is-open");
  pop.setAttribute("aria-hidden", "false");
}

function closeEmoPopup() {
  const pop = $("#emopop");
  pop.classList.remove("is-open");
  pop.setAttribute("aria-hidden", "true");
  emoTarget = null;
}

function pickEmotion(key) {
  if (!emoTarget) return;
  const { postId, anchor } = emoTarget;
  const post = POSTS.find((p) => p.id === postId);
  const isToggleOff = post.picked === key;
  const prev = post.picked;

  if (isToggleOff) {
    post.picked = null;
    post.like = Math.max(0, post.like - 1);
    post.reactions = post.reactions.filter((k) => k !== key);
  } else {
    post.picked = key;
    if (!prev) post.like += 1;
    if (!post.reactions.includes(key)) post.reactions.unshift(key);
  }

  flyEmotion(key, anchor);
  closeEmoPopup();
  refreshPostEverywhere(postId);
  toast(isToggleOff ? "Đã bỏ cảm xúc" : `${EMO.find((e) => e.key === key).label} · đã gửi`);
}

function flyEmotion(key, anchor) {
  const flyer = $("#flyer");
  const host = $("#viewport").getBoundingClientRect();
  const rect = anchor.getBoundingClientRect();
  flyer.innerHTML = `<img src="${emoImg(key)}" alt="" />`;
  flyer.style.left = rect.left - host.left + rect.width / 2 - 15 + "px";
  flyer.style.top = rect.top - host.top - 6 + "px";
  flyer.classList.remove("is-fly");
  void flyer.offsetWidth;
  flyer.classList.add("is-fly");
  setTimeout(() => flyer.classList.remove("is-fly"), 950);
}

function refreshPostEverywhere(postId) {
  const post = POSTS.find((p) => p.id === postId);
  if (!post) return;

  // feed card in place, no full re-render (keeps scroll)
  const card = $(`.fcard[data-post="${postId}"]`);
  if (card) {
    const likeBtn = $(".f-like", card);
    if (likeBtn) {
      likeBtn.classList.toggle("emo-on", !!post.picked);
      likeBtn.innerHTML = `<img src="${
        post.picked ? emoImg(post.picked) : "assets/icons/ico_emotion_s_like.png"
      }" alt="" />${post.picked ? EMO.find((e) => e.key === post.picked).label : "Cảm xúc"}`;
    }
    const counts = $$(".fbar .fcount-n", card);
    if (counts[0]) counts[0].textContent = compact(post.like);
    const react = $(".react", card);
    if (react) {
      react.innerHTML =
        post.reactions
          .slice(0, 3)
          .map((k) => `<img src="${emoImg(k)}" alt="" />`)
          .join("") +
        (post.reactions.length > 3 ? `<span>+${post.reactions.length - 3}</span>` : "");
    }
  }

  if (currentRoute === "detail" && $("#detailscroll").dataset.post === postId) {
    renderDetail(postId);
  }
  if (currentRoute === "home") renderMyGrid(currentMyTab);
}

/* bottom sheet */
function openSheet(html) {
  $("#sheetbody").innerHTML = html;
  const s = $("#sheet");
  s.classList.add("is-open");
  s.setAttribute("aria-hidden", "false");
}

function closeSheet() {
  const s = $("#sheet");
  s.classList.remove("is-open");
  s.setAttribute("aria-hidden", "true");
}

function shareSheet(postId) {
  const post = POSTS.find((p) => p.id === postId);
  openSheet(`
    <div class="sheet-title">Chia sẻ story</div>
    <div class="share-preview">
      <img src="${post.img}" alt="" />
      <div>
        <div class="spt">${post.title || post.body.slice(0, 34)}</div>
        <div class="sps">${USERS[post.user].name} · ${post.time}</div>
      </div>
    </div>
    <div class="sheet-item" data-sheet-toast="Đã chia sẻ lên KakaoTalk"><img src="assets/icons/ico_mystory_accept.png" alt="" />KakaoTalk</div>
    <div class="sheet-item" data-sheet-toast="Đã sao chép liên kết"><img src="assets/icons/ico_feed_share.png" alt="" />Sao chép liên kết</div>
    <div class="sheet-item" data-sheet-toast="Đã lưu story"><img src="assets/icons/ico_more_save.png" alt="" />Lưu story</div>
    <div class="sheet-item danger" data-sheet-close>Huỷ</div>`);
}

function menuSheet(postId) {
  openSheet(`
    <div class="sheet-title">Story</div>
    <div class="sheet-item" data-sheet-toast="Đã lưu story"><img src="assets/icons/ico_more_save.png" alt="" />Lưu story</div>
    <div class="sheet-item" data-sheet-toast="Đã bật thông báo cho story này"><img src="assets/icons/ico_more_setting.png" alt="" />Bật thông báo</div>
    <div class="sheet-item" data-sheet-toast="Đã ẩn story khỏi feed"><img src="assets/icons/ico_more_edit.png" alt="" />Chỉnh sửa</div>
    <div class="sheet-item" data-sheet-toast="Đã báo cáo"><img src="assets/icons/ico_more_report.png" alt="" />Báo cáo</div>
    <div class="sheet-item danger" data-sheet-toast="Đã xoá story"><img src="assets/icons/ico_more_delete.png" alt="" />Xoá story</div>
    <div class="sheet-item" data-sheet-close>Huỷ</div>`);
}

/* ---------------------------------------------------------------- interactions */

let longPressTimer = null;
let pullStart = null;
let pulling = false;
let suppressLikeClick = false;

function bindGlobal() {
  document.addEventListener("click", (e) => {
    const routeBtn = e.target.closest("[data-route]");
    if (routeBtn) {
      e.preventDefault();
      go(routeBtn.dataset.route);
      return;
    }
    if (e.target.closest("[data-back]")) {
      back();
      return;
    }
    const toastBtn = e.target.closest("[data-toast]");
    if (toastBtn) {
      toast(toastBtn.dataset.toast);
      return;
    }

    const likeBtn = e.target.closest("[data-like]");
    if (likeBtn) {
      if (suppressLikeClick) {
        suppressLikeClick = false;
        return;
      }
      const id = likeBtn.dataset.like;
      const post = POSTS.find((p) => p.id === id);
      const rect = likeBtn.getBoundingClientRect();
      if (post.picked) {
        // tap toggles off, long press re-opens the rail
        post.picked = null;
        post.like = Math.max(0, post.like - 1);
        refreshPostEverywhere(id);
        toast("Đã bỏ cảm xúc");
      } else {
        post.picked = "like";
        post.like += 1;
        if (!post.reactions.includes("like")) post.reactions.unshift("like");
        flyEmotion("like", likeBtn);
        refreshPostEverywhere(id);
        toast("Like · đã gửi");
      }
      void rect;
      return;
    }

    const pick = e.target.closest("[data-pick]");
    if (pick) {
      pickEmotion(pick.dataset.pick);
      return;
    }
    if (e.target.closest("[data-sheet-close]") || e.target.closest(".sheet-scrim")) {
      closeSheet();
      return;
    }
    const st = e.target.closest("[data-sheet-toast]");
    if (st) {
      toast(st.dataset.sheetToast);
      closeSheet();
      return;
    }
    if (e.target.closest(".emopop-scrim")) {
      closeEmoPopup();
      return;
    }

    const open = e.target.closest("[data-open]");
    if (open) {
      openDetail(open.dataset.open);
      return;
    }
    const openSrc = e.target.closest("[data-open-src]");
    if (openSrc) {
      openSheet(`
        <div class="sheet-title">Ảnh</div>
        <div class="share-preview"><img src="${openSrc.dataset.openSrc}" style="width:100%;height:auto;border-radius:8px" alt="" /></div>
        <div class="sheet-item" data-sheet-toast="Đã lưu ảnh"><img src="assets/icons/ico_more_save.png" alt="" />Lưu ảnh</div>
        <div class="sheet-item" data-sheet-close>Đóng</div>`);
      return;
    }

    const commentBtn = e.target.closest("[data-comment]");
    if (commentBtn) {
      openDetail(commentBtn.dataset.comment);
      setTimeout(() => $("#cbInput").focus(), 300);
      return;
    }

    const shareBtn = e.target.closest("[data-share]");
    if (shareBtn) {
      shareSheet(shareBtn.dataset.share);
      return;
    }
    const menuBtn = e.target.closest("[data-menu]");
    if (menuBtn) {
      menuSheet(menuBtn.dataset.menu);
      return;
    }
    const clike = e.target.closest("[data-clike]");
    if (clike) {
      clike.classList.toggle("is-on");
      return;
    }
    const followBtn = e.target.closest("[data-follow]");
    if (followBtn) {
      const on = followBtn.classList.toggle("is-on");
      if (followBtn.classList.contains("fmini")) {
        followBtn.textContent = on ? "Bạn thân" : "Theo dõi";
      }
      toast(on ? "Đã thêm vào bạn thân" : "Đã bỏ theo dõi");
      return;
    }
    const profile = e.target.closest("[data-profile]");
    if (profile) {
      toast(`Mở story của ${USERS[profile.dataset.profile].name}`);
      return;
    }

    const stab = e.target.closest("[data-stab]");
    if (stab) {
      setMyTab(stab.dataset.stab);
      return;
    }
    const chip = e.target.closest(".chip");
    if (chip) {
      $$(".chip").forEach((c) => c.classList.remove("is-on"));
      chip.classList.add("is-on");
      renderExplore();
      return;
    }
    const ntab = e.target.closest("[data-ntab]");
    if (ntab) {
      notiTab = ntab.dataset.ntab;
      $$(".ntab").forEach((t) => t.classList.toggle("is-on", t === ntab));
      renderNoti();
      return;
    }
  });

  // long press on a like control opens the emotion rail
  document.addEventListener("pointerdown", (e) => {
    const likeBtn = e.target.closest("[data-like]");
    if (!likeBtn) return;
    longPressTimer = setTimeout(() => {
      openEmoPopup(likeBtn, likeBtn.dataset.like);
      suppressLikeClick = true;
      longPressTimer = null;
    }, 380);
  });
  ["pointerup", "pointercancel", "pointerleave", "scroll"].forEach((ev) =>
    document.addEventListener(ev, () => clearTimeout(longPressTimer), true)
  );

  // route bar
  $$(".routebar button").forEach((b) =>
    b.addEventListener("click", () => go(b.dataset.route, { force: true }))
  );

  // comment composer
  $("#cbInput").addEventListener("input", syncCommentPost);
  $("#cbInput").addEventListener("keydown", (e) => {
    if (e.key === "Enter") $("#cbPost").click();
  });

  $("#cbPost").addEventListener("click", () => {
    const input = $("#cbInput");
    const text = input.value.trim();
    if (!text) return;
    const id = $("#detailscroll").dataset.post;
    const post = POSTS.find((p) => p.id === id);
    post.comment += 1;
    post.latest = { user: "me", text };

    const row = el("div", "comment");
    row.innerHTML = `
      <img class="cav" src="${USERS.me.avatar}" alt="" />
      <div class="cbody">
        <div class="cname">${USERS.me.name}${badgeFor("me")}</div>
        <div class="ctext"></div>
        <div class="cmeta"><span>Vừa xong</span><span class="dot"></span><span>Thích · 0</span></div>
      </div>
      <button class="clike" data-clike><img src="assets/icons/ico_emotion_s_like.png" alt="" /></button>`;
    $(".ctext", row).textContent = text;
    $("#comments").appendChild(row);

    input.value = "";
    syncCommentPost();
    $("#detailscroll").scrollTo({ top: $("#detailscroll").scrollHeight, behavior: "smooth" });
    toast("Đã đăng bình luận");
    renderFeed();
  });

  // emotion button inside the comment bar
  $("#cbEmo").addEventListener("click", () => {
    openEmoPopup($("#cbEmo"), $("#detailscroll").dataset.post);
  });

  // bottom bar "write"
  $("#newfeed").addEventListener("click", () => {
    $("#feedscroll").scrollTo({ top: 0, behavior: "smooth" });
    $("#newfeed").classList.remove("is-shown");
    renderFeed();
    toast("Đã làm mới feed");
  });

  $("#writeDone").addEventListener("click", () => {
    if ($("#writeDone").disabled) return;
    toast("Đã đăng story");
    go("feed");
  });

  $("#writeText").addEventListener("input", syncWrite);

  // pull to refresh with the APK's StorySwipeRefreshLayout feel
  const scroll = $("#feedscroll");
  const zone = $(".pullzone");
  scroll.addEventListener(
    "touchstart",
    (e) => {
      if (scroll.scrollTop <= 0) pullStart = e.touches[0].clientY;
    },
    { passive: true }
  );
  scroll.addEventListener(
    "touchmove",
    (e) => {
      if (pullStart == null) return;
      const dy = e.touches[0].clientY - pullStart;
      if (dy > 8 && scroll.scrollTop <= 0) {
        pulling = true;
        const pull = Math.min(dy * 0.6, 46);
        zone.classList.add("is-visible");
        zone.classList.toggle("is-armed", dy > 70);
        zone.style.transform = `translateY(${pull - 46}px)`;
        scroll.style.transform = `translateY(${pull}px)`;
      }
    },
    { passive: true }
  );
    scroll.addEventListener(
      "touchend",
      () => {
        if (!pulling) return;
        const armed = zone.classList.contains("is-armed");
        pulling = false;
        pullStart = null;
        zone.style.transform = "";
        scroll.style.transform = "";
        // let the list snap back, then land the chip
        setTimeout(() => {
          zone.classList.remove("is-visible", "is-armed");
          if (armed) {
            renderFeed();
            $("#newfeed").classList.add("is-shown");
            toast("Đã làm mới feed");
          }
        }, 180);
      },
      { passive: true }
    );

  // wheel "pull" for desktop
  scroll.addEventListener("wheel", (e) => {
    if (e.deltaY < -40 && scroll.scrollTop <= 0) {
      $("#newfeed").classList.add("is-shown");
    }
  });

  // Escape closes overlays
  document.addEventListener("keydown", (e) => {
    if (e.key !== "Escape") return;
    closeEmoPopup();
    closeSheet();
  });
}

/* ---------------------------------------------------------------- my story tabs */

let currentMyTab = "grid";

function setMyTab(tab) {
  currentMyTab = tab;
  const idx = ["grid", "feed", "discovery", "me"].indexOf(tab);
  $$(".stab").forEach((s) => {
    const on = s.dataset.stab === tab;
    s.classList.toggle("is-on", on);
    const img = $("img", s);
    if (!img) return;
    // base = the off-state file; the HTML may ship either variant
    const base =
      s.dataset.base ||
      (s.dataset.base = img.getAttribute("src").replace(/_on(\.png)$/, "$1"));
    img.src = on ? base.replace(/\.png$/, "_on.png") : base;
  });
  $(".stab-bar").style.transform = `translateX(${idx * 100}%)`;

  renderMyGrid(tab);
}

/* ---------------------------------------------------------------- capture states */

/* ?shot=1&ui=... drives the overlay states so a still can show the motion.
   Everything here is capture scaffolding; nothing in it ships to a user flow. */
function applyUiState(ui, qs) {
  const post = qs.get("post") || "p1";
  const run = () => {
    if (ui === "emo") {
      const btn = $(`.fcard[data-post="${post}"] [data-like]`) || $("#cbEmo");
      if (btn) {
        const r = btn.getBoundingClientRect();
        if (!r.width) btn.scrollIntoView({ block: "center" });
        openEmoPopup(btn, post);
      }
      return;
    }
    if (ui === "sheet") return menuSheet(post);
    if (ui === "share") return shareSheet(post);
    if (ui === "toast") return toast(qs.get("msg") || "Like · đã gửi");
    if (ui === "pull") {
      const zone = $(".pullzone");
      zone.classList.add("is-visible", "is-armed");
      zone.style.transform = "translateY(0)";
      $("#feedscroll").style.transform = "translateY(46px)";
      return;
    }
    if (ui === "fly") {
      const btn = $(`[data-like="${post}"]`);
      if (btn) flyEmotion("like", btn);
      return;
    }
    if (ui === "newfeed") return $("#newfeed").classList.add("is-shown");
    if (ui === "comment") {
      const input = $("#cbInput");
      if (input) {
        input.value = qs.get("text") || "Sương lên tới đầu gối luôn.";
        input.dispatchEvent(new Event("input"));
      }
    }
  };
  setTimeout(run, 240);
}

/* ---------------------------------------------------------------- boot */

function boot() {
  const qs = new URLSearchParams(location.search);
  if (qs.get("shot")) document.body.classList.add("shot");
  if (qs.get("route") === "splash") holdSplash = true;

  mountTabbars();
  renderFeed();
  renderMyGrid("grid");
  go("feed", { replace: true, force: true });
  bindGlobal();
  syncRouteBar();

  // capture helpers: ?shot=1 strips the page chrome, ?route=<name> opens a screen
  const want = qs.get("route");
  if (want && ROUTES[want]) {
    if (want === "detail") renderDetail(qs.get("post") || "p1");
    go(want, { force: true, replace: true });
    if (qs.get("stab")) setMyTab(qs.get("stab"));
    if (qs.get("ntab")) {
      notiTab = qs.get("ntab");
      $$(".ntab").forEach((t) => t.classList.toggle("is-on", t.dataset.ntab === notiTab));
      renderNoti();
    }
    if (qs.get("chip")) {
      const chips = $$(".chip");
      const c = chips[Number(qs.get("chip"))];
      if (c) {
        chips.forEach((x) => x.classList.remove("is-on"));
        c.classList.add("is-on");
      }
    }
    if (qs.get("scroll")) {
      const sc = $(`#screen-${want} .scroll`);
      if (sc) sc.scrollTop = Number(qs.get("scroll"));
    }
  }
  if (qs.get("ui")) applyUiState(qs.get("ui"), qs);

  // clock
  const tick = () => {
    const d = new Date();
    $("#sb-clock").textContent =
      d.getHours().toString().padStart(2, "0") + ":" + d.getMinutes().toString().padStart(2, "0");
  };
  if (qs.get("clock")) {
    $("#sb-clock").textContent = qs.get("clock");
  } else {
    tick();
    setInterval(tick, 20000);
  }
}

document.addEventListener("DOMContentLoaded", boot);
