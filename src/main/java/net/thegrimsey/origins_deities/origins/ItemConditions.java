package net.thegrimsey.origins_deities.origins;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.thegrimsey.origins_deities.OriginsDeities;

public class ItemConditions {
    public static void register() {
        register(new ConditionFactory<>(new Identifier(OriginsDeities.MODID, "can_increase_count_by"), new SerializableData()
                .add("amount", SerializableDataTypes.INT),
                (data, stack) -> (stack.getCount() + data.getInt("amount")) <= stack.getMaxCount()));
    }

    private static void register(ConditionFactory<ItemStack> conditionFactory) {
        Registry.register(ApoliRegistries.ITEM_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
