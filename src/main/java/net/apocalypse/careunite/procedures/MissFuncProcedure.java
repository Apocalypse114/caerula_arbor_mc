package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.PredatorAbyssalEntity;
import net.apocalypse.careunite.entity.ChitinGolemEntity;

import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.apocalypse.careunite.CaerulaEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.damagesource.DamageType;

@Mod.EventBusSubscriber
public class MissFuncProcedure {
	@SubscribeEvent
(priority = EventPriority.LOW)
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	public static final TagKey<DamageType> NO_MISS = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:bypass_miss"));
	public static final TagKey<DamageType> SKIP_SOURCE = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:skip_source_check"));

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		Entity sourceent = null;
		LivingEntity _livingEntity0 = null;
		if (entity instanceof LivingEntity) _livingEntity0 = (LivingEntity)entity;
		if (_livingEntity0 == null) return;
		double missr = _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get())
			? _livingEntity0.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).getValue(): 0;
		if (missr > 0) {
			if (!damagesource.is(NO_MISS)) {
				if (!_livingEntity0.hasEffect(CaerulaArborModMobEffects.MUTE.get())) {
					sourceent = damagesource.getEntity();
					if (!damagesource.is(SKIP_SOURCE) && sourceent == null) {
						return;
					}
					
					CaerulaEvents.LivingMissEvent ev = new CaerulaEvents.LivingMissEvent(_livingEntity0, damagesource, missr);
					MinecraftForge.EVENT_BUS.post(ev);
					missr = ev.getMissRate();
					if (missr > 0 && _livingEntity0.getRandom().nextDouble() * 100 < missr) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MISS.get()), x, y, z, 1, 1, 1, 1, 0.1);
						if (entity instanceof PredatorAbyssalEntity) {
							((PredatorAbyssalEntity) entity).setAnimation("animation.predator.miss");
						}
						if (entity instanceof ChitinGolemEntity) {
							((ChitinGolemEntity) entity).setAnimation("animation.chitgolem.block");
						}
						if (event != null && event.isCancelable()) {
							
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
}
