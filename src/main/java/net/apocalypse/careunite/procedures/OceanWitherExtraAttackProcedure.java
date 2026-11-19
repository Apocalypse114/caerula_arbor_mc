package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class OceanWitherExtraAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getSource().getDirectEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		execute(null, world, damagesource, entity, immediatesourceentity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (damagesource == null || entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		if (event.isCanceled()) {
			return;
		}
		if (sourceentity == entity) {
			return;
		}
		if (sourceentity instanceof OceanizedWitherEntity || sourceentity instanceof OceannizedWitheriaEntity) {
			if (damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_wither"))) || damagesource.is(DamageTypes.WITHER_SKULL)) {
				entity.hurt(
						new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"))), immediatesourceentity,
								sourceentity),
						(float) (sourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
				entity.invulnerableTime = 0;
			}
		}
	}
}
