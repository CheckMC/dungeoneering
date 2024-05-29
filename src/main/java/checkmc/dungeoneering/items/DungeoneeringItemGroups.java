package checkmc.dungeoneering.items;

import checkmc.dungeoneering.DungeoneeringMain;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DungeoneeringItemGroups {

    public static final ItemGroup DUNGEONEERING_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(DungeoneeringMain.MOD_ID, "dungeoneering_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dungeoneering_group")).icon(() -> new ItemStack(DungeoneeringItems.COPPER_BOTTLE)).entries((displayContext, entries) -> {
                for (ItemConvertible item : DungeoneeringItems.ALL_ITEMS) {
                    entries.add(item);
                }

            }).build());

    public static void registerItemGroups() {
        DungeoneeringMain.LOGGER.info("Dungeoneering item groups registered");
    }
}
