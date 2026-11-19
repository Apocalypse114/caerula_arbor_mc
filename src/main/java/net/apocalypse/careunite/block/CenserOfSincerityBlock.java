
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ComposterBlock;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import java.util.List;
import net.minecraft.world.item.TooltipFlag;

public class CenserOfSincerityBlock extends ComposterBlock implements AlchemyCreation.Furnace {
	//public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 8);

	public CenserOfSincerityBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 4f));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("block.caerula_arbor_reunite.open_sea_remain_lamp.description_0"));
	}

	public int injectionType(){
		return 1;
	}
	
	public EntityType<?> injectionCreation(){
		return CaerulaArborModEntities.CONFESSION_CENSER.get();
	}
}
