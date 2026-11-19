package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class TriggerEchoProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double light = 0;
		double rate = 0;
		double per = 0;
		double rejection = 0;
		double ttt = 0;
		double rep = 0;
		if (world.isClientSide()) {
			return;
		}
		if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.NATURAL_GAIN_CALL_OF_WE_MANY)) {
			ttt = world.dayTime();
			if (ttt % 24000 == 13500) {
				light = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_light;
				if (light < 85) {
					rate = 0.15;
					if (light < 1) {
						rate = rate * 2;
					} else if (light >= 50) {
						rate = rate * 0.5;
					}
					if (Math.random() < rate) {
						rep = 1;
						if (IfSilenceProcedure.execute(world)) {
							rep = 2;
						}
						for (int index0 = 0; index0 < (int) rep; index0++) {
							rejection = ChoseEchoNoReptProcedure.execute(entity);
							if (rejection >= 0) {
								SetEchoProcedure.execute(entity, 2400, rejection);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:echo_trigger")), SoundSource.PLAYERS, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:echo_trigger")), SoundSource.PLAYERS, 1, 1, false);
									}
								}
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(
											Component.literal(("\u00A7c" + Component.translatable("gameplay.caerula_arbor_reunite.echo.get").getString() + Component.translatable(EchoNameFromIndexProcedure.execute(rejection)).getString())), false);
								ResetRewardLimitProcedure.execute(entity, rejection, 2400);
							}
						}
					}
				}
			}
		}
	}
}
