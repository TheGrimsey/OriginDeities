package net.thegrimsey.origins_deities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thegrimsey.origins_deities.blocks.GlobeOfLight;
import net.thegrimsey.origins_deities.entities.ThrownGlobeOfLightEntity;
import net.thegrimsey.origins_deities.origins.EntityConditions;
import net.thegrimsey.origins_deities.origins.ItemConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OriginsDeities implements ModInitializer {
	public static final String MODID = "origins_deities";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final EntityType<ThrownGlobeOfLightEntity> THROWN_GLOBE_OF_LIGHT_ENTITY;
	public static final GlobeOfLight GLOBE_OF_LIGHT = new GlobeOfLight(FabricBlockSettings.of(Material.GLASS).breakInstantly().noCollision().luminance(state -> 15).ticksRandomly().sounds(BlockSoundGroup.GLASS));
	public static final BlockItem GLOBE_OF_LIGHT_ITEM = new BlockItem(GLOBE_OF_LIGHT, new FabricItemSettings());

	@Override
	public void onInitialize() {
		ItemConditions.register();
		EntityConditions.register();

		Registry.register(Registry.BLOCK, new Identifier(MODID, "globe_of_light"), GLOBE_OF_LIGHT);
		Registry.register(Registry.ITEM, new Identifier(MODID, "globe_of_light"), GLOBE_OF_LIGHT_ITEM);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "globe_of_light"), THROWN_GLOBE_OF_LIGHT_ENTITY);

	}

	static {
		THROWN_GLOBE_OF_LIGHT_ENTITY = FabricEntityTypeBuilder.<ThrownGlobeOfLightEntity>create(SpawnGroup.MISC, ThrownGlobeOfLightEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10, true).build();
	}
}
