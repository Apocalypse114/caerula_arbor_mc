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

import net.apocalypse.careunite.entity.SpecterDollEntity;
import net.apocalypse.careunite.entity.GladiiaEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HuntersHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		double factor = 0;
		double xxx = 0;
		Entity lastEnemt = null;
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:bypasses_invulnerability")))) {
			return;
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")))) {
			factor = 1;
			if (!(IsGladiiaAroundProcedure.execute(world, x, y, z) == null)) {
				factor = 0.7;
			}
			if (entity instanceof GladiiaEntity) {
				xxx = GetHealthPercProcedure.execute(entity);
				factor = factor * Math.max(1 - 2.68 * Math.pow(xxx - 1, 2), 0.33);
			} else if (entity instanceof SpecterDollEntity) {
				lastEnemt = (entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
				if (!(lastEnemt == null) && (lastEnemt != null ? entity.distanceTo(lastEnemt) : -1) <= 9) {
					factor = factor * 0.65;
				}
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * factor));
			}
		}
	}
}
