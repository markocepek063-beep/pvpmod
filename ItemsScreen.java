package dev.pvpmod.gui;

import dev.pvpmod.config.PvpConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemsScreen extends PvpScreen {
    private static final String[] ITEMS = {
        "minecraft:totem_of_undying", "minecraft:end_crystal", "minecraft:obsidian",
        "minecraft:respawn_anchor", "minecraft:glowstone", "minecraft:golden_apple",
        "minecraft:enchanted_golden_apple", "minecraft:ender_pearl", "minecraft:shield",
        "minecraft:diamond_sword", "minecraft:netherite_sword", "minecraft:diamond_axe",
        "minecraft:netherite_axe", "minecraft:mace", "minecraft:bow", "minecraft:crossbow",
        "minecraft:trident", "minecraft:splash_potion", "minecraft:potion",
        "minecraft:experience_bottle", "minecraft:firework_rocket", "minecraft:elytra",
        "minecraft:wind_charge", "minecraft:cobweb", "minecraft:lava_bucket", "minecraft:water_bucket"
    };
    private static final int PER_PAGE = 6;
    private int page = 0;

    public ItemsScreen(Screen parent) { super("Item Sizes (in hand)", parent); }

    private static String nameOf(String id) {
        return Registries.ITEM.get(Identifier.of(id)).getName().getString();
    }

    @Override
    protected void init() {
        PvpConfig c = PvpConfig.get();
        int x = this.width / 2 - 100;
        int y = 32;
        addDrawableChild(new FloatSlider(x, y, 200, H, "All held items", 0.25f, 2f, c.handScale, v -> c.handScale = v));
        y += GAP + 6;

        int pages = (ITEMS.length + PER_PAGE - 1) / PER_PAGE;
        page = Math.max(0, Math.min(page, pages - 1));
        for (int i = page * PER_PAGE; i < Math.min(ITEMS.length, (page + 1) * PER_PAGE); i++) {
            String id = ITEMS[i];
            addDrawableChild(new FloatSlider(x, y, 200, H, nameOf(id), 0.25f, 2.5f,
                c.itemScales.getOrDefault(id, 1f), v -> c.itemScales.put(id, v)));
            y += GAP;
        }

        int ny = this.height - 54;
        addDrawableChild(button(x, ny, 98, "< Prev", () -> { page--; clearAndInit(); }));
        addDrawableChild(button(x + 102, ny, 98, "Next >", () -> { page++; clearAndInit(); }));
        addDone();
    }
}
