package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class RegainTideBishopCoreProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REPELLER_SHELL.get()) {
			if ((world.getBlockState(BlockPos.containing(x, y + 2, z))).canBeReplaced()) {
				world.setBlock(BlockPos.containing(x, y + 2, z), CaerulaArborModBlocks.TIDE_BISHOP_CORE.get().defaultBlockState(), 3);
				world.setBlock(BlockPos.containing(x, y, z), Blocks.STONE_BRICKS.defaultBlockState(), 3);
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.tidebishop_core_empty.warn").getString())), true);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.tidebishop_core_empty.note").getString())), true);
		}
		return InteractionResult.PASS;
	}
}
