package net.calibermc.secretblocks.render;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.calibermc.secretblocks.SecretBlocks;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;

public class SecretBlockResourceProvider implements ModelResourceProvider {

	public static final SecretBlockUnbakedModel CAMO_MODEL = new SecretBlockUnbakedModel();
	public static final Identifier CAMO_BLOCK = SecretBlocks.id("block/camo_block");

	@Override
	public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) {
		if (identifier.equals(CAMO_BLOCK)) {
			return CAMO_MODEL;
		} else {
			return null;
		}
	}
}
