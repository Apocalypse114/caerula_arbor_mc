package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IsharmlaEntity;

import java.util.Comparator;

public class GetIsharmlaSkillIndexProcedure {
	public static Comparator<Entity> compareDistOf(double _x, double _y, double _z){
		return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
	}
	
	public static double execute(LevelAccessor world, double x, double y, double z) {
		Entity ent = null;
		double ind = 0;
		ent = (Entity) world.getEntitiesOfClass(IsharmlaEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true)
		.stream()
		.sorted(compareDistOf(x, y, z))
		.findFirst().orElse(null);
		if (!(ent == null)) {
			ind = 2400 - (ent instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_SKILLP_1) : 0);
		}
		return ind / 2400;
	}
}
