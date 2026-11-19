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
import net.apocalypse.careunite.entity.IshmaelEntity;

public class GiveTrappedBuffProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity obj) {
		if (entity == null || obj == null)
			return;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.TRAPPED.get()))) {
			if ((entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_SKILL_P) : 0) < 0) {
				if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAPPED.get(), 80, 1, false, false));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_trap")), SoundSource.NEUTRAL, (float) 1.5,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_trap")), SoundSource.NEUTRAL, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			} else if (Math.random() < 0.5) {
				if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAPPED.get(), 80, 0, false, false));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_trap")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_trap")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
		}
		if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 80, 0, false, false));
	}
}
