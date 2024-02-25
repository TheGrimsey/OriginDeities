package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.thegrimsey.origins_deities.OriginsDeities;

public class EntityConditions {
    public static void register() {
        register(new ConditionFactory<>(new Identifier(OriginsDeities.MODID, "is_hostile"), new SerializableData(),
                (data, entity) -> entity instanceof HostileEntity));

        register(new ConditionFactory<>(new Identifier(OriginsDeities.MODID, "within_distance_of_spawn"), new SerializableData().add("distance", SerializableDataTypes.INT),
                (data, entity) -> {
            int distance = data.getInt("distance");

            if(entity instanceof ServerPlayerEntity playerEntity) {
                BlockPos pos = playerEntity.getSpawnPointPosition();

                return pos != null && pos.isWithinDistance(playerEntity.getPos(), distance);
            } else {
                return false;
            }
        }));

        register(new ConditionFactory<>(new Identifier(OriginsDeities.MODID, "moon_in_phase"), new SerializableData().add("phase", SerializableDataTypes.INT),
                (data, entity) -> {
            int phase = data.getInt("phase");

            return phase == entity.getWorld().getMoonPhase();
        }));
    }

    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}

