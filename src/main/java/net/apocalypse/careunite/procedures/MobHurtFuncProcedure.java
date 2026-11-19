package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobHurtFuncProcedure {
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
		Entity sss = null;
		sss = damagesource.getEntity();
		OnEchoHurtProcedure.execute(entity, sss);
		if (entity instanceof Player) {
			OnEchoPlayerHurtProcedure.execute(world, damagesource, entity, amount);
		}
		if (sss instanceof OceanizedLlamaEntity) {
			entity.invulnerableTime = 0;
			if (sss instanceof OceanizedLlamaEntity _datEntL4 && _datEntL4.getEntityData().get(OceanizedLlamaEntity.DATA_CHARGED)) {
				GiveLessArmorProcedure.execute(entity, 24);
			}
		}
		if (entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.SANITY_COMPENSORY.get())) {
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * 0.5));
			}
			DeductPlayerSanityProcedure.execute(entity, Math.min(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1, amount) * 7.5);
		}
	}
}
