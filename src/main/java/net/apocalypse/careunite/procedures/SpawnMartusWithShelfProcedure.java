package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
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
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.MartusEntity;

public class SpawnMartusWithShelfProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double num = 0;
		BlockState tgt = Blocks.AIR.defaultBlockState();
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.WHIRL_EYE.get()) {
			if (!world.getEntitiesOfClass(MartusEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).isEmpty()) {
				return InteractionResult.PASS;
			}
			for (int index0 = 0; index0 < 3; index0++) {
				for (int index1 = 0; index1 < 3; index1++) {
					for (int index2 = 0; index2 < 3; index2++) {
						tgt = (world.getBlockState(BlockPos.containing(x + index0 - 1, y + index1 - 1, z + index2 - 1)));
						if (tgt.getBlock() == CaerulaArborModBlocks.CAERULA_BOOK_SHELF.get()) {
							num++;
						}
						if (num >= 9) {
							break;
						}
					}
					if (num >= 9) {
						break;
					}
				}
				if (num >= 9) {
					break;
				}
			}
			if (num >= 9) {
				for (int index3 = 0; index3 < 3; index3++) {
					for (int index4 = 0; index4 < 3; index4++) {
						for (int index5 = 0; index5 < 3; index5++) {
							tgt = (world.getBlockState(BlockPos.containing(x + index3 - 1, y + index4 - 1, z + index5 - 1)));
							if (tgt.getBlock() == CaerulaArborModBlocks.CAERULA_BOOK_SHELF.get()) {
								world.destroyBlock(BlockPos.containing(x + index3 - 1, y + index4 - 1, z + index5 - 1), false);
								num--;
							}
							if (num <= 0) {
								break;
							}
						}
						if (num <= 0) {
							break;
						}
					}
					if (num <= 0) {
						break;
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("spawn.martus").getString())), false);
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.MARTUS.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
