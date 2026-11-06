# Capture the clone's screens with headless Chrome at 390×844 @2x.
#   python export/capture.py            all shots
#   python export/capture.py feed noti  a subset
# Requires export/serve.js on 127.0.0.1:8791  (node export/serve.js 8791)

import subprocess
import sys
import urllib.request
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SHOTS = ROOT / "screenshots"
CHROME = r"C:\Program Files\Google\Chrome\Application\chrome.exe"
BASE = "http://127.0.0.1:8791/index.html?shot=1&clock=9%3A41"

# name -> (route, extra query, screenshot delay in ms)
PLAN = {
    "01-splash": ("splash", "", 1300),
    "02-home": ("home", "", 700),
    "03-feed": ("feed", "", 700),
    "04-detail": ("detail", "&post=p1", 700),
    "05-noti": ("noti", "", 700),
    "06-explore": ("explore", "", 700),
    "07-friends": ("friends", "", 700),
    "08-write": ("write", "", 700),
    "09-home-feed": ("home", "&stab=feed", 700),
    "10-home-discovery": ("home", "&stab=discovery", 700),
    "11-noti-message": ("noti", "&ntab=message", 700),
    "12-explore-chip": ("explore", "&chip=1", 700),
    "13-detail-newcomment": ("detail", "&post=p5&ui=comment", 900),
    # motion states
    "14-motion-emotions": ("feed", "&ui=emo", 900),
    "15-motion-emotions-detail": ("detail", "&post=p1&ui=emo", 900),
    "16-motion-flyer": ("feed", "&ui=fly", 700),
    "17-motion-sheet": ("feed", "&ui=sheet", 900),
    "18-motion-sheet-share": ("feed", "&ui=share", 900),
    "19-motion-pull": ("feed", "&ui=pull", 700),
    "20-motion-newfeed": ("feed", "&ui=newfeed", 700),
    "21-motion-toast": ("feed", "&ui=toast&msg=Like%20%C2%B7%20%C4%91%C3%A3%20g%E1%BB%ADi", 700),
    "22-detail-scroll": ("detail", "&post=p1&scroll=520", 800),
    "23-home-me": ("home", "&stab=me", 700),
}


def health() -> None:
    try:
        urllib.request.urlopen("http://127.0.0.1:8791/index.html", timeout=4)
    except Exception:
        raise SystemExit("serve.js is not answering on 127.0.0.1:8791 — run: node export/serve.js 8791")


def shoot(name: str, route: str, extra: str, delay: int) -> None:
    SHOTS.mkdir(exist_ok=True)
    out = SHOTS / f"{name}.png"
    url = f"{BASE}&route={route}{extra}"
    subprocess.run(
        [
            CHROME,
            "--headless=new",
            "--disable-gpu",
            "--hide-scrollbars",
            "--no-first-run",
            "--force-device-scale-factor=2",
            "--window-size=390,844",
            f"--virtual-time-budget={delay}",
            f"--screenshot={out}",
            url,
        ],
        check=True,
        capture_output=True,
    )
    print(f"{name}.png  {out.stat().st_size // 1024} KB")


def main() -> None:
    health()
    want = sys.argv[1:]
    for name, (route, extra, delay) in PLAN.items():
        if want and not any(w in name for w in want):
            continue
        shoot(name, route, extra, delay)


if __name__ == "__main__":
    main()
