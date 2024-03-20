package net.thegrimsey.origins_deities.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.thegrimsey.origins_deities.StoryActionType;
import net.thegrimsey.origins_deities.StoryEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class StoryItem extends Item {
    public final static int ARTICLE_COUNT = 3;
    public final static int DESCRIPTOR_COUNT = 12;
    public final static int BINDING_COUNT = 1;

    public StoryItem() {
        super(new FabricItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if(nbt == null || !nbt.contains("power")) {
            return super.getRarity(stack);
        }

        float power = nbt.getFloat("power");
        if(power <= 4.0) {
            return Rarity.COMMON;
        } else if(power <= 8.0) {
            return Rarity.UNCOMMON;
        } else if(power <= 12.0) {
            return Rarity.RARE;
        } else {
            return Rarity.EPIC;
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if(nbt != null) {
            Text writer = Text.Serializer.fromJson(nbt.getString("writer"));
            Text subject = Text.Serializer.fromJson(nbt.getString("subject"));

            int articleIndex = nbt.getInt("name_article");
            Text article = Text.translatable("origins_deities.story.name.article." + articleIndex, writer);

            int descriptorIndex = nbt.getInt("name_descriptor");
            Text descriptor = Text.translatable("origins_deities.story.name.descriptor." + descriptorIndex);

            int bindingIndex = nbt.getInt("name_binding");
            Text binding = Text.translatable("origins_deities.story.name.binding." + bindingIndex);

            int finalIndex = nbt.getInt("name_final");
            StoryActionType actionType = StoryActionType.fromId(nbt.getInt("action_type"));
            Text finalName = Text.translatable(actionType.namesTranslationKey + finalIndex, subject);

            return Text.translatable("origins_deities.story.name.combined", article, descriptor, binding, finalName);
        }

        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        if(nbt == null) {
            return;
        }

        String writer = nbt.getString("writer");
        Text writerText = Text.Serializer.fromJson(writer);

        long creationTime = nbt.getLong("creation_time");
        long day = creationTime / 24_000L + 1;

        Text writerTranslatedText = Text.translatable("origins_deities.story.written_by", writerText, day).formatted(Formatting.ITALIC, Formatting.GRAY);
        tooltip.add(writerTranslatedText);

        StoryEffectType effect = StoryEffectType.values()[nbt.getInt("effect_type")];

        Text effectTranslatedText = Text.translatable(effect.translationKey).formatted(Formatting.ITALIC, Formatting.BOLD, effect.formatting);
        tooltip.add(effectTranslatedText);

        int power = nbt.getInt("power");
        Text powerTranslatedText = Text.translatable("origins_deities.story.power", power).formatted(Formatting.ITALIC, Formatting.GRAY);
        tooltip.add(powerTranslatedText);
    }
}
