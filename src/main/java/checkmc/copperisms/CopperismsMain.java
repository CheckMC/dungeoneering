package checkmc.copperisms;

import checkmc.copperisms.items.CopperismsItemGroups;
import checkmc.copperisms.items.CopperismsItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopperismsMain implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("copperisms");
    public static final String MOD_ID = "copperisms";
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("copperisms started");
        CopperismsItems.registerItems();
        CopperismsItemGroups.registerItemGroups();

        // Model Predicate for Copper Bottle


    }
}