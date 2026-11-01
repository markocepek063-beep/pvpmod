# PvP Mod (Fabric, Minecraft 1.21.11)

Client-side only. Open settings with /pvpmod in chat. Config: config/pvpmod.json

## Build the jar
1. Install JDK 21.
2. Go to https://fabricmc.net/develop/template and generate a project:
   Minecraft 1.21.11, Yarn mappings, mod id "pvpmod", package "dev.pvpmod", Java 21.
   (This gives you the Gradle wrapper, which can't be shipped here.)
3. Delete the template's example mod/mixin classes and resources, then copy this folder's
   src/ over it. Keep this fabric.mod.json and pvpmod.mixins.json.
4. In that project run:  ./gradlew build   (Windows: gradlew.bat build)
5. The jar is in build/libs/ - use the one WITHOUT "-sources" in the name.
6. Put it in .minecraft/mods/ together with Fabric API for 1.21.11.
