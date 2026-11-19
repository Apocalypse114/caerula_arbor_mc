package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.entity.RouteFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

import java.util.List;
import java.util.Comparator;

public class SummonFractalProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double num = 0;
		if (GetSeabornNumProcedure.execute(world, x, y, z) > (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
			return;
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof RouteFractalEntity || entityiterator instanceof LineringPathshaperEntity) {
					num = num + 1;
				}
			}
		}
		if (num < 18) {
			SummonSyncedFractalProcedure.execute(world, x, y, z, entity);
		}
	}
}
