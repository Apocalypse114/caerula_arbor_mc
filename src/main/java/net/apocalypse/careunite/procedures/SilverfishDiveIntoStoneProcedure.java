package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedSilverfishEntity;

public class SilverfishDiveIntoStoneProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double ddtt = 0;
		double ding = 0;
		ddtt = entity instanceof OceanizedSilverfishEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSilverfishEntity.DATA_DIVE_TICK) : 0;
		ding = entity instanceof OceanizedSilverfishEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSilverfishEntity.DATA_DIVING) : 0;
		if (ddtt <= 0 && ding < 0) {
			if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get() || (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get()) {
				if (entity instanceof OceanizedSilverfishEntity) {
					((OceanizedSilverfishEntity) entity).setAnimation("animation.oceanized_silverfish.dive");
				}
				if (entity instanceof OceanizedSilverfishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedSilverfishEntity.DATA_DIVING, 17);
				if (entity instanceof OceanizedSilverfishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedSilverfishEntity.DATA_DIVE_TICK, 200);
			}
		} else {
			if (entity instanceof OceanizedSilverfishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSilverfishEntity.DATA_DIVE_TICK, (int) (ddtt - 1));
		}
		if (ding == 0) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get()) {
				world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState()));
				world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState(), 3);
				if (!entity.level().isClientSide())
					entity.discard();
			} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get()) {
				world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState()));
				world.setBlock(BlockPos.containing(x, y - 1, z), CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState(), 3);
				if (!entity.level().isClientSide())
					entity.discard();
			} else {
				if (entity instanceof OceanizedSilverfishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedSilverfishEntity.DATA_DIVING, -1);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 40, 0, false, false));
			}
		} else if (ding > 0) {
			if (entity instanceof OceanizedSilverfishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSilverfishEntity.DATA_DIVING, (int) (ding - 1));
		}
	}
}
