package net.thegrimsey.origins_deities;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.thegrimsey.origins_deities.items.StoryItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoryUtils {
    public static void createStory(
            StoryActionType actionType,
            ServerPlayerEntity storyFor,
            @Nullable Entity subject,
            List<Entity> participants,
            float participantPowerMultiplier
    ) {
        float participantPower = participants.size() * participantPowerMultiplier;
        int totalPower = (int)(participantPower + actionType.power);

        NbtCompound nbt = new NbtCompound();
        nbt.putString("writer", Text.Serializer.toJson(storyFor.getDisplayName()));
        if(subject != null) {
            nbt.putString("subject", Text.Serializer.toJson(subject.getDisplayName()));
        }

        NbtList participantsNbt = new NbtList();
        participants.forEach(participant -> participantsNbt.add(NbtString.of(Text.Serializer.toJson(participant.getName()))));
        nbt.put("participants", participantsNbt);
        nbt.putInt("participant_count", participants.size());

        nbt.putInt("action_type", actionType.ordinal());
        nbt.putInt("power", totalPower);

        int article = storyFor.getRandom().nextInt(StoryItem.ARTICLE_COUNT);
        nbt.putInt("name_article", article);

        int descriptor = storyFor.getRandom().nextInt(StoryItem.DESCRIPTOR_COUNT);
        nbt.putInt("name_descriptor", descriptor);

        int binding = storyFor.getRandom().nextInt(StoryItem.BINDING_COUNT);
        nbt.putInt("name_binding", binding);

        int finalName = storyFor.getRandom().nextInt(actionType.namesCount);
        nbt.putInt("name_final", finalName);

        long inGameTime = storyFor.getWorld().getTimeOfDay();
        nbt.putLong("creation_time", inGameTime);

        long realTime = System.currentTimeMillis();
        nbt.putLong("creation_time_real", realTime);

        // EFFECT
        int possibleEffect = storyFor.getRandom().nextInt(actionType.possibleEffects.length);
        int effect = actionType.possibleEffects[possibleEffect].ordinal();

        nbt.putInt("effect_type", effect);

        ItemStack story = new ItemStack(OriginsDeities.STORY, 1);
        story.setNbt(nbt);

        storyFor.giveItemStack(story);
    }
}
