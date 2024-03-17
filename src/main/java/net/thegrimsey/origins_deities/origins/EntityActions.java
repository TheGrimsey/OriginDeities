package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.minecraft.util.Pair;
import net.thegrimsey.origins_deities.origins.actions.CreateStory;
import net.thegrimsey.origins_deities.origins.actions.DrainResourceToHeal;
import net.thegrimsey.origins_deities.origins.actions.LineOfEffectAction;

public class EntityActions {

    public static void registerEntityAction() {
        registerEntityAction(DrainResourceToHeal.getFactory());
        registerEntityAction(LineOfEffectAction.getFactory());
        registerBiEntityAction(CreateStory.getFactory());
    }

    private static void registerEntityAction(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
    private static void registerBiEntityAction(ActionFactory<Pair<Entity, Entity>> actionFactory) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
