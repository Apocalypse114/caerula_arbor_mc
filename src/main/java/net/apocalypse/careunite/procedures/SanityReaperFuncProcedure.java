package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class SanityReaperFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
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
		ItemStack mainHandItem = ItemStack.EMPTY;
		double lvl = 0;
		mainHandItem = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SANITY_REAPER.get(), mainHandItem) != 0) {
			lvl = mainHandItem.getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_REAPER.get());
			DeductPlayerSanityProcedure.execute(entity, amount * 2 * lvl);
			if (CaerulaConfigsConfiguration.SANITY_PTC.get()) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), x, (y + 1 + entity.getBbHeight() * 0.5), z, (int) Math.min(2 * lvl, 10), 1, 1, 1.2, 0.1);
			}
		}
	}
}
