package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class PlayerRejectionFuncProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double rate = 0;
		double per = 0;
		double rejection = 0;
		double light = 0;
		if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization > 2) {
			return;
		}
		if (entity instanceof Player && IsSeabornProcedure.execute(sourceentity)) {
			light = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_light;
			if (light < 85 && (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).disoclusion < 15) {
				per = Math.min(amount / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1), 1);
				rate = 0.0025 + 0.0125 * per;
				if (light < 1) {
					rate = rate * 2;
				} else if (light >= 50) {
					rate = rate * 0.5;
				}
				if (CheckRejectionEnchantmentProcedure.execute(world, entity)) {
					rate = rate * 2;
				}
				if (Math.random() < rate) {
					rejection = Mth.nextInt(RandomSource.create(), 0, 3);
					if (!IsRejectionOnProcedure.execute(entity, rejection)) {
						SetRejectionOnProcedure.execute(entity, rejection);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie_villager.cure")), SoundSource.PLAYERS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie_villager.cure")), SoundSource.PLAYERS, 1, 1, false);
							}
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(
									Component.literal(
											(Component.translatable("item.caerula_arbor_reunite.rejection_key.description_0").getString() + "" + Component.translatable(("item.caerula_arbor_reunite.rejection_key.description_" + (int) (rejection + 1))).getString())),
									false);
						if (entity instanceof ServerPlayer _player) {
							Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:to_we_many"));
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
		}
	}
}
