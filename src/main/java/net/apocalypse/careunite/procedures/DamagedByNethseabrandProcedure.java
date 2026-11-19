package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

public class DamagedByNethseabrandProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double lvl = 0;
		double gap = 0;
		double lvl1 = 0;
		ItemStack a0 = ItemStack.EMPTY;
		ItemStack a1 = ItemStack.EMPTY;
		ItemStack a2 = ItemStack.EMPTY;
		ItemStack a3 = ItemStack.EMPTY;
		if (entity instanceof LivingEntity) {
			if (!(entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAIL_BUFF.get(), 10, 0, false, false));
			}
			gap = 20;
			a0 = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
			a1 = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
			a2 = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
			a3 = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
			if (a0.getItem() == CaerulaArborModItems.SEALEATHER_BOOTS.get() || a0.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_BOOTS.get()) {
				gap = gap + 8;
			} else if (a0.getItem() == CaerulaArborModItems.SEALEATHER_CHITIN_BOOTS.get()) {
				gap = gap + 6;
			} else if (a0.getItem() == CaerulaArborModItems.INQUISITION_SUIT_BOOTS.get() || a0.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_BOOTS.get()) {
				gap = gap + 4;
			} else if (a0.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_BOOTS.get()) {
				gap = gap + 3;
			}
			if (a1.getItem() == CaerulaArborModItems.SEALEATHER_LEGGINGS.get() || a1.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_LEGGINGS.get()) {
				gap = gap + 6;
			} else if (a1.getItem() == CaerulaArborModItems.SEALEATHER_CHITIN_LEGGINGS.get()) {
				gap = gap + 5;
			} else if (a1.getItem() == CaerulaArborModItems.INQUISITION_SUIT_LEGGINGS.get() || a1.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_LEGGINGS.get() || a1.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_LEGGINGS.get()) {
				gap = gap + 2;
			}
			if (a2.getItem() == CaerulaArborModItems.SEALEATHER_CHESTPLATE.get() || a2.getItem() == CaerulaArborModItems.SEALEATHER_CHITIN_CHESTPLATE.get() || a2.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_CHESTPLATE.get()) {
				gap = gap + 4;
			} else if (a2.getItem() == CaerulaArborModItems.INQUISITION_SUIT_CHESTPLATE.get() || a2.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_CHESTPLATE.get()) {
				gap = gap + 2;
			} else if (a2.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_CHESTPLATE.get()) {
				gap = gap + 1;
			}
			if (a3.getItem() == CaerulaArborModItems.SEALEATHER_HELMET.get() || a3.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_HELMET.get()) {
				gap = gap + 2;
			} else if (a3.getItem() == CaerulaArborModItems.SEALEATHER_CHITIN_HELMET.get()) {
				gap = gap + 3;
			} else if (a3.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_HELMET.get()) {
				gap = gap + 1;
			}
			if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.NETHERSEA_WALKER.get(), a0) != 0) {
				lvl = a0.getEnchantmentLevel(CaerulaArborModEnchantments.NETHERSEA_WALKER.get());
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.RUNNING_ON_TRAIL.get(), 30, (int) lvl, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 0, false, false));
			}
			if (entity.tickCount % gap == 0 && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:immue_to_nethersea_brand")))) {
				if (entity instanceof Player) {
					if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.DEPTH_STRIDER, a0) != 0) {
						lvl1 = a0.getEnchantmentLevel(Enchantments.DEPTH_STRIDER);
					}
					if (Math.random() < 0.2 * lvl + 0.05 * lvl1) {
						return;
					}
					if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
						return;
					}
				}
				if (!(entity instanceof LivingEntity _livEnt38 && _livEnt38.getMobType() == MobType.UNDEAD)) {
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:trail_damage")))), 2);
				}
				DeductPlayerSanityProcedure.execute(entity, 20);
			}
		}
	}
}
