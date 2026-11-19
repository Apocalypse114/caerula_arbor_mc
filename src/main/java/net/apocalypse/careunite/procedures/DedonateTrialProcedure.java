package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class DedonateTrialProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double dx = 0;
		double dz = 0;
		double dy = 0;
		BlockState target = Blocks.AIR.defaultBlockState();
		dx = -10;
		for (int index0 = 0; index0 < 21; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SMALL_FLAME, (x + dx), (y + 0.5), (z + 10), 32, 0.5, 2, 0.5, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SMALL_FLAME, (x + dx), (y + 0.5), (z - 10), 32, 0.5, 2, 0.5, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SMALL_FLAME, (x + 10), (y + 0.5), (z + dx), 32, 0.5, 2, 0.5, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SMALL_FLAME, (x - 10), (y + 0.5), (z + dx), 32, 0.5, 2, 0.5, 0.1);
			dx = dx + 1;
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 6, 3, 3, 3, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 128, 3, 3, 3, 0.1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.BLOCKS, (float) 3.2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.BLOCKS, (float) 3.2, 1, false);
			}
		}
		dx = -10;
		for (int index1 = 0; index1 < 21; index1++) {
			dz = -10;
			for (int index2 = 0; index2 < 21; index2++) {
				dy = -2;
				for (int index3 = 0; index3 < 5; index3++) {
					target = (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)));
					if (target.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:blow_up")))) {
						world.destroyBlock(BlockPos.containing(x + dx, y + dy, z + dz), false);
					}
					dy = dy + 1;
				}
				dz = dz + 1;
			}
			dx = dx + 1;
		}
		world.setBlock(BlockPos.containing(x, y, z), Blocks.IRON_BLOCK.defaultBlockState(), 3);
	}
}
