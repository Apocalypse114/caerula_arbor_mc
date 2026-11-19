package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class EatPeduncleRiceProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_MISS_RATE.get(), 500, 15, false, false));
		RevivesPlayerSanityProcedure.execute(entity, Mth.nextInt(RandomSource.create(), 48, 64));
	}
}
