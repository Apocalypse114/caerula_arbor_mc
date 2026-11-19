
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.IsharmlaCurseEntityProcedure;
import net.apocalypse.careunite.procedures.FuncByHalfscdProcedure;

import java.util.List;
import java.util.ArrayList;

public class IsharmlaCurseMobEffect extends MobEffect {
	public IsharmlaCurseMobEffect() {
		super(MobEffectCategory.HARMFUL, -11580593);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		IsharmlaCurseEntityProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return FuncByHalfscdProcedure.execute(duration);
	}
}
