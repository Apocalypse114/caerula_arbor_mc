package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.RunvolutionWaveEntity;

public class RunvolutionWaveTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double ttt = 0;
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
		entity.clearFire();
		ttt = entity instanceof RunvolutionWaveEntity _datEntI ? _datEntI.getEntityData().get(RunvolutionWaveEntity.DATA_EXIST_TICK) : 0;
		if (entity instanceof RunvolutionWaveEntity _datEntSetI)
			_datEntSetI.getEntityData().set(RunvolutionWaveEntity.DATA_EXIST_TICK, (int) (ttt + 1));
		if (ttt == 35) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, (float) 1.15);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, (float) 1.15, false);
				}
			}
			RunvolutionMatrixAttackProcedure.execute(world, x, y, z, entity, 16, 10, 2);
		}
		if (ttt == 45) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, 1, false);
				}
			}
			RunvolutionMatrixAttackProcedure.execute(world, x, y, z, entity, 10, 4, 3);
		}
		if (ttt == 55) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, (float) 0.85);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:runvolution_skill")), SoundSource.HOSTILE, 3, (float) 0.85, false);
				}
			}
			RunvolutionMatrixAttackProcedure.execute(world, x, y, z, entity, 4, 0, 4);
		}
		if (ttt >= 70) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
