package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class TrailSwordHitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		double absorp = 0;
		double rate = 0;
		DeductPlayerSanityProcedure.execute(entity, 330);
		if (!(entity instanceof Player)) {
			if (Math.random() < 0.2 + itemstack.getEnchantmentLevel(Enchantments.MOB_LOOTING) * 0.02) {
				rate = 0.025 + itemstack.getEnchantmentLevel(Enchantments.SHARPNESS) * 0.005;
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * rate) {
					absorp = (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * rate;
					if (absorp > (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
						absorp = sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
					}
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) - absorp < 1) {
						return;
					}
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) - absorp));
					LivingHeal1Procedure.execute(sourceentity, absorp);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sword_skill")), SoundSource.PLAYERS, (float) 1.75,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sword_skill")), SoundSource.PLAYERS, (float) 1.75, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ENCHANTED_HIT, x, (y + 1), z, 32, 2, 2, 2, 0.25);
				}
			}
		}
	}
}
