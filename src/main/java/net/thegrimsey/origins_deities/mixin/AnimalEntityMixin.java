package net.thegrimsey.origins_deities.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.thegrimsey.origins_deities.origins.powers.SelfActionOnBreedPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {

    @Inject(method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/PassiveEntity;)V", at = @At("HEAD"))
    public void breed(ServerWorld world, AnimalEntity other, PassiveEntity baby, CallbackInfo ci) {
        AnimalEntity self = (AnimalEntity)(Object)this;
        ServerPlayerEntity playerEntity = self.getLovingPlayer();
        if(playerEntity == null) {
            playerEntity = other.getLovingPlayer();
        }
        if(playerEntity != null) {
            PowerHolderComponent.getPowers(playerEntity, SelfActionOnBreedPower.class).forEach(selfActionOnBreedPower -> selfActionOnBreedPower.onBreed(self));
        }
    }
}
