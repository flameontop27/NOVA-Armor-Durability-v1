# NOVA Armor Durability

A client-side Fabric HUD mod for Minecraft Java 1.21.11.

## Features
- Shows Helmet, Chestplate, Leggings, and Boots durability separately.
- Green: 60–100% remaining durability.
- Yellow: 25–59% remaining durability.
- Red: 0–24% remaining durability.
- Shows `--` when an armor slot is empty or the item is not damageable.
- No server installation is required; this is a client-side HUD overlay.

## Requirements
- Minecraft Java Edition 1.21.11
- Fabric Loader 0.18.1 or newer
- Fabric API 0.141.6+1.21.11
- Java 21 for building

## Build the mod
1. Open this folder in IntelliJ IDEA or another Gradle-compatible Java IDE.
2. Let Gradle sync and download the Minecraft/Fabric dependencies.
3. Run the Gradle task `build`.
4. The mod JAR will be created in `build/libs/`.
5. Put the generated JAR in your Minecraft instance's `mods` folder, alongside Fabric API.

## Notes
This is the source project, not a precompiled JAR. The HUD uses Fabric's legacy `HudRenderCallback`, which is deprecated but still present for the 1.21.11 Fabric API line. If you update Minecraft beyond 1.21.11, the rendering code will need to be ported to the newer HUD API.
