package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class SummonCarmenProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, Entity entity, ItemStack itemstack) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		if (blockstate.getBlock() == CaerulaArborModBlocks.FAX.get()) {
			tx = x + 0.5 + direction.getStepX();
			ty = y + direction.getStepY();
			tz = z + 0.5 + direction.getStepZ();
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), tx, (ty + 0.64), tz, 48, 0.64, 0.64, 0.64, 0.1);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.SAINT_CARMEN.get().spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("spawn.saint_carmen").getString())), true);
			itemstack.shrink(1);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
