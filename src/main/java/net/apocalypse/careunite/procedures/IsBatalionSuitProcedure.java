package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.entity.EntityType;

public class IsBatalionSuitProcedure {
	public static final TagKey<EntityType<?>> PH = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:phalax"));
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		
		if (entity.getType().is(PH)) {
			return true;
		}
		if (entity instanceof LivingEntity _entGetArmor){
			ItemStack helm = _entGetArmor.getItemBySlot(EquipmentSlot.HEAD).copy();
			ItemStack chest = _entGetArmor.getItemBySlot(EquipmentSlot.CHEST).copy();
			ItemStack legg = _entGetArmor.getItemBySlot(EquipmentSlot.LEGS).copy();
			ItemStack boot = _entGetArmor.getItemBySlot(EquipmentSlot.FEET).copy();
			return helm.getItem() == CaerulaArborModItems.BATALION_ARMOR_HELMET.get() 
					&& chest.getItem() == CaerulaArborModItems.BATALION_ARMOR_CHESTPLATE.get() 
					&& legg.getItem() == CaerulaArborModItems.BATALION_ARMOR_LEGGINGS.get()
					&& boot.getItem() == CaerulaArborModItems.BATALION_ARMOR_BOOTS.get();
		}
		return false;
	}
}
