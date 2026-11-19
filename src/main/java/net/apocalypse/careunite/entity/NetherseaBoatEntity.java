
package net.apocalypse.careunite.entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.PlayMessages;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.item.Item;

public class NetherseaBoatEntity extends Boat {
	public NetherseaBoatEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.NETHERSEA_BOAT.get(), world);
	}

	public NetherseaBoatEntity(EntityType<NetherseaBoatEntity> type, Level world) {
		super(CaerulaArborModEntities.NETHERSEA_BOAT.get(), world);
	}

	public static final TagKey<Block> NETHERSEA_BRAND = BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail"));

	public NetherseaBoatEntity(Level pLevel, double pX, double pY, double pZ) {
      super(pLevel, pX, pY, pZ);
   }

    public static void init() {
	}

	public Item getDropItem(){
		return CaerulaArborModItems.ITEM_NETHERSEA_BOAT.get();
	}
}
