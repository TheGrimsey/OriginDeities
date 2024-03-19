package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.client.render.debug.DebugRenderer;
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
import net.thegrimsey.origins_deities.StoryUtils;
import net.thegrimsey.origins_deities.items.StoryItem;

import java.util.List;

public class CreateStory {
    public static void action(SerializableData.Instance data, Pair<Entity, Entity> entities) {
        if(entities.getLeft() instanceof ServerPlayerEntity player) {
            float participantDistance = data.getFloat("participant_distance");
            float participantMultiplier = data.getFloat("participant_multiplier");
            String actionTypeId = data.getString("action_type");
            StoryActionType actionType = StoryActionType.fromName(actionTypeId);

            Vec3d minBox = player.getPos().subtract(participantDistance, participantDistance, participantDistance);
            Vec3d maxBox = player.getPos().add(participantDistance, participantDistance, participantDistance);
            Box supporterBox = new Box(minBox, maxBox);

            List<Entity> participants = player.getWorld().getOtherEntities(player, supporterBox, otherEntity -> otherEntity instanceof PlayerEntity || otherEntity instanceof TameableEntity tame && tame.isTamed() && !tame.isSitting());

            StoryUtils.createStory(actionType, player, entities.getRight(), participants, participantMultiplier);
        }
    }
    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
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
