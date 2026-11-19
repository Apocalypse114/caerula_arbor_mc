package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IsharmlaEntity;

import java.util.Comparator;

public class IsharmlaTearSkillRProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		Entity isharmla = null;
		double skillP = 0;
		isharmla = (Entity) world.getEntitiesOfClass(IsharmlaEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (isharmla == null) {
			return false;
		}
		if (isharmla instanceof IsharmlaEntity _datEntL2 && _datEntL2.getEntityData().get(IsharmlaEntity.DATA_IS_MONSTER)) {
			return false;
		}
		skillP = isharmla instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_SKILLP_1) : 0;
		if (skillP > 0) {
			if (isharmla instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, (int) Math.max(skillP - 500, 0));
			return true;
		}
		return false;
	}
}
