package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class GetTridentThrowingProcedure {
	public static double execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return 0;
		if ((entity instanceof LivingEntity _entUseItem0 ? _entUseItem0.getUseItem() : ItemStack.EMPTY) == itemstack) {
			return 1;
		}
		return 0;
	}
}
