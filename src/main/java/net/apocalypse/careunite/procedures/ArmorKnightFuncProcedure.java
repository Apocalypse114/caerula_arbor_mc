package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ArmorKnightFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack helm = ItemStack.EMPTY;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		double rate = 0;
		double freeze = 0;
		helm = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		if (helm.getItem() == CaerulaArborModItems.KNIGHT_IRON_HELMET.get()) {
			rate = rate + 0.25;
		}
		if (chest.getItem() == CaerulaArborModItems.KNIGHT_IRON_CHESTPLATE.get()) {
			rate = rate + 0.25;
		}
		if (legg.getItem() == CaerulaArborModItems.KNIGHT_IRON_LEGGINGS.get()) {
			rate = rate + 0.25;
		}
		if (boot.getItem() == CaerulaArborModItems.KNIGHT_IRON_BOOTS.get()) {
			rate = rate + 0.25;
		}
		if (rate > 0) {
			freeze = sourceentity.getTicksFrozen();
			if (freeze < 140) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
					sourceentity.setTicksFrozen((int) Math.min(freeze + 120 * rate, 200));
				} else {
					sourceentity.setTicksFrozen((int) Math.min(freeze + 60 * rate, 200));
				}
			} else {
				if (!(sourceentity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(CaerulaArborModMobEffects.FROZEN.get()))) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(sourceentity.getX(), sourceentity.getY(), sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_jnight_freeze")),
									SoundSource.HOSTILE, 4, (float) Mth.nextDouble(RandomSource.create(), 1, 1.15));
						} else {
							_level.playLocalSound((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_jnight_freeze")), SoundSource.HOSTILE, 4,
									(float) Mth.nextDouble(RandomSource.create(), 1, 1.15), false);
						}
					}
				}
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), (int) (120 * rate), 0, false, false));
				} else {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), (int) (60 * rate), 0, false, false));
				}
			}
		}
		if (rate > 0.8) {
			if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
			}
		}
	}
}
