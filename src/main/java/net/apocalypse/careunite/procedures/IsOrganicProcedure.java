package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class IsOrganicProcedure {
	public static boolean execute(BlockState block) {
		if (block.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:cannot_cover")))) {
			return false;
		}
		if (CaerulaConfigsConfiguration.EXTERNAL_ERROSION.get() && (block.is(BlockTags.create(new ResourceLocation("forge:phayrilesh"))) || block.is(BlockTags.create(new ResourceLocation("spore:fungal_blocks"))))) {
			return Math.random() < 0.33;
		}
		return block.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:organic")));
	}
}
