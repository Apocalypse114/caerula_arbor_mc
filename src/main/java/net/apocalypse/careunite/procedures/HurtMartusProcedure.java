package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

public class HurtMartusProcedure {
	public static void execute(LevelAccessor world, Entity obj, @Nullable Entity source, double num, double perc) {
		if (obj == null)
			return;
		double amount = 0;
		Entity player = null;
		amount = (obj instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * perc + num;
		if (amount > 0) {
			obj.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:inv_killer"))), source), (float) amount);
		}
	}
}
