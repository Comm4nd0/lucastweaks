package com.lucas.tweaks.block;

import com.lucas.tweaks.LucasTweaks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final BluestoneWireBlock BLUESTONE_WIRE = register("bluestone_wire",
            new BluestoneWireBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .breakInstantly()
                    .mapColor(MapColor.LAPIS_BLUE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(LucasTweaks.MOD_ID, "bluestone_wire")))));

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, Identifier.of(LucasTweaks.MOD_ID, name), block);
    }

    public static void initialize() {
        // Forces static initialization
    }
}
