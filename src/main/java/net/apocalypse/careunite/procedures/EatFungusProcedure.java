package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedSheepEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class EatFungusProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof OceanizedSheepEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedSheepEntity.DATA_fur))) {
			if ((world.getBlockState(BlockPos.containing(x + entity.getLookAngle().x, y, z + entity.getLookAngle().z))).getBlock() == CaerulaArborModBlocks.TRAIL_MUSHROOM.get()) {
				if (entity instanceof OceanizedSheepEntity) {
					((OceanizedSheepEntity) entity).setAnimation("animation.oceanized_sheep.eat");
				}
				if (entity instanceof OceanizedSheepEntity _datEntSetL)
					_datEntSetL.getEntityData().set(OceanizedSheepEntity.DATA_fur, true);
				world.setBlock(BlockPos.containing(x + entity.getLookAngle().x, y, z + entity.getLookAngle().z), Blocks.AIR.defaultBlockState(), 3);
				CaerulaArborMod.queueServerWork(20, () -> {
					if (entity instanceof OceanizedSheepEntity _datEntL10 && _datEntL10.getEntityData().get(OceanizedSheepEntity.DATA_fur)) {
						if (entity instanceof OceanizedSheepEntity animatable)
							animatable.setTexture("oceanized_sheep");
					}
				});
			}
		}
	}
}
