package com.lucas.tweaks.network;

import com.lucas.tweaks.LucasTweaks;
import com.lucas.tweaks.config.ModConfig;
import com.lucas.tweaks.feature.toolbar.ToolDetection;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ModNetworking {

    public static void registerPayloads() {
        PayloadTypeRegistry.playC2S().register(SwapToolbarSlotPayload.ID, SwapToolbarSlotPayload.CODEC);
    }

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(SwapToolbarSlotPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    int sourceSlot = payload.inventorySlot();

                    ModConfig config = ModConfig.get();
                    if (!config.secondaryToolbarEnabled) return;

                    // Validate slot range: must be non-hotbar main inventory (9-35)
                    if (sourceSlot < 9 || sourceSlot > 35) {
                        LucasTweaks.LOGGER.warn("Player {} sent invalid toolbar swap slot: {}",
                                player.getName().getString(), sourceSlot);
                        return;
                    }

                    ItemStack sourceStack = player.getInventory().getStack(sourceSlot);

                    // Validate the item is actually a tool/weapon
                    if (!ToolDetection.isToolOrWeapon(sourceStack)) return;

                    // Perform the swap: source slot <-> current hotbar selected slot
                    int hotbarSlot = player.getInventory().getSelectedSlot();
                    ItemStack hotbarStack = player.getInventory().getStack(hotbarSlot);

                    player.getInventory().setStack(hotbarSlot, sourceStack);
                    player.getInventory().setStack(sourceSlot, hotbarStack);
                });
    }
}
