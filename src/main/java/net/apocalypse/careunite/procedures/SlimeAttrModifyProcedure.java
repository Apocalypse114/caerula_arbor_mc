package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.NetherseaSlimeEntity;

public class SlimeAttrModifyProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double size = 0;
		size = entity instanceof NetherseaSlimeEntity _datEntI ? _datEntI.getEntityData().get(NetherseaSlimeEntity.DATA_SIZE) : 0;
		if (size > 1) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
						((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity1.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * Math.pow(size, 2)));
			if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * size));
			if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED))
				_livingEntity5.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((0.2 + 0.025 * Math.max(size, 4)));
			if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
				_livingEntity6.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue((size * 0.2));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		}
	}
}
