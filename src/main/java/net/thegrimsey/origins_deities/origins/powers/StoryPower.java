package net.thegrimsey.origins_deities.origins.powers;


import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.thegrimsey.origins_deities.OriginsDeities;

import java.util.function.Consumer;

public class StoryPower extends Power {
    private final Consumer<Entity> entityAction;

    public StoryPower(PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction) {
        super(type, entity);
        this.entityAction = entityAction;
    }

    public void onActivateStory() {
        entityAction.accept(this.entity);
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier(OriginsDeities.MODID, "self_action_on_kill"),
                new SerializableData()
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION),
                data ->
                        (type, player) -> new StoryPower(type, player, data.get("entity_action"))
        );
    }
}
