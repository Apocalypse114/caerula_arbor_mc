package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.IsharmlaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IsharmlaDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_attack_pre")), SoundSource.HOSTILE,
						(float) 2.5, 1);
			} else {
				_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_attack_pre")), SoundSource.HOSTILE, (float) 2.5, 1, false);
			}
		}
		CaerulaArborMod.queueServerWork(13, () -> {
			if (sourceentity.isAlive()) {
				IsharmlaPtcStreamProcedure.execute(world, sourceentity);
			}
		});
		CaerulaArborMod.queueServerWork(15, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 32) {
					IsharmlaMultiAttackProcedure.execute(world, x, y, z, sourceentity);
				}
			}
		});
		if (sourceentity instanceof IsharmlaEntity _datEntSetI)
			_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 60);
	}
}
