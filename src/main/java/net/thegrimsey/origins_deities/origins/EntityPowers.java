package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import net.thegrimsey.origins_deities.origins.powers.SelfActionOnBreedPower;
import net.thegrimsey.origins_deities.origins.powers.SelfActionOnKillPower;
import net.thegrimsey.origins_deities.origins.powers.StoryPower;

public class EntityPowers {

    public static void registerEntityPowers() {
        registerEntityPower(SelfActionOnKillPower.createFactory());
        registerEntityPower(StoryPower.createFactory());
        registerEntityPower(SelfActionOnBreedPower.createFactory());
    }

    private static void registerEntityPower(PowerFactory powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }
}
