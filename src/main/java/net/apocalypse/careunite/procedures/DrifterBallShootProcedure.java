package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.FleefishBulletEntity;

public class DrifterBallShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double xx = 0;
		double yy = 0;
		double zz = 0;
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
					AbstractArrow entityToSpawn = new FleefishBulletEntity(CaerulaArborModEntities.FLEEFISH_BULLET.get(), level);
					entityToSpawn.setOwner(shooter);
					entityToSpawn.setBaseDamage(damage);
					entityToSpawn.setKnockback(knockback);
					entityToSpawn.setSilent(true);
					return entityToSpawn;
				}
			}.getArrow(projectileLevel, entity, 5, 0);
			_entityToSpawn.setPos(x, (y + 0.625), z);
			_entityToSpawn.shoot((entity.getLookAngle().x), (entity.getLookAngle().y), (entity.getLookAngle().z), (float) 1.5, 1);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == itemstack.getItem()) {
			if (entity instanceof LivingEntity _entity)
				_entity.swing(InteractionHand.MAIN_HAND, true);
		} else {
			if (entity instanceof LivingEntity _entity)
				_entity.swing(InteractionHand.OFF_HAND, true);
		}
		itemstack.shrink(1);
	}
}
