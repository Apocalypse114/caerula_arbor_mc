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
import net.apocalypse.careunite.entity.IreneEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IreneSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double dura = 0;
		double skillp2 = 0;
		double tap = 0;
		double less = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 40 == 15) {
			IreneBurnBrandAroundProcedure.execute(world, x, y, z);
		}
		sklp1 = entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_skillp1) : 0;
		skillp2 = entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_skillp2) : 0;
		dura = entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_duration) : 0;
		tap = entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_tapTick) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_duration, (int) (dura - 1));
		}
		if (tap > 0) {
			if (entity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_tapTick, (int) (tap - 1));
		}
		if (sklp1 >= 4 && dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
					if (entity instanceof IreneEntity) {
						((IreneEntity) entity).setAnimation("animation.irene.skill_1");
					}
					if (entity instanceof IreneEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IreneEntity.DATA_skillp1, 0);
					if (entity instanceof IreneEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IreneEntity.DATA_duration, 27);
					CaerulaArborMod.queueServerWork(10, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_fly")), SoundSource.NEUTRAL, 3, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_fly")), SoundSource.NEUTRAL, 3, 1, false);
								}
							}
							IreneAttackFlyProcedure.execute(world, x, y, z, entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null, entity, 3, 30);
						}
					});
				}
			}
		} else if (skillp2 >= 16 && dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 6) {
					if (entity instanceof IreneEntity) {
						((IreneEntity) entity).setAnimation("animation.irene.skill_2");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 60, 9, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_skill")), SoundSource.NEUTRAL, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_skill")), SoundSource.NEUTRAL, 3, 1, false);
						}
					}
					if (entity instanceof IreneEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IreneEntity.DATA_skillp2, 0);
					if (entity instanceof IreneEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IreneEntity.DATA_duration, 70);
					CaerulaArborMod.queueServerWork(9, () -> {
						if (entity.isAlive()) {
							IreneRangedFlyProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(16, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_skill_loop")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_skill_loop")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						}
					});
					for (int index0 = 0; index0 < 10; index0++) {
						CaerulaArborMod.queueServerWork((int) Math.toIntExact(Math.round(20 + index0 * 3.778)), () -> {
							if (entity.isAlive()) {
								IreneRangedGunProcedure.execute(world, x, y, z, entity);
							}
						});
					}
					CaerulaArborMod.queueServerWork(59, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_reload")), SoundSource.NEUTRAL, 3, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_reload")), SoundSource.NEUTRAL, 3, 1, false);
								}
							}
						}
					});
				}
			}
		}
		VanguardBuffProcedure.execute(world, x, y, z, entity);
	}
}
