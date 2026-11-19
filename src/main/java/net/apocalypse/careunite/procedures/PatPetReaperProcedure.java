package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.ReaperPetEntity;

public class PatPetReaperProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == sourceentity) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()
					&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.HEART, x, y, z, 4, 0.8, 0.5, 0.8, 0.3);
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
				if (entity instanceof Mob _entity)
					_entity.getNavigation().stop();
				if (entity instanceof ReaperPetEntity) {
					((ReaperPetEntity) entity).setAnimation("animation.reaperpet.interact");
				}
				return InteractionResult.SUCCESS;
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:fish_food")))) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:fish_food")))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dolphin.eat")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.STICK) {
				if (sourceentity.isShiftKeyDown()) {
					if (entity instanceof ReaperPetEntity _datEntSetI)
						_datEntSetI.getEntityData().set(ReaperPetEntity.DATA_state, 2);
					if (sourceentity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + "" + Component.translatable("item.caerula_arbor_reunite.a_second_key.description_2").getString())), true);
				} else {
					if ((entity instanceof ReaperPetEntity _datEntI ? _datEntI.getEntityData().get(ReaperPetEntity.DATA_state) : 0) == 0) {
						if (entity instanceof ReaperPetEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ReaperPetEntity.DATA_state, 1);
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + "" + Component.translatable("item.caerula_arbor_reunite.a_second_key.description_1").getString())), true);
					} else {
						if (entity instanceof ReaperPetEntity _datEntSetI)
							_datEntSetI.getEntityData().set(ReaperPetEntity.DATA_state, 0);
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal((entity.getDisplayName().getString() + "" + Component.translatable("item.caerula_arbor_reunite.a_second_key.description_0").getString())), true);
					}
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		}
		return InteractionResult.PASS;
	}
}
