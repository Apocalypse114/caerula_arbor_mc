package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedHorseEntity;
import net.apocalypse.careunite.entity.GuideAbyssalEntity;

public class GuideLayTrailProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState target = Blocks.AIR.defaultBlockState();
		if (entity.isAlive()) {
			if (ModGriefSettingsProcedure.execute(world) && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
					target = (world.getBlockState(BlockPos.containing(x, y, z)));
					if ((entity instanceof GuideAbyssalEntity _datEntI ? _datEntI.getEntityData().get(GuideAbyssalEntity.DATA_laylimit) : 0) > 0) {
						if ((target.canBeReplaced() || !(world.getBlockFloorHeight(BlockPos.containing(x, y, z)) > 0)) && !(target.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get())
								&& target.getDestroySpeed(world, BlockPos.containing(0, 0, 0)) >= 0) {
							if (CaerulaArborModBlocks.SEA_TRAIL_GROWN.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z))) {
								{
									BlockPos _pos = BlockPos.containing(x, y, z);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
								world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.SEA_TRAIL_GROWN.get().defaultBlockState(), 3);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.step")), SoundSource.NEUTRAL, 2, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.step")), SoundSource.NEUTRAL, 2, 1, false);
									}
								}
								if (entity instanceof GuideAbyssalEntity _datEntSetI)
									_datEntSetI.getEntityData().set(GuideAbyssalEntity.DATA_laylimit, (int) ((entity instanceof GuideAbyssalEntity _datEntI ? _datEntI.getEntityData().get(GuideAbyssalEntity.DATA_laylimit) : 0) - 1));
							}
						}
					} else if ((entity instanceof OceanizedHorseEntity _datEntI ? _datEntI.getEntityData().get(OceanizedHorseEntity.DATA_lay_limit) : 0) > 0) {
						if (target.canBeReplaced()) {
							if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z))) {
								{
									BlockPos _pos = BlockPos.containing(x, y, z);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
								world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(), 3);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.step")), SoundSource.NEUTRAL, 2, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.step")), SoundSource.NEUTRAL, 2, 1, false);
									}
								}
								if (entity instanceof OceanizedHorseEntity _datEntSetI)
									_datEntSetI.getEntityData().set(OceanizedHorseEntity.DATA_lay_limit, (int) ((entity instanceof OceanizedHorseEntity _datEntI ? _datEntI.getEntityData().get(OceanizedHorseEntity.DATA_lay_limit) : 0) - 1));
							}
						}
					}
				}
			}
		}
	}
}
