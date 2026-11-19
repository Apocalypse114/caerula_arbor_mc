package net.apocalypse.careunite.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class PlayerDropSmartProcedure {
	public static void execute(Entity entity) {
		if (entity instanceof Player player) playerThrow(player);
	}
	public static void playerThrow(Player player){
		//CaerulaArborMod.LOGGER.info("try throw");
        Inventory inventory = player.getInventory();
        int slotToThrow = getSuitableSlot(inventory, inventory.selected, player.getRandom());
        //CaerulaArborMod.LOGGER.info("select slot {}", slotToThrow);
        ItemStack toThrow;
        if(slotToThrow == 40) toThrow = player.getOffhandItem();
        else toThrow = inventory.getItem(slotToThrow);
        if(toThrow.isEmpty()) return;
        ItemStack copyItem = toThrow.copy();
        copyItem.setCount(1);
        //CaerulaArborMod.LOGGER.info("will throw {}", copyItem);
        ItemEntity item = player.drop(copyItem, false, true);
        //CaerulaArborMod.LOGGER.info("create item entity {}", item);
        if(item != null){
        	toThrow.shrink(1);
        	//CaerulaArborMod.LOGGER.info("throwed {}", toThrow);
        }
    }
    public static int getSuitableSlot(Inventory inventory, int selected, RandomSource source){
        List<Integer> slotWithSingleItem = new ArrayList<>(9);
        List<Integer> slotWithMultiItem = new ArrayList<>(9);
        List<Integer> slotWithExpensiveItem = new ArrayList<>(9);
        ItemStack pItem;
        for(int i=0;i<9;i++){
            if (i == selected) continue;
            pItem = inventory.getItem(i);
            if (pItem.isEmpty()) continue;
            if(pItem.getCount() > 1) slotWithMultiItem.add(i);
            else if (pItem.isEnchanted()) slotWithExpensiveItem.add(i);
            else slotWithSingleItem.add(i);
        }
        if (!slotWithMultiItem.isEmpty()){
            return slotWithMultiItem.get(source.nextIntBetweenInclusive(0, slotWithMultiItem.size()-1));
        }
        if (!slotWithSingleItem.isEmpty()){
            return slotWithSingleItem.get(source.nextIntBetweenInclusive(0, slotWithSingleItem.size()-1));
        }
        if (!slotWithExpensiveItem.isEmpty()){
            return slotWithExpensiveItem.get(source.nextIntBetweenInclusive(0, slotWithExpensiveItem.size()-1));
        }
        pItem = inventory.getSelected();
        if(!pItem.isEmpty()) return selected;
        return 40;
    }
}
