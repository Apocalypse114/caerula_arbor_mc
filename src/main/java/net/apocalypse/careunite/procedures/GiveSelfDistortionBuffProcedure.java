package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class GiveSelfDistortionBuffProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		ResourceLocation EffectRes = new ResourceLocation("bobsoriginiumdream","distortion");
		MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(EffectRes);
		if (effect == null) return;
		MobEffectInstance effectInst = new MobEffectInstance(effect, 200, 1);
		if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(effectInst);
	}
}
