package net.thegrimsey.origins_deities.origins.actions;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.thegrimsey.origins_deities.OriginsDeities;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class LineOfEffectAction {
    public static void action(SerializableData.Instance data, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            Consumer<Pair<Entity, Entity>> bientityAction = data.get("bientity_action");
            Predicate<Pair<Entity, Entity>> bientityCondition = data.get("bientity_condition");
            boolean includeTarget = data.get("include_target");
            double length = data.get("length");
            double width = data.get("width");
            double diameter = length * 2;

            Vec3d eyePos = livingEntity.getEyePos();
            Vec3d altForward = Vec3d.fromPolar(livingEntity.getPitch(), livingEntity.getYaw());
            Vec3d forward = livingEntity.getRotationVec(1.0f);

            for (Entity check : livingEntity.getWorld().getNonSpectatingEntities(Entity.class, Box.of(eyePos, diameter, diameter, diameter))) {
                if (check == livingEntity && !includeTarget)
                    continue;

                if(!isInLine(eyePos, forward, width, check.getPos())) {
                    continue;
                }

                Pair<Entity, Entity> actorTargetPair = new Pair<>(livingEntity, check);
                if (bientityCondition == null || bientityCondition.test(actorTargetPair))
                    bientityAction.accept(actorTargetPair);
            }
        }
    }

    // This makes sense, i ported it from rust don't worry about it.
    static boolean isInLine(Vec3d start, Vec3d direction, double width, Vec3d position) {
        Vec3d delta = position.subtract(start);
        double angle = Math.acos(direction.dotProduct(delta) / Math.sqrt(direction.lengthSquared() * delta.lengthSquared()));

        double distanceFromLine = Math.abs(Math.sin(angle)) * start.distanceTo(position);

        return distanceFromLine <= width;
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(new Identifier(OriginsDeities.MODID, "line_of_effect"),
                new SerializableData()
                        .add("length", SerializableDataTypes.DOUBLE, 16D)
                        .add("width", SerializableDataTypes.DOUBLE, 2D)
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                        .add("include_target", SerializableDataTypes.BOOLEAN, false),
                LineOfEffectAction::action
        );
    }
}
