
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

public class OceanTrimTemplateItem extends SmithingTemplateItem {
	public OceanTrimTemplateItem() {
		super(
			Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_3").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_5").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_0").withStyle(ChatFormatting.GRAY),
			Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_2"),
			Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_4"),
			commonUpgradeList(), commonMaterialList()
		);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.ocean_trim_template.description_6"));
	}

	public static List<ResourceLocation> commonUpgradeList(){
		return List.of(
			EMPTY_SLOT_HELMET, 
			EMPTY_SLOT_SWORD, 
			EMPTY_SLOT_CHESTPLATE, 
			EMPTY_SLOT_PICKAXE, 
			EMPTY_SLOT_LEGGINGS, 
			EMPTY_SLOT_AXE, 
			EMPTY_SLOT_SHIELD,
			EMPTY_SLOT_BOOTS, 
			EMPTY_SLOT_HOE, 
			EMPTY_SLOT_SHOVEL);
	}

	public static List<ResourceLocation> commonMaterialList(){
		return List.of(EMPTY_SLOT_CHITIN);
	}

	public static final ResourceLocation EMPTY_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
	public static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
	public static final ResourceLocation EMPTY_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
	public static final ResourceLocation EMPTY_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
	public static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation("item/empty_slot_hoe");
	public static final ResourceLocation EMPTY_SLOT_AXE = new ResourceLocation("item/empty_slot_axe");
	public static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation("item/empty_slot_sword");
	public static final ResourceLocation EMPTY_SLOT_SHOVEL = new ResourceLocation("item/empty_slot_shovel");
	public static final ResourceLocation EMPTY_SLOT_PICKAXE = new ResourceLocation("item/empty_slot_pickaxe");
	public static final ResourceLocation EMPTY_SLOT_INGOT = new ResourceLocation("item/empty_slot_ingot");
	public static final ResourceLocation EMPTY_SLOT_CHITIN = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_chitin");
	public static final ResourceLocation EMPTY_SLOT_SHIELD = new ResourceLocation("caerula_arbor_reunite:block/empty_slot_shield");
}
