package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class DetectLightProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("light, block:" + world.getBrightness(LightLayer.BLOCK, BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ())) + "sky: "
					+ world.getBrightness(LightLayer.SKY, BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ())))), false);
		return InteractionResult.SUCCESS;
	}
}
