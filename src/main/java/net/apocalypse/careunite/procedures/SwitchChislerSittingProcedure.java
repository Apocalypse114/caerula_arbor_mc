package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.ChieslerPetEntity;

public class SwitchChislerSittingProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == sourceentity) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
					&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof ChieslerPetEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ChieslerPetEntity.DATA_SITTING, (!(entity instanceof ChieslerPetEntity _datEntL3 && _datEntL3.getEntityData().get(ChieslerPetEntity.DATA_SITTING))));
				return InteractionResult.SUCCESS;
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem().isEdible()) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.PLAYERS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.PLAYERS, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 8, 0.6, 0.6, 0.6, 0.1);
					(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
					return InteractionResult.SUCCESS;
				}
			}
		}
		return InteractionResult.PASS;
	}
}
