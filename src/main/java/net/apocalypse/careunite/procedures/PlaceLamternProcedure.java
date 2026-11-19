package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class PlaceLamternProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		double tX = 0;
		double tY = 0;
		double tZ = 0;
		boolean wattered = false;
		BlockState toPlace = Blocks.AIR.defaultBlockState();
		if (!itemstack.getOrCreateTag().getBoolean("used")) {
			return InteractionResult.PASS;
		}
		tX = x + direction.getStepX();
		tY = y + direction.getStepY();
		tZ = z + direction.getStepZ();
		wattered = (world.getFluidState(BlockPos.containing(tX, tY, tZ)).createLegacyBlock()).getBlock() == Blocks.WATER;
		toPlace = (CaerulaArborModBlocks.EMERGENCY_LIGHT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp8
				? CaerulaArborModBlocks.EMERGENCY_LIGHT.get().defaultBlockState().setValue(_withbp8, wattered)
				: CaerulaArborModBlocks.EMERGENCY_LIGHT.get().defaultBlockState());
		if (CaerulaArborModBlocks.EMERGENCY_LIGHT.get().defaultBlockState().canSurvive(world, BlockPos.containing(tX, tY, tZ)) && (world.getBlockState(BlockPos.containing(tX, tY, tZ))).canBeReplaced()) {
			if (direction == Direction.DOWN) {
				world.setBlock(BlockPos.containing(tX, tY, tZ), (new Object() {
					public BlockState with(BlockState _bs, String _property, int _newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
						return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
					}
				}.with(toPlace, "blockstate", 2)), 3);
			} else {
				world.setBlock(BlockPos.containing(tX, tY, tZ), (new Object() {
					public BlockState with(BlockState _bs, String _property, int _newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
						return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
					}
				}.with(toPlace, "blockstate", 1)), 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lantern.place")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lantern.place")), SoundSource.NEUTRAL, 1, 1, false);
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
				itemstack.shrink(1);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
