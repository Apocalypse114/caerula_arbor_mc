package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class GunmuInvulnerableProcedure {
	public static boolean execute(LevelAccessor world, Entity immediatesourceentity, Entity sourceentity) {
		if (immediatesourceentity == null || sourceentity == null)
			return false;
		sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:gunmu_damage")))),
				sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		if (!(sourceentity == immediatesourceentity)) {
			return false;
		}
		return true;
	}
}
