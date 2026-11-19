package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class IsAlchemistSuitProcedure {
	public static boolean execute(Entity entity, boolean did) {
		if (entity instanceof LivingEntity _entGetArmor)
{
			ItemStack helm = _entGetArmor.getItemBySlot(EquipmentSlot.HEAD);
			ItemStack chest = _entGetArmor.getItemBySlot(EquipmentSlot.CHEST);
			ItemStack legg = _entGetArmor.getItemBySlot(EquipmentSlot.LEGS);
			ItemStack boot = _entGetArmor.getItemBySlot(EquipmentSlot.FEET);
			if (helm.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_HELMET.get() 
				&& chest.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_CHESTPLATE.get() 
				&& legg.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_LEGGINGS.get()
				&& boot.getItem() == CaerulaArborModItems.MASTER_ALCHEMIST_BOOTS.get()) {
				if (did){
					if (entity.getTicksFrozen() > 0) {
						entity.setTicksFrozen(0);
					}
					if (_entGetArmor.hasEffect(CaerulaArborModMobEffects.FROZEN.get())) {
						_entGetArmor.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
					}
				}
				return true;
			}
		}
		return false;
	}

	public static boolean execute(Entity entity){
		return execute(entity, true);
	}
}
