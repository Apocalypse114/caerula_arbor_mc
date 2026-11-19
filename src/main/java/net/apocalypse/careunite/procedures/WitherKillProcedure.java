package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class WitherKillProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		execute(null, world, x, y, z, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double rand = 0;
		BlockState toPlace = Blocks.AIR.defaultBlockState();
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			if (sourceentity instanceof OceanizedWitherEntity || sourceentity instanceof OceannizedWitheriaEntity) {
				rand = Mth.nextInt(RandomSource.create(), 0, 2);
				if (rand == 0) {
					toPlace = CaerulaArborModBlocks.VIVIPAROUS_LILY.get().defaultBlockState();
				} else if (rand == 1) {
					toPlace = CaerulaArborModBlocks.TRAIL_MUSHROOM.get().defaultBlockState();
				} else {
					toPlace = Blocks.WITHER_ROSE.defaultBlockState();
				}
				if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced() && toPlace.canSurvive(world, BlockPos.containing(x, y, z))) {
					world.setBlock(BlockPos.containing(x, y, z), toPlace, 3);
				}
			}
		}
	}
}
