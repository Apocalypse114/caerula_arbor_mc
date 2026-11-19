package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SkadiEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobDiedOnTrailProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		Entity Sentity = null;
		if (event.isCanceled()) {
			return;
		}
		if (entity instanceof SkadiEntity) {
			return;
		}
		Sentity = damagesource.getEntity();
		if (Sentity instanceof Player) {
			return;
		}
		if (entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get()) || Sentity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get())) {
			return;
		}
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:can_trigger_oceanization")))) {
			if (TransformIndexProcedure.execute(world, x, y, z, entity)) {
				if (event != null && event.isCancelable()) {
					event.setCanceled(true);
				}
				if (!entity.level().isClientSide())
					entity.discard();
			}
		}
	}
}
