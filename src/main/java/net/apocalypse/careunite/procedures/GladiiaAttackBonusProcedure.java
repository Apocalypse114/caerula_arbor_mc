package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.GladiiaEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class GladiiaAttackBonusProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, Entity sourceentity, double amount) {
		execute(null, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double factor = 0;
		if (sourceentity instanceof GladiiaEntity) {
			factor = 1;
			if (GetSizeProcedure.execute(entity) < GetSizeProcedure.execute(sourceentity) * 2) {
				factor = factor * 1.3;
			}
			if (GetHealthPercProcedure.execute(entity) < GetHealthPercProcedure.execute(sourceentity)) {
				factor = factor * 1.5;
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * factor));
			}
		} else if (entity instanceof GladiiaEntity) {
			factor = 1;
			if (GetSizeProcedure.execute(sourceentity) >= GetSizeProcedure.execute(entity) * 2) {
				factor = 0.75;
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * factor));
			}
		}
	}
}
