package checkmc.copperisms.datagen;

import checkmc.copperisms.items.CopperismsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //for (int i = 0; i < copperismsItems.ALL_ITEMS.size(); i++) {
        //    itemModelGenerator.register(copperismsItems.ALL_ITEMS.get(i), Models.GENERATED);
        //}
        itemModelGenerator.register(CopperismsItems.COPPER_BOTTLE, Models.GENERATED);
    }

}
