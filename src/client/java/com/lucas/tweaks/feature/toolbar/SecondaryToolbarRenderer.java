package com.lucas.tweaks.feature.toolbar;

import com.lucas.tweaks.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.List;

public class SecondaryToolbarRenderer {

    private static final int SLOT_SIZE = 20;
    private static final int ITEM_OFFSET = 2;
    private static final int HORIZONTAL_GAP = 4;
    private static final int VANILLA_HOTBAR_WIDTH = 182;

    // Colors (ARGB)
    private static final int BG_COLOR = 0xAA000000;
    private static final int SELECTED_COLOR = 0xAAFFFFFF;
    private static final int BORDER_COLOR = 0xFF555555;

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (!ModConfig.get().secondaryToolbarEnabled) return;
        if (client.currentScreen != null) return;

        SecondaryToolbarState state = SecondaryToolbarState.get();
        state.refresh();

        List<SecondaryToolbarState.SlotReference> displayItems = state.getDisplayItems();
        if (displayItems.isEmpty()) return;

        int displayCount = displayItems.size();
        int selectedDisplayIdx = state.getDisplaySelectedIndex();

        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        int toolbarWidth = displayCount * SLOT_SIZE + 2;
        int toolbarHeight = SLOT_SIZE + 2;

        int vanillaHotbarLeft = (screenWidth - VANILLA_HOTBAR_WIDTH) / 2;
        int vanillaHotbarTop = screenHeight - 22;
        int toolbarX = vanillaHotbarLeft - HORIZONTAL_GAP - toolbarWidth;
        int toolbarY = vanillaHotbarTop;

        // Background
        drawContext.fill(toolbarX, toolbarY,
                toolbarX + toolbarWidth, toolbarY + toolbarHeight, BG_COLOR);

        // Border (top, bottom, left, right)
        drawContext.fill(toolbarX, toolbarY,
                toolbarX + toolbarWidth, toolbarY + 1, BORDER_COLOR);
        drawContext.fill(toolbarX, toolbarY + toolbarHeight - 1,
                toolbarX + toolbarWidth, toolbarY + toolbarHeight, BORDER_COLOR);
        drawContext.fill(toolbarX, toolbarY,
                toolbarX + 1, toolbarY + toolbarHeight, BORDER_COLOR);
        drawContext.fill(toolbarX + toolbarWidth - 1, toolbarY,
                toolbarX + toolbarWidth, toolbarY + toolbarHeight, BORDER_COLOR);

        // Draw each slot
        TextRenderer textRenderer = client.textRenderer;
        for (int i = 0; i < displayCount; i++) {
            int slotX = toolbarX + 1 + i * SLOT_SIZE;
            int slotY = toolbarY + 1;

            // Selection highlight
            if (i == selectedDisplayIdx) {
                drawContext.fill(slotX, slotY, slotX + SLOT_SIZE, slotY + SLOT_SIZE, SELECTED_COLOR);
            }

            // Item icon and overlay (durability bar, count)
            SecondaryToolbarState.SlotReference ref = displayItems.get(i);
            int itemX = slotX + ITEM_OFFSET;
            int itemY = slotY + ITEM_OFFSET;
            drawContext.drawItem(ref.stack(), itemX, itemY);
            drawContext.drawStackOverlay(textRenderer, ref.stack(), itemX, itemY);
        }
    }
}
