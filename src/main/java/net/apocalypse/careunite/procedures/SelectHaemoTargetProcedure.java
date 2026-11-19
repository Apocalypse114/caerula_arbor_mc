package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class SelectHaemoTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity tgt_ent = null;
		double max_h = 0;
		tgt_ent = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (tgt_ent == null || tgt_ent instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.SUB_HAEMO.get())) {
			tgt_ent = (entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
		}
		if (tgt_ent == null || tgt_ent instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.SUB_HAEMO.get())) {
			for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 32), (z + 32), (x - 32), (y - 32), (z - 32)))) {
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					continue;
				}
				if (entityiterator instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(CaerulaArborModMobEffects.SUB_HAEMO.get())) {
					continue;
				}
				if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > max_h) {
					max_h = entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
					tgt_ent = entityiterator;
				}
			}
		}
		if (!(tgt_ent == null) && !(tgt_ent instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(CaerulaArborModMobEffects.SUB_HAEMO.get()))) {
			if (tgt_ent instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SUB_HAEMO.get(), 800, 0));
		}
	}
}
