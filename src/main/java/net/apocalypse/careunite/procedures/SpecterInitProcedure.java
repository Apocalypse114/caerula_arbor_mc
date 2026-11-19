package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SpecterEntity;

public class SpecterInitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_spect_skill")), SoundSource.NEUTRAL, (float) 2.5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:saw_spect_skill")), SoundSource.NEUTRAL, (float) 2.5, 1, false);
			}
		}
		if (entity instanceof SpecterEntity) {
			((SpecterEntity) entity).setAnimation("animation.specter.start");
		}
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
			_livingEntity3.getAttribute(ForgeMod.SWIM_SPEED.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity2.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0) * 8));
		if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.33);
	}
}
