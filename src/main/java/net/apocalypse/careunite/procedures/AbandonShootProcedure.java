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
import net.apocalypse.careunite.entity.TheAbandonedEntity;
import net.apocalypse.careunite.entity.AbandonedShootEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class AbandonShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		Entity enemy = null;
		sklp = entity instanceof TheAbandonedEntity _datEntI ? _datEntI.getEntityData().get(TheAbandonedEntity.DATA_skillp) : 0;
		if (sklp > 0) {
			if (entity instanceof TheAbandonedEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TheAbandonedEntity.DATA_skillp, (int) (sklp - 1));
		} else {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) < 7) {
				if (entity instanceof TheAbandonedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TheAbandonedEntity.DATA_skillp, 100);
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + enemy.getBbHeight()), (enemy.getZ())));
				if (entity instanceof TheAbandonedEntity) {
					((TheAbandonedEntity) entity).setAnimation("animation.the_abandoned.shoot");
				}
				CaerulaArborMod.queueServerWork(23, () -> {
					if (entity.isAlive()) {
						new Object() {
							void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot")), SoundSource.HOSTILE, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot")), SoundSource.HOSTILE, 1, 1, false);
									}
								}
								{
									Entity _shootFrom = entity;
									Level projectileLevel = _shootFrom.level();
									if (!projectileLevel.isClientSide()) {
										Projectile _entityToSpawn = new Object() {
											public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
												AbstractArrow entityToSpawn = new AbandonedShootEntity(CaerulaArborModEntities.ABANDONED_SHOOT.get(), level);
												entityToSpawn.setOwner(shooter);
												entityToSpawn.setBaseDamage(damage);
												entityToSpawn.setKnockback(knockback);
												entityToSpawn.setSilent(true);
												return entityToSpawn;
											}
										}.getArrow(projectileLevel, entity,
												(float) ((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)
														? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
														: 0) * 0.85),
												0);
										_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
										_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.25, 2);
										projectileLevel.addFreshEntity(_entityToSpawn);
									}
								}
								final int tick2 = ticks;
								CaerulaArborMod.queueServerWork(tick2, () -> {
									if (timedlooptotal > timedloopiterator + 1) {
										timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
									}
								});
							}
						}.timedLoop(0, 3, 1);
					}
				});
			}
		}
		SeabornRidePolarProcedure.execute(world, x, y, z, entity);
	}
}
