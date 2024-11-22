package net.calibermc.secretblocks.render;

import java.util.*;
import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SecretBlockUnbakedModel implements UnbakedModel {

	public static Mesh mesh;
	private static final Identifier DEFAULT_BLOCK_MODEL = new Identifier("minecraft:block/block");

	@Override
	public Collection<Identifier> getModelDependencies() {
		return List.of(DEFAULT_BLOCK_MODEL);
	}

	@Override
	public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

	}

	@Override
	public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
		return new SecretBlockBakedModel();
	}

}
