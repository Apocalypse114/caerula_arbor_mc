
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SlabBlock;

public class SaltwindBrickSlabBlock extends SlabBlock {
	public SaltwindBrickSlabBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(1f, 3f).requiresCorrectToolForDrops().dynamicShape());
	}
}
