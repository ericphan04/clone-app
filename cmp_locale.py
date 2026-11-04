import re, io, sys

LIB_PREFIX = (
    "abc_", "m3_", "m3c_", "mtrl_", "mr_", "common_", "material_",
    "androidx", "com.google", "com.appboy", "com_brave", "com_braze",
    "com_facebook", "zs_", "zui_", "zg_", "exo_", "nav_", "bottomsheet",
    "call_notification", "calendar_", "privacy_", "request_", "trackers_",
    "offline_", "fingerprint_", "generic_error", "fallback_menu", "support_",
    "help_", "sections_", "articles_", "categories_", "ask_request",
    "v7_preference", "range_", "state_", "tooltip_", "character_counter",
    "clear_text", "close_drawer", "close_sheet", "copy", "default_",
    "dropdown_menu", "error_", "expand_button", "exposed_", "icon_",
    "in_progress", "indeterminate", "item_view", "native_", "network_activity",
    "not_selected", "not_set", "password_toggle", "preference_copied",
    "retry_view", "search_menu", "selected", "snackbar", "status_bar",
    "summary_collapsed", "switch_role", "tab", "template_percent",
    "view_article", "watermark_label", "confirm_device", "contact_fragment",
    "android.credentials", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
)

def is_app(n):
    return not n.startswith(LIB_PREFIX)

p = r"apktool\split_vi\res\values-vi\strings.xml"
t = open(p, encoding="utf-8", errors="ignore").read()
pairs = re.findall(r'<string name="([^"]+)"[^>]*>(.*?)</string>', t, re.S)
own = [(n, v) for n, v in pairs if is_app(n)]

out = io.StringIO()
out.write("meetup-owned vi strings: %d\n\n" % len(own))
for n, v in own:
    out.write("%s = %s\n" % (n, v[:80]))

open("vi_own.txt", "w", encoding="utf-8").write(out.getvalue())
print("written", len(own))
