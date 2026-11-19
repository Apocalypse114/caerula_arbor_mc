package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.Al1SHelperEntity;

public class RecycleLittleHelperProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		if (!world.isClientSide() && ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.APOCALYPSE.get()) : false) && entity instanceof Al1SHelperEntity) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.FLAME, x, y, z, 32, 0.75, 0.75, 0.75, 0.15);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_spec")), SoundSource.BLOCKS, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_spec")), SoundSource.BLOCKS, 3, 1, false);
				}
			}
			return InteractionResult.SUCCESS;
		}
		if (sourceentity.isShiftKeyDown() && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
			if (!entity.level().isClientSide())
				entity.discard();
			if (entity instanceof Al1SHelperEntity) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.ITEM_HELPER_AL_1S.get()));
					entityToSpawn.setPickUpDelay(5);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			} else {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.ITEM_HELPER.get()));
					entityToSpawn.setPickUpDelay(5);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
			return InteractionResult.FAIL;
		}
		return InteractionResult.PASS;
	}
}
