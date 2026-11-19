package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class LanternBurnTrailsProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		BlockState output = Blocks.AIR.defaultBlockState();
		ItemStack toGive = ItemStack.EMPTY;
		if (blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get() || blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get() || blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get()
				|| blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_STOP.get() || blockstate.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get() || blockstate.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get()) {
			BurndownTrailProcedure.execute(world, blockstate, x, y, z);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (x + 0.5), (y + 1), (z + 0.5), 48, 0.75, 0.75, 0.75, 0.15);
			for (Direction directioniterator : Direction.values()) {
				output = (world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ())));
				if (output.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get() || output.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get() || output.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get()
						|| output.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get()) {
					BurndownTrailProcedure.execute(world, output, x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ());
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
		if (blockstate.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get()) {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), null);
				world.destroyBlock(_pos, false);
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (x + 0.5), (y + 1), (z + 0.5), 48, 0.75, 0.75, 0.75, 0.15);
			for (Direction directioniterator : Direction.values()) {
				output = (world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ())));
				if (output.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get()) {
					{
						BlockPos _pos = BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ());
						Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + directioniterator.getStepX() + 0.5, y + directioniterator.getStepY() + 0.5, z + directioniterator.getStepZ() + 0.5), null);
						world.destroyBlock(_pos, false);
					}
				}
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.hurt(1, RandomSource.create(), null)) {
					_ist.shrink(1);
					_ist.setDamageValue(0);
				}
			}
			return InteractionResult.SUCCESS;
		}
		if (blockstate.is(BlockTags.create(new ResourceLocation("minecraft:logs")))) {
			toGive = new ItemStack(Items.CHARCOAL).copy();
			if (blockstate.getBlock() == CaerulaArborModBlocks.TRAIL_LOG.get() || blockstate.getBlock() == CaerulaArborModBlocks.STRIPPED_TRAIL_LOG.get()) {
				toGive = new ItemStack(CaerulaArborModItems.TRAIL_POWDER.get()).copy();
			}
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (x + 0.5), (y + 1), (z + 0.5), 48, 0.75, 0.75, 0.75, 0.15);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), toGive);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			if (Math.random() < 0.5) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), toGive);
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
