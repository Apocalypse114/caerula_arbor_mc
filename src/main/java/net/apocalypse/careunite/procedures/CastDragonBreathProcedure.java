package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.MoistDragonBreathEntity;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CastDragonBreathProcedure {
	public static SoundEvent SHOOT = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_cast"));
	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity owner, Entity target, double type) {
		if (owner == null) return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.MOIST_DRAGON_BREATH.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn instanceof MoistDragonBreathEntity _datEntSetS){
				RandomSource random = world.getRandom();
				entityToSpawn.setDeltaMovement(owner.getLookAngle().scale(0.25).add(
					Mth.nextDouble(random, -0.15, 0.15),
					Mth.nextDouble(random, -0.15, 0.15),
					Mth.nextDouble(random, -0.15, 0.15)
        		));
        		_level.playSound(owner, BlockPos.containing(x, y, z), SHOOT, SoundSource.HOSTILE, 2, 
        		Mth.nextFloat(owner.level().getRandom(), 0.9f, 1.1f));
				SynchedEntityData data = _datEntSetS.getEntityData();
				data.set(MoistDragonBreathEntity.DATA_OWNER, owner.getStringUUID());
				if(target != null) data.set(MoistDragonBreathEntity.DATA_TARGET, target.getStringUUID());
				data.set(MoistDragonBreathEntity.DATA_TYPE, (int) type);
				if(type > 0.5){
					AttributeInstance instance = _datEntSetS.getAttribute(Attributes.MAX_HEALTH);
					if (instance != null) instance.setBaseValue(instance.getBaseValue() * 2);
					_datEntSetS.setHealth(_datEntSetS.getMaxHealth());
				}
			}
		}
	}
}
