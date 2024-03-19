package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.InventoryPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.StoryActionType;
import net.thegrimsey.origins_deities.StoryEffectType;
import net.thegrimsey.origins_deities.entities.PowerInterface;
import net.thegrimsey.origins_deities.items.StoryItem;
import net.thegrimsey.origins_deities.origins.powers.StoryPower;

import java.util.HashSet;
import java.util.List;

public class ActivateStories {
    static final PowerType INVENTORY_ID = new PowerTypeReference<>(new Identifier(OriginsDeities.MODID, "primary/lokin_activate_open_inventory"));

    public static void action(SerializableData.Instance data, Entity entity) {
        if(entity instanceof ServerPlayerEntity player) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(player);

            // Terrible performance.
            InventoryPower power = (InventoryPower) component.getPower(INVENTORY_ID);

            int totalPower = 0;
            HashSet<Integer> effects = new HashSet<>();
            for(ItemStack stack : power.getContainer()) {
                if(stack.getItem() == OriginsDeities.STORY) {
                    NbtCompound nbt = stack.getNbt();

                    totalPower += nbt.getInt("power");
                    effects.add(nbt.getInt("effect"));
                }
            }
            ((PowerInterface)player).setPower(totalPower);

            for (int effect: effects) {
                StoryEffectType effectType = StoryEffectType.values()[effect];

                Power effectPower = component.getPower(effectType.power);
                if(effectPower instanceof StoryPower storyPower) {
                    storyPower.onActivateStory();
                }
            }
        }
    }
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
                new Identifier(OriginsDeities.MODID, "activate_stories"),
                new SerializableData(),
                ActivateStories::action
        );
    }
}
