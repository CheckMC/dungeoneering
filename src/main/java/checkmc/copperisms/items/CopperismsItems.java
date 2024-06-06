package checkmc.copperisms.items;

import checkmc.copperisms.CopperismsMain;
import checkmc.copperisms.items.classes.CopperBottle;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentType;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;

public class CopperismsItems {

    // ArrayList of all items for datagen and item group
    public static final ArrayList<Item> ALL_ITEMS = new ArrayList<Item>();

    // Item definitions
    public static final DataComponentType<Integer> TICKS_LEFT = DataComponentType.<Integer>builder().codec(Codecs.NONNEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT).build();

    public static final CopperBottle COPPER_BOTTLE = (CopperBottle) registerItem("copper_bottle", new CopperBottle(new Item.Settings().maxDamage(5).component(TICKS_LEFT, 0), 5));

    // Item predicates

    private static Item registerItem(String name, Item item) {
        Item addItem = Registry.register(Registries.ITEM, new Identifier(CopperismsMain.MOD_ID, name), item);
        ALL_ITEMS.add(addItem);
        return addItem;
    }

    public static void registerItems() {
        CopperismsMain.LOGGER.info("copperisms items registered");

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
