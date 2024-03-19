package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.entities.PowerInterface;

public class HealWithStory {
    public static void action(SerializableData.Instance data, Pair<Entity, Entity> entities) {
        if(entities.getLeft() instanceof LivingEntity livingEntity && entities.getRight() instanceof LivingEntity targetEntity) {
            int power = ((PowerInterface) livingEntity).getPower();

            float overHeal = Math.abs(targetEntity.getMaxHealth() - targetEntity.getHealth() - (float)power);
            targetEntity.heal(power);

            float overHealAmount = (float) ((overHeal * 0.5f) * Math.pow(0.98f, Math.max(overHeal - 2.0f, 0.0f)));

            if(overHealAmount > 1.0 && targetEntity.getAbsorptionAmount() < overHealAmount) {
               targetEntity.setAbsorptionAmount(overHealAmount);
            }
        }
    }
    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                new Identifier(OriginsDeities.MODID, "heal_with_story"),
                new SerializableData(),
                HealWithStory::action
        );
    }
}
