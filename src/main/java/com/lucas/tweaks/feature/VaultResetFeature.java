package com.lucas.tweaks.feature;

/**
 * Vault reset feature - logic is now handled directly in VaultBlockEntityServerMixin.
 * This class is kept for the clearTracking() hook registered in LucasTweaks.
 */
public class VaultResetFeature {

    public static void clearTracking() {
        // No-op: per-instance tracking is now handled by the mixin's @Unique field,
        // which is automatically cleaned up when the block entity is unloaded.
    }
}
