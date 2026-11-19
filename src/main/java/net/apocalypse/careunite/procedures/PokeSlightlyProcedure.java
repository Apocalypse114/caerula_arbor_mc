package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class PokeSlightlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack mainhand = ItemStack.EMPTY;
		mainhand = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (mainhand.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:nethersea_protective")))) {
			return;
		}
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization < 3) {
			DeductPlayerSanityProcedure.execute(entity, Mth.nextInt(RandomSource.create(), 16, 32));
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + 0.5), (y + 0.5), (z + 0.5), 12, 0.75, 0.75, 0.75, 0.1);
	}
}
