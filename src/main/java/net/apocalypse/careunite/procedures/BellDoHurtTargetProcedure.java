package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.ShakingBellEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BellDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double atkp = 0;
		if ((sourceentity instanceof ShakingBellEntity _datEntI ? _datEntI.getEntityData().get(ShakingBellEntity.DATA_ATTACK_DURATION) : 0) <= 0) {
			if (sourceentity instanceof ShakingBellEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ShakingBellEntity.DATA_ATTACK_DURATION, 17);
			atkp = sourceentity instanceof ShakingBellEntity _datEntI ? _datEntI.getEntityData().get(ShakingBellEntity.DATA_ATTACK_P) : 0;
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			if (atkp <= 0) {
				if (sourceentity instanceof ShakingBellEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ShakingBellEntity.DATA_ATTACK_P, 3);
				CaerulaArborMod.queueServerWork(4, () -> {
					if (sourceentity.isAlive()) {
						BellRangedRIngProcedure.execute(world, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), sourceentity, 2);
					}
				});
				CaerulaArborMod.queueServerWork(9, () -> {
					if (sourceentity.isAlive()) {
						BellRangedRIngProcedure.execute(world, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), sourceentity, 2.9);
					}
				});
				CaerulaArborMod.queueServerWork(13, () -> {
					if (sourceentity.isAlive()) {
						BellRangedRIngProcedure.execute(world, sourceentity.getX(), sourceentity.getY(), sourceentity.getZ(), sourceentity, 2);
					}
				});
			} else {
				if (sourceentity instanceof ShakingBellEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ShakingBellEntity.DATA_ATTACK_P, (int) (atkp - 1));
				CaerulaArborMod.queueServerWork(10, () -> {
					if (sourceentity.isAlive() && (sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 2.5) {
						entity.invulnerableTime = 0;
						entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), sourceentity),
								(float) (sourceentity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity25.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				});
			}
		}
	}
}
