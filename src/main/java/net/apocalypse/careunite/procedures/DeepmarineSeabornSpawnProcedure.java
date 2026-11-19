package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.Difficulty;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class DeepmarineSeabornSpawnProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (!world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, new ResourceLocation("caerula_arbor_reunite:deepmarine_spawn_biome")))) {
			return false;
		}
		if (Math.random() * 100 < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SEABORN_SPAWN_RATE))) {
			if (world.getDifficulty() == Difficulty.PEACEFUL) {
				return false;
			}
			return true;
		}
		return false;
	}
}
