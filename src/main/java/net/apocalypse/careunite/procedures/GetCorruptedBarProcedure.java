package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.Comparator;

public class GetCorruptedBarProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		Entity corrupted = null;
		double convertP = 0;
		double phase = 0;
		double progress = 0;
		corrupted = (Entity) world.getEntitiesOfClass(SkadiCorruptedEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (corrupted == null) {
			return 0;
		}
		convertP = corrupted instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_convertP) : 0;
		phase = corrupted instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0;
		if (phase < 0.5) {
			progress = (900 - convertP) / (double) 900;
		} else if (phase < 1.5) {
			progress = (1120 - convertP) / (double) 1120;
		}
		return Math.max(0, Math.min(Math.round(50 * progress), 50));
	}
}
