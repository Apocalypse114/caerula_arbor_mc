package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.NautilusHeadhunterEntity;

public class NautilusKillBonusProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		double bonus = 0;
		bonus = sourceentity instanceof NautilusHeadhunterEntity _datEntI ? _datEntI.getEntityData().get(NautilusHeadhunterEntity.DATA_BONUS) : 0;
		if (bonus < 10) {
			if (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) + 1));
			if (sourceentity instanceof NautilusHeadhunterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(NautilusHeadhunterEntity.DATA_BONUS, (int) (bonus + 1));
		}
	}
}
