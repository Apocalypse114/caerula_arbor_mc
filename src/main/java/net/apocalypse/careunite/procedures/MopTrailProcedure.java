package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class MopTrailProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		double rate = 0;
		if (blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get()) {
			rate = 0.9;
		} else if (blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get()) {
			rate = 0.65;
		} else if (blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get() || blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_STOP.get()) {
			rate = 0.5;
		} else if (blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get() || blockstate.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get()) {
			rate = 0.33;
		}
		if (rate > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.brush.brushing.generic")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.brush.brushing.generic")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (Math.random() < rate) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + 0.5, y, z + 0.5), null);
					world.destroyBlock(_pos, false);
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
