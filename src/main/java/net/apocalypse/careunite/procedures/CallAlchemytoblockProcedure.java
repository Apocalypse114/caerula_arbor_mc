package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;

public class CallAlchemytoblockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			DispatchAlchemyProcedure.execute(world, entity, itemstack, direction.getStepX() + x, direction.getStepY() + y, direction.getStepZ() + z);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
