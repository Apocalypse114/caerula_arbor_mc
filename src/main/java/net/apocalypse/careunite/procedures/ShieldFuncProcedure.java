package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ShieldFuncProcedure {
	@SubscribeEvent
	public static void whenEntityBlocksWithShield(ShieldBlockEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getDamageSource().getEntity(), event.getBlockedDamage());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, double blockedamount) {
		execute(null, world, entity, sourceentity, blockedamount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity, double blockedamount) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		ItemStack shield = ItemStack.EMPTY;
		shield = (entity instanceof LivingEntity _entUseItem0 ? _entUseItem0.getUseItem() : ItemStack.EMPTY).copy();
		if (shield.getItem() == CaerulaArborModItems.COMPLEX_CHITIN_SHIELD.get()) {
			DeductPlayerSanityProcedure.execute(sourceentity, Math.min(blockedamount * 2, 333));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (sourceentity.getX()), (sourceentity.getY() + 0.5), (sourceentity.getZ()), 8, 0.5, 0.5, 0.5, 0.1);
		} else if (shield.getItem() == CaerulaArborModItems.TIDELINKED_SHIELD.get()) {
			if (sourceentity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.LESS_ARMOR.get())) {
				amplifi = (sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.LESS_ARMOR.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.LESS_ARMOR.get()).getAmplifier() : 0) + 1;
				(entity instanceof LivingEntity _entUseItem10 ? _entUseItem10.getUseItem() : ItemStack.EMPTY).setDamageValue((int) (shield.getDamageValue() - amplifi));
				if (entity.isAlive() && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
					LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * amplifi * 0.01);
				}
			}
			GiveLessArmorProcedure.execute(sourceentity, 5);
		} else if (shield.getItem() == CaerulaArborModItems.TRAILRITE_SHIELD.get()) {
			if (shield.getDamageValue() < shield.getMaxDamage() - 1) {
				if (!IsPalsyProcedure.execute(sourceentity)) {
					if (Math.random() < 0.33) {
						if (sourceentity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
							_livingEntity19.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(1);
						if (event instanceof ShieldBlockEvent _event) {
							_event.setShieldTakesDamage(false);
						}
					}
				}
				if (!(entity instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(CaerulaArborModMobEffects.DIZZY.get()))) {
					if (Math.random() < 0.33) {
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 60, 0, false, false));
					}
				}
				DeductPlayerSanityProcedure.execute(sourceentity, blockedamount * 2.5);
			}
		}
	}
}
