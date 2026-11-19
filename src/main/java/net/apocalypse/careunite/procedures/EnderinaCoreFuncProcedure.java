package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

import java.util.List;

public class EnderinaCoreFuncProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		boolean canSummon = false;
		double count = 0;
		BlockPos curPos = BlockPos.containing(x, y, z);
		if (!(blockstate.getBlock().getStateDefinition().getProperty("can_summon") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			if(!world.getEntitiesOfClass(OceanizedEnderinaEntity.class, 
			AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> e.isAlive()).isEmpty()) return;
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<MoistEnderCrystalEntity> _entfound = world.getEntitiesOfClass(MoistEnderCrystalEntity.class,
				new AABB(_center, _center).inflate(11 / 2d), e -> {return e.IS_STATIC;});
				for (MoistEnderCrystalEntity entityiterator : _entfound) {
					count ++;
					EnderinaLinkPtcToProcedure.execute(world, entityiterator.getX(), entityiterator.getY() + 1, entityiterator.getZ(), x + 0.5, y + 1, z + 0.5);
				}
			}
			if (count >= 4) {
				{
					BlockPos _pos = curPos;
					BlockState _bs = blockstate;
					if (_bs.getBlock().getStateDefinition().getProperty("can_summon") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, curPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.growl")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.growl")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
			}
		} else {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<MoistEnderCrystalEntity> _entfound = world.getEntitiesOfClass(MoistEnderCrystalEntity.class,
				new AABB(_center, _center).inflate(11 / 2d), e -> {return e.IS_STATIC;});
				for (MoistEnderCrystalEntity entityiterator : _entfound) {
					if (!entityiterator.level().isClientSide())
						entityiterator.discard();
					count = count + 1;
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.EXPLOSION, (entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ()), 2, 0.1, 0.1, 0.1, 1);
					if (count >= 4) {
						break;
					}
				}
			}
			if (count >= 4) {
				if (world instanceof ServerLevel _level) {
					LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
					entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));
					entityToSpawn.setVisualOnly(true);
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_ENDERINA.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, curPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_skill")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_skill")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
				world.destroyBlock(curPos, false);
			} else {
				{
					BlockPos _pos = curPos;
					BlockState _bs = blockstate;
					if (_bs.getBlock().getStateDefinition().getProperty("can_summon") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
			}
		}
	}
}
