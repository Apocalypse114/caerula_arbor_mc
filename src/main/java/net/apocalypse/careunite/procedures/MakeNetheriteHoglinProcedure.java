package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedHoglinEntity;

public class MakeNetheriteHoglinProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).setBaseValue(8);
		if (Math.random() <= 0.15) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue((1.25 * (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0)));
			if (entity instanceof OceanizedHoglinEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedHoglinEntity.DATA_isNetherite, true);
			if (entity instanceof OceanizedHoglinEntity animatable)
				animatable.setTexture("oceanized_hoglin_netherite");
		}
	}
}
