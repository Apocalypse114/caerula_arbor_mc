package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class SummonThirsterProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double state = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.WHIRL_EYE.get()) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.THIRSTER.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("spawn.thirster").getString())), false);
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			return InteractionResult.SUCCESS;
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.thirst_coral.note").getString())), true);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
