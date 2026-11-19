package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.*;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

public class GetTransporterContainedEntityProcedure {
	public static Entity execute(Entity entity) {
		if (entity == null)
			return null;
		String emptyNameHolder = "apocata";
		ItemStack transp = ItemStack.EMPTY;
		String name = "";
		transp = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (IsFilledwithPersonnelProcedure.execute(transp)) {
			name = transp.getOrCreateTag().getString("name");
			if (name.isEmpty() || name.equals(emptyNameHolder))return entity;
        	ResourceLocation location = new ResourceLocation(name);
        	EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(location);
        	if(type==null) return entity;
        	return type.create(entity.level());
		}
		return entity;
	}
}
