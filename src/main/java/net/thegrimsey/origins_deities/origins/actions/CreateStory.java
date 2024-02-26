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
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.thegrimsey.origins_deities.OriginsDeities;

import java.util.List;
import java.util.Optional;

public class CreateStory {
    public static void action(SerializableData.Instance data, Entity entity) {
        if(entity instanceof ServerPlayerEntity player) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);

            float participantDistance = data.getFloat("participant_distance");
            float participantMultiplier = data.getFloat("participant_multiplier");
            String actionType = data.getString("action_type");

            Vec3d minBox = entity.getPos().subtract(participantDistance, participantDistance, participantDistance);
            Vec3d maxBox = entity.getPos().add(participantDistance, participantDistance, participantDistance);
            Box supporterBox = new Box(minBox, maxBox);


            List<Entity> participants = player.getWorld().getOtherEntities(entity, supporterBox, otherEntity -> otherEntity instanceof PlayerEntity || otherEntity instanceof TameableEntity tame && tame.isTamed() && !tame.isSitting());

            float participantPower = participants.size() * participantMultiplier;

            NbtCompound nbt = new NbtCompound();
            nbt.putString("writer", player.getName().getString());

            NbtList participantsNbt = new NbtList();
            participants.forEach(participant -> participantsNbt.add(NbtString.of(participant.getName().getString())));
            nbt.put("participants", participantsNbt);

            ItemStack story = new ItemStack(OriginsDeities.STORY, 1);
            story.setNbt(nbt);
        }
    }
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
                new Identifier(OriginsDeities.MODID, "create_story"),
                new SerializableData()
                        .add("participant_distance", SerializableDataTypes.FLOAT)
                        .add("participant_multiplier", SerializableDataTypes.FLOAT)
                        .add("action_type", SerializableDataTypes.STRING),
                CreateStory::action
        );
    }
}
