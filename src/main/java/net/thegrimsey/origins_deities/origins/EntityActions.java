package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.thegrimsey.origins_deities.origins.actions.DrainResourceToHeal;

public class EntityActions {

    public static void register() {
        register(DrainResourceToHeal.getFactory());
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
