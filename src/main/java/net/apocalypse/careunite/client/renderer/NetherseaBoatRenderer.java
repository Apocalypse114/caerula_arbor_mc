package net.apocalypse.careunite.client.renderer;

import net.apocalypse.careunite.entity.NetherseaBoatEntity;
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

public class NetherseaBoatRenderer extends BoatRenderer {

	public Pair<ResourceLocation, ListModel<Boat>> BOAT_MODEL;
	public static final ResourceLocation BOAT = new ResourceLocation("caerula_arbor_reunite:textures/entities/nethersea_boat.png");
	
	public NetherseaBoatRenderer(EntityRendererProvider.Context context) {
		super(context, false);
		this.BOAT_MODEL = Pair.of(BOAT, getModel(context));
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat){
		return this.BOAT_MODEL;
	}

	private static ListModel<Boat> getModel(EntityRendererProvider.Context pContext){
      	ModelPart modelpart = pContext.bakeLayer(ModelLayers.createBoatModelName(Boat.Type.OAK));
      	return new BoatModel(modelpart);
	}
	
}


