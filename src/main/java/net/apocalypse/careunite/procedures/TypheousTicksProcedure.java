package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

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

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.TyphoeusEntity;

public class TypheousTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dura = 0;
		double skill1cooldown = 0;
		double skill2cooldown = 0;
		double skill1t = 0;
		double skill2t = 0;
		double afloatShotLeft = 0;
		Entity enemy = null;
		boolean canAttack = false;
		if (entity.isAlive()) {
			dura = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_DURATION) : 0;
			skill1t = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_AFLOAT_T) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			canAttack = !(enemy == null) && enemy.isAlive();
			if (dura > 0) {
				if (entity instanceof TyphoeusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_DURATION, (int) (dura - 1));
			}
			if (skill1t > 0) {
				if (skill1t > 66) {
					afloatShotLeft = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_AFLOAT_SHOOT_LEFT) : 0;
					if (afloatShotLeft <= 0) {
						if (entity instanceof TyphoeusEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_AFLOAT_T, 40);
						if (entity instanceof TyphoeusEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_DURATION, 40);
						dura = 30;
					}
				} else {
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_AFLOAT_T, (int) (skill1t - 1));
					if (skill1t == 10) {
						if (entity instanceof TyphoeusEntity) {
							((TyphoeusEntity) entity).setAnimation("quit_afloat");
						}
						if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
							_livingEntity12.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(0);
					}
				}
			} else {
				skill1cooldown = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_SKILL_COOLDOWN_1) : 0;
				if (skill1cooldown > 0) {
					if (skill2t <= 0) {
						if (entity instanceof TyphoeusEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_COOLDOWN_1, (int) (skill1cooldown - 1));
					}
				} else if (skill2t <= 0 && dura <= 0 && canAttack) {
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_AFLOAT_T, 99);
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_AFLOAT_SHOOT_LEFT, 5);
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_DURATION, 30);
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_COOLDOWN_1, 300);
					dura = 30;
					skill1t = 30;
					if (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
						_livingEntity19.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(60);
					if (entity instanceof TyphoeusEntity) {
						((TyphoeusEntity) entity).setAnimation("to_afloat");
					}
				}
			}
			skill2t = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_SKILL_T) : 0;
			if (skill2t > 0) {
				if (entity instanceof TyphoeusEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_T, (int) (skill2t - 1));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
				if (skill2t == 39) {
					TypheousDropSIgnProcedure.execute(world, x, y, z, enemy);
				}
				if (skill2t == 32) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:typheous_skill_post")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:typheous_skill_post")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
				}
				if (skill2t == 12) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:typheous_skill_shoot")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:typheous_skill_shoot")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
				if (skill2t == 10) {
					TyphoeusDoSkillShootProcedure.execute(world, enemy, entity, 0, 0);
				}
				if (skill2t == 8) {
					TyphoeusDoSkillShootProcedure.execute(world, enemy, entity, 3, 3);
				}
				if (skill2t == 6) {
					TyphoeusDoSkillShootProcedure.execute(world, enemy, entity, 3, -3);
				}
				if (skill2t == 4) {
					TyphoeusDoSkillShootProcedure.execute(world, enemy, entity, -3, 3);
				}
				if (skill2t == 2) {
					TyphoeusDoSkillShootProcedure.execute(world, enemy, entity, -3, -3);
				}
			} else {
				skill2cooldown = entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_SKILL_COOLDOWN_2) : 0;
				if (skill2cooldown > 0) {
					if (skill1t <= 0) {
						if (entity instanceof TyphoeusEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_COOLDOWN_2, (int) (skill2cooldown - 1));
					}
				} else if (skill1t <= 0 && dura <= 0 && canAttack) {
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_T, 40);
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_DURATION, 40);
					if (entity instanceof TyphoeusEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TyphoeusEntity.DATA_SKILL_COOLDOWN_2, 360);
					dura = 40;
				}
			}
			if (entity.tickCount % 40 == 25) {
				IreneBurnBrandAroundProcedure.execute(world, x, y, z);
			}
			VanguardBuffProcedure.execute(world, x, y, z, entity);
		}
	}
}
