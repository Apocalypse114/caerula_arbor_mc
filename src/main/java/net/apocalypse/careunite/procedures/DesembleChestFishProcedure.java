package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class DesembleChestFishProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double bs = 0;
		bs = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
			if (bs == 0) {
				for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 3, 4); index0++) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.OCEAN_PEDUNCLE.get()));
						entityToSpawn.setPickUpDelay(5);
						_level.addFreshEntity(entityToSpawn);
					}
				}
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			} else if (bs == 1) {
				for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 3, 4); index1++) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.OCEAN_FIBRE.get()));
						entityToSpawn.setPickUpDelay(5);
						_level.addFreshEntity(entityToSpawn);
					}
				}
				{
					int _value = 2;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			} else if (bs == 2) {
				for (int index2 = 0; index2 < Mth.nextInt(RandomSource.create(), 2, 4); index2++) {
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.CHITIN_COOKIE_RAW.get()));
						entityToSpawn.setPickUpDelay(5);
						_level.addFreshEntity(entityToSpawn);
					}
				}
				world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
