package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ConstructShulkerProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_VARIANT) : 0) < 2) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.COMPLEX_CHITIN.get()) {
				if (entity instanceof OceanizedShulkerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_VARIANT, 2);
				if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
					_livingEntity5.getAttribute(Attributes.MAX_HEALTH)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity4.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 2));
				if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity7.getAttribute(Attributes.ARMOR)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity6.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 1.5));
				if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
					_livingEntity9.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
									? _livingEntity8.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
									: 0) + 5));
				if (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
					_livingEntity11.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
									? _livingEntity10.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
									: 0) + 35));
				if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()))
					_livingEntity13.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
									? _livingEntity12.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).getBaseValue()
									: 0) + 35));
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				if (sourceentity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:construction"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
				return InteractionResult.SUCCESS;
			}
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BEDROCK.asItem()) {
				if (entity instanceof OceanizedShulkerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_VARIANT, 3);
				if (entity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
					_livingEntity25.getAttribute(Attributes.MAX_HEALTH)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity24.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 18));
				if (entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity27.getAttribute(Attributes.ARMOR)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity26.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 10));
				if (entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
					_livingEntity29.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
									? _livingEntity28.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
									: 0) + 32767));
				if (entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
					_livingEntity31.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity30 && _livingEntity30.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
									? _livingEntity30.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
									: 0) + 100));
				if (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()))
					_livingEntity33.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
									? _livingEntity32.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).getBaseValue()
									: 0) + 100));
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
