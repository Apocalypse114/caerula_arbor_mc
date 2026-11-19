package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SpecterEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SpecterSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double gap = 0;
		double sklp1 = 0;
		double dura = 0;
		double skillp2 = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_skillp1) : 0;
		skillp2 = entity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_skillp2) : 0;
		dura = entity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof SpecterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SpecterEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 >= 5) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
					if (entity instanceof SpecterEntity) {
						((SpecterEntity) entity).setAnimation("animation.specter.skill");
					}
					if (entity instanceof SpecterEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SpecterEntity.DATA_skillp1, 0);
					if (entity instanceof SpecterEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SpecterEntity.DATA_duration, (int) (dura + 45));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 35, 0, false, false));
					CaerulaArborMod.queueServerWork(8, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_heavy")), SoundSource.NEUTRAL, 3, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_heavy")), SoundSource.NEUTRAL, 3, 1, false);
							}
						}
					});
					CaerulaArborMod.queueServerWork(15, () -> {
						if (entity.isAlive()) {
							SpecterRangedProcedure.execute(world, x, y, z, entity, 2);
						}
					});
					CaerulaArborMod.queueServerWork(25, () -> {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_cut_spect")), SoundSource.NEUTRAL, 3, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_cut_spect")), SoundSource.NEUTRAL, 3, 1, false);
							}
						}
					});
					CaerulaArborMod.queueServerWork(31, () -> {
						if (entity.isAlive()) {
							SpecterRangedProcedure.execute(world, x, y, z, entity, 1.5);
						}
					});
				}
			}
		}
		if (skillp2 > 0) {
			if (GetHealthPercProcedure.execute(entity) <= 0.5 && skillp2 < 600) {
				if (entity instanceof SpecterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SpecterEntity.DATA_skillp2, (int) (skillp2 - 2));
			} else {
				if (entity instanceof SpecterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SpecterEntity.DATA_skillp2, (int) (skillp2 - 1));
			}
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (GetHealthPercProcedure.execute(entity) <= 0.5) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_skill_on")), SoundSource.NEUTRAL, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_skill_on")), SoundSource.NEUTRAL, 3, 1, false);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_skill")), SoundSource.NEUTRAL, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:specter_skill")), SoundSource.NEUTRAL, 3, 1, false);
						}
					}
					if (entity instanceof SpecterEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SpecterEntity.DATA_skillp2, 1000);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 400, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 400, 5, false, false));
				}
			}
		}
		HealFromGladiiaProcedure.execute(world, x, y, z, entity);
		SpecterGiveHealthBoostProcedure.execute(world, x, y, z, entity);
	}
}
