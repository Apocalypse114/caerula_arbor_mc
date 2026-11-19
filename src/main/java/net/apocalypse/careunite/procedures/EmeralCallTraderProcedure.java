package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class EmeralCallTraderProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		boolean hasEcho = false;
		if (blockstate.getBlock() == Blocks.BELL) {
			if (CaerulaArborModVariables.MapVariables.get(world).traderSpawnGapCheck) {
				for (int index0 = 0; index0 < 8; index0++) {
					if (GetEchoProcedure.execute(entity, index0) > 0) {
						hasEcho = true;
						break;
					}
				}
				if (hasEcho) {
					DoSpawnTraderProcedure.execute(world, x, z);
					CaerulaArborModVariables.MapVariables.get(world).traderSpawnGapCheck = false;
					CaerulaArborModVariables.MapVariables.get(world).syncData(world);
					itemstack.shrink(1);
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.nethersea_emerald.success").getString())), true);
					return InteractionResult.SUCCESS;
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.nethersea_emerald.note").getString())), true);
					return InteractionResult.FAIL;
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.nethersea_emerald.gap").getString())), true);
				return InteractionResult.FAIL;
			}
		}
		return InteractionResult.PASS;
	}
}
