package com.lucas.tweaks;

import com.lucas.tweaks.block.ModBlocks;
import com.lucas.tweaks.entity.ModEntityTypes;
import com.lucas.tweaks.feature.toolbar.SecondaryToolbarRenderer;
import com.lucas.tweaks.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LucasTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.GRAPPLING_HOOK,
                (context) -> new FlyingItemEntityRenderer<>(context));

        HudRenderCallback.EVENT.register(SecondaryToolbarRenderer::render);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            int power = state.get(RedstoneWireBlock.POWER);
            return getBlueWireColor(power);
        }, ModBlocks.BLUESTONE_WIRE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getBlueWireColor(15), ModItems.BLUESTONE_DUST);
    }

    private static int getBlueWireColor(int power) {
        float f = power / 15.0f;
        float r = f * f * 0.3f;
        float g = f * f * 0.4f + (power > 0 ? 0.1f : 0.0f);
        float b = f * 0.6f + (power > 0 ? 0.4f : 0.3f);
        int ri = (int) (r * 255) & 0xFF;
        int gi = (int) (g * 255) & 0xFF;
        int bi = (int) (b * 255) & 0xFF;
        return (ri << 16) | (gi << 8) | bi;
    }
}
