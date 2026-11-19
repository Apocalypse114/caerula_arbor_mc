package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedCowEntity;

public class LayTrailCowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedCowEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedCowEntity.DATA_skill) && entity.isAlive()) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.MUTE.get()))
					&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				if (ModGriefSettingsProcedure.execute(world)) {
					if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z)) && !(world.getBlockFloorHeight(BlockPos.containing(x, y, z)) > 0)) {
						TrailReplaceProcedure.execute(world, CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(), (world.getFluidState(BlockPos.containing(x, y, z)).createLegacyBlock()).getBlock() == Blocks.WATER, x, y, z);
					}
					for (Direction directioniterator : Direction.Plane.HORIZONTAL) {
						if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ()))
								&& !(world.getBlockFloorHeight(BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ())) > 0)) {
							if (Math.random() < 0.33) {
								TrailReplaceProcedure.execute(world, CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(),
										(world.getFluidState(BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ())).createLegacyBlock()).getBlock() == Blocks.WATER, x + directioniterator.getStepX(), y,
										z + directioniterator.getStepZ());
							}
						}
					}
					if (entity instanceof OceanizedCowEntity _datEntSetL)
						_datEntSetL.getEntityData().set(OceanizedCowEntity.DATA_skill, false);
					if (entity instanceof OceanizedCowEntity animatable)
						animatable.setTexture("oceanzied_cow_trailless");
				}
			}
		}
	}
}
