
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;

public class TideHunetTemplateItem extends SmithingTemplateItem {
	public TideHunetTemplateItem() {
		super(
			Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_3").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_5").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_0").withStyle(ChatFormatting.GRAY),
			Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_2"),
			Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_4"),
			knightUpgradeList(), List.of(EMPTY_SLOT_KNIGHT)
		);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.tide_hunet_template.description_6"));
	}

	public static List<ResourceLocation> knightUpgradeList(){
		return List.of(
			OceanTrimTemplateItem.EMPTY_SLOT_HELMET, 
			OceanTrimTemplateItem.EMPTY_SLOT_SWORD, 
			OceanTrimTemplateItem.EMPTY_SLOT_CHESTPLATE, 
			OceanTrimTemplateItem.EMPTY_SLOT_LEGGINGS, 
			OceanTrimTemplateItem.EMPTY_SLOT_BOOTS
		);
	}
	public static final ResourceLocation EMPTY_SLOT_KNIGHT = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_knight");
}
