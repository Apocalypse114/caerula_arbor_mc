package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LastKnightSwordFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		double rate = 0;
		rate = 1;
		if (damagesource.is(DamageTypes.PLAYER_ATTACK) && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.IRON_SWORD_OF_KNIGHT_CORPUS.get()
					|| (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.LONG_SWORD_OF_KNIGHT_CORPUS.get()) {
				rate = 1.5;
			}
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.LONG_SWORD_OF_KNIGHT_CORPUS.get()
				&& (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 33) {
			rate = rate * 1.5;
		}
		if (rate > 1) {
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * rate));
			}
		}
	}
}
