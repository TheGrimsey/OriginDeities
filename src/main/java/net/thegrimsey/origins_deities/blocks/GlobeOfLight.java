package net.thegrimsey.origins_deities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GlobeOfLight extends Block {
    final VoxelShape SHAPE = createCuboidShape(4,4,4,12,12,12);
    public GlobeOfLight(Settings settings) {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, 2000); // Scheduled tick for removal
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        for(int i = 0; i < 2; i++) {
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5D + random.nextGaussian()/3.0D, pos.getY() + 0.5D + random.nextGaussian()/3.0D, pos.getZ() + 0.5D + random.nextGaussian()/3.0D, 0,0,0);
        }
    }
}
