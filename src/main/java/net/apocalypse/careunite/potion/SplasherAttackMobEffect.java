
package net.apocalypse.careunite.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.apocalypse.careunite.procedures.FuncBySecondsProcedure;
import net.apocalypse.careunite.procedures.AttackAllonTrailProcedure;

import java.util.List;
import java.util.ArrayList;

public class SplasherAttackMobEffect extends MobEffect {
	public SplasherAttackMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -13421773);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> cures = new ArrayList<ItemStack>();
		return cures;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		AttackAllonTrailProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return FuncBySecondsProcedure.execute(duration);
	}
}
