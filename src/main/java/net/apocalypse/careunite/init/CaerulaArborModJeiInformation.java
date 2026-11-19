
package net.apocalypse.careunite.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.IModPlugin;

import java.util.List;

@JeiPlugin
public class CaerulaArborModJeiInformation implements IModPlugin {
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation("caerula_arbor_reunite:information");
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModItems.OCARINA.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.descr_ocarino"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModItems.MOIST_STAR.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_moist_star"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModItems.GENE_SAMPLE_NORMAL.get()), new ItemStack(CaerulaArborModItems.GENE_SAMPLE_UPGRADED.get()), new ItemStack(CaerulaArborModItems.GENE_SAMPLE_SUPERB.get())),
				VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_gene_sample"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModItems.COLLECTOR_MEAT.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_fish_body"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModBlocks.CHEST_FISH_FOOD.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_chest_fish"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModBlocks.TRAIL_DEBRIS.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_brand_debris"));
		registration.addIngredientInfo(List.of(new ItemStack(CaerulaArborModBlocks.DRAGON_BRAND.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.caerula_arbor_reunite.desc_dragon_brand"));
	}
}
