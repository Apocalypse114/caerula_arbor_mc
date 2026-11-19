package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.item.WavecleaverItem;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.CaerulaArborMod;

public class CleaverSkillProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			if (itemstack.getItem() instanceof WavecleaverItem)
				itemstack.getOrCreateTag().putString("geckoAnim", "animation.wavelceaver.spin");
			CaerulaArborMod.queueServerWork(5, () -> {
				if ((entity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.WAVECLEAVER.get()) : false) {
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							if ((entity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.WAVECLEAVER.get()) : false) {
								CleaverSpinProcedure.execute(world, entity, entity.getX() + 2 * entity.getLookAngle().x, entity.getY() + 2 * entity.getLookAngle().y, entity.getZ() + 2 * entity.getLookAngle().z);
							}
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 10, 2);
				}
			});
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 240);
		}
	}
}
