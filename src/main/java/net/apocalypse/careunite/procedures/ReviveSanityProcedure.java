package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class ReviveSanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double dur = 0;
		double cursnt = 0;
		if (entity.isAlive()) {
			dur = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.SANITY_IMMUE.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.SANITY_IMMUE.get()).getDuration() : 0;
			cursnt = entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0;
			if (dur <= 1) {
				if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
					_livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
			} else if (dur <= 200) {
				if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
					_livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(Math.max(Math.max((200 - dur) * 5, 1), cursnt));
			}
		}
	}
}
