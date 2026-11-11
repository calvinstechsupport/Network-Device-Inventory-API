# Curating the rest of your engineering portfolio

The strongest student portfolio is a small set of reproducible projects, not a dump of every class file, screenshot and assessment. Use the following repository plan as the next steps after the website and Network Device Inventory API.

## Suggested GitHub repositories

| Repository | Include | Evidence recruiters can inspect |
|---|---|---|
| `calvinstechsupport.github.io` | Existing website source (already live) | Live URL, source, HTML/CSS/JS, GitHub Pages deployment |
| `network-device-inventory-api` | Java/Spring Boot app and SQL migrations | CRUD API, Swagger docs, CI tests, deployment |
| `computer-networking-labs` | Original Packet Tracer labs you are allowed to share | `.pkt` files, sanitised CLI configs, IP plan, topology screenshots |
| `digital-electronics-projects` | Your seven-segment decoder design | Truth table, K-maps, logic equations, gate diagram and build photos |
| `engineering-simulations` | Your own Simulink/Simscape model source | `.slx` files, calculations, simulation screenshots and explanation |
| `java-programming-projects` | Original Java applications you write | `.java` source, README, screenshots and instructions to run |

## What I found in your currently accessible study files

- The SFD ZIP contains multiple-choice assessment screenshots, not Java project source code. Keep those private; do not publish assessment questions as a portfolio project.
- The Digits ZIP includes a Packet Tracer practical reassessment activity and its background/marking scenario. Do not upload the reassessment file, answer material or course assessment content to a public repository. Build a separate original network lab to demonstrate the same skills.
- The engineering Simulink ZIP contains circuit-diagram screenshots, not the editable model files. Add your own `.slx` models, calculations and measured/simulated results before presenting it as a reproducible project.
- Course module PDFs and lecture slides are learning materials, not work samples. Do not re-host them in public GitHub repositories.

## Example structure for an academic project repository

```text
computer-networking-labs/
  README.md
  labs/
    vlan-routing-lab/
      topology.pkt
      device-configs/
        router1.txt
        switch1.txt
      addressing-plan.md
      screenshots/
  docs/
    design-notes.md
  .gitignore
```

## README checklist for every project

1. State the problem and objective in your own words.
2. List the exact tools and technologies you used.
3. Explain what you personally designed or configured.
4. Include one or more screenshots/diagrams with captions.
5. Include instructions that allow another person to open or run the project.
6. Mention limitations and what you would improve next.
7. Credit team members and external sources where required.

Only label work as a team project if it was built with a team. Separate academic coursework, personal experiments and client work. Never put student numbers, customer details, passwords, API keys or private course materials in a public repository.
