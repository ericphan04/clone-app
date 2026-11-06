"""Convert the Android vector drawables we need into standalone SVG files.

Reads apktool_base/res/drawable/*.xml, keeps only the <path> elements, and
writes an SVG with the same viewport so the geometry stays 1:1. Every icon is
masked with `currentColor`, so one file serves both the light and dark UI.
"""
import os
import re

import xml.etree.ElementTree as ET

SRC = os.path.join("apktool_base", "res", "drawable")
OUT = os.path.join("..", "instagram-clone", "assets", "icons")

# name in the APK -> name we want in the clone
WANTED = {
    # nav
    "threads_icons_cotton_house_outline_24": "home-outline",
    "threads_icons_cotton_house_heavy_filled_24": "home-filled",
    "threads_icons_cotton_magnifying_glass_outline_24": "search",
    "threads_icons_cotton_reels_outline_24": "reels",
    "threads_icons_cotton_instagram_direct_outline_24": "direct",
    "threads_icons_cotton_user_outline_24": "profile",
    "threads_icons_cotton_user_filled_24": "profile-filled",
    # post actions
    "threads_icons_cotton_heart_outline_24": "heart-outline",
    "threads_icons_cotton_heart_filled_24": "heart-filled",
    "threads_icons_cotton_comment_outline_24": "comment",
    "threads_icons_cotton_share_outline_24": "share",
    "threads_icons_cotton_bookmark_outline_24": "save-outline",
    "threads_icons_cotton_bookmark_filled_24": "save-filled",
    "threads_icons_cotton_ellipsis_outline_24": "more",
    # chrome
    "threads_icons_cotton_plus_outline_24": "plus",
    "threads_icons_cotton_circle_plus_outline_24": "plus-circle",
    "threads_icons_cotton_x_outline_24": "close",
    "threads_icons_cotton_chevron_right_outline_24": "chevron-right",
    "threads_icons_cotton_chevron_down_outline_24": "chevron-down",
    "threads_icons_cotton_chevron_left_outline_24": "chevron-left",
    "threads_icons_cotton_arrow_back_outline_24": "back",
    "threads_icons_cotton_verified_filled_24": "verified",
    "threads_icons_cotton_pin_outline_24": "pin",
    "threads_icons_cotton_location_pin_outline_24": "location",
    "threads_icons_cotton_circle_check_outline_24": "check-circle",
    "threads_icons_cotton_check_outline_24": "check",
    "threads_icons_cotton_eye_outline_24": "eye",
    "threads_icons_cotton_eye_disabled_outline_24": "eye-off",
    "threads_icons_cotton_camera_outline_24": "camera",
    "threads_icons_cotton_settings_outline_24": "settings",
    "threads_icons_cotton_gear_outline_24": "gear",
    "threads_icons_cotton_bell_outline_24": "bell",
    "threads_icons_cotton_clock_outline_24": "clock",
    "threads_icons_cotton_trash_can_outline_24": "trash",
    "threads_icons_cotton_hashtag_outline_24": "hashtag",
    "threads_icons_cotton_flag_outline_24": "flag",
    "threads_icons_cotton_trash_can_outline_24": "trash",
    "threads_icons_cotton_lock_outline_24": "lock",
    "threads_icons_cotton_envelope_outline_24": "mail",
    "threads_icons_cotton_globe_outline_24": "globe",
    "threads_icons_cotton_qr_code_outline_24": "qr",
    "threads_icons_cotton_link_outline_24": "link",
    # composer / DM
    "threads_icons_cotton_plane_diagonal_outline_24": "send",
    "threads_icons_cotton_plane_horizontal_filled_24": "send-filled",
    "threads_icons_cotton_image_outline_24": "image",
    "threads_icons_cotton_sticker_outline_24": "sticker",
    "threads_icons_cotton_gif_outline_24": "gif",
    "threads_icons_cotton_face_outline_24": "emoji",
    "threads_icons_cotton_play_filled_24": "play",
    "threads_icons_cotton_pause_filled_24": "pause",
    "threads_icons_cotton_volume_outline_24": "volume",
    "threads_icons_cotton_volume_off_outline_24": "volume-off",
    "threads_icons_cotton_comment_plus_outline_24": "comment-plus",
    "threads_icons_cotton_users_outline_24": "users",
    "threads_icons_cotton_face_dashed_plus_outline_24": "add-story",
    # brand
    "basel_icons_svg_instagram_logo_thick_small": "instagram-logo",
    "hatch_icon_phone": "phone",
    "hatch_icon_videos": "video",
    "hatch_icon_more_vertical": "more-vertical",
    "ic_call_answer": "call",
    "ic_call_answer_video": "call-video",
    "arrow_left_outline_24": "back-arrow",
    "hatch_permission_microphone_wght400_outline_24": "mic",
}

ANDROID_NS = "{http://schemas.android.com/apk/res/android}"
FORCED_TINT = "#000000"


def convert(path_xml, out_svg):
    root = ET.parse(path_xml).getroot()
    vw = root.get(ANDROID_NS + "viewportWidth", "24")
    vh = root.get(ANDROID_NS + "viewportHeight", "24")

    paths = []
    for el in root.iter():
        tag = el.tag.split("}")[-1]
        if tag != "path":
            continue
        d = el.get(ANDROID_NS + "pathData")
        if not d:
            continue
        # Android tolerates newlines and runs of spaces inside pathData;
        # SVG parsers do not agree on that, so normalise to single spaces.
        d = re.sub(r"\s+", " ", d).strip()
        fill_type = el.get(ANDROID_NS + "fillType") or "nonZero"
        rule = "evenodd" if fill_type.lower() == "evenodd" else "nonzero"
        paths.append((d, rule))

    if not paths:
        return False

    body = "\n".join(
        '  <path fill-rule="%s" d="%s"/>' % (rule, d) for d, rule in paths
    )
    svg = (
        '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 %s %s" '
        'width="24" height="24" fill="currentColor">\n%s\n</svg>\n' % (vw, vh, body)
    )
    with open(out_svg, "w", encoding="utf-8") as f:
        f.write(svg)
    return True


def main():
    os.makedirs(OUT, exist_ok=True)
    ok, missing = [], []
    for src_name, out_name in WANTED.items():
        src = os.path.join(SRC, src_name + ".xml")
        if not os.path.exists(src):
            missing.append(src_name)
            continue
        dst = os.path.join(OUT, out_name + ".svg")
        if convert(src, dst):
            ok.append(out_name)
        else:
            missing.append(src_name)

    print("wrote %d svg -> %s" % (len(ok), OUT))
    if missing:
        print("MISSING (%d):" % len(missing))
        for m in missing:
            print("  " + m)


if __name__ == "__main__":
    main()
