package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import java.util.List;
import java.util.Comparator;

public class TrailroiteArrowFuncProcedure {
	public static Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
		AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		entityToSpawn.setKnockback(knockback);
		entityToSpawn.setCritArrow(true);
		return entityToSpawn;
	}


	public static void execute(LevelAccessor world, double x, double y, double z, Entity arrow, Entity entity, double amount, double link) {
		if (arrow == null || entity == null)
			return;
		Entity recentAttacker = null;
		Entity recentVictim = null;
		double arrowDamage = amount;
		if(arrow instanceof AbstractArrow){
			AbstractArrow absArrow = (AbstractArrow) arrow;
			arrowDamage = absArrow.getBaseDamage();
		}
		if (link <= 0) {
			return;
		}
		final Vec3 _center = new Vec3(x, y, z);
		List<LivingEntity> _entfound = world.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(12d), e -> e != entity)
			.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
		for (LivingEntity entityiterator : _entfound) {
			if (entityiterator.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
				continue;
			}
			if (!(entityiterator instanceof Monster) 
				&& (entity instanceof LivingEntity living)
				&& (entityiterator instanceof Mob m3)) {
				recentVictim = living.getLastHurtMob();
				recentAttacker = living.getLastHurtByMob();
				if (!(m3.getTarget() == entity || entityiterator == recentVictim || entityiterator == recentAttacker)) {
					continue;
				}
			}
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = getArrow(projectileLevel, entity, (float)arrowDamage, 0);
				_entityToSpawn.setPos(x, arrow.getY(), z);
				_entityToSpawn.shoot(
					entityiterator.getX() - x, 
					entityiterator.getY() + entityiterator.getBbHeight() * 0.9 - arrow.getY(), 
					entityiterator.getZ() - z, 2.75f, 0);
				_entityToSpawn.getPersistentData().putDouble("TrailriteLink", link -1);
				projectileLevel.addFreshEntity(_entityToSpawn);
				break;
			}
		}
	}
}
