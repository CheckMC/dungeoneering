package checkmc.copperisms.entity;

import checkmc.copperisms.CopperismsMain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CopperismsEntities {

    public static final EntityType<HorizontalLightningEntity> HORIZONTAL_LIGHTNING_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(CopperismsMain.MOD_ID, "cube"),
            EntityType.Builder.create(HorizontalLightningEntity::new, SpawnGroup.MISC).disableSummon().build()
    );

    public static void registerEntities() {
        CopperismsMain.LOGGER.info("Registering entities.");
    }

}
