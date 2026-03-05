package com.lucas.tweaks.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfigScreen {
    public static Screen create(Screen parent) {
        ModConfig config = ModConfig.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.lucastweaks.config"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory vaultCategory = builder.getOrCreateCategory(
                Text.translatable("category.lucastweaks.vault_reset"));

        vaultCategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("option.lucastweaks.vault_reset_enabled"),
                        config.vaultResetEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("tooltip.lucastweaks.vault_reset_enabled"))
                .setSaveConsumer(val -> config.vaultResetEnabled = val)
                .build());

        vaultCategory.addEntry(entryBuilder.startIntSlider(
                        Text.translatable("option.lucastweaks.vault_reset_minutes"),
                        config.vaultResetMinutes, 1, 60)
                .setDefaultValue(10)
                .setTooltip(Text.translatable("tooltip.lucastweaks.vault_reset_minutes"))
                .setSaveConsumer(val -> config.vaultResetMinutes = val)
                .build());

        // Stonecutter Damage
        ConfigCategory stonecutterCategory = builder.getOrCreateCategory(
                Text.translatable("category.lucastweaks.stonecutter_damage"));

        stonecutterCategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("option.lucastweaks.stonecutter_damage_enabled"),
                        config.stonecutterDamageEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("tooltip.lucastweaks.stonecutter_damage_enabled"))
                .setSaveConsumer(val -> config.stonecutterDamageEnabled = val)
                .build());

        // Secondary Toolbar
        ConfigCategory toolbarCategory = builder.getOrCreateCategory(
                Text.translatable("category.lucastweaks.secondary_toolbar"));

        toolbarCategory.addEntry(entryBuilder.startBooleanToggle(
                        Text.translatable("option.lucastweaks.secondary_toolbar_enabled"),
                        config.secondaryToolbarEnabled)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("tooltip.lucastweaks.secondary_toolbar_enabled"))
                .setSaveConsumer(val -> config.secondaryToolbarEnabled = val)
                .build());

        toolbarCategory.addEntry(entryBuilder.startIntSlider(
                        Text.translatable("option.lucastweaks.secondary_toolbar_slots"),
                        config.secondaryToolbarSlots, 1, 9)
                .setDefaultValue(6)
                .setTooltip(Text.translatable("tooltip.lucastweaks.secondary_toolbar_slots"))
                .setSaveConsumer(val -> config.secondaryToolbarSlots = val)
                .build());

        builder.setSavingRunnable(ModConfig::save);

        return builder.build();
    }
}
