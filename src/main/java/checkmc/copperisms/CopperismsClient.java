package checkmc.copperisms;

import checkmc.copperisms.entity.CopperismsEntities;
import checkmc.copperisms.rendering.HorizontalLightningEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;

public class CopperismsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(CopperismsEntities.HORIZONTAL_LIGHTNING_ENTITY, (context, startX, startY, startZ, endX, endY, endZ) -> {
            return new HorizontalLightningEntityRenderer(context, startX, startY, startZ, endX, endY, endZ);
        });


    }
}
