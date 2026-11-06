"""Generate neutral placeholder media so the clone renders offline.

Nothing here comes from Instagram - these are synthetic scene-like gradients
sized to match the real layout (1:1 post media, 160px avatars).
"""
import math
import os
import random

from PIL import Image, ImageDraw, ImageFilter

OUT = os.path.join("..", "instagram-clone", "assets", "img")
os.makedirs(OUT, exist_ok=True)

# (sky top, sky bottom, sun/haze, land dark, land light)
SCENES = [
    ((252, 226, 196), (243, 189, 152), (255, 244, 224), (92, 68, 62), (168, 128, 112)),
    ((198, 224, 232), (146, 184, 200), (240, 250, 255), (40, 62, 78), (96, 132, 148)),
    ((228, 214, 190), (186, 162, 128), (250, 240, 214), (70, 58, 44), (140, 120, 92)),
    ((212, 204, 230), (146, 132, 176), (240, 232, 252), (52, 44, 72), (112, 100, 140)),
    ((236, 206, 208), (196, 138, 148), (255, 236, 236), (74, 46, 54), (146, 100, 110)),
    ((196, 216, 226), (112, 148, 172), (232, 246, 255), (28, 46, 64), (72, 104, 128)),
    ((242, 232, 208), (206, 184, 136), (255, 250, 228), (78, 68, 48), (148, 130, 96)),
    ((210, 226, 214), (128, 168, 148), (240, 252, 240), (38, 60, 50), (86, 122, 104)),
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


def scene(idx):
    w = h = 1080
    rnd = random.Random(idx * 977 + 13)
    top, bottom, haze, land_dark, land_light = SCENES[idx % len(SCENES)]
    img = vertical((w, h), top, bottom, 1.35)

    # sun / haze glow
    glow = Image.new("L", (w, h), 0)
    gd = ImageDraw.Draw(glow)
    sx = int(w * rnd.uniform(0.22, 0.78))
    sy = int(h * rnd.uniform(0.24, 0.46))
    sr = int(w * rnd.uniform(0.10, 0.20))
    gd.ellipse([sx - sr, sy - sr, sx + sr, sy + sr], fill=255)
    glow = glow.filter(ImageFilter.GaussianBlur(sr * 0.55))
    img = Image.composite(Image.new("RGB", (w, h), haze), img, glow)

    # horizon band of haze
    band_y = int(h * rnd.uniform(0.52, 0.66))
    band = Image.new("L", (w, h), 0)
    bd = ImageDraw.Draw(band)
    bd.rectangle([0, band_y - int(h * 0.09), w, band_y + int(h * 0.09)], fill=110)
    band = band.filter(ImageFilter.GaussianBlur(h * 0.045))
    img = Image.composite(Image.new("RGB", (w, h), haze), img, band)

    # layered ridges
    layers = rnd.randint(3, 4)
    for i in range(layers):
        t = i / max(1, layers - 1)
        base_y = band_y + int(h * (0.02 + t * 0.22))
        amp = h * (0.05 + 0.05 * (1 - t))
        col = lerp(land_light, land_dark, 0.35 + 0.55 * t)

        mask = Image.new("L", (w, h), 0)
        md = ImageDraw.Draw(mask)
        phase = rnd.uniform(0, math.tau)
        freq = rnd.uniform(1.1, 2.4)
        pts = []
        for x in range(0, w + 8, 8):
            u = x / w
            y = base_y + amp * math.sin(u * math.tau * freq + phase) + amp * 0.35 * math.sin(
                u * math.tau * freq * 2.7 + phase * 1.7
            )
            pts.append((x, y))
        pts += [(w, h), (0, h)]
        md.polygon(pts, fill=255)
        mask = mask.filter(ImageFilter.GaussianBlur(3))
        img = Image.composite(Image.new("RGB", (w, h), col), img, mask)

    # foreground texture grain
    grain = Image.effect_noise((w, h), 14).convert("L")
    img = Image.composite(
        Image.new("RGB", (w, h), (255, 255, 255)), img, grain.point(lambda v: int(v * 0.06))
    )

    return img


def avatar(idx, size=200):
    rnd = random.Random(idx * 331 + 7)
    pal = SCENES[(idx + 2) % len(SCENES)]
    top, bottom, haze, dark, light = pal
    bg = vertical((size, size), lerp(top, haze, 0.4), lerp(bottom, dark, 0.3), 1.2)
    bg = bg.filter(ImageFilter.GaussianBlur(size // 40))

    d = ImageDraw.Draw(bg)
    skin = lerp(light, haze, rnd.uniform(0.1, 0.35))
    shirt = lerp(dark, bottom, rnd.uniform(0.15, 0.5))

    # shoulders
    d.ellipse(
        [size * 0.10, size * 0.62, size * 0.90, size * 1.45],
        fill=shirt,
    )
    # neck
    d.rectangle([size * 0.42, size * 0.44, size * 0.58, size * 0.70], fill=lerp(skin, dark, 0.18))
    # head
    hw = size * rnd.uniform(0.145, 0.165)
    hh = hw * rnd.uniform(1.16, 1.28)
    cx, cy = size * 0.5, size * 0.36
    d.ellipse([cx - hw, cy - hh, cx + hw, cy + hh], fill=skin)
    # hair cap
    hr = hw * 1.10
    hair = lerp(dark, top, rnd.uniform(0.0, 0.25))
    d.ellipse([cx - hr, cy - hh * 1.12, cx + hr, cy + hh * 0.26], fill=hair)
    d.ellipse([cx - hw * 0.96, cy - hh * 1.0, cx + hw * 0.96, cy + hh * 0.42], fill=skin)

    bg = bg.filter(ImageFilter.GaussianBlur(size // 150))
    return bg


def main():
    for i in range(1, 7):
        scene(i).save(os.path.join(OUT, "post-%02d.jpg" % i), "JPEG", quality=84, optimize=True)
    for i in range(1, 11):
        avatar(i).save(os.path.join(OUT, "avatar-%02d.jpg" % i), "JPEG", quality=86, optimize=True)
    print("wrote", len(os.listdir(OUT)), "files")


if __name__ == "__main__":
    main()
