package com.lucas.tweaks;

import com.lucas.tweaks.config.ModConfig;
import com.lucas.tweaks.block.ModBlocks;
import com.lucas.tweaks.entity.ModEntityTypes;
import com.lucas.tweaks.feature.StackableTotemFeature;
import com.lucas.tweaks.feature.VaultResetFeature;
import com.lucas.tweaks.item.ModItems;
import com.lucas.tweaks.network.ModNetworking;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LucasTweaks implements ModInitializer {
    public static final String MOD_ID = "lucastweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.load();
        ModBlocks.initialize();
        ModEntityTypes.initialize();
        ModItems.initialize();
        ModNetworking.registerPayloads();
        ModNetworking.registerServerReceivers();
        StackableTotemFeature.initialize();
        LOGGER.info("LucasTweaks loaded!");

        ServerWorldEvents.UNLOAD.register((server, world) -> {
            VaultResetFeature.clearTracking();
        });
    }
}
