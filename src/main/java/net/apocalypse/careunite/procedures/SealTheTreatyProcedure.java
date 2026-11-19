package net.apocalypse.careunite.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class SealTheTreatyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		ItemStack money = ItemStack.EMPTY;
		ItemStack toGive = ItemStack.EMPTY;
		money = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).copy();
		money = new ItemStack(CaerulaArborModItems.TREATY_EMPTY.get()).copy();
		if (money.getItem() == CaerulaArborModItems.REDSTONE_INGOT.get()) {
			if (itemstack.getCount() >= 12) {
				money.shrink(12);
				toGive = new ItemStack(CaerulaArborModItems.TREATY_COPPER.get()).copy();
			}
			if (!(toGive.getItem() == CaerulaArborModItems.TREATY_EMPTY.get())) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (1 + y), z, 32, 1, 1, 1, 0.1);
				itemstack.shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = toGive.copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
			}
		}
	}
}
