package net.thegrimsey.origins_deities.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.entity.GodFontBlockEntity;
import org.jetbrains.annotations.Nullable;

public class GodFont extends BlockWithEntity {
    public GodFont(Settings settings) {
        super(settings.luminance(value -> 15));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GodFontBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, OriginsDeities.GOD_FONT_BLOCKENTITY, GodFontBlockEntity::tick);
    }
}
