package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class YieldAlchemyBaseProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		Direction blockFacing = Direction.NORTH;
		Direction leftfacing = Direction.NORTH;
		Direction rightfacing = Direction.NORTH;
		double leftx = 0;
		double rightx = 0;
		double leftz = 0;
		double rightz = 0;
		BlockState leftState = Blocks.AIR.defaultBlockState();
		BlockState topState = Blocks.AIR.defaultBlockState();
		BlockState rightState = Blocks.AIR.defaultBlockState();
		BlockState neededLeftState = Blocks.AIR.defaultBlockState();
		BlockState neededRightState = Blocks.AIR.defaultBlockState();
		boolean isCreative = false;
		ItemStack output = ItemStack.EMPTY;
		ItemStack extraOutput = ItemStack.EMPTY;
		ItemStack eextraOutput = ItemStack.EMPTY;
		blockFacing = new Object() {
			public Direction getDirection(BlockState _bs) {
				Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
				if (_prop instanceof DirectionProperty _dp)
					return _bs.getValue(_dp);
				_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
				return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
			}
		}.getDirection(blockstate);
		if (direction == blockFacing) {
			leftfacing = blockFacing.getClockWise(Direction.Axis.Y);
			rightfacing = blockFacing.getCounterClockWise(Direction.Axis.Y);
			leftx = x + leftfacing.getStepX();
			rightx = x + rightfacing.getStepX();
			leftz = z + leftfacing.getStepZ();
			rightz = z + rightfacing.getStepZ();
			leftState = (world.getBlockState(BlockPos.containing(leftx, y, leftz)));
			rightState = (world.getBlockState(BlockPos.containing(rightx, y, rightz)));
			topState = (world.getBlockState(BlockPos.containing(x, y + 1, z)));
			if (topState.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:alchemy/yield_common")))) {
				neededLeftState = CaerulaArborModBlocks.ALCHEMY_PRISMARINE.get().defaultBlockState();
				output = new ItemStack(CaerulaArborModItems.ALCHEMY_BASE_ITEM.get()).copy();
				output.setCount(Mth.nextInt(RandomSource.create(), 3, 5));
				extraOutput = new ItemStack(Items.PRISMARINE_SHARD).copy();
				extraOutput.setCount(Mth.nextInt(RandomSource.create(), 3, 5));
			} else if (topState.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:alchemy/yield_hyper")))) {
				neededLeftState = CaerulaArborModBlocks.ALCHEMY_BASE_BLOCK.get().defaultBlockState();
				neededRightState = Blocks.WATER.defaultBlockState();
				output = new ItemStack(CaerulaArborModItems.HYPER_ALCHEMY_BASE.get()).copy();
				output.setCount(Mth.nextInt(RandomSource.create(), 2, 4));
				extraOutput = new ItemStack(CaerulaArborModItems.ALCHEMY_BASE_SHARD.get()).copy();
				extraOutput.setCount(Mth.nextInt(RandomSource.create(), 5, 9));
			} else if (topState.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:alchemy/yield_spotless")))) {
				neededLeftState = CaerulaArborModBlocks.HYPER_BASE_BLOCK.get().defaultBlockState();
				neededRightState = Blocks.LAVA.defaultBlockState();
				output = new ItemStack(CaerulaArborModItems.SPOTLESS_ALCHEMY_BASE.get()).copy();
				output.setCount(Mth.nextInt(RandomSource.create(), 1, 3));
				extraOutput = new ItemStack(CaerulaArborModItems.HYPER_ALCHEMY_BASE.get()).copy();
				extraOutput.setCount(Mth.nextInt(RandomSource.create(), 1, 2));
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.alchemist_crafter.no_mater").getString())), true);
				return InteractionResult.PASS;
			}
			isCreative = new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity);
			if ((entity instanceof Player _plr ? _plr.totalExperience : 0) >= 32 || isCreative) {
				if (leftState.getBlock() == neededLeftState.getBlock() && (neededRightState.getBlock() == Blocks.AIR || rightState.getBlock() == neededRightState.getBlock())) {
					world.destroyBlock(BlockPos.containing(leftx, y, leftz), false);
					if (!(neededRightState.getBlock() == Blocks.AIR)) {
						world.setBlock(BlockPos.containing(rightx, y, rightz), Blocks.AIR.defaultBlockState(), 3);
					}
					world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1.5), z, output);
						entityToSpawn.setPickUpDelay(5);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alchemist_trigger")), SoundSource.BLOCKS, 1,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alchemist_trigger")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
					if (IsAlchemistSuitProcedure.execute(entity)) {
						eextraOutput = new ItemStack(CaerulaArborModItems.ALCHEMY_BASE_SHARD.get()).copy();
						extraOutput.setCount((int) (extraOutput.getCount() * Mth.nextDouble(RandomSource.create(), 1.25, 1.75)));
						eextraOutput.setCount(Mth.nextInt(RandomSource.create(), 3, 9));
						if (output.getItem() == CaerulaArborModItems.HYPER_ALCHEMY_BASE.get()) {
							eextraOutput.setCount(Mth.nextInt(RandomSource.create(), 9, 16));
						} else if (output.getItem() == CaerulaArborModItems.SPOTLESS_ALCHEMY_BASE.get()) {
							extraOutput.setCount(Mth.nextInt(RandomSource.create(), 16, 24));
						}
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1.5), z, eextraOutput);
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
						if (world instanceof ServerLevel _level)
							_level.addFreshEntity(new ExperienceOrb(_level, x, y, z, 8));
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1.5), z, extraOutput);
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
					if (!isCreative) {
						if (entity instanceof Player _player)
							_player.giveExperiencePoints(-(32));
					}
					return InteractionResult.SUCCESS;
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.alchemist_crafter.lack_matr").getString())), true);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.alchemist_crafter.no_xp").getString())), true);
			}
		}
		return InteractionResult.PASS;
	}
}
