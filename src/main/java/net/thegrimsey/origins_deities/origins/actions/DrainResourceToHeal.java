package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.VariableIntPower;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.thegrimsey.origins_deities.OriginsDeities;

public class DrainResourceToHeal {
    public static void action(SerializableData.Instance data, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);

            float healthPerResource = data.getFloat("health_per_resource");
            PowerType<?> powerType = data.get("resource");
            Power p = component.getPower(powerType);

            if(p instanceof VariableIntPower resource) {
                int value = resource.getValue();
                int healing = (int)(healthPerResource * (float)value);

                int missingHealth = (int)(livingEntity.getMaxHealth() - livingEntity.getHealth());

                if(healing <= missingHealth) {
                    resource.setValue(0);
                } else {
                    int consumedResource = (int)(missingHealth * healthPerResource);
                    healing = (int)(consumedResource * healthPerResource);

                    resource.setValue(resource.getValue() - consumedResource);
                }

                livingEntity.heal(healing);
                PowerHolderComponent.syncPower(entity, powerType);

            }
        }
    }
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
                new Identifier(OriginsDeities.MODID, "drain_resource_to_heal"),
                new SerializableData()
                        .add("health_per_resource", SerializableDataTypes.FLOAT)
                        .add("resource", ApoliDataTypes.POWER_TYPE),
                DrainResourceToHeal::action
        );
    }
}
