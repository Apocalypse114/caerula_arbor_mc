package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedEvokerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TotemFuncProcedure {
	@SubscribeEvent
	public static void whenEntityUsesTotem(LivingUseTotemEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedEvokerEntity) {
			CaerulaArborMod.queueServerWork(1, () -> {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 0, false, false));
				if (!(entity == null) && entity.isAlive()) {
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.65));
				}
			});
		} else if (entity instanceof Player) {
			ChangePlayerLPOrShieldProcedure.execute(world, entity, false, 1, "totem_of_undying");
		}
	}
}
