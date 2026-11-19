package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.MartusEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class DoMartusBuffProcedure {
	public static void execute(Entity entity, Entity tgt) {
		if (entity == null || tgt == null)
			return;
		if (!(tgt == null) && tgt.isAlive() && !tgt.getPersistentData().getBoolean("blessed")
			&& entity instanceof MartusEntity _datEntSetI && tgt instanceof LivingEntity _livEnt) {
			_datEntSetI.getEntityData().set(MartusEntity.DATA_skillp1, 400);
			_datEntSetI.setAnimation("animation.martus.buff");
			tgt.getPersistentData().putBoolean("blessed", true);
			float perc = _livEnt.getHealth() / _livEnt.getMaxHealth();
			setAttr(_livEnt, Attributes.ATTACK_DAMAGE, 2.5, false);
			setAttr(_livEnt, Attributes.MAX_HEALTH, 2.5, false);
			setAttr(_livEnt, CaerulaArborModAttributes.SANITY_RATE.get(), 25, true);
			_livEnt.setHealth(_livEnt.getMaxHealth() * perc);
			_livEnt.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.GUIDED_EVO.get(), -1, 0));
			_datEntSetI.tryLinkEntity(tgt);
		}
	}

	private static void setAttr(LivingEntity liv, Attribute attr, double rate, boolean directly){
		AttributeInstance ai = liv.getAttribute(attr);
		if (ai != null){
			ai.setBaseValue(directly ? (ai.getBaseValue() + rate): (ai.getBaseValue() * rate));
		}
	}
}
