package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TuitionHandbowEntity;

public class OceanArrowHitProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		BombArrowProcedure.execute(world, immediatesourceentity);
		CeaseInvTickProcedure.execute(world, entity, immediatesourceentity, sourceentity);
		if (immediatesourceentity.getPersistentData().getBoolean("batallionSkill")) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 160, 0, false, false));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 300, 0, false, false));
		}
		if (sourceentity instanceof TuitionHandbowEntity) {
			HandbowRangedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, immediatesourceentity, sourceentity,
					sourceentity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0, 3);
		}
	}
}
