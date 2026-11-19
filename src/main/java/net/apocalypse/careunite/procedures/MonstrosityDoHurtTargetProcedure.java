package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class MonstrosityDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (IsMonstrosityDurativeProcedure.execute(sourceentity)) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_attack_pre")), SoundSource.NEUTRAL, 2,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_attack_pre")), SoundSource.NEUTRAL, 2,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			CaerulaArborMod.queueServerWork(15, () -> {
				if (sourceentity.isAlive() && (sourceentity != null ? entity.distanceTo(sourceentity) : -1) < 5) {
					MonstrosityRangedProcedure.execute(world, sourceentity, entity, Mth.nextDouble(RandomSource.create(), 1, 1.5));
				}
			});
			if (sourceentity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_DURATION, (int) ((entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_DURATION) : 0) + 40));
		}
	}
}
