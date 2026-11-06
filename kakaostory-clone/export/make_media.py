"""Generate neutral placeholder media so the clone renders offline.

Nothing here is Kakao photography - synthetic scene gradients + generated
avatars, sized to match the layout slots decoded from the APK (1:1 feed
media at 3:4 / 1:1, 240dp cover band, 36dp feed avatars, 100dp profile).

Run from this folder:  python make_media.py
"""
import math
import os
import random

from PIL import Image, ImageDraw, ImageFilter

OUT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "assets", "img"))
os.makedirs(OUT, exist_ok=True)

# (sky top, sky bottom, haze, land dark, land light)
SCENES = [
    ((226, 222, 238), (166, 152, 206), (246, 240, 255), (46, 38, 68), (104, 92, 138)),
    ((252, 231, 210), (243, 176, 142), (255, 246, 228), (92, 58, 52), (176, 120, 104)),
    ((206, 228, 226), (120, 176, 172), (240, 255, 252), (28, 62, 60), (74, 124, 118)),
    ((246, 238, 214), (208, 190, 138), (255, 252, 224), (78, 68, 44), (150, 132, 92)),
    ((214, 216, 232), (128, 134, 176), (240, 242, 255), (40, 44, 78), (92, 100, 142)),
    ((236, 210, 216), (190, 138, 156), (255, 238, 242), (72, 44, 56), (146, 100, 116)),
    ((198, 220, 236), (108, 148, 180), (236, 248, 255), (26, 48, 68), (70, 106, 134)),
    ((224, 230, 214), (132, 168, 132), (244, 255, 240), (38, 62, 44), (88, 124, 96)),
]

# avatar palettes: bg, fg
FACE = [
    ((246, 226, 210), (72, 56, 52), (198, 148, 128)),
    ((226, 232, 246), (44, 52, 74), (168, 178, 206)),
    ((244, 232, 216), (74, 58, 44), (206, 170, 132)),
    ((232, 240, 232), (40, 62, 52), (150, 188, 166)),
    ((242, 224, 232), (72, 44, 60), (204, 152, 170)),
    ((236, 236, 236), (52, 52, 58), (176, 176, 184)),
]


def lerp(a, b, t):
    return tuple(int(round(a[i] + (b[i] - a[i]) * t)) for i in range(3))


def vertical(size, top, bottom, curve=1.0):
    w, h = size
    img = Image.new("RGB", (w, h))
    d = ImageDraw.Draw(img)
    for y in range(h):
        t = (y / max(1, h - 1)) ** curve
        d.line([(0, y), (w, y)], fill=lerp(top, bottom, t))
    return img


def scene(idx, size=(1080, 1080)):
    w, h = size
    rnd = random.Random(idx * 977 + 13)
    top, bottom, haze, land_dark, land_light = SCENES[idx % len(SCENES)]
    img = vertical((w, h), top, bottom, 1.35)

    glow = Image.new("L", (w, h), 0)
    gd = ImageDraw.Draw(glow)
    sx = int(w * rnd.uniform(0.2, 0.8))
    sy = int(h * rnd.uniform(0.2, 0.45))
    sr = int(w * rnd.uniform(0.09, 0.18))
    gd.ellipse([sx - sr, sy - sr, sx + sr, sy + sr], fill=255)
    glow = glow.filter(ImageFilter.GaussianBlur(sr * 0.5))
    img = Image.composite(Image.new("RGB", (w, h), haze), img, glow)

    horizon = int(h * rnd.uniform(0.55, 0.7))
    land = Image.new("RGB", (w, h), land_dark)
    ld = ImageDraw.Draw(land)
    pts = []
    for i in range(9):
        pts.append((int(w * i / 8), horizon + int(rnd.uniform(-h * 0.05, h * 0.03))))
    ld.polygon(pts + [(w, h), (0, h)], fill=land_dark)
    ld.polygon(
        [(int(w * 0.05), h), (int(w * 0.45), horizon + int(h * 0.06)), (w, h)],
        fill=land_light,
    )
    mask = Image.new("L", (w, h), 0)
    ImageDraw.Draw(mask).polygon(pts + [(w, h), (0, h)], fill=255)
    img = Image.composite(land, img, mask)

    # soft grain
    noise = Image.effect_noise((w, h), 12).convert("L").resize((w, h))
    img = Image.composite(img, Image.new("RGB", (w, h), (0, 0, 0)), noise.point(lambda v: 255))
    return img


def avatar(idx, size=320):
    bg, fg, skin = FACE[idx % len(FACE)]
    img = Image.new("RGB", (size, size), bg)
    d = ImageDraw.Draw(img)
    rnd = random.Random(idx * 331 + 7)
    # shoulder mass
    d.ellipse(
        [size * 0.12, size * 0.62, size * 0.88, size * 1.35],
        fill=lerp(fg, bg, 0.45),
    )
    # head
    cx, cy, r = size * 0.5, size * 0.44, size * 0.24
    d.ellipse([cx - r, cy - r * 1.12, cx + r, cy + r * 1.12], fill=skin)
    # hair
    d.chord(
        [cx - r * 1.06, cy - r * 1.24, cx + r * 1.06, cy + r * 0.55],
        180, 360, fill=fg,
    )
    # features
    eye_y = cy - r * 0.1
    for dx in (-0.42, 0.42):
        d.ellipse(
            [cx + dx * r - r * 0.09, eye_y - r * 0.09, cx + dx * r + r * 0.09, eye_y + r * 0.09],
            fill=lerp(fg, (0, 0, 0), 0.35),
        )
    d.arc(
        [cx - r * 0.34, cy + r * 0.15, cx + r * 0.34, cy + r * 0.62],
        start=15, end=165, fill=lerp(skin, (0, 0, 0), 0.4), width=max(2, int(size * 0.012)),
    )
    img = img.filter(ImageFilter.GaussianBlur(0.6))
    return img


def cover(idx, size=(1080, 720)):
    img = scene(idx, size)
    img = img.filter(ImageFilter.GaussianBlur(1.2))
    veil = Image.new("RGB", size, (0, 0, 0))
    return Image.blend(img, veil, 0.07)


def main():
    for i in range(1, 9):
        scene(i).save(os.path.join(OUT, f"post-{i:02d}.jpg"), quality=88)
    for i in range(1, 11):
        avatar(i).save(os.path.join(OUT, f"avatar-{i:02d}.jpg"), quality=90)
    for i in range(1, 5):
        cover(i * 3).save(os.path.join(OUT, f"cover-{i:02d}.jpg"), quality=86)
    avatar(99, 640).save(os.path.join(OUT, "me.jpg"), quality=92)
    print("wrote", len(os.listdir(OUT)), "files to", OUT)


if __name__ == "__main__":
    main()
