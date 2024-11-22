package net.calibermc.secretblocks;

import io.netty.buffer.Unpooled;
import net.calibermc.secretblocks.blocks.*;
import net.calibermc.secretblocks.blocks.entity.SecretBlockEntity;
import net.calibermc.secretblocks.registry.ModBlocks;
import net.calibermc.secretblocks.registry.ModEntities;
import net.calibermc.secretblocks.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Collection;


@SuppressWarnings("resource")
public class SecretBlocks implements ModInitializer {

	public static final String MOD_ID = "secretblocks";
	public static Identifier id(String id) { return new Identifier("secretblocks", id); }
	public static RegistryWrapper.Impl<Block> LOOKUP = BuiltinRegistries.createWrapperLookup().getWrapperOrThrow(RegistryKeys.BLOCK);


	// CREATIVE INVENTORY TAB GROUP
	public static final ItemGroup SECRET_BLOCKS_GROUP = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.secretblocks.secret_blocks")).icon(() -> new ItemStack(ModItems.CAMOUFLAGE_PASTE)).entries(((displayContext, entries) -> {
		ModBlocks.BLOCKS.forEach(entries::add);
		ModItems.ITEMS.forEach(entries::add);

	})).build();

	@Override
	public void onInitialize() {

		Registry.register(Registries.ITEM_GROUP, id("secret_blocks"), SECRET_BLOCKS_GROUP);

		ModBlocks.registerBlocks();
		ModItems.registerItems();
		ModEntities.registerAllEntities();

		ServerPlayNetworking.registerGlobalReceiver(SecretBlocks.id("hit_setter"), (server, player, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			BlockHitResult hit = buf.readBlockHitResult();
			boolean glass = buf.readBoolean();
			World world = player.getWorld();
			Direction facing = player.getHorizontalFacing().getOpposite();
			server.execute(() -> {
				if (world.getBlockEntity(pos) instanceof SecretBlockEntity blockEntity) {

					if (hit.getType() != HitResult.Type.MISS) {

						BlockPos blockPos = hit.getBlockPos();
						BlockState blockState = world.getBlockState(blockPos);
						Block block = blockState.getBlock();

						if (block instanceof SecretBlock) {
							if (world.getBlockEntity(blockPos) instanceof SecretBlockEntity blockEntityAdjacent) {
                                for (Direction dir : Direction.values()) {
									if (glass) {
										SecretBlocks.updateSide(facing != dir ? blockEntityAdjacent.getState(hit.getSide()) : Blocks.GLASS.getDefaultState(), dir, pos, blockEntity);
									} else {
										SecretBlocks.updateSide(blockEntityAdjacent.getState(hit.getSide()), dir, pos, blockEntity);
									}
								}
							} else {
								for (Direction dir : Direction.values()) {
									if (glass) {
										SecretBlocks.updateSide(facing != dir ? Blocks.STONE.getDefaultState() : Blocks.GLASS.getDefaultState(), dir, pos, blockEntity);
									} else {
										SecretBlocks.updateSide(Blocks.STONE.getDefaultState(), dir, pos, blockEntity);
									}
								}
							}
						} else if (block != Blocks.AIR && blockState.isFullCube(world, blockPos)) {
							for (Direction dir : Direction.values()) {
								if (glass) {
									SecretBlocks.updateSide(facing != dir ? blockState : Blocks.GLASS.getDefaultState(), dir, pos, blockEntity);
								} else {
									SecretBlocks.updateSide(blockState, dir, pos, blockEntity);
								}
							}
						} else {
							for (Direction dir : Direction.values()) {
								if (glass) {
									SecretBlocks.updateSide(facing != dir ? Blocks.STONE.getDefaultState() : Blocks.GLASS.getDefaultState(), dir, pos, blockEntity);
								} else {
									SecretBlocks.updateSide(Blocks.STONE.getDefaultState(), dir, pos, blockEntity);
								}
							}
						}
					}

					blockEntity.refresh();
				}
			});
		});
	}

	public static void updateSide(BlockState state, Direction dir, BlockPos pos, SecretBlockEntity entity) {
		if (!entity.getWorld().isClient) {
			PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
			NbtCompound tag = new NbtCompound();
			tag.put("state", NbtHelper.fromBlockState(state));
			passedData.writeNbt(tag);
			passedData.writeEnumConstant(dir);
			passedData.writeBlockPos(pos);
			Collection<ServerPlayerEntity> watchingPlayers = PlayerLookup.world((ServerWorld) entity.getWorld());
			watchingPlayers.forEach(player -> ServerPlayNetworking.send(player, SecretBlocks.id("update_side"), passedData));
		}
		entity.setState(dir, state);
	}

	public static void updateDirection(Direction faceDir, Direction dir, BlockPos pos, SecretBlockEntity entity) {
		if (!entity.getWorld().isClient) {
			PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
			passedData.writeEnumConstant(faceDir);
			passedData.writeEnumConstant(dir);
			passedData.writeBlockPos(pos);
			Collection<ServerPlayerEntity> watchingPlayers = PlayerLookup.world((ServerWorld) entity.getWorld());
			watchingPlayers.forEach(player -> ServerPlayNetworking.send(player, SecretBlocks.id("update_direction"), passedData));
		}
		entity.setDirection(dir, faceDir);
	}

	public static void updateRotation(int rotation, Direction dir, BlockPos pos, SecretBlockEntity entity) {
		if (!entity.getWorld().isClient) {
			PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
			passedData.writeInt(rotation);
			passedData.writeEnumConstant(dir);
			passedData.writeBlockPos(pos);
			Collection<ServerPlayerEntity> watchingPlayers = PlayerLookup.world((ServerWorld) entity.getWorld());
			watchingPlayers.forEach(player -> ServerPlayNetworking.send(player, SecretBlocks.id("update_rotation"), passedData));
		}
		entity.setRotation(dir, rotation);
	}
}
