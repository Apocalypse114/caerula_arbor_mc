package net.apocalypse.careunite.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.effect.MobEffectCategory;
import java.util.List;
import java.util.ArrayList;

public class DrinkIsharmlaMilkProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
{
			List<MobEffectInstance> effects = new ArrayList<>(_entity.getActiveEffects());
			for (MobEffectInstance effect: effects){
	            if(!effect.isInfiniteDuration() && effect.getEffect().getCategory() == MobEffectCategory.HARMFUL)
	                _entity.removeEffect(effect.getEffect());
			}
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REGENERATION_PERCLY.get(), 200, 1));
		}
		itemstack.shrink(1);
		if (entity instanceof Player _player) {
			ItemStack _setstack = new ItemStack(CaerulaArborModItems.OCEANGLASS_CUP.get()).copy();
			_setstack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
		}
	}
}
