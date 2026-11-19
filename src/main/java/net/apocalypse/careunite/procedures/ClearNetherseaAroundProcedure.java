package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.LittleHelperEntity;
import net.apocalypse.careunite.entity.Al1SHelperEntity;

public class ClearNetherseaAroundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState target = Blocks.AIR.defaultBlockState();
		boolean canBreak = false;
		boolean mayDrop = false;
		double px = 0;
		double pz = 0;
		double py = 0;
		double dur = 0;
		if (entity.tickCount % 30 == 5) {
			if (entity instanceof LittleHelperEntity) {
				dur = entity instanceof LittleHelperEntity _datEntI ? _datEntI.getEntityData().get(LittleHelperEntity.DATA_durability) : 0;
			} else {
				dur = entity instanceof Al1SHelperEntity _datEntI ? _datEntI.getEntityData().get(Al1SHelperEntity.DATA_durability) : 0;
			}
			if (dur < 4) {
				dur = dur + 1;
				if (entity instanceof LittleHelperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LittleHelperEntity.DATA_durability, (int) dur);
				if (entity instanceof Al1SHelperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(Al1SHelperEntity.DATA_durability, (int) dur);
			}
		}
		if (!entity.isVehicle()) {
			return;
		}
		for (int index0 = 0; index0 < 3; index0++) {
			for (int index1 = 0; index1 < 2; index1++) {
				for (int index2 = 0; index2 < 3; index2++) {
					px = index0 - 1 + x;
					py = index1 + y;
					pz = index2 - 1 + z;
					target = (world.getBlockState(BlockPos.containing(px, py, pz)));
					canBreak = false;
					mayDrop = false;
					if (target.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail")))) {
						canBreak = true;
					} else if (target.getBlock() == CaerulaArborModBlocks.OCEAN_OVARY.get()) {
						canBreak = true;
						mayDrop = true;
					} else if (target.canBeReplaced()) {
						canBreak = true;
						mayDrop = true;
					}
					if (canBreak) {
						if (mayDrop) {
							{
								BlockPos _pos = BlockPos.containing(px, py, pz);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(px + 0.5, py + 0.5, pz + 0.5), null);
								world.destroyBlock(_pos, false);
							}
						} else {
							world.destroyBlock(BlockPos.containing(px, py, pz), false);
						}
					}
				}
			}
		}
	}
}
