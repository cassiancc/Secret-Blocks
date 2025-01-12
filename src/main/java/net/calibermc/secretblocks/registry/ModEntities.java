package net.calibermc.secretblocks.registry;

import net.calibermc.secretblocks.SecretBlocks;
import net.calibermc.secretblocks.blocks.entity.SecretBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.calibermc.secretblocks.SecretBlocks.id;

public class ModEntities {
    public static BlockEntityType<SecretBlockEntity> SECRET_BLOCK_ENTITY;
//    public static BlockEntityType<SecretInventoryEntity> SECRET_INVENTORY_ENTITY;

    // Registry
    public static void registerAllEntities() {
        SECRET_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                id("secret_block_entity_type"), FabricBlockEntityTypeBuilder.create(SecretBlockEntity::new, ModBlocks.BLOCKS.toArray(Block[]::new)).build());

//		SECRET_INVENTORY_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
//		        id("secret_inventory_entity_type"), FabricBlockEntityTypeBuilder.create(SecretInventoryEntity::new,
//		                inventoryBlocksList).build());
    }
}

