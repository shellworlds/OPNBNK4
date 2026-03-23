# Training video (15-minute walkthrough)

Record locally and share with the client (USB, SharePoint, or streaming). **Do not commit large binaries** to git.

**Suggested filename:** `training-video.mp4` (same folder) — listed in `.gitignore`.

**Script (outline):**

1. Open staging/production URL (HTTPS) and sign in (web).
2. Dashboard → account → transactions (note UNDER_REVIEW if demoing fraud).
3. Mobile web or Expo deep link (if available).
4. Postman: one AIS and one PIS call with consent.
5. Grafana: show request/error panels.
6. Show `kubectl get pods` or Azure portal (blur secrets).

**Companion slides:** `docs/training/training-slides.md`
