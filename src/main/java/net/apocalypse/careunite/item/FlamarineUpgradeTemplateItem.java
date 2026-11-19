
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ChatFormatting;

public class FlamarineUpgradeTemplateItem extends SmithingTemplateItem {
	public FlamarineUpgradeTemplateItem() {
		super(
			Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_3").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_5").withStyle(ChatFormatting.BLUE),
			Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_0").withStyle(ChatFormatting.GRAY),
			Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_2"),
			Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_4"),
			flamarineUpgradeList(), List.of(OceanTrimTemplateItem.EMPTY_SLOT_INGOT)
		);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_6"));
		list.add(Component.translatable("item.caerula_arbor_reunite.flamarine_upgrade_template.description_7"));
	}

	public static List<ResourceLocation> flamarineUpgradeList(){
		return List.of(
			OceanTrimTemplateItem.EMPTY_SLOT_HELMET, 
			OceanTrimTemplateItem.EMPTY_SLOT_SWORD, 
			OceanTrimTemplateItem.EMPTY_SLOT_CHESTPLATE, 
			OceanTrimTemplateItem.EMPTY_SLOT_LEGGINGS, 
			OceanTrimTemplateItem.EMPTY_SLOT_BOOTS,
			OceanTrimTemplateItem.EMPTY_SLOT_AXE, 
			OceanTrimTemplateItem.EMPTY_SLOT_SHIELD,
			OceanTrimTemplateItem.EMPTY_SLOT_HOE, 
			OceanTrimTemplateItem.EMPTY_SLOT_SHOVEL,
			EMPTY_SLOT_PBOW
		);
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
    public static final ResourceLocation EMPTY_SLOT_PBOW = new ResourceLocation("caerula_arbor_reunite:item/empty_slot_pbow");
}
