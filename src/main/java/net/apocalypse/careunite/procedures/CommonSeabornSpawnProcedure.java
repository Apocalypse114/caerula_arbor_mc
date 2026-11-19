package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.Difficulty;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class CommonSeabornSpawnProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (!world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, new ResourceLocation("caerula_arbor_reunite:common_spawn_biome")))) {
			return false;
		}
		if (Math.random() * 100 < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SEABORN_SPAWN_RATE))) {
			if (world.getDifficulty() == Difficulty.PEACEFUL) {
				return false;
			}
			if (Math.random() < 0.8) {
				return world.getBrightness(LightLayer.SKY, BlockPos.containing(x, y, z)) >= 14 && world.getBrightness(LightLayer.BLOCK, BlockPos.containing(x, y, z)) < 3;
			}
			return world.getBrightness(LightLayer.SKY, BlockPos.containing(x, y, z)) >= 10 && world.getBrightness(LightLayer.BLOCK, BlockPos.containing(x, y, z)) < 3;
		}
		return false;
	}
}
