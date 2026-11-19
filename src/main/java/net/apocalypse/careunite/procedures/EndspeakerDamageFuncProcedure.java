package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.Endspeaker3Entity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EndspeakerDamageFuncProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity, double amount) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof Endspeaker3Entity && amount > 0) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ENDSPEAKER_PARTICLE.get()), x, (y + 0.75), z, 8, 0.75, 0.75, 0.75, 0.1);
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4) {
				if (sourceentity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + amount * 1.4));
			} else {
				if (sourceentity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + amount * 1.8));
			}
		}
	}
}
