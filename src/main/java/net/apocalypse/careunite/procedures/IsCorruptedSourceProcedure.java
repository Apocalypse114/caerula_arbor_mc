package net.apocalypse.careunite.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class IsCorruptedSourceProcedure {
	public static final TagKey<EntityType<?>> HUMAN = TagKey.create(
			Registries.ENTITY_TYPE,
			new ResourceLocation("caerula_arbor_reunite:is_humanside")
		);

	public static boolean execute(DamageSource damagesource) {
		if (damagesource == null)
			return false;
		
return isCorruptedSource(damagesource);
	}

    public static boolean isCorruptedSource(DamageSource source){
        Entity entity = source.getEntity();
        if(entity == null) return true;
        if(entity.getType().is(HUMAN)) return true;
        return entity instanceof Player;
    }
}
