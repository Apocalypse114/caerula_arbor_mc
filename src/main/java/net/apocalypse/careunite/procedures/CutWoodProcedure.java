package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class CutWoodProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		BlockState tgt = Blocks.AIR.defaultBlockState();
		if (blockstate.is(BlockTags.create(new ResourceLocation("minecraft:mineable/axe")))) {
			if (entity.isShiftKeyDown()) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + 0.5, y, z + 0.5), null);
					world.destroyBlock(_pos, false);
				}
				for (int index0 = 0; index0 < 16; index0++) {
					tgt = (world.getBlockState(BlockPos.containing(x, y + index0 + 1, z)));
					if (tgt.is(BlockTags.create(new ResourceLocation("minecraft:mineable/axe")))) {
						{
							BlockPos _pos = BlockPos.containing(x, y + index0 + 1, z);
							Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + 0.5, y + index0 + 1, z + 0.5), null);
							world.destroyBlock(_pos, false);
						}
					} else {
						break;
					}
				}
			}
			if (!(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity))) {
				{
					ItemStack _ist = itemstack;
					if (_ist.hurt(1, RandomSource.create(), null)) {
						_ist.shrink(1);
						_ist.setDamageValue(0);
					}
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
