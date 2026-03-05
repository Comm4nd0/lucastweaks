package com.lucas.tweaks.mixin;

import net.minecraft.block.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public interface VaultServerDataAccessor {
    @Accessor("rewardedPlayers")
    Set<UUID> lucastweaks$getRewardedPlayers();

    @Accessor("dirty")
    void lucastweaks$setDirty(boolean dirty);

    @Accessor("stateUpdatingResumesAt")
    void lucastweaks$setStateUpdatingResumesAt(long time);
}
