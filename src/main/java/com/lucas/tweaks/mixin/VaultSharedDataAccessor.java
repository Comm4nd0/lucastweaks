package com.lucas.tweaks.mixin;

import net.minecraft.block.vault.VaultSharedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VaultSharedData.class)
public interface VaultSharedDataAccessor {
    @Accessor("dirty")
    void lucastweaks$setDirty(boolean dirty);
}
