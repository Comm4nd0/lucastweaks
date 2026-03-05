package com.lucas.tweaks.feature;

import com.lucas.tweaks.config.ModConfig;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;

public class StackableTotemFeature {

    public static void initialize() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            if (!ModConfig.get().stackableTotemsEnabled) return;
            context.modify(Items.TOTEM_OF_UNDYING, builder -> {
                builder.add(DataComponentTypes.MAX_STACK_SIZE, 64);
            });
        });
    }
}
