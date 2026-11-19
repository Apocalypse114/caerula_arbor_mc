package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import java.util.ArrayList;

public class UpgradeSublimationProcedure {
	public static void execute(LevelAccessor world, double point) {
		double stra = 0;
		String prefix = "";
		String num = "";
		stra = CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation;
		if (stra > 0) {
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if (entityiterator instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:fifth_touch"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		}
		if (IfCanSublimeProcedure.execute(world)) {
			CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation = CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation + point;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			if (stra < 4) {
				if (CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation >= Math.pow(stra + 1, 3) * (double) CaerulaConfigsConfiguration.COEFFICIENT.get() * 12) {
					CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation = stra + 1;
					CaerulaArborModVariables.MapVariables.get(world).syncData(world);
					stra = stra + 1;
					CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation = 0;
					CaerulaArborModVariables.MapVariables.get(world).syncData(world);
					if (stra == 1) {
						num = "I";
						prefix = "\u00A7p";
					} else if (stra == 2) {
						num = "II";
						prefix = "\u00A7p";
					} else if (stra == 3) {
						num = "III";
						prefix = "\u00A7e";
					} else if (stra == 4) {
						num = "IV";
						prefix = "\u00A76";
					}
					if (CaerulaConfigsConfiguration.EVOSOUND.get()) {
						for (Entity entityiterator : new ArrayList<>(world.players())) {
							if (stra <= 2) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_1")),
												SoundSource.NEUTRAL, 5, 1);
									} else {
										_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_1")), SoundSource.NEUTRAL,
												5, 1, false);
									}
								}
							} else {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_2")),
												SoundSource.NEUTRAL, 5, 1);
									} else {
										_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_2")), SoundSource.NEUTRAL,
												5, 1, false);
									}
								}
							}
						}
					}
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal((prefix + "" + Component.translatable("evolution.caerula_arbor_reunite.sublimation").getString() + num)), false);
				}
			} else {
				CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				for (Entity entityiterator : new ArrayList<>(world.players())) {
					if (entityiterator instanceof ServerPlayer _player) {
						Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:absurd_of_evolution"));
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
		} else {
			CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation = 0;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		}
	}
}
