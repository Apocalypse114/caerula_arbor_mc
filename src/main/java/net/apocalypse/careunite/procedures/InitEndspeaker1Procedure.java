package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.Endspeaker1Entity;

public class InitEndspeaker1Procedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Endspeaker1Entity) {
			((Endspeaker1Entity) entity).setAnimation("animation.endspeaker_1.start");
		}
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(30);
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
							: 0) + 60));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 9, false, false));
		InitEndspeakerAbilitiesProcedure.execute(world, entity);
		GetEndspeakerPrefixesProcedure.execute(world, entity);
	}
}
