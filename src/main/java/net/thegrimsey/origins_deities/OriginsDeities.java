package net.thegrimsey.origins_deities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.thegrimsey.origins_deities.blocks.GlobeOfLight;
import net.thegrimsey.origins_deities.entities.ThrownGlobeOfLightEntity;
import net.thegrimsey.origins_deities.items.StoryItem;
import net.thegrimsey.origins_deities.origins.EntityActions;
import net.thegrimsey.origins_deities.origins.EntityConditions;
import net.thegrimsey.origins_deities.origins.EntityPowers;
import net.thegrimsey.origins_deities.origins.ItemConditions;

public class OriginsDeities implements ModInitializer {
	public static final String MODID = "origins_deities";
	public static final EntityType<ThrownGlobeOfLightEntity> THROWN_GLOBE_OF_LIGHT_ENTITY;
	public static final GlobeOfLight GLOBE_OF_LIGHT = new GlobeOfLight(AbstractBlock.Settings.create().mapColor(MapColor.CYAN).breakInstantly().noCollision().luminance(state -> 15).ticksRandomly().sounds(BlockSoundGroup.GLASS));
	public static final BlockItem GLOBE_OF_LIGHT_ITEM = new BlockItem(GLOBE_OF_LIGHT, new FabricItemSettings());

	public static final StoryItem STORY = new StoryItem();

	@Override
	public void onInitialize() {
		ItemConditions.register();
		EntityConditions.register();
		EntityActions.registerEntityAction();
		EntityPowers.registerEntityPowers();

		Registry.register(Registries.BLOCK, new Identifier(MODID, "globe_of_light"), GLOBE_OF_LIGHT);
		Registry.register(Registries.ITEM, new Identifier(MODID, "globe_of_light"), GLOBE_OF_LIGHT_ITEM);
		Registry.register(Registries.ENTITY_TYPE, new Identifier(MODID, "globe_of_light"), THROWN_GLOBE_OF_LIGHT_ENTITY);

		Registry.register(Registries.ITEM, new Identifier(MODID, "story"), STORY);
	}

	static {
		THROWN_GLOBE_OF_LIGHT_ENTITY = FabricEntityTypeBuilder.<ThrownGlobeOfLightEntity>create(SpawnGroup.MISC, ThrownGlobeOfLightEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(2).build();
	}
}
