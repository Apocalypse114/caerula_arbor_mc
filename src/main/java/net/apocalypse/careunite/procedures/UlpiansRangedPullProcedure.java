package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;

public class UlpiansRangedPullProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity obj) {
		if (obj == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		r = 4.5;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(9 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (!entityiterator.isAlive()) {
					continue;
				}
				if (entityiterator instanceof Player || (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				if (entityiterator == obj) {
					continue;
				}
				if ((entityiterator != null ? obj.distanceTo(entityiterator) : -1) <= r && GetEntityCosineProcedure.execute(obj, entityiterator) > 0.6 && IsValidEnemyForIreneProcedure.execute(entityiterator, obj)) {
					PullToMEProcedure.execute(entityiterator, obj);
				}
			}
		}
	}
}
