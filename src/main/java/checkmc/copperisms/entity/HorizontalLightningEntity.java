package checkmc.copperisms.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;

public class HorizontalLightningEntity extends LightningEntity {
    private final float startX;
    private final float startY;
    private final float startZ;
    private final float endX;
    private final float endY;
    private final float endZ;

    public HorizontalLightningEntity(EntityType<? extends LightningEntity> entityType, World world) {
        super(entityType, world);
        startX = 0;
        startY = 0;
        startZ = 0;
        endX = 0;
        endY = 0;
        endZ = 0;
    }

    public HorizontalLightningEntity(EntityType<? extends LightningEntity> entityType, World world, float x1, float y1, float z1, float x2, float y2, float z2) {
        super(entityType, world);
        startX = x1;
        startY = y1;
        startZ = z1;
        endX = x2;
        endY = y2;
        endZ = z2;
    }
}
