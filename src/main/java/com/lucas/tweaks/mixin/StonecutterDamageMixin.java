package com.lucas.tweaks.mixin;

import com.lucas.tweaks.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StonecutterBlock.class)
public class StonecutterDamageMixin {

    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    private void lucastweaks$damageOnStep(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (!ModConfig.get().stonecutterDamageEnabled) return;
        if (!world.isClient && entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(world.getDamageSources().hotFloor(), 1.0F);
        }
    }
}
