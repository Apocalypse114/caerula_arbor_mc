package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;

public class LlamaTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean charged = false;
		double sklP = 0;
		Entity eee = null;
		if (entity.isAlive()) {
			charged = entity instanceof OceanizedLlamaEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedLlamaEntity.DATA_CHARGED);
			sklP = entity instanceof OceanizedLlamaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedLlamaEntity.DATA_POUR_P) : 0;
			if (charged) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ELECT_SPARK.get()), x, (y + 0.8), z, 1, 0.75, 0.8, 0.75, 0.1);
				if (entity instanceof OceanizedLlamaEntity animatable)
					animatable.setTexture("oceanized_llama_charged");
			} else {
				if (entity instanceof OceanizedLlamaEntity animatable)
					animatable.setTexture("oceanized_llama");
			}
			if (sklP > 0) {
				if (sklP == 60) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.angry")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.angry")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
				}
				eee = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(eee == null)) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((eee.getX()), (eee.getY()), (eee.getZ())));
				}
				if (entity instanceof OceanizedLlamaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedLlamaEntity.DATA_POUR_P, (int) (sklP - 1));
				if (sklP > 10 && sklP <= 50 && sklP % 2 == 0) {
					LlamaSpitProcedure.execute(entity);
				}
			}
		}
	}
}
