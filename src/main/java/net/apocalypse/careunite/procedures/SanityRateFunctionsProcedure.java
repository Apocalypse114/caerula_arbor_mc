package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class SanityRateFunctionsProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		ItemStack item_temp = ItemStack.EMPTY;
		boolean validItem = false;
		double rate = 0;
		double time = 0;
		double light_cost = 0;
		if (event.isCanceled()) {
			return;
		}
		if ((sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
				? _livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getValue()
				: 0) > 0) {
			DeductPlayerSanityProcedure.execute(entity,
					amount * (sourceentity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getValue()
							: 0));
			if (CaerulaConfigsConfiguration.SANITY_PTC.get()) {
				new Object() {
					void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), x, (y + entity.getBbHeight() * 0.5), z,
									(int) (Math.min(16,
											Math.max((sourceentity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
													? _livingEntity5.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getValue()
													: 0) * 0.25, 1))),
									1.2, 1.5, 1.2, 0.1);
						final int tick2 = ticks;
						CaerulaArborMod.queueServerWork(tick2, () -> {
							if (timedlooptotal > timedloopiterator + 1) {
								timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
							}
						});
					}
				}.timedLoop(0, 5, 1);
			}
		}
	}
}
