package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.SaintCarmenEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class CarmenSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double dura = 0;
		double sklp2 = 0;
		double shootCooldown = 0;
		double bullet = 0;
		double reloadP = 0;
		boolean canShoot = false;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 40 == 20) {
			IreneBurnBrandAroundProcedure.execute(world, x, y, z);
		}
		sklp1 = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_skillP1) : 0;
		sklp2 = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_skillP2) : 0;
		shootCooldown = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_shootP) : 0;
		bullet = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_bullet) : 0;
		reloadP = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_reloadP) : 0;
		dura = entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof SaintCarmenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_duration, (int) (dura - 1));
		}
		if (shootCooldown > 0) {
			if (entity instanceof SaintCarmenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_shootP, (int) (shootCooldown - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof SaintCarmenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_skillP1, (int) (sklp1 - 1));
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
					if (entity instanceof SaintCarmenEntity) {
						((SaintCarmenEntity) entity).setAnimation("animation.saint_carmen.melee_skill");
					}
					if (entity instanceof SaintCarmenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_skillP1, 240);
					if (entity instanceof SaintCarmenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_duration, 33);
					dura = 33;
					CaerulaArborMod.queueServerWork(10, () -> {
						if (entity.isAlive()) {
							CarmenTeleportProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(21, () -> {
						if (entity.isAlive()) {
							CarmenShootProcedure.execute(world, x, y, z, entity, 2);
						}
					});
				}
			}
		}
		canShoot = bullet > 0 && shootCooldown <= 0;
		if (sklp2 > 0) {
			if (entity instanceof SaintCarmenEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_skillP2, (int) (sklp2 - 1));
		} else if (dura <= 0 && canShoot) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
					if (entity instanceof SaintCarmenEntity) {
						((SaintCarmenEntity) entity).setAnimation("animation.saint_carmen.gun_skill");
					}
					if (entity instanceof SaintCarmenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_skillP2, 480);
					if (entity instanceof SaintCarmenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_duration, 50);
					if (entity instanceof SaintCarmenEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_shootP, 50);
					shootCooldown = 50;
					dura = 50;
					entity.push((entity.getLookAngle().x * (-1.5)), 0, (entity.getLookAngle().z * (-1.5)));
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (entity.getY() + 1.8), (enemy.getZ())));
					CaerulaArborMod.queueServerWork(12, () -> {
						if (entity.isAlive()) {
							CarmenShootAbundantProcedure.execute(world, x, y, z, entity, 1);
						}
					});
					CaerulaArborMod.queueServerWork(23, () -> {
						if (entity.isAlive()) {
							CarmenShootAbundantProcedure.execute(world, x, y, z, entity, 2);
						}
					});
					CaerulaArborMod.queueServerWork(35, () -> {
						if (entity.isAlive()) {
							CarmenShootAbundantProcedure.execute(world, x, y, z, entity, 3);
						}
					});
				}
			}
		}
		if (canShoot) {
			if (dura <= 0) {
				if (!(enemy == null) && enemy.isAlive()) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 6) {
						if (entity instanceof SaintCarmenEntity) {
							((SaintCarmenEntity) entity).setAnimation("animation.saint_carmen.gun");
						}
						if (entity instanceof SaintCarmenEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_duration, 20);
						if (entity instanceof SaintCarmenEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_shootP, 80);
						if (entity instanceof SaintCarmenEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_bullet, (int) (bullet - 1));
						shootCooldown = 80;
						dura = 20;
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (entity.getY() + 1.8), (enemy.getZ())));
						CaerulaArborMod.queueServerWork(9, () -> {
							if (entity.isAlive()) {
								CarmenShootProcedure.execute(world, x, y, z, entity, 2);
							}
						});
					}
				}
			}
		} else {
			if (reloadP > 0) {
				if (entity instanceof SaintCarmenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_reloadP, (int) (reloadP - 1));
			} else if (dura <= 0) {
				if (entity instanceof SaintCarmenEntity) {
					((SaintCarmenEntity) entity).setAnimation("animation.saint_carmen.reload");
				}
				if (entity instanceof SaintCarmenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_bullet, 3);
				if (entity instanceof SaintCarmenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_duration, 20);
				if (entity instanceof SaintCarmenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_reloadP, 600);
				if (entity instanceof SaintCarmenEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintCarmenEntity.DATA_shootP, 20);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_reload")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_reload")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
			}
		}
		CArmenShowBulletsProcedure.execute(world, x, y, z, entity, bullet);
	}
}
