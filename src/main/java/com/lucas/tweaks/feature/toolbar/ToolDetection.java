package com.lucas.tweaks.feature.toolbar;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public final class ToolDetection {
    private ToolDetection() {}

    public static boolean isToolOrWeapon(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.contains(DataComponentTypes.TOOL)) return true;
        if (stack.contains(DataComponentTypes.WEAPON)) return true;
        if (stack.contains(DataComponentTypes.PIERCING_WEAPON)) return true;
        return false;
    }
}
