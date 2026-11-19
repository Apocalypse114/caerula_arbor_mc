package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IzumikEntity;

import java.util.Comparator;

public class GetIzumikSkillbarProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		Entity ent = null;
		double ind = 0;
		double phase = 0;
		ent = (Entity) world.getEntitiesOfClass(IzumikEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(ent == null)) {
			phase = ent instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0;
			ind = ent instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_skillp) : 0;
			if (phase == 0) {
				ind = Math.round(ind * 20);
			} else if (phase == 1) {
				ind = Math.round(ind / 6);
			} else {
				ind = Math.round(ind / 4);
			}
		}
		if (ind > 100) {
			ind = 100;
		} else if (ind < 0) {
			ind = 0;
		}
		return ind;
	}
}
