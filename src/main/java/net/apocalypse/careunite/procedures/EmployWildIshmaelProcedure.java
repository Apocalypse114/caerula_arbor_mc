package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class EmployWildIshmaelProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if (EmployWildInquisitionProcedure.execute(world, x, y, z, entity, sourceentity, new ItemStack(CaerulaArborModItems.TREATY_NETHERITE.get()))) {
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
