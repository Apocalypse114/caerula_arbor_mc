package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class CollideBlocksAroundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState target = Blocks.AIR.defaultBlockState();
		boolean breaked = false;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.deathTime : 0) == 22) {
			RavagerSummonFellowsProcedure.execute(world, x, y, z, entity, 3);
		}
		if (entity instanceof Mob _mobEnt1 && _mobEnt1.isAggressive()) {
			if (ModGriefSettingsProcedure.execute(world)) {
				if (Math.random() < 0.05) {
					for (int dx = (int) (-1); dx <= (int) 1; dx++) {
						for (int dy = (int) 1; dy <= (int) 3; dy++) {
							for (int dz = (int) (-1); dz <= (int) 1; dz++) {
								target = (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)));
								if (world.getBlockFloorHeight(BlockPos.containing(x + dx, y + dy, z + dz)) > 0 && target.getDestroySpeed(world, BlockPos.containing(0, 0, 0)) > 0 && target.getDestroySpeed(world, BlockPos.containing(0, 0, 0)) <= 5) {
									world.destroyBlock(BlockPos.containing(x + dx, y + dy, z + dz), false);
									breaked = true;
								}
							}
						}
					}
					if (breaked) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
					}
				}
			}
		}
	}
}
