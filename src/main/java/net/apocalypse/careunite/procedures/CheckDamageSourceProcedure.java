package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class CheckDamageSourceProcedure {
	public static boolean execute(DamageSource damagesource) {
		if (damagesource == null)
			return false;
		Entity sEntity = null;
		sEntity = damagesource.getEntity();
		if (sEntity == null) {
			return false;
		}
		if ((sEntity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.ENDERINA_SPAWNER.get()) {
			return true;
		}
		if (sEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
			return false;
		}
		if (sEntity instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
			return false;
		}
		return !(sEntity instanceof Player || sEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside"))));
	}
}
