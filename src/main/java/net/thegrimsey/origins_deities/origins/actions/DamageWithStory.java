package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.entities.PowerInterface;

public class DamageWithStory {
    public static void action(SerializableData.Instance data, Pair<Entity, Entity> entities) {
        if(entities.getLeft() instanceof LivingEntity livingEntity && entities.getRight() instanceof LivingEntity targetEntity) {
            int power = ((PowerInterface) livingEntity).getPower();

            targetEntity.damage(livingEntity.getDamageSources().create(DamageTypes.MAGIC, livingEntity), power);
        }
    }
    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                new Identifier(OriginsDeities.MODID, "damage_with_story"),
                new SerializableData(),
                DamageWithStory::action
        );
    }
}
