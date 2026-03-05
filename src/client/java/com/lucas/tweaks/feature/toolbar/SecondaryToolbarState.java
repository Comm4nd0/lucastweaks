package com.lucas.tweaks.feature.toolbar;

import com.lucas.tweaks.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SecondaryToolbarState {

    private static final SecondaryToolbarState INSTANCE = new SecondaryToolbarState();

    private int selectedIndex = 0;
    private final List<SlotReference> cachedTools = new ArrayList<>();
    private int lastInventoryHash = 0;

    public static SecondaryToolbarState get() {
        return INSTANCE;
    }

    public record SlotReference(int inventorySlot, ItemStack stack) {}

    /**
     * Rebuild the tool list by scanning inventory slots 9-35 for tools/weapons.
     * Uses a hash check to avoid unnecessary rebuilds every frame.
     */
    public void refresh() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        PlayerInventory inventory = client.player.getInventory();

        int hash = computeInventoryHash(inventory);
        if (hash == lastInventoryHash && !cachedTools.isEmpty()) return;
        lastInventoryHash = hash;

        cachedTools.clear();
        for (int i = 9; i <= 35; i++) {
            ItemStack stack = inventory.getStack(i);
            if (ToolDetection.isToolOrWeapon(stack)) {
                cachedTools.add(new SlotReference(i, stack));
            }
        }

        if (cachedTools.isEmpty()) {
            selectedIndex = 0;
        } else if (selectedIndex >= cachedTools.size()) {
            selectedIndex = cachedTools.size() - 1;
        }
    }

    /**
     * Scroll the selection by the given direction (-1 or +1).
     * Wraps around through ALL tools, not just displayed ones.
     * Returns the newly selected SlotReference, or null if no tools.
     */
    public SlotReference scroll(int direction) {
        if (cachedTools.isEmpty()) return null;
        selectedIndex = Math.floorMod(selectedIndex + direction, cachedTools.size());
        return cachedTools.get(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public List<SlotReference> getTools() {
        return cachedTools;
    }

    /**
     * Returns the items to display in the toolbar as a sliding window
     * centered around the selected index.
     */
    public List<SlotReference> getDisplayItems() {
        if (cachedTools.isEmpty()) return List.of();
        int configSlots = ModConfig.get().secondaryToolbarSlots;
        int displayCount = Math.min(configSlots, cachedTools.size());

        int start = selectedIndex - displayCount / 2;
        start = Math.max(0, start);
        start = Math.min(cachedTools.size() - displayCount, start);

        return cachedTools.subList(start, start + displayCount);
    }

    /**
     * Returns the index within the display window that corresponds
     * to the currently selected item.
     */
    public int getDisplaySelectedIndex() {
        if (cachedTools.isEmpty()) return 0;
        int configSlots = ModConfig.get().secondaryToolbarSlots;
        int displayCount = Math.min(configSlots, cachedTools.size());

        int start = selectedIndex - displayCount / 2;
        start = Math.max(0, start);
        start = Math.min(cachedTools.size() - displayCount, start);

        return selectedIndex - start;
    }

    private int computeInventoryHash(PlayerInventory inventory) {
        int hash = 0;
        for (int i = 9; i <= 35; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                hash = 31 * hash + stack.getItem().hashCode();
                hash = 31 * hash + stack.getCount();
            }
        }
        return hash;
    }
}
