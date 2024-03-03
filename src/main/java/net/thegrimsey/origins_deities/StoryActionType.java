package net.thegrimsey.origins_deities;

class StoryConstants {
    static final String KILL_TRANSLATION_KEY = "origins_deities.story.name.final.kill.";
    static final int KILL_FINAL_NAMES_COUNT = 6;

    static final String FROZEN_TRANSLATION_KEY = "origins_deities.story.name.final.frozen.";
    static final int FROZEN_FINAL_NAMES_COUNT = 5;
}

public enum StoryActionType {
    KILLED_HOSTILE(1.0f, StoryConstants.KILL_TRANSLATION_KEY, StoryConstants.KILL_FINAL_NAMES_COUNT, StoryEffectType.NEGATIVE_EFFECTS),
    KILLED_PLAYER(1.0f, StoryConstants.KILL_TRANSLATION_KEY, StoryConstants.KILL_FINAL_NAMES_COUNT, StoryEffectType.NEGATIVE_EFFECTS),
    KILLED_BOSS(4.0f, StoryConstants.KILL_TRANSLATION_KEY, StoryConstants.KILL_FINAL_NAMES_COUNT, StoryEffectType.NEGATIVE_EFFECTS),
    FROZEN(1.0f, StoryConstants.FROZEN_TRANSLATION_KEY, StoryConstants.FROZEN_FINAL_NAMES_COUNT, StoryEffectType.COLD_EFFECTS);


    public final float power;
    public final String namesTranslationKey;
    public final int namesCount;

    public final StoryEffectType[] possibleEffects;

    StoryActionType(float power, String namesTranslationKey, int namesCount, StoryEffectType[] possibleEffects) {
        this.power = power;
        this.namesTranslationKey = namesTranslationKey;
        this.namesCount = namesCount;
        this.possibleEffects = possibleEffects;
    }

    public static StoryActionType fromName(String name) {
        return switch(name) {
            case "KILLED_HOSTILE" -> KILLED_HOSTILE;
            case "KILLED_PLAYER" -> KILLED_PLAYER;
            case "KILLED_BOSS" -> KILLED_BOSS;
            case "FROZEN" -> FROZEN;
            default -> null;
        };
    }

    public static StoryActionType fromId(int id) {
        return values()[id];
    }

}
