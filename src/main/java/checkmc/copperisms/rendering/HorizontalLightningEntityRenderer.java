package checkmc.copperisms.rendering;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LightningEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LightningEntity;
import org.joml.Matrix4f;

public class HorizontalLightningEntityRenderer extends LightningEntityRenderer {
    private final float startX;
    private final float startY;
    private final float startZ;
    private final float endX;
    private final float endY;
    private final float endZ;

    public HorizontalLightningEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(LightningEntity lightningEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        drawLine(matrix4f, vertexConsumer, startX, startY, startZ, endX, endY, endZ);
    }

    private void drawLine(Matrix4f matrix, VertexConsumer buffer, float x1, float y1, float z1, float x2, float y2, float z2) {
        // Define the first vertex
        buffer.vertex(matrix, x1, y1, z1)  // Vertex position transformed by the matrix
                .color(0.45f, 0.45f, 0.5f, 0.3f)  // Vertex color with transparency
                .next();  // Finalize this vertex

        // Define the second vertex
        buffer.vertex(matrix, x2, y2, z2)  // Vertex position transformed by the matrix
                .color(0.45f, 0.45f, 0.5f, 0.3f)  // Vertex color with transparency
                .next();  // Finalize this vertex
    }
}
