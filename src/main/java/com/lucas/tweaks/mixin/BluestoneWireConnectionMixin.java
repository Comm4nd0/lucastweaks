package com.lucas.tweaks.mixin;

import com.lucas.tweaks.block.BluestoneWireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class BluestoneWireConnectionMixin {

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private static void lucastweaks$connectBluestone(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof BluestoneWireBlock) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void lucastweaks$connectBluestoneDirectional(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof BluestoneWireBlock) {
            cir.setReturnValue(true);
        }
    }
}
