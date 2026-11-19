package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedChickenEntity;

public class LayNetherseaEggProcedure {
	public static ItemStack execute(Entity entity) {
		if (entity == null)
			return ItemStack.EMPTY;
		ItemStack egg = ItemStack.EMPTY;
		double rrr = 0;
		double ooo = 0;
		double c = 0;
		egg = new ItemStack(CaerulaArborModItems.NETHERSEA_CHICKEN_EGG.get()).copy();
		rrr = entity instanceof OceanizedChickenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedChickenEntity.DATA_EGG_RATE) : 0;
		ooo = entity instanceof OceanizedChickenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedChickenEntity.DATA_EGG_OFFSET) : 0;
		c = Mth.nextInt(RandomSource.create(), 1, 16);
		if (c > 9) {
			if (c <= 12) {
				rrr = rrr + 0.05;
			} else if (c <= 15) {
				ooo = ooo + 1;
			} else {
				rrr = rrr + 0.05;
				ooo = ooo + 1;
			}
		}
		egg.getOrCreateTag().putDouble("rate", rrr);
		egg.getOrCreateTag().putDouble("offset", ooo);
		return egg;
	}
}
