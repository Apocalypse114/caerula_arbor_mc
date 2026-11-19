package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class BishopEndskillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dx = 0;
		double dz = 0;
		double yfnl = 0;
		Entity sss = null;
		dx = Mth.nextInt(RandomSource.create(), -18, 18);
		dz = Mth.nextInt(RandomSource.create(), -18, 18);
		yfnl = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (x + dx), (int) (z + dz));
		if (yfnl < y - 6) {
			yfnl = y;
		}
		if (yfnl > y + 3) {
			yfnl = y + 3;
		}
		sss = SummonRandomSeabornProcedure.execute(world, 0.75, x + dx, yfnl, z + dz);
		EmrelySyncTeamProcedure.execute(entity, sss);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CLOUD, (x + dx), (yfnl + 1), (z + dz), 64, 1, 1, 1, 0.1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x + dx, yfnl + 1, z + dz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("ambient.underwater.enter")), SoundSource.NEUTRAL, (float) 1.5, 1);
			} else {
				_level.playLocalSound((x + dx), (yfnl + 1), (z + dz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("ambient.underwater.enter")), SoundSource.NEUTRAL, (float) 1.5, 1, false);
			}
		}
		if (ModGriefSettingsProcedure.execute(world)) {
			if (world instanceof ServerLevel _level)
				FallingBlockEntity.fall(_level, BlockPos.containing(x + dx, yfnl + 6, z + dz), CaerulaArborModBlocks.SEA_TRAIL_GROWN.get().defaultBlockState());
		}
	}
}
