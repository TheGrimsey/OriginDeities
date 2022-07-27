package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thegrimsey.origins_deities.OriginsDeities;

public class EntityConditions {
    public static void register() {
        register(new ConditionFactory<>(new Identifier(OriginsDeities.MODID, "is_hostile"), new SerializableData(),
                (data, entity) -> entity instanceof HostileEntity));
    }

    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}

