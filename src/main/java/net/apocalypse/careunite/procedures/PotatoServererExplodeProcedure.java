package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class PotatoServererExplodeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity.tickCount % 20 == 10) {
			itemstack.setDamageValue((int) Math.min(100 + itemstack.getDamageValue(), 800));
			if (itemstack.getDamageValue() >= 799) {
				if (Math.random() < 0.2 && !world.isClientSide()) {
					if (world instanceof Level _level && !_level.isClientSide())
						_level.explode(null, x, y, z, 12, Level.ExplosionInteraction.MOB);
					if (world instanceof ServerLevel _level) {
						LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
						entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));;
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 6, 4, 4, 4, 0);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.BLOCKS, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.BLOCKS, 3, 1, false);
						}
					}
					itemstack.shrink(1);
				}
			}
		}
	}
}
