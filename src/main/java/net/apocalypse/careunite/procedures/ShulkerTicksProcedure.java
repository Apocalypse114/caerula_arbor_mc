package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
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
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Direction dire = Direction.NORTH;
		Direction curDire = Direction.NORTH;
		String curDIreName = "";
		boolean isAttached = false;
		Entity enemy = null;
		double peekTime = 0;
		double shootDelay = 0;
		double variant = 0;
		if (entity.isAlive()) {
			if (entity.tickCount <= 3) {
				{
					Entity _ent = entity;
					_ent.setYRot(0);
					_ent.setXRot(0);
					_ent.setYBodyRot(_ent.getYRot());
					_ent.setYHeadRot(_ent.getYRot());
					_ent.yRotO = _ent.getYRot();
					_ent.xRotO = _ent.getXRot();
					if (_ent instanceof LivingEntity _entity) {
						_entity.yBodyRotO = _entity.getYRot();
						_entity.yHeadRotO = _entity.getYRot();
					}
				}
			}
			variant = entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_VARIANT) : 0;
			if (variant == 1) {
				if (entity instanceof OceanizedShulkerEntity animatable)
					animatable.setTexture("oceanized_shulker_white");
			} else if (variant == 2) {
				if (entity instanceof OceanizedShulkerEntity animatable)
					animatable.setTexture("oceanized_shulker_complex");
			} else if (variant == 3) {
				if (entity instanceof OceanizedShulkerEntity animatable)
					animatable.setTexture("oceanized_shulker_bedrock");
			} else {
				if (entity instanceof OceanizedShulkerEntity animatable)
					animatable.setTexture("oceanized_shulker");
			}
			curDIreName = entity instanceof OceanizedShulkerEntity _datEntS ? _datEntS.getEntityData().get(OceanizedShulkerEntity.DATA_DIRECTION) : "";
			dire = Direction.byName(curDIreName);
			peekTime = entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_PEEK_TIME) : 0;
			shootDelay = entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_SHOOT_DELAY) : 0;
			isAttached = !(entity instanceof OceanizedShulkerEntity _datEntL11 && _datEntL11.getEntityData().get(OceanizedShulkerEntity.DATA_WALKING));
			if (isAttached) {
				if (!CanShulkerStayProcedure.execute(world, x, y, z, curDire)) {
					dire = GetShulkerDirectionProcedure.execute(world, x, y, z);
					if (!(dire == null)) {
						if (entity instanceof OceanizedShulkerEntity _datEntSetS)
							_datEntSetS.getEntityData().set(OceanizedShulkerEntity.DATA_DIRECTION, dire.toString());
					} else {
						ShulkerToSomewhereProcedure.execute(world, x, y, z, entity);
					}
				}
				entity.setDeltaMovement(new Vec3(0, 0, 0));
				if (!world.isClientSide()) {
					if (peekTime <= 0) {
						if (Math.random() < 0.01) {
							if (entity instanceof OceanizedShulkerEntity _datEntSetI)
								_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_PEEK_TIME, Mth.nextInt(RandomSource.create(), 60, 160));
							if (Math.random() < 0.5) {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.open1");
								}
							} else {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.open2");
								}
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.open")), SoundSource.HOSTILE, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.open")), SoundSource.HOSTILE, 1, 1, false);
								}
							}
						}
					} else {
						if (peekTime <= 1) {
							if (Math.random() < 0.5) {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.close1");
								}
							} else {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.close2");
								}
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.close")), SoundSource.HOSTILE, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.close")), SoundSource.HOSTILE, 1, 1, false);
								}
							}
						}
					}
				}
			} else {
				if (entity instanceof OceanizedShulkerEntity _datEntSetS)
					_datEntSetS.getEntityData().set(OceanizedShulkerEntity.DATA_DIRECTION, "up");
			}
			if (peekTime <= 0) {
				if (variant == 2) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SHULKER_BUFF.get(), 5, 2, false, false));
				} else if (variant == 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SHULKER_BUFF.get(), 5, 9, false, false));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SHULKER_BUFF.get(), 5, 0, false, false));
				}
			} else {
				if (entity instanceof OceanizedShulkerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_PEEK_TIME, (int) (peekTime - 1));
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(CaerulaArborModMobEffects.SHULKER_BUFF.get());
			}
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (shootDelay <= 0) {
				if (!(enemy == null) && enemy.isAlive()) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
						if (entity instanceof OceanizedShulkerEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_SHOOT_DELAY, Mth.nextInt(RandomSource.create(), 40, 60));
						if (entity instanceof OceanizedShulkerEntity _datEntSetI)
							_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_PEEK_TIME, 20);
						if (isAttached) {
							if (Math.random() < 0.5) {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.attack1");
								}
							} else {
								if (entity instanceof OceanizedShulkerEntity) {
									((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.attack2");
								}
							}
						} else {
							if (entity instanceof OceanizedShulkerEntity) {
								((OceanizedShulkerEntity) entity).setAnimation("animation.oceanized_shulker.attack_withfeet");
							}
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 9, false, false));
						}
						ShootShulkerBulletProcedure.execute(world, x, y, z, entity, enemy);
						if (Math.random() < 0.5) {
							ShootShulkerBulletProcedure.execute(world, x, y, z, entity, enemy);
						}
					}
				}
			} else {
				if (entity instanceof OceanizedShulkerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_SHOOT_DELAY, (int) (shootDelay - 1));
			}
		}
	}
}
