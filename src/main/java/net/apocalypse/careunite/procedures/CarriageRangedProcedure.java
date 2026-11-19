package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Mob;

public class CarriageRangedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amount, double range) {
		if (entity == null)
			return;
		double damage = 0;
		double r = 0;
		r = range;
		damage = amount;
		if (entity instanceof Mob mob){
	
			DamageSources ss = entity.level().damageSources();
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= r) {
					if (entityiterator.hurt(ss.mobAttack(mob), (float) damage)){
						entityiterator.push((entity.getLookAngle().x * 0.5), 0.5, (entity.getLookAngle().z * 0.5));
					}
				}
			}
		}
	}
}
