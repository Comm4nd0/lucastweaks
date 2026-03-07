package com.lucas.tweaks.recipe;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipeCategory;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class ElytraBannerRecipe extends SpecialCraftingRecipe {

    public ElytraBannerRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack elytra = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(Items.ELYTRA)) {
                if (!elytra.isEmpty()) return false;
                elytra = stack;
            } else if (stack.getItem() instanceof BannerItem) {
                if (!banner.isEmpty()) return false;
                banner = stack;
            } else {
                return false;
            }
        }

        return !elytra.isEmpty() && !banner.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack elytra = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(Items.ELYTRA)) {
                elytra = stack;
            } else if (stack.getItem() instanceof BannerItem) {
                banner = stack;
            }
        }

        ItemStack result = elytra.copyWithCount(1);
        BannerPatternsComponent patterns = banner.get(DataComponentTypes.BANNER_PATTERNS);
        if (patterns != null) {
            result.set(DataComponentTypes.BANNER_PATTERNS, patterns);
        }
        result.set(DataComponentTypes.BASE_COLOR, ((BannerItem) banner.getItem()).getColor());
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ELYTRA_BANNER;
    }
}
