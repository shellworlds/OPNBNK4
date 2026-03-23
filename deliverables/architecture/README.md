# Architecture exports (PDF / PNG)

Source diagrams live in **Mermaid** inside the repo:

- `docs/architecture/high-level-design.md`
- `docs/architecture/final-architecture.md`

## How to export

1. **VS Code / Cursor:** Mermaid preview → print to PDF, or use **mermaid-cli** (`mmdc -i diagram.mmd -o out.png`).
2. **Browser:** Paste Mermaid into [mermaid.live](https://mermaid.live) → Export PNG/SVG/PDF.
3. Place exported files here as:

- `c4-context.png` (optional)
- `sequence-pis.png` (optional)

**Do not commit** large binary updates if your process prefers client DMS only — keep this README as the procedure.
