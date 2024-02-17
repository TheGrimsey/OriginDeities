package net.thegrimsey.origins_deities.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegrimsey.origins_deities.OriginsDeities;

public class ThrownGlobeOfLightEntity extends ThrownItemEntity {

    public ThrownGlobeOfLightEntity(EntityType<? extends ThrownGlobeOfLightEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownGlobeOfLightEntity(World world, LivingEntity owner) {
        super(OriginsDeities.THROWN_GLOBE_OF_LIGHT_ENTITY, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ThrownGlobeOfLightEntity(World world, double x, double y, double z) {
        super(OriginsDeities.THROWN_GLOBE_OF_LIGHT_ENTITY, x, y, z, world);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);


        if (this.getWorld() instanceof ServerWorld world && !this.isRemoved()) {
            BlockPos blockPos = BlockPos.ofFloored(hitResult.getPos());

            if(world.isAir(blockPos)) {
                world.setBlockState(blockPos, OriginsDeities.GLOBE_OF_LIGHT.getDefaultState());
            } else {
                BlockPos adjustedPos = BlockPos.ofFloored(hitResult.getPos().subtract(this.getVelocity().multiply(0.5D)));
                if(world.isAir(adjustedPos)) {
                    world.setBlockState(adjustedPos, OriginsDeities.GLOBE_OF_LIGHT.getDefaultState());
                }
            }

            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return OriginsDeities.GLOBE_OF_LIGHT_ITEM;
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getWorld().isClient()) {
            this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }
}
