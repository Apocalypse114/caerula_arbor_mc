package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class LastKnightFreezeProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double freeze = 0;
		freeze = sourceentity.getTicksFrozen();
		if (freeze < 140) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				sourceentity.setTicksFrozen((int) Math.min(freeze + 80, 200));
			} else {
				sourceentity.setTicksFrozen((int) Math.min(freeze + 40, 200));
			}
		} else {
			if (!(sourceentity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.FROZEN.get()))) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_jnight_freeze")), SoundSource.HOSTILE,
								4, (float) Mth.nextDouble(RandomSource.create(), 1, 1.15));
					} else {
						_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_jnight_freeze")), SoundSource.HOSTILE, 4,
								(float) Mth.nextDouble(RandomSource.create(), 1, 1.15), false);
					}
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), 80, 0, false, false));
			} else {
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), 40, 0, false, false));
			}
		}
	}
}
