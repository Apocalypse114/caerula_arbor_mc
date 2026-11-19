package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class TameInquisitorProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double less = 0;
		double tap = 0;
		Entity enemy = null;
		if (!entity.isAlive()) {
			return InteractionResult.PASS;
		}
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.PERSONNEL_TRANSPORTER.get()) : false) {
			return InteractionResult.PASS;
		}
		if (EmployWildInquisitionProcedure.execute(world, x, y, z, entity, sourceentity, new ItemStack(CaerulaArborModItems.TREATY_DIAMOND.get()))) {
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
