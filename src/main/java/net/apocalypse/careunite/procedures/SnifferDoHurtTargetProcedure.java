package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceanizedSnifferEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SnifferDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		CaerulaArborMod.queueServerWork(14, () -> {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
					(float) (sourceentity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
		});
		if (sourceentity instanceof OceanizedSnifferEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_duration, (int) ((sourceentity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_duration) : 0) + 25));
		if (sourceentity instanceof OceanizedSnifferEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp1, (int) ((sourceentity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_skillp1) : 0) + 1));
	}
}
