package com.lucas.tweaks;

import com.lucas.tweaks.entity.ModEntityTypes;
import com.lucas.tweaks.feature.toolbar.SecondaryToolbarRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LucasTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.GRAPPLING_HOOK,
                (context) -> new FlyingItemEntityRenderer<>(context));

        HudRenderCallback.EVENT.register(SecondaryToolbarRenderer::render);
    }
}
