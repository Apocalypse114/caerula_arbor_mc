package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

public class SetUUIDItemTagProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		itemstack.getOrCreateTag().putUUID("owner", entity.getUUID());
	}
}
