package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class SnifferEggCrackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double h = 0;
		Entity animal = null;
		animal = SummonRandonAnimalProcedure.execute(world, x, y, z);
		if (animal instanceof LivingEntity _entity)
			_entity.setHealth((float) ((animal instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5));
		EmrelySyncTeamProcedure.execute(entity, animal);
		world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.PHLOEM_BLOCK.get().defaultBlockState()));
		if (!entity.level().isClientSide())
			entity.discard();
	}
}
