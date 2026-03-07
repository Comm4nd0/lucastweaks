package com.lucas.tweaks.mixin.client;

import com.lucas.tweaks.config.ModConfig;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin {

    @Shadow @Final private ElytraEntityModel model;

    private static final Identifier ELYTRA_TEXTURE = Identifier.ofVanilla("textures/entity/elytra.png");

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V", at = @At("TAIL"))
    private void lucastweaks$renderBannerColor(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
            int light, BipedEntityRenderState state, float limbAngle, float limbDistance,
            CallbackInfo ci) {

        if (!ModConfig.get().elytraBannerEnabled) return;

        ItemStack elytraStack = state.equippedChestStack;
        if (elytraStack == null || !elytraStack.isOf(Items.ELYTRA)) return;

        DyeColor baseColor = elytraStack.get(DataComponentTypes.BASE_COLOR);
        if (baseColor == null) return;

        int color = baseColor.getEntityColor();
        int argbColor = (0xCC << 24) | (color & 0x00FFFFFF);

        matrixStack.push();
        matrixStack.translate(0.0F, 0.0F, 0.125F);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(
                RenderLayer.getEntityTranslucent(ELYTRA_TEXTURE));

        this.model.render(matrixStack, vertexConsumer, light,
                OverlayTexture.DEFAULT_UV, argbColor);

        matrixStack.pop();
    }
}
