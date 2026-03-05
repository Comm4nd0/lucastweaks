package com.lucas.tweaks.mixin;

import com.lucas.tweaks.config.ModConfig;
import com.lucas.tweaks.LucasTweaks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultBlockEntity.class)
public class VaultBlockEntityServerMixin {

    @Shadow
    private VaultSharedData sharedData;

    @Unique
    private long lucastweaks$lastResetTime = 0;

    @Inject(method = "getServerData", at = @At("RETURN"))
    private void lucastweaks$resetIfNeeded(CallbackInfoReturnable<VaultServerData> cir) {
        VaultServerData serverData = cir.getReturnValue();
        if (serverData == null) return;

        BlockEntity self = (BlockEntity) (Object) this;
        World world = self.getWorld();
        if (world == null || world.isClient()) return;

        ModConfig config = ModConfig.get();
        if (!config.vaultResetEnabled) return;

        long now = world.getTime();
        long interval = config.getVaultResetTicks();

        if (now - lucastweaks$lastResetTime >= interval) {
            VaultServerDataAccessor dataAccessor = (VaultServerDataAccessor) (Object) serverData;
            Set<UUID> rewardedPlayers = dataAccessor.lucastweaks$getRewardedPlayers();

            if (!rewardedPlayers.isEmpty()) {
                rewardedPlayers.clear();

                // Mark server data dirty so the cleared state is saved to disk
                dataAccessor.lucastweaks$setDirty(true);

                // Reset the state-update cooldown so the vault can immediately
                // transition from INACTIVE -> ACTIVE when a player is nearby
                dataAccessor.lucastweaks$setStateUpdatingResumesAt(0L);

                // Mark shared data dirty to trigger a client sync
                // (updates the vault's visual state for nearby players)
                ((VaultSharedDataAccessor) (Object) this.sharedData).lucastweaks$setDirty(true);

                // Mark the block entity dirty so the chunk is saved
                self.markDirty();

                LucasTweaks.LOGGER.info("LucasTweaks: Reset vault at {}", self.getPos());
            }
            lucastweaks$lastResetTime = now;
        }
    }
}
