package dev.pvpmod.gui;

import dev.pvpmod.config.PvpConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParticlesScreen extends PvpScreen {
    private final List<Identifier> ids = new ArrayList<>();
    private int page = 0;

    public ParticlesScreen(Screen parent) {
        super("Particles", parent);
        ids.addAll(Registries.PARTICLE_TYPE.getIds());
        ids.sort(Comparator.comparing(Identifier::toString));
    }

    @Override
    protected void init() {
        PvpConfig c = PvpConfig.get();
        int left = this.width / 2 - 160;

        addDrawableChild(toggle(left, 28, 150, "All particles", () -> c.particlesEnabled, v -> c.particlesEnabled = v));
        addDrawableChild(new FloatSlider(left + 154, 28, 166, H, "Global size", 0.1f, 3f, c.particleScale, v -> c.particleScale = v));

        int top = 56;
        int perPage = Math.max(3, (this.height - top - 64) / GAP);
        int pages = Math.max(1, (ids.size() + perPage - 1) / perPage);
        page = Math.max(0, Math.min(page, pages - 1));

        int y = top;
        for (int i = page * perPage; i < Math.min(ids.size(), (page + 1) * perPage); i++) {
            String full = ids.get(i).toString();
            String shown = ids.get(i).getPath();
            addDrawableChild(toggle(left, y, 150, shown,
                () -> !c.hiddenParticles.contains(full),
                show -> { if (show) c.hiddenParticles.remove(full); else c.hiddenParticles.add(full); }));
            addDrawableChild(new FloatSlider(left + 154, y, 166, H, "Size", 0.1f, 3f,
                c.particleScales.getOrDefault(full, 1f), v -> c.particleScales.put(full, v)));
            y += GAP;
        }

        int nx = this.width / 2 - 100;
        int ny = this.height - 54;
        addDrawableChild(button(nx, ny, 98, "< Prev", () -> { page--; clearAndInit(); }));
        addDrawableChild(button(nx + 102, ny, 98, "Next >", () -> { page++; clearAndInit(); }));
        addDone();
    }
}
