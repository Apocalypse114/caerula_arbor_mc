package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.TiderageDireswineEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class DireswineDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		CaerulaArborMod.queueServerWork(11, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 3) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
							(float) ((sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
									* (1 + 0.5 * (sourceentity instanceof TiderageDireswineEntity _datEntI ? _datEntI.getEntityData().get(TiderageDireswineEntity.DATA_RUN_BONUS) : 0))));
					entity.push(0, 0.5, 0);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.hoglin.attack")), SoundSource.HOSTILE, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.hoglin.attack")), SoundSource.HOSTILE, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					if ((entity instanceof TiderageDireswineEntity _datEntI ? _datEntI.getEntityData().get(TiderageDireswineEntity.DATA_RUN_BONUS) : 0) > 5) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.hoglin.angry")), SoundSource.HOSTILE, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.hoglin.angry")), SoundSource.HOSTILE, 1,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
							}
						}
					}
				}
				if (sourceentity instanceof TiderageDireswineEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TiderageDireswineEntity.DATA_RUN_BONUS, 0);
			}
		});
	}
}
