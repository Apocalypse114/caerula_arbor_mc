package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ArmorEnchantFuncProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level(), event.player.getX(), event.player.getY(), event.player.getZ(), event.player);
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack helm = ItemStack.EMPTY;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		double lvl = 0;
		double gap = 0;
		double maxAmplif = 0;
		double lvl0 = 0;
		double lvl1 = 0;
		double lvl2 = 0;
		double lvl3 = 0;
		helm = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		if (entity.tickCount % 10 == 0) {
			lvl = helm.getEnchantmentLevel(CaerulaArborModEnchantments.FLEXIBILITY.get()) + chest.getEnchantmentLevel(CaerulaArborModEnchantments.FLEXIBILITY.get()) + legg.getEnchantmentLevel(CaerulaArborModEnchantments.FLEXIBILITY.get())
					+ boot.getEnchantmentLevel(CaerulaArborModEnchantments.FLEXIBILITY.get());
			if (lvl > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FLEXIBILITY_BUFF.get(), 10, (int) Math.min(lvl - 1, 16), false, false));
			}
			lvl = helm.getEnchantmentLevel(CaerulaArborModEnchantments.MAGIC_TOLERANCE.get()) + chest.getEnchantmentLevel(CaerulaArborModEnchantments.MAGIC_TOLERANCE.get()) + legg.getEnchantmentLevel(CaerulaArborModEnchantments.MAGIC_TOLERANCE.get())
					+ boot.getEnchantmentLevel(CaerulaArborModEnchantments.MAGIC_TOLERANCE.get());
			if (lvl > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MAGIC_RESIS_BUFF.get(), 10, (int) Math.min(lvl - 1, 16), false, false));
			}
			lvl = helm.getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_INJURY_CURSE.get()) + chest.getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_INJURY_CURSE.get())
					+ legg.getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_INJURY_CURSE.get()) + boot.getEnchantmentLevel(CaerulaArborModEnchantments.SANITY_INJURY_CURSE.get());
			if (lvl > 0) {
				DeductPlayerSanityProcedure.execute(entity, lvl * 2);
			}
		}
		if (entity.tickCount % 40 == 10) {
			if (IsInquisitionSuitProcedure.execute(entity) > 1.5) {
				VanguardPlayerBuffProcedure.execute(world, x, y, z, entity, 6);
			} else if (IsInquisitionSuitProcedure.execute(entity) > 0.5) {
				VanguardPlayerBuffProcedure.execute(world, x, y, z, entity, 10);
			} else if (IsPlayerBatalionSuitProcedure.execute(entity)) {
				CorssbowmanPlayerBuffProcedure.execute(world, x, y, z, entity);
			}
		}
		lvl0 = helm.getEnchantmentLevel(CaerulaArborModEnchantments.HAZARD_PROTECTION.get());
		lvl1 = chest.getEnchantmentLevel(CaerulaArborModEnchantments.HAZARD_PROTECTION.get());
		lvl2 = legg.getEnchantmentLevel(CaerulaArborModEnchantments.HAZARD_PROTECTION.get());
		lvl3 = boot.getEnchantmentLevel(CaerulaArborModEnchantments.HAZARD_PROTECTION.get());
		lvl = lvl0 + lvl1 + lvl2 + lvl3;
		if (lvl > 0) {
			gap = Math.max(600 - 25 * lvl, 300);
			maxAmplif = Math.min(Math.max(Math.max(lvl0, lvl1), Math.max(lvl2, lvl3)), 2);
			if (entity.tickCount % gap == 64) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), (int) gap, (int) (maxAmplif - 1), false, false));
			}
		}
	}
}
