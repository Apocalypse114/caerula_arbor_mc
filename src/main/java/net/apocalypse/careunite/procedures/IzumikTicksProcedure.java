package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IzumikOffspringEntity;
import net.apocalypse.careunite.entity.IzumikEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class IzumikTicksProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		Entity enemy = null;
		double sklp = 0;
		double grow = 0;
		double phase = 0;
		double sklp1 = 0;
		double waves = 0;
		double amplifi = 0;
		if (entity.isAlive()) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			sklp = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_skillp) : 0;
			sklp1 = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_skillp_1) : 0;
			grow = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_growth_p) : 0;
			phase = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0;
			waves = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_wave) : 0;
			if (GetSpeedProcedure.execute(entity) > (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity9.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0)
					* 1.2) {
				entity.setDeltaMovement(new Vec3(0, 0, 0));
			}
			if (!(entity instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(CaerulaArborModMobEffects.IZUMIK_LEARN.get()))) {
				amplifi = Math.floor(grow / 5);
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence > 3) {
					amplifi = amplifi * 4;
				} else if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow > 3) {
					amplifi = amplifi * 3;
				} else {
					amplifi = amplifi * 2;
				}
				if (amplifi > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IZUMIK_LEARN.get(), 20, (int) (amplifi - 1), false, false));
				}
			}
			if (phase == 0) {
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.REGENERATION);
				if (!(entity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 35, 2, false, false));
				}
				if (grow < 20) {
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(14 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof IzumikOffspringEntity && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 7) {
								if (!entityiterator.level().isClientSide())
									entityiterator.discard();
								if (entity instanceof IzumikEntity _datEntSetI)
									_datEntSetI.getEntityData().set(IzumikEntity.DATA_growth_p, (int) (grow + 1));
								if (entity instanceof IzumikEntity _datEntSetI)
									_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, (int) (sklp - 1));
								if (world instanceof ServerLevel _level)
									_level.sendParticles(ParticleTypes.CLOUD, (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 32, 0.6, 0.6, 0.6, 0.1);
								break;
							}
						}
					}
				} else {
					if (entity instanceof IzumikEntity) {
						((IzumikEntity) entity).setAnimation("animation.izumik.revive");
					}
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_phase, 1);
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 300, 1, false, false));
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 300);
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("izumik.saying").getString())), false);
						}
					}
				}
				if (sklp <= 0) {
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 5);
					if (grow + 1 < 20) {
						if (entity instanceof IzumikEntity) {
							((IzumikEntity) entity).setAnimation("animation.izumik.grow");
						}
					}
					CaerulaArborMod.queueServerWork(25, () -> {
						if (!(entity == null)) {
							IzumikRangedProcedure.execute(world, x, y, z, entity);
						}
					});
					return true;
				}
				if (waves <= 0) {
					if (entity instanceof IzumikEntity) {
						((IzumikEntity) entity).setAnimation("animation.izumik.revive");
					}
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_phase, 1);
					if (entity instanceof LivingEntity _entity)
						_entity.removeEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 300, 1, false, false));
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 300);
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("izumik.saying").getString())), false);
						}
					}
				}
				if (sklp1 <= 0 && waves > 0) {
					for (int index0 = 0; index0 < 12; index0++) {
						IzumikSpawnProcedure.execute(world, x, y, z, entity);
					}
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_wave, (int) (waves - 1));
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp_1, 600);
				} else {
					if (entity instanceof IzumikEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp_1, (int) (sklp1 - 1));
				}
			} else {
				if (sklp <= 0) {
					if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).isAlive()) {
						if (((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) != null ? entity.distanceTo((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) : -1) <= 24) {
							if (phase >= 2) {
								if (entity instanceof IzumikEntity _datEntSetI)
									_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 400);
							} else {
								if (entity instanceof IzumikEntity _datEntSetI)
									_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 600);
							}
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 0, false, false));
							if (entity instanceof IzumikEntity) {
								((IzumikEntity) entity).setAnimation("animation.izumik.skill");
							}
							CaerulaArborMod.queueServerWork(35, () -> {
								if (entity.isAlive()) {
									if (entity instanceof LivingEntity _entity)
										_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.03));
									if (entity instanceof LivingEntity _livingEntity65 && _livingEntity65.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
										_livingEntity65.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
												.setBaseValue(Math.min((entity instanceof LivingEntity _livingEntity63 && _livingEntity63.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
														? _livingEntity63.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
														: 0) + 1, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05));
									IzumikShockPreProcedure.execute(world, x, y, z, entity);
								}
							});
							return true;
						}
					}
				} else {
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.33) {
						if (entity instanceof IzumikEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, (int) (sklp - 2));
					} else {
						if (entity instanceof IzumikEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, (int) (sklp - 1));
					}
				}
			}
		}
		return false;
	}
}
