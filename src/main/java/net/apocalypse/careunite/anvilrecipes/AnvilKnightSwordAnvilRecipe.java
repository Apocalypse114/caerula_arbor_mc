
package net.apocalypse.careunite.anvilrecipes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.AnvilUpdateEvent;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

import net.apocalypse.careunite.init.CaerulaArborModItems;

@Mod.EventBusSubscriber
public class AnvilKnightSwordAnvilRecipe {
	@SubscribeEvent
	public static void execute(AnvilUpdateEvent event) {
		if ((event.getLeft().getItem() == Items.IRON_SWORD) && (event.getRight().getItem() == CaerulaArborModItems.KNIGHT_CORPSE.get())) {
			if ((event.getLeft().getCount() == 1) && (event.getRight().getCount() >= 1)) {
				event.setMaterialCost(1);
				event.setCost(4);
				event.setOutput(new ItemStack(CaerulaArborModItems.IRON_SWORD_OF_KNIGHT_CORPUS.get()));
			}
		}
	}
}
