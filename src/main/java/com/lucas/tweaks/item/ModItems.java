package com.lucas.tweaks.item;

import com.lucas.tweaks.LucasTweaks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final GrapplingHookItem GRAPPLING_HOOK = register("grappling_hook",
            new GrapplingHookItem(new Item.Settings()
                    .maxDamage(64)
                    .enchantable(14)
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LucasTweaks.MOD_ID, "grappling_hook")))));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(LucasTweaks.MOD_ID, name), item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(GRAPPLING_HOOK);
        });
    }
}
