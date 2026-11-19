package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class ValidTransporterInHandProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		ItemStack item = ItemStack.EMPTY;
		item = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (!(item.getItem() == CaerulaArborModItems.PERSONNEL_TRANSPORTER.get())) {
			return false;
		}
		return IsFilledwithPersonnelProcedure.execute(item);
	}
}
