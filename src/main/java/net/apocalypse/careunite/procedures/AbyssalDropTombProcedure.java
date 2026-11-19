package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.entity.UmbrellaAbyssalEntity;
import net.apocalypse.careunite.entity.TombstoneStultiferaSmallEntity;
import net.apocalypse.careunite.entity.TombstoneStultiferaEntity;
import net.apocalypse.careunite.entity.SplasherAbyssalEntity;
import net.apocalypse.careunite.entity.PredatorAbyssalEntity;
import net.apocalypse.careunite.entity.GuideAbyssalEntity;
import net.apocalypse.careunite.entity.CrackerAbyssalEntity;
import net.apocalypse.careunite.entity.BaselayerAbyssalEntity;

public class AbyssalDropTombProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity tomb = null;
		double sublm = 0;
		double rate = 0;
		double subs_mig = 0;
		sublm = CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation;
		subs_mig = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting, CaerulaArborModVariables.MapVariables.get(world).strategy_migration);
		if (sublm > 0 && OnNetherseaBrandProcedure.execute(world, x, y, z) || subs_mig > 0 && subs_mig <= 2 && Math.random() < 0.1 || subs_mig > 2 && Math.random() < 0.25) {
			rate = (subs_mig <= 2) ? 0.3 : 0.5;
			tomb = SpawnStultiferarTombProcedure.execute(world, x, y, z, !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanelite"))),
					(entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate,
					(entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity2.getAttribute(Attributes.MAX_HEALTH).getValue() : 0) * rate);
			if (!(tomb == null)) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CLOUD, x, (y + 0.5), z, 16, 0.5, 0.5, 0.5, 0.1);
				if (entity instanceof UmbrellaAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaEntity.DATA_Varint, "umbrella");
				} else if (entity instanceof GuideAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaEntity.DATA_Varint, "brandguider");
				} else if (entity instanceof CrackerAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaEntity.DATA_Varint, "reefbreaker");
				} else if (entity instanceof PredatorAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaSmallEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaSmallEntity.DATA_Varint, "predator");
				} else if (entity instanceof BaselayerAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaEntity.DATA_Varint, "founder");
				} else if (entity instanceof SplasherAbyssalEntity) {
					if (tomb instanceof TombstoneStultiferaSmallEntity _datEntSetS)
						_datEntSetS.getEntityData().set(TombstoneStultiferaSmallEntity.DATA_Varint, "spewer");
				}
			}
		}
	}
}
