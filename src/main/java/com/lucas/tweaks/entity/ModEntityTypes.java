package com.lucas.tweaks.entity;

import com.lucas.tweaks.LucasTweaks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

    private static final RegistryKey<EntityType<?>> GRAPPLING_HOOK_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(LucasTweaks.MOD_ID, "grappling_hook"));

    public static final EntityType<GrapplingHookEntity> GRAPPLING_HOOK = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(LucasTweaks.MOD_ID, "grappling_hook"),
            EntityType.Builder.<GrapplingHookEntity>create(GrapplingHookEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
                    .build(GRAPPLING_HOOK_KEY)
    );

    public static void initialize() {
        // Forces static initialization
    }
}
