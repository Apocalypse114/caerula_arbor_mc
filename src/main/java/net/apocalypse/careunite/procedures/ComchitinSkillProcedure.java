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
import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ComchitinSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double perc = 0;
		double dura = 0;
		CompChitinGolemTextureProcedure.execute(entity);
		if (!entity.isAlive()) {
			return;
		}
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
		BackToHomeCompProcedure.execute(x, y, z, entity);
		sklp1 = entity instanceof ComplexChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ComplexChitinGolemEntity.DATA_skillp) : 0;
		dura = entity instanceof ComplexChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ComplexChitinGolemEntity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_skillp, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5 && dura < 1) {
					if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_duration, 110);
					if (entity instanceof ComplexChitinGolemEntity) {
						((ComplexChitinGolemEntity) entity).setAnimation("animation.complex_chitin_golem.spin");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 110, 9, false, false));
					if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_skillp, 400);
					CaerulaArborMod.queueServerWork(6, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.extend")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.extend")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						}
					});
					CaerulaArborMod.queueServerWork(13, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.contract")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.contract")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						}
					});
					CaerulaArborMod.queueServerWork(20, () -> {
						if (entity.isAlive()) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 60, 0, false, false));
						}
					});
					for (int index0 = 0; index0 < 16; index0++) {
						CaerulaArborMod.queueServerWork((int) index0 * 3 + 26, () -> {
							if (entity.isAlive()) {
								GolemRangedProcedure.execute(world, entity);
							}
						});
					}
					CaerulaArborMod.queueServerWork(90, () -> {
						if (entity.isAlive()) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.contract")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.contract")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						}
					});
				}
			}
		}
	}
}
