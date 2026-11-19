
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.SmithingTemplateItem;

public class InquisitionTemplateItem extends SmithingTemplateItem {
	public InquisitionTemplateItem() {
		super(
			Component.translatable("item.caerula_arbor_reunite.inquisition_template.description_1").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.inquisition_template.description_2").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.inquisition_template.description_0").withStyle(ChatFormatting.GRAY),
			Component.translatable("item.caerula_arbor_reunite.inquisition_template.description_3"),
			Component.translatable("item.caerula_arbor_reunite.inquisition_template.description_4"),
			armorList(), List.of(EMPTY_SLOT_BLOCK)
		);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
	}

	public static List<ResourceLocation> armorList(){
		return List.of(
			OceanTrimTemplateItem.EMPTY_SLOT_HELMET, 
			OceanTrimTemplateItem.EMPTY_SLOT_CHESTPLATE, 
			OceanTrimTemplateItem.EMPTY_SLOT_LEGGINGS, 
			OceanTrimTemplateItem.EMPTY_SLOT_BOOTS);
	}

	public static final ResourceLocation EMPTY_SLOT_BLOCK = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_block");
}
