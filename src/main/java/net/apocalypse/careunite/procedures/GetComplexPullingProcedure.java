package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class GetComplexPullingProcedure {
	public static double execute(Entity entity, ItemStack itemstack) {
		if (entity instanceof LivingEntity living)
{
			if (living.getUseItem() == itemstack) return living.getTicksUsingItem();
		}
		return 0;
	}
}
