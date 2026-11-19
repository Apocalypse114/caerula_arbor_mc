package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class EraceDamageTesterProcedure {
	public static InteractionResult execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if (((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.APOCALYPSE.get()) : false) || ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.BANNED_ITEM.get()) : false)) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:inv_killer")))), 114514);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
