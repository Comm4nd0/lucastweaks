package com.lucas.tweaks.block;

import com.lucas.tweaks.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class BluestoneWireBlock extends RedstoneWireBlock {

    public BluestoneWireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        int power = super.getWeakRedstonePower(state, world, pos, direction);
        if (!ModConfig.get().bluestoneEnabled) return power;
        return power > 0 ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        int power = super.getStrongRedstonePower(state, world, pos, direction);
        if (!ModConfig.get().bluestoneEnabled) return power;
        return power > 0 ? 15 : 0;
    }
}
