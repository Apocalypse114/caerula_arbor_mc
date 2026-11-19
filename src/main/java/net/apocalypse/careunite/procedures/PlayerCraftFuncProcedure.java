package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.tags.ItemTags;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.util.Set;
import net.minecraft.world.item.Item;
import java.util.HashSet;
import net.minecraft.world.item.SwordItem;

@Mod.EventBusSubscriber
public class PlayerCraftFuncProcedure {
	@SubscribeEvent
	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		Player entity = event.getEntity();
		double x = entity.getX(), y = entity.getY(), z = entity.getZ();
		Level world = entity.level();
		if (entity == null)
			return;

		if (count(event.getInventory()) >= 2) OnEchoCraftOrSmeltProcedure.execute(world, x, y, z, entity);
		
		syncNetherseaSword(event);
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
	}

	private static void syncNetherseaSword(PlayerEvent.ItemCraftedEvent event){
		ItemStack output = event.getCrafting();
        ItemStack sword = ItemStack.EMPTY;
        Container inv = event.getInventory();
        int size = inv.getContainerSize();
        for (int i=0; i<size; i++){
            ItemStack tempItem = inv.getItem(i);
            if(tempItem.is(ItemTags.SWORDS) || tempItem.getItem() instanceof SwordItem){
                sword = tempItem.copy();
                if(i < size-1 && inv.getItem(i+1).is(CaerulaArborModItems.TRAIL_CREAM.get())) break;
                else return;
            }
        }
        if (sword.isEmpty()) return;
        
        CompoundTag _nbtTag = sword.getTag();
        if (_nbtTag != null)
            output.setTag(_nbtTag.copy());
	}

	public static int count(Container container){
        if (container.isEmpty()) return 0;
        Set<Item> itemSet = new HashSet<>();
        for(int i = 0; i < container.getContainerSize();i++){
        	if (container.getItem(i).isEmpty()) continue;
            Item item = container.getItem(i).getItem();
            itemSet.add(item);
        }
        return itemSet.size();
    }
}
