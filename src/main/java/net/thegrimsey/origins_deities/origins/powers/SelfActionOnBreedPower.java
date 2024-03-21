package net.thegrimsey.origins_deities.origins.powers;


import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.thegrimsey.origins_deities.OriginsDeities;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelfActionOnBreedPower extends CooldownPower {

    private final Predicate<Entity> targetCondition;
    private final Consumer<Pair<Entity, Entity>> biEntityAction;

    public SelfActionOnBreedPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Pair<Entity, Entity>> entityAction, Predicate<Entity> targetCondition) {
        super(type, entity, cooldownDuration, hudRender);
        this.biEntityAction = entityAction;
        this.targetCondition = targetCondition;
    }

    public void onBreed(Entity target) {
        if(targetCondition == null || targetCondition.test(target)) {
            if(canUse()) {
                this.biEntityAction.accept(new Pair<>(this.entity, target));
                use();
            }
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier(OriginsDeities.MODID, "self_action_on_breed"),
                new SerializableData()
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                        .add("cooldown", SerializableDataTypes.INT, 1)
                        .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                        .add("target_condition", ApoliDataTypes.ENTITY_CONDITION, null),
                data ->
                        (type, player) -> new SelfActionOnBreedPower(type, player, data.getInt("cooldown"),
                                data.get("hud_render"),
                                data.get("bientity_action"),
                                data.get("target_condition")))
                .allowCondition();
    }
}
