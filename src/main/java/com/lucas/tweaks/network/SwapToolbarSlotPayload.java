package com.lucas.tweaks.network;

import com.lucas.tweaks.LucasTweaks;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SwapToolbarSlotPayload(int inventorySlot) implements CustomPayload {

    public static final CustomPayload.Id<SwapToolbarSlotPayload> ID =
            new CustomPayload.Id<>(Identifier.of(LucasTweaks.MOD_ID, "swap_toolbar_slot"));

    public static final PacketCodec<RegistryByteBuf, SwapToolbarSlotPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_INT, SwapToolbarSlotPayload::inventorySlot,
                    SwapToolbarSlotPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
