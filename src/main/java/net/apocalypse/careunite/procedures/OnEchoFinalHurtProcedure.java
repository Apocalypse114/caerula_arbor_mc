package net.apocalypse.careunite.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class OnEchoFinalHurtProcedure {
	public static void execute(LevelAccessor world, Entity entity, double amount) {
		if (entity == null)
			return;
		double ddd = 0;
		double migGrow = 0;
		if (GetEchoSpindlingProcedure.execute(entity) != 0 && world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.TARGET_LIFE_FUNCTION)) {
			ddd = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).damage_count;
			ddd = ddd + amount;
			{
				double _setval = ddd;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.damage_count = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (ddd >= Math.max(0.025 * (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)
					* ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive + 9), 20)) {
				{
					double _setval = 0;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.damage_count = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				migGrow = CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
				if (migGrow >= 3 && Math.random() < 0.125 || migGrow < 3 && Math.random() < 0.1) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.MAX_LP_FOOD.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				}
			}
		}
	}
}
