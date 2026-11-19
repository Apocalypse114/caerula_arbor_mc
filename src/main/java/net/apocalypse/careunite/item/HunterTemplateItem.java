
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;

public class HunterTemplateItem extends SmithingTemplateItem {
	public HunterTemplateItem() {
		super(
			Component.translatable("item.caerula_arbor_reunite.hunter_template.description_1").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.hunter_template.description_2").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.hunter_template.description_0").withStyle(ChatFormatting.GRAY),
			Component.translatable("item.caerula_arbor_reunite.hunter_template.description_3"),
			Component.translatable("item.caerula_arbor_reunite.hunter_template.description_4"),
			List.of(EMPTY_SLOT_ANCHOR), List.of(EMPTY_SLOT_KEEPSAKE)
		);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.hunter_template.descr"));
	}

	public static final ResourceLocation EMPTY_SLOT_ANCHOR = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_anchor");
	public static final ResourceLocation EMPTY_SLOT_KEEPSAKE = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_keepsake");
}
