package net.apocalypse.careunite.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.CaerulaArborMod;

public class DrinkStimutantProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 280, 3));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 3));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 480, 0));
		DeductPlayerSanityProcedure.execute(entity, 75);
		itemstack.shrink(1);
		if (entity instanceof Player _player) {
			ItemStack _setstack = new ItemStack(CaerulaArborModItems.OCEANGLASS_CUP.get()).copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
		CaerulaArborMod.queueServerWork(240, () -> {
			if (entity.isAlive() && entity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get())) {
				if (Math.random() < 0.5) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.POISON, 280, 0));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 280, 0));
				}
				DeductPlayerSanityProcedure.execute(entity, 125);
			}
		});
	}
}
