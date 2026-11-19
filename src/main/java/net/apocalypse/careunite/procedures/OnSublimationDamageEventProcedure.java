package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.DivicellularGoEntity;
import net.apocalypse.careunite.entity.AccumulatorProkaryoteEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class OnSublimationDamageEventProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, double amount) {
		execute(null, world, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		Entity sourceent = null;
		double finalGrow = 0;
		double rate = 0;
		finalGrow = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation, CaerulaArborModVariables.MapVariables.get(world).strategy_grow);
		sourceent = damagesource.getEntity();
		if (sourceent instanceof AccumulatorProkaryoteEntity || sourceent instanceof DivicellularGoEntity) {
			entity.invulnerableTime = 0;
		}
		rate = 1;
		if (!(sourceent == null) && finalGrow > 3) {
			if (entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.UNDER_BREAK.get()) && sourceent.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (finalGrow == 3) {
					rate = 1.15;
				} else if (finalGrow == 4) {
					rate = 1.3;
				}
			}
		}
		if (finalGrow > 0) {
			if (entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get()) && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (finalGrow <= 1) {
					rate = 0.9;
				} else {
					rate = 0.8;
				}
			}
		}
		if (rate != 1) {
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * rate));
			}
		}
	}
}
