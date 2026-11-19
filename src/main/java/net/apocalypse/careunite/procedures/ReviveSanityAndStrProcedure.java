package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public class ReviveSanityAndStrProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerSanityProcedure.execute(entity, 45);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
	}
}
