package checkmc.dungeoneering.items;

import checkmc.dungeoneering.DungeoneeringMain;
import checkmc.dungeoneering.items.classes.CopperBottle;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class DungeoneeringItems {

    // ArrayList of all items for datagen and item group
    public static final ArrayList<Item> ALL_ITEMS = new ArrayList<Item>();

    // Item definitions
    public static final CopperBottle COPPER_BOTTLE = (CopperBottle) registerItem("copper_bottle", new CopperBottle(new Item.Settings().maxDamage(5), 5));

    // Item predicates

    private static Item registerItem(String name, Item item) {
        Item addItem = Registry.register(Registries.ITEM, new Identifier(DungeoneeringMain.MOD_ID, name), item);
        ALL_ITEMS.add(addItem);
        return addItem;
    }

    public static void registerItems() {
        DungeoneeringMain.LOGGER.info("Dungeoneering items registered");

        // Item predicate setup for copper bottle texture changing
        ModelPredicateProviderRegistry.register(COPPER_BOTTLE, new Identifier("charges"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            float chargesLeft = (float) (itemStack.getMaxDamage() - itemStack.getDamage()) / 5;
            return chargesLeft;
        });

    }
}
