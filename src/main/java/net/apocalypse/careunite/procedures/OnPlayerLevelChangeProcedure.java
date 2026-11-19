package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.minecraft.world.level.Level;

@Mod.EventBusSubscriber
public class OnPlayerLevelChangeProcedure {

	public static final String LAST_BONUS_LEVEL_ID = "caerulaLastBonusLevel";

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void levelUpBonus(PlayerXpEvent.LevelChange event){
        if(event.isCanceled()) return;
        Player player = event.getEntity();

        CaerulaArborModVariables.PlayerVariables V = player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
    		.orElse(new CaerulaArborModVariables.PlayerVariables());
    	double lp = V.player_lives, lpm = V.player_maxlive;

		if(lp >= lpm) return; 

		if(!player.level().getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.TARGET_LIFE_FUNCTION)) return;
		
        int lvl = event.getLevels(), lastBonusLvl = player.getPersistentData().getInt(LAST_BONUS_LEVEL_ID), currentLevel = player.experienceLevel;
        if (shouldBonus(player, lastBonusLvl, currentLevel, lvl)) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(CaerulaArborModItems.LIFE_POINT_FOOD.get()));
            player.getPersistentData().putInt(LAST_BONUS_LEVEL_ID, currentLevel + lvl);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onXpGet(PlayerXpEvent.XpChange event){
        if (event.getAmount() > 0){
        	Player player = event.getEntity();
        	Level level = player.level();
        	double bonus = GetEchoXoBonusProcedure.execute(level, player);
        	if (bonus > 1)
            	event.setAmount((int) (event.getAmount() * 1.5));
        }
    }

    private static float getBonusProbability(int current){
        return current > 5 ? 1f / (current * 0.2f) : 1;
    }

    private static boolean shouldBonus(Player player, int former, int current, int addition){
        return addition > 0 && current + addition > former + 5
                && player.getRandom().nextFloat() < getBonusProbability(current);
    }
}
