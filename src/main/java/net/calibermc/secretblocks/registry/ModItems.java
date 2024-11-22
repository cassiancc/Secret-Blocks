package net.calibermc.secretblocks.registry;

import net.calibermc.secretblocks.SecretBlocks;
import net.calibermc.secretblocks.items.SwitchProbe;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;

import static net.calibermc.secretblocks.SecretBlocks.SECRET_BLOCKS_GROUP;

public class ModItems {
    public static final ArrayList<Item> ITEMS = new ArrayList<>();


    // Items
    public static final Item SECRET_GOGGLES = registerItem("secret_goggles",
            new Item( new Item.Settings().rarity(Rarity.UNCOMMON)));

    public static final Item SWITCH_PROBE = registerItem("switch_probe",
            new SwitchProbe( new Item.Settings().rarity(Rarity.RARE).maxCount(1)));

    public static final Item SWITCH_PROBE_ROTATION_MODE = registerItem("switch_probe_rotation_mode",
            new SwitchProbe( new Item.Settings().rarity(Rarity.RARE).maxCount(1)));

    public static final Item CAMOUFLAGE_PASTE = registerItem("camouflage_paste",
            new Item( new Item.Settings()));


    // Registry
    private static Item registerItem(String name, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, new Identifier(SecretBlocks.MOD_ID, name), item);
    }

    // Console Output
    public static void registerItems() {
        System.out.println("Registering Items for " + SecretBlocks.MOD_ID);
    }
}

