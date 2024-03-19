package net.thegrimsey.origins_deities;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public enum StoryEffectType {
    DAMAGE(
        "origins_deities.story.effect.damage",
        Formatting.RED,
        new PowerTypeReference(new Identifier(OriginsDeities.MODID,"story/damage"))
    ),
    HEAL(
        "origins_deities.story.effect.heal",
        Formatting.GREEN,
        new PowerTypeReference(new Identifier(OriginsDeities.MODID,"story/healing"))
    ),
    FREEZE(
        "origins_deities.story.effect.freeze",
        Formatting.AQUA,
        new PowerTypeReference(new Identifier(OriginsDeities.MODID,""))
    );

    public static final StoryEffectType[] NEGATIVE_EFFECTS = new StoryEffectType[]{StoryEffectType.DAMAGE};
    public static final StoryEffectType[] POSITIVE_EFFECTS = new StoryEffectType[]{StoryEffectType.HEAL};
    public static final StoryEffectType[] COLD_EFFECTS = new StoryEffectType[]{StoryEffectType.FREEZE};

    public final String translationKey;
    public final Formatting formatting;
    public final PowerType<?> power;

    StoryEffectType(String translationKey, Formatting formatting, PowerType<?> power) {
        this.translationKey = translationKey;
        this.formatting = formatting;
        this.power = power;
    }
}
