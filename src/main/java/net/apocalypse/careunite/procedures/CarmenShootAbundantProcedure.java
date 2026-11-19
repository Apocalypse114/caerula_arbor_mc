package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.CarmenBulletEntity;

public class CarmenShootAbundantProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double t) {
		if (entity == null)
			return;
		double dama = 0;
		double xx = 0;
		double yy = 0;
		double zz = 0;
		Entity enemy = null;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_big_shoot")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_big_shoot")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		dama = entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			xx = enemy.getX();
			yy = enemy.getY();
			zz = enemy.getZ();
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(xx, (yy + 1.8), zz));
		}
		for (int index0 = 0; index0 < (int) t; index0++) {
			for (int index1 = 0; index1 < 3; index1++) {
				{
					Entity _shootFrom = entity;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
								AbstractArrow entityToSpawn = new CarmenBulletEntity(CaerulaArborModEntities.CARMEN_BULLET.get(), level);
								entityToSpawn.setOwner(shooter);
								entityToSpawn.setBaseDamage(damage);
								entityToSpawn.setKnockback(knockback);
								entityToSpawn.setSilent(true);
								entityToSpawn.setPierceLevel(piercing);
								return entityToSpawn;
							}
						}.getArrow(projectileLevel, entity, (float) dama, 0, (byte) 1);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.75, 15);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
		}
	}
}
