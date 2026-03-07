package com.lucas.tweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucas.tweaks.LucasTweaks;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("lucastweaks.json");

    private static ModConfig INSTANCE = new ModConfig();

    // Vault Reset
    public boolean vaultResetEnabled = true;
    public int vaultResetMinutes = 10;

    // Stonecutter Damage
    public boolean stonecutterDamageEnabled = true;

    // Bluestone
    public boolean bluestoneEnabled = true;

    // Stackable Totems
    public boolean stackableTotemsEnabled = true;

    // Secondary Toolbar
    public boolean secondaryToolbarEnabled = true;
    public int secondaryToolbarSlots = 6;

    // Elytra Banner
    public boolean elytraBannerEnabled = true;

    public static ModConfig get() {
        return INSTANCE;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                INSTANCE = GSON.fromJson(json, ModConfig.class);
                if (INSTANCE == null) {
                    INSTANCE = new ModConfig();
                }
                INSTANCE.validate();
            } catch (IOException e) {
                LucasTweaks.LOGGER.error("Failed to load config", e);
                INSTANCE = new ModConfig();
            }
        }
        save();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            LucasTweaks.LOGGER.error("Failed to save config", e);
        }
    }

    private void validate() {
        if (vaultResetMinutes < 1) vaultResetMinutes = 1;
        if (vaultResetMinutes > 60) vaultResetMinutes = 60;
        if (secondaryToolbarSlots < 1) secondaryToolbarSlots = 1;
        if (secondaryToolbarSlots > 9) secondaryToolbarSlots = 9;
    }

    public long getVaultResetTicks() {
        return (long) vaultResetMinutes * 60 * 20;
    }
}
