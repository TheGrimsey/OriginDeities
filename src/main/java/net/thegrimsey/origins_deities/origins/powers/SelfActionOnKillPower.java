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

public class SelfActionOnKillPower extends CooldownPower {

    private final Predicate<Pair<DamageSource, Float>> damageCondition;
    private final Predicate<Entity> targetCondition;
    private final Consumer<Pair<Entity, Entity>> biEntityAction;

    public SelfActionOnKillPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Predicate<Pair<DamageSource, Float>> damageCondition, Consumer<Pair<Entity, Entity>> entityAction, Predicate<Entity> targetCondition) {
        super(type, entity, cooldownDuration, hudRender);
        this.damageCondition = damageCondition;
        this.biEntityAction = entityAction;
        this.targetCondition = targetCondition;
    }

    public void onKill(Entity target, DamageSource damageSource, float damageAmount) {
        if(targetCondition == null || targetCondition.test(target)) {
            if(damageCondition == null || damageCondition.test(new Pair<>(damageSource, damageAmount))) {
                if(canUse()) {
                    this.biEntityAction.accept(new Pair<>(this.entity, target));
                    use();
                }
            }
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(new Identifier(OriginsDeities.MODID, "self_action_on_kill"),
                new SerializableData()
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                        .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                        .add("cooldown", SerializableDataTypes.INT, 1)
                        .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                        .add("target_condition", ApoliDataTypes.ENTITY_CONDITION, null),
                data ->
                        (type, player) -> new SelfActionOnKillPower(type, player, data.getInt("cooldown"),
                                data.get("hud_render"), data.get("damage_condition"),
                                data.get("bientity_action"),
                                data.get("target_condition")))
                .allowCondition();
    }
}
