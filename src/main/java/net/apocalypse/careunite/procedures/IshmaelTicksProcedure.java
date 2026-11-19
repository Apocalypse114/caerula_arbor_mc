package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.IshmaelEntity;

public class IshmaelTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double chargeP = 0;
		double skillP = 0;
		double duration = 0;
		double spell = 0;
		double bt = 0;
		double blinkt = 0;
		Entity enemy = null;
		if (world.isClientSide()) {
			return;
		}
		if (entity.isAlive()) {
			VanguardBuffProcedure.execute(world, x, y, z, entity);
			chargeP = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_CHARGE_P) : 0;
			skillP = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_SKILL_P) : 0;
			duration = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_DURATION) : 0;
			spell = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_CHARGED_SPELL) : 0;
			bt = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_BEAM_RENDER_T) : 0;
			blinkt = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_BLINK_T) : 0;
			if (bt > 0) {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_BEAM_RENDER_T, (int) (bt - 1));
			}
			if (blinkt > 0) {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_BLINK_T, (int) (blinkt - 1));
			}
			if (duration > 0) {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_DURATION, (int) (duration - 1));
			}
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (skillP > -300) {
				if (skillP > 0) {
					if (entity instanceof IshmaelEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IshmaelEntity.DATA_MAX_CHARGE_P, 60);
					if (entity instanceof IshmaelEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IshmaelEntity.DATA_SKILL_P, (int) (skillP - 1));
				} else {
					if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) < 24) {
						if (skillP == 0) {
							IshmaelRoundBuffProcedure.execute(world, x, y, z, entity);
							if (entity instanceof IshmaelEntity _datEntSetI)
								_datEntSetI.getEntityData().set(IshmaelEntity.DATA_SKILL_P, -1);
						}
					}
					if (skillP < 0) {
						IshmaelRimPtcProcedure.execute(world, x, y, z, entity);
						if ((skillP * (-1)) % 20 == 10) {
							IshmaelAttackProcedure.execute(world, x, y, z, entity, GetRandomEnemyForIshmaelProcedure.execute(world, x, y, z, entity));
						}
						if (entity instanceof IshmaelEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IshmaelEntity.DATA_MAX_CHARGE_P, 20);
						if (entity instanceof IshmaelEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IshmaelEntity.DATA_SKILL_P, (int) (skillP - 1));
					}
				}
			} else {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_SKILL_P, 600);
			}
			if (chargeP > 0) {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_CHARGE_P, (int) (chargeP - 1));
				if (chargeP == 8 && spell < 3 && duration <= 0) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_charge")), SoundSource.NEUTRAL, (float) 1.5,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_charge")), SoundSource.NEUTRAL, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
			} else if (spell < 3 && duration <= 0) {
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_CHARGED_SPELL, (int) (spell + 1));
				if (entity instanceof IshmaelEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IshmaelEntity.DATA_CHARGE_P, (int) (entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_MAX_CHARGE_P) : 0));
			}
			if (entity.tickCount % 30 == 30) {
				IreneBurnBrandAroundProcedure.execute(world, x, y, z);
			}
		}
	}
}
