package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.BishopFishEntity;

import java.util.Comparator;

public class GetBoshopSkillIndexProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		Entity ent = null;
		double ind = 0;
		ent = (Entity) world.getEntitiesOfClass(BishopFishEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(ent == null)) {
			ind = Math.round((ent instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_endp) : 0) / 24);
		}
		if (ind > 100) {
			ind = 100;
		} else if (ind < 0) {
			ind = 0;
		}
		return ind;
	}
}
