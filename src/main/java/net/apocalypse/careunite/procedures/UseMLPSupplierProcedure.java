package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class UseMLPSupplierProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double lp = 0;
		double lpm = 0;
		lp = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives;
		lpm = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.levelup")), SoundSource.NEUTRAL, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.levelup")), SoundSource.NEUTRAL, 2, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 72, 0.75, 1, 0.75, 1);
		{
			double _setval = lpm + 1;
			entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.player_maxlive = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		ChangePlayerLPOrShieldProcedure.execute(world, entity, true, 1, "max_life_point_supllier");
		itemstack.shrink(1);
	}
}
