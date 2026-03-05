package com.lucas.tweaks.mixin.client;

import com.lucas.tweaks.config.ModConfig;
import com.lucas.tweaks.feature.toolbar.SecondaryToolbarState;
import com.lucas.tweaks.network.SwapToolbarSlotPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseScrollMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void lucastweaks$onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;
        if (client.currentScreen != null) return;
        boolean shiftHeld = InputUtil.isKeyPressed(client.getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputUtil.isKeyPressed(client.getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT);
        if (!shiftHeld) return;
        if (!ModConfig.get().secondaryToolbarEnabled) return;

        int direction = vertical > 0 ? -1 : 1;

        SecondaryToolbarState state = SecondaryToolbarState.get();
        state.refresh();

        SecondaryToolbarState.SlotReference selected = state.scroll(direction);
        if (selected != null) {
            ClientPlayNetworking.send(new SwapToolbarSlotPayload(selected.inventorySlot()));
        }

        ci.cancel();
    }
}
