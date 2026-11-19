package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

public class DoSpawnTraderProcedure {
	public static void execute(LevelAccessor world, double x, double z) {
		double ddd = 0;
		double ttt = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		if (!world.isClientSide()) {
			ddd = Mth.nextInt(RandomSource.create(), 16, 36);
			ttt = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			tx = x + ddd * Math.sin(ttt);
			tz = z + ddd * Math.cos(ttt);
			ty = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) tx, (int) tz);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_WANDERING_TRADER.get().spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			CaerulaArborMod.LOGGER.info(("Oceanized wandering trader emerged at x: " + (int) tx + " y: " + (int) ty + " z: " + (int) tz));
			CaerulaArborModVariables.MapVariables.get(world).traderSpawnRate = 0.01;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
