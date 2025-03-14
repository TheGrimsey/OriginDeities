package net.thegrimsey.origins_deities.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.ResourcePower;
import io.github.apace100.apoli.power.factory.condition.entity.ResourceCondition;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.origins.powers.SelfActionOnBreedPower;

import java.util.Iterator;
import java.util.List;

public class GodFontBlockEntity extends BlockEntity {
    public GodFontBlockEntity(BlockPos pos, BlockState state) {
        super(OriginsDeities.GOD_FONT_BLOCKENTITY, pos, state);
    }

    static final Identifier POWER_ID = new Identifier(OriginsDeities.MODID, "primary/echoes_from_beyond");

    public static void tick(World world, BlockPos pos, BlockState state, GodFontBlockEntity blockEntity) {
        if (!world.isClient && world.getTime() % 20L == 0L) {
            Box box = new Box(pos).expand(5.0);

            List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            Iterator<PlayerEntity> var11 = list.iterator();

            PlayerEntity playerEntity;
            while (var11.hasNext()) {
                playerEntity = var11.next();

                for(var power: PowerHolderComponent.getPowers(playerEntity, ResourcePower.class)) {
                    if(power.getType().getIdentifier().equals(POWER_ID)) {
                        power.setValue(power.getValue() + 20);
                        PowerHolderComponent.syncPower(playerEntity, power.getType());

                        break;
                    }
                }
            }
        }
    }
}
