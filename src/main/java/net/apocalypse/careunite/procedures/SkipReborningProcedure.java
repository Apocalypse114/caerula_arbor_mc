package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

public class SkipReborningProcedure {
	public static InteractionResult execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		if (itemstack.getItem() == CaerulaArborModItems.OCEANIZED_WITHER_SPAWNEGG.get()) {
			if (entity instanceof OceanizedWitherEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_duration, 0);
			if (entity instanceof OceanizedWitherEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWitherEntity.DATA_spawn, 100);
			if (entity instanceof OceanizedWitherEntity animatable)
				animatable.setTexture("oceanized_wither");
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
