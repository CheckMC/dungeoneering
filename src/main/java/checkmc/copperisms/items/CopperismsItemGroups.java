package checkmc.copperisms.items;

import checkmc.copperisms.CopperismsMain;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CopperismsItemGroups {

    public static final ItemGroup copperisms_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(CopperismsMain.MOD_ID, "copperisms_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.copperisms_group")).icon(() -> new ItemStack(CopperismsItems.COPPER_BOTTLE)).entries((displayContext, entries) -> {
                for (ItemConvertible item : CopperismsItems.ALL_ITEMS) {
                    entries.add(item);
                }

            }).build());

    public static void registerItemGroups() {
        CopperismsMain.LOGGER.info("Copperisms item groups registered");
    }
}
