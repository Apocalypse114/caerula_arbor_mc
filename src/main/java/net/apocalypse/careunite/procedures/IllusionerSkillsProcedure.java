package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.entity.OceanizedIllusionerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IllusionerSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dura = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
			if (entity instanceof OceanizedIllusionerEntity animatable)
				animatable.setTexture("oceanized_illusioner_broken");
		}
		if (!entity.isAlive()) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.INVISIBILITY);
			return;
		}
		sklp1 = entity instanceof OceanizedIllusionerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedIllusionerEntity.DATA_spellP) : 0;
		sklp2 = entity instanceof OceanizedIllusionerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedIllusionerEntity.DATA_mirrorP) : 0;
		dura = entity instanceof OceanizedIllusionerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedIllusionerEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_spellP, (int) (sklp1 - 1));
			if (sklp1 == 100) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.prepare_blindness")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.prepare_blindness")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			}
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 12) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.HOSTILE, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.HOSTILE, 1, 1, false);
						}
					}
					if (entity instanceof OceanizedIllusionerEntity) {
						((OceanizedIllusionerEntity) entity).setAnimation("animation.oceanized_illusioner.cast");
					}
					if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 0));
					if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DEDUCT_ONE_SANITY.get(), 200, 1));
					if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_spellP, 180);
					if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_duration, 15);
					dura = 15;
				}
			}
		}
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DEDUCT_ONE_SANITY.get());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.BLINDNESS);
		if (sklp2 > 0) {
			if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_mirrorP, (int) (sklp2 - 1));
			if (sklp2 == 100) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.prepare_mirror")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.prepare_mirror")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			}
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if (GetIllusionNumProcedure.execute(world, x, y, z) < 8 && GetSeabornNumProcedure.execute(world, x, y, z) < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.HOSTILE, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.cast_spell")), SoundSource.HOSTILE, 1, 1, false);
						}
					}
					if (entity instanceof OceanizedIllusionerEntity) {
						((OceanizedIllusionerEntity) entity).setAnimation("animation.oceanized_illusioner.fission");
					}
					DistributeIllusionProcedure.execute(world, x, y, z, entity);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 0));
					CaerulaArborMod.queueServerWork(18, () -> {
						if (!entity.isPassenger()) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 2400, 0));
						}
					});
					if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_mirrorP, 340);
					if (entity instanceof OceanizedIllusionerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedIllusionerEntity.DATA_duration, 20);
				}
			}
		}
		RaiderRideRavagerProcedure.execute(world, x, y, z, entity);
	}
}
