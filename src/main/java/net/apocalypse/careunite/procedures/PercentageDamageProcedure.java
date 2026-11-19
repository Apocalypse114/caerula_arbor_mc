package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class PercentageDamageProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack itemstack) {
		if (entity == null || sourceentity == null)
			return;
		if (Math.random() < 0.15 + itemstack.getEnchantmentLevel(Enchantments.SILK_TOUCH) * 0.08) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:axe_cleave"))), sourceentity),
					(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (0.08 + itemstack.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY) * 0.03 + itemstack.getEnchantmentLevel(Enchantments.SHARPNESS) * 0.02)));
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.BLOODOOZE.get()), x, (y + 1), z, 32, 2, 2, 2, 0.15);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:axe_skill")), SoundSource.PLAYERS, (float) 1.75,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:axe_skill")), SoundSource.PLAYERS, (float) 1.75, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		}
		DeductPlayerSanityProcedure.execute(entity, 225);
	}
}
