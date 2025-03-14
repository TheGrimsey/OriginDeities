package net.thegrimsey.origins_deities.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.thegrimsey.origins_deities.OriginsDeities;
import net.thegrimsey.origins_deities.OriginsDeitiesClient;
import net.thegrimsey.origins_deities.entity.GodFontBlockEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
public class GodFontRenderer implements BlockEntityRenderer<GodFontBlockEntity> {
    public GodFontRenderer(BlockEntityRendererFactory.Context context) {
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -4.0F, -4.0F));
        return TexturedModelData.of(modelData, 8, 8);
    }

    @Override
    public void render(GodFontBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var entityModelPart = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(OriginsDeitiesClient.godFontModelLayer);

        float time = entity.getWorld().getTime() + tickDelta;

        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(new Quaternionf().fromAxisAngleDeg(new Vector3f((float) Math.cos(time / 40.0), 1.0F, 0.0F), time * 5.0f));

        float scale = (float) (1.0 + Math.sin(time / 20.0) * 0.09);
        matrices.scale(scale, scale, scale);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(new Identifier(OriginsDeities.MODID, "textures/block/god_font.png")));
        entityModelPart.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }
}