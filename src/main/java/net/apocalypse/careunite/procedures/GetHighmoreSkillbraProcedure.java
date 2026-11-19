package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.HighmoreEntity;

import java.util.Comparator;

public class GetHighmoreSkillbraProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		Entity ent = null;
		double ind = 0;
		ent = (Entity) world.getEntitiesOfClass(HighmoreEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(ent == null)) {
			ind = Math.round((ent instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_skillp2) : 0) / 8);
		}
		if (ind > 85) {
			ind = 85;
		} else if (ind < 0) {
			ind = 0;
		}
		return ind;
	}
}
