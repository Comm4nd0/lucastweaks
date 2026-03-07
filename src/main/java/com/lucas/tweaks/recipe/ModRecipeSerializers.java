package com.lucas.tweaks.recipe;

import com.lucas.tweaks.LucasTweaks;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {

    public static final RecipeSerializer<ElytraBannerRecipe> ELYTRA_BANNER =
            Registry.register(Registries.RECIPE_SERIALIZER,
                    Identifier.of(LucasTweaks.MOD_ID, "elytra_banner"),
                    new SpecialCraftingRecipe.SpecialRecipeSerializer<>(ElytraBannerRecipe::new));

    public static void initialize() {
        // Triggers static initialization
    }
}
