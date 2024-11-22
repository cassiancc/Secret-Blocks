package net.calibermc.secretblocks.registry;

import net.calibermc.secretblocks.SecretBlocks;
import net.calibermc.secretblocks.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModBlocks {
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();

    // Blocks
    public static final Block SOLID_BLOCK = registerBlock("solid_block",
            new SolidBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));

    public static final Block STAIR_BLOCK = registerBlock("stair_block",
            new StairBlock(SOLID_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(SOLID_BLOCK)));

    public static final Block SLAB_BLOCK = registerBlock("slab_block",
            new SlabBlock(AbstractBlock.Settings.copy(SOLID_BLOCK)));

    public static final Block GHOST_BLOCK = registerBlock("ghost_block",
            new GhostBlock(AbstractBlock.Settings.copy(SOLID_BLOCK).noCollision()));

    public static final Block ONE_WAY_GLASS = registerBlock("one_way_glass",
            new OneWayGlassBlock(AbstractBlock.Settings.copy(SOLID_BLOCK).nonOpaque()));

    public static final Block DOOR_BLOCK = registerBlock("door_block",
            new DoorBlock(AbstractBlock.Settings.copy(SOLID_BLOCK), BlockSetType.OAK));

    public static final Block IRON_DOOR_BLOCK = registerBlock("iron_door_block",
            new DoorBlock(AbstractBlock.Settings.copy(SOLID_BLOCK), BlockSetType.IRON));

    public static final Block TRAPDOOR_BLOCK = registerBlock("trapdoor_block",
            new TrapdoorBlock(AbstractBlock.Settings.copy(SOLID_BLOCK), BlockSetType.OAK));

    public static final Block IRON_TRAPDOOR_BLOCK = registerBlock("iron_trapdoor_block",
            new TrapdoorBlock(AbstractBlock.Settings.copy(SOLID_BLOCK), BlockSetType.OAK));

    public static final Block WOODEN_BUTTON = registerBlock("wooden_button",
            new SecretButton(AbstractBlock.Settings.copy(SOLID_BLOCK), new BlockSetType("secret"), true));

    public static final Block STONE_BUTTON = registerBlock("stone_button",
            new SecretButton(AbstractBlock.Settings.copy(SOLID_BLOCK), new BlockSetType("secret"), false));

    public static final Block SECRET_LEVER = registerBlock("secret_lever",
            new SecretLever(AbstractBlock.Settings.copy(SOLID_BLOCK)));

//    public static final Block SECRET_LADDER = registerBlock("secret_ladder",
//            new SecretLadder(SOLID_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(SOLID_BLOCK)), SecretBlocks.SECRET_BLOCKS_GROUP);

//    public static final Block SECRET_OBSERVER = registerBlock("secret_observer",
//            new SecretLever(AbstractBlock.Settings.copy(SOLID_BLOCK)), SecretBlocks.SECRET_BLOCKS_GROUP);

//    public static final Block SECRET_DISPENSER = registerBlock("secret_dispenser",
//            new SecretDispenser(AbstractBlock.Settings.copy(SOLID_BLOCK)), SecretBlocks.SECRET_BLOCKS_GROUP);

//    public static final Block SECRET_HOPPER = registerBlock("secret_hopper",
//            new SecretHopper(AbstractBlock.Settings.copy(SOLID_BLOCK)), SecretBlocks.SECRET_BLOCKS_GROUP);

//    public static final Block PRESSURE_PLATE = registerBlock("pressure_plate",
//            new PressurePlate(AbstractBlock.Settings.copy(SOLID_BLOCK)), SecretBlocks.SECRET_BLOCKS_GROUP);

    public static final Block SECRET_REDSTONE = registerBlock("secret_redstone",
            new SecretRedstone(FabricBlockSettings.copy(SOLID_BLOCK)));

    public static final Block SECRET_CHEST = registerBlock("secret_chest",
            new SecretChest(FabricBlockSettings.copy(SOLID_BLOCK)));

    //  DISABLED BLOCKS ^ SECRET_DISPENSER, SECRET_HOPPER, PRESSURE_PLATE, SECRET_OBSERVER, SECRET_LADDER
    //	public static final Block[] glassBlocksList = {  };  // For future use

    // Registry
    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(SecretBlocks.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, new Identifier(SecretBlocks.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(SecretBlocks.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    // Console Output
    public static void registerBlocks() {
        System.out.println("Registering Blocks for " + SecretBlocks.MOD_ID);
    }


}

