package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.YirahelBeaconEntity;

import java.util.List;
import java.util.Comparator;

public class OnBeaconTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		boolean mayRelease = false;
		double atkAnim = 0;
		double laserT = 0;
		double laserCooldown = 0;
		double skillT = 0;
		double skillCooldown = 0;
		double enemyCount = 0;
		double pp = 0;
		if (entity.isAlive()) {
			atkAnim = entity instanceof YirahelBeaconEntity _datEntI ? _datEntI.getEntityData().get(YirahelBeaconEntity.DATA_ATTACK_ANIM) : 0;
			laserT = entity instanceof YirahelBeaconEntity _datEntI ? _datEntI.getEntityData().get(YirahelBeaconEntity.DATA_LASER_T) : 0;
			laserCooldown = entity instanceof YirahelBeaconEntity _datEntI ? _datEntI.getEntityData().get(YirahelBeaconEntity.DATA_LASER_COOLDOWN) : 0;
			skillT = entity instanceof YirahelBeaconEntity _datEntI ? _datEntI.getEntityData().get(YirahelBeaconEntity.DATA_SKILL_T) : 0;
			skillCooldown = entity instanceof YirahelBeaconEntity _datEntI ? _datEntI.getEntityData().get(YirahelBeaconEntity.DATA_SKILL_COOLDOWN) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			mayRelease = !world.isClientSide() && !(enemy == null) && enemy.isAlive();
			if (atkAnim > 0) {
				if (entity instanceof YirahelBeaconEntity _datEntSetI)
					_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_ATTACK_ANIM, (int) (atkAnim - 1));
				mayRelease = false;
			}
			if (laserT > 0) {
				if (entity instanceof YirahelBeaconEntity _datEntSetI)
					_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_LASER_T, (int) (laserT - 1));
				if (laserT == 45) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:beacon_laser_pre")), SoundSource.NEUTRAL, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:beacon_laser_pre")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
				}
				if (laserT == 28) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:beacon_laser")), SoundSource.NEUTRAL, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:beacon_laser")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
				}
				if (mayRelease) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), y, (enemy.getZ())));
				}
				if (laserT <= 27 && laserT >= 15) {
					DoBeaconForwardLaserProcedure.execute(world, x, y, z, entity, GetDistOfLaserProcedure.execute(laserT));
				}
				mayRelease = false;
			} else {
				if (laserCooldown > 0) {
					if (entity instanceof YirahelBeaconEntity _datEntSetI)
						_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_LASER_COOLDOWN, (int) (laserCooldown - 1));
				} else if (skillT <= 0) {
					if (mayRelease && (enemy != null ? entity.distanceTo(enemy) : -1) < 10) {
						if (entity instanceof YirahelBeaconEntity _datEntSetI)
							_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_LASER_COOLDOWN, 300);
						if (entity instanceof YirahelBeaconEntity _datEntSetI)
							_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_LASER_T, 45);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 9, 0, false, false));
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 9, false, false));
						laserT = 45;
					}
				}
			}
			if (skillT > 0) {
				if (entity instanceof YirahelBeaconEntity _datEntSetI)
					_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_SKILL_T, (int) (skillT - 1));
				entity.setDeltaMovement(new Vec3(0, 0, 0));
				if (skillT == 85) {
					if (mayRelease) {
						PutBeaconSelectorProcedure.execute(world, x, enemy.getY(), z, entity);
					} else {
						PutBeaconSelectorProcedure.execute(world, x, y, z, entity);
					}
				}
				mayRelease = false;
			} else {
				if (skillCooldown > 0) {
					if (entity instanceof YirahelBeaconEntity _datEntSetI)
						_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_SKILL_COOLDOWN, (int) (skillCooldown - 1));
					entity.setDeltaMovement(new Vec3(0, 0, 0));
				} else if (laserT <= 0) {
					if (mayRelease && entity.tickCount % 5 == 0 && (enemy != null ? entity.distanceTo(enemy) : -1) < 24) {
						pp = GetHealthPercProcedure.execute(entity);
						if (pp > 0.6) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
								for (Entity entityiterator : _entfound) {
									if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
										enemyCount = enemyCount + 1;
									}
									if (enemyCount >= 3) {
										break;
									}
								}
							}
						}
						if (pp <= 0.6 || enemyCount >= 3) {
							if (entity instanceof YirahelBeaconEntity _datEntSetI)
								_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_SKILL_T, 85);
							if (entity instanceof YirahelBeaconEntity _datEntSetI)
								_datEntSetI.getEntityData().set(YirahelBeaconEntity.DATA_SKILL_COOLDOWN, 480);
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 85, 9, false, false));
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 85, 9, false, false));
						}
					}
				}
			}
		}
	}
}
