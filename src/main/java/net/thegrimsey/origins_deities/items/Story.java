package net.thegrimsey.origins_deities.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Story extends Item {
    public Story() {
        super(new FabricItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        return super.getName(stack);
    }
}
