package net.apocalypse.careunite.client.renderer;

import net.apocalypse.careunite.entity.NetherseaChestBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.ListModel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.model.ChestBoatModel;

public class NetherseaChestBoatRenderer extends BoatRenderer {
	public Pair<ResourceLocation, ListModel<Boat>> BOAT_MODEL;
	public static final ResourceLocation BOAT = new ResourceLocation("caerula_arbor_reunite:textures/entities/nethersea_chest_boat.png");
	
	public NetherseaChestBoatRenderer(EntityRendererProvider.Context context) {
		super(context, true);
		this.BOAT_MODEL = Pair.of(BOAT, getModel(context));
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat){
		return this.BOAT_MODEL;
	}

	private static ListModel<Boat> getModel(EntityRendererProvider.Context pContext){
      	ModelPart modelpart = pContext.bakeLayer(ModelLayers.createChestBoatModelName(Boat.Type.OAK));
      	return new ChestBoatModel(modelpart);
	}
}
