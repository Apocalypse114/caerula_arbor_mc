package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SpecterEntity;

import java.util.Comparator;

public class CallSpecterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		Entity specter = null;
		if (sourceentity instanceof Player || sourceentity instanceof SpecterEntity) {
			return;
		}
		specter = (Entity) world.getEntitiesOfClass(SpecterEntity.class, AABB.ofSize(new Vec3(x, y, z), 32, 32, 32), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(specter == null)) {
			if (specter instanceof Mob _entity)
				_entity.getNavigation().moveTo(x, y, z, 1);
			if (specter instanceof Mob _entity && sourceentity instanceof LivingEntity _ent)
				_entity.setTarget(_ent);
		}
	}
}
