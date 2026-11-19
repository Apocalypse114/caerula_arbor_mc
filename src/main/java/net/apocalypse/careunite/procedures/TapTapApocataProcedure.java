package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.ApocataEntity;

public class TapTapApocataProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof ApocataEntity _datEntI ? _datEntI.getEntityData().get(ApocataEntity.DATA_duration) : 0) > 0) {
			return InteractionResult.PASS;
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
			if (entity instanceof ApocataEntity) {
				((ApocataEntity) entity).setAnimation("animation.apocata.tap");
			}
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:apocata_interact")), SoundSource.NEUTRAL, 3, (float) 1.5);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:apocata_interact")), SoundSource.NEUTRAL, 3, (float) 1.5, false);
					}
				}
			}
			if (entity instanceof ApocataEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ApocataEntity.DATA_duration, 20);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
