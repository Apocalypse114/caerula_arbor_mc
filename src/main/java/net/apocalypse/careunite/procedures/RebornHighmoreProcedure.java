package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class RebornHighmoreProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) != 1) {
			return InteractionResult.PASS;
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.HIGHMORE_SCYTHE.get()) {
			world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.HIGHMORE_SPAWNBLOCK.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal_frame.fill")), SoundSource.BLOCKS, (float) 1.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal_frame.fill")), SoundSource.BLOCKS, (float) 1.5, 1, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.WAX_ON, (x + 0.5), (y + 0.5), (z + 0.5), 16, 2, 2, 2, 0.15);
			for (int index0 = 0; index0 < 16; index0++) {
				{
					ItemStack _ist = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
					if (_ist.hurt(64, RandomSource.create(), null)) {
						_ist.shrink(1);
						_ist.setDamageValue(0);
					}
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
