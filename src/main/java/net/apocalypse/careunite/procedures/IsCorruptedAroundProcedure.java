package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.Comparator;

public class IsCorruptedAroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		Entity corrupted = null;
		corrupted = (Entity) world.getEntitiesOfClass(SkadiCorruptedEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (corrupted == null) {
			return false;
		}
		return (corrupted instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0) < 1.5;
	}
}
