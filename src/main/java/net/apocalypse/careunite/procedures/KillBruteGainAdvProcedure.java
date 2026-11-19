package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class KillBruteGainAdvProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		String str = "";
		String name = "";
		if (sourceentity instanceof Player && !(sourceentity instanceof ServerPlayer _plr1 && _plr1.level() instanceof ServerLevel
				&& _plr1.getAdvancements().getOrStartProgress(_plr1.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:kill_brute"))).isDone())) {
			str = entity.getPersistentData().getString("hurtPlayer");
			name = sourceentity.getDisplayName().getString();
			if (!str.contains(name)) {
				if (sourceentity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:kill_brute"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 0.5), z, new ItemStack(CaerulaArborModItems.CRIMSON_TREATY.get()));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
	}
}
