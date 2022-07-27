package net.thegrimsey.origins_deities;

import io.github.apace100.apoli.ApoliClient;
import io.github.apace100.origins.Origins;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.thegrimsey.origins_deities.OriginsDeities;
import org.lwjgl.glfw.GLFW;

public class OriginsDeitiesClient implements ClientModInitializer {
    public static KeyBinding useGodPowerKeybind;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(OriginsDeities.GLOBE_OF_LIGHT, RenderLayer.getTranslucent());
        EntityRendererRegistry.register(OriginsDeities.THROWN_GLOBE_OF_LIGHT_ENTITY, FlyingItemEntityRenderer::new);

        useGodPowerKeybind = new KeyBinding("key.origins_deities.god_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category." + Origins.MODID);
        ApoliClient.registerPowerKeybinding("key.origins_deities.god_active", useGodPowerKeybind);
        KeyBindingHelper.registerKeyBinding(useGodPowerKeybind);
    }
}
