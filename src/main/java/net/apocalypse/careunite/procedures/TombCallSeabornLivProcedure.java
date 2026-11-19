package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TombstoneLivingEntity;

import java.util.UUID;
import java.util.List;
import java.util.Comparator;

public class TombCallSeabornLivProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity chasing = null;
		String uuidd = "";
		if (!world.isClientSide()) {
			if (entity.tickCount % 20 == 0) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tombstone")))) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
							continue;
						}
						if (entityiterator.getPersistentData().getBoolean("caerulaTombEatenSmall")) {
							continue;
						}
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
							OnSmallTombstoneEatenProcedure.execute(world, x, y, z, entityiterator, entity);
							break;
						}
					}
				}
			}
			if (entity.tickCount % 5 == 0) {
				uuidd = entity instanceof TombstoneLivingEntity _datEntS ? _datEntS.getEntityData().get(TombstoneLivingEntity.DATA_chasingEntity) : "";
				if (!(uuidd).isEmpty()) {
					chasing = new Object() {
						Entity entityFromStringUUID(String uuid, Level world) {
							Entity _uuidentity = null;
							if (world instanceof ServerLevel _server) {
								try {
									_uuidentity = _server.getEntity(UUID.fromString(uuid));
								} catch (Exception e) {
								}
							}
							return _uuidentity;
						}
					}.entityFromStringUUID(uuidd, (Level) world);
				}
				if (!(chasing == null) && chasing.isAlive() && !chasing.getPersistentData().getBoolean("caerulaTombEatenSmall")) {
					VexChargeToSlayerProcedure.execute(entity, chasing);
					if (chasing instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 20, 0, false, false));
				} else {
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tombstone")))) {
								continue;
							}
							if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
								continue;
							}
							if (entityiterator.getPersistentData().getBoolean("caerulaTombEatenSmall")) {
								continue;
							}
							if (entityiterator instanceof LivingEntity _livEnt17 && _livEnt17.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
								continue;
							}
							if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
								if (entity instanceof TombstoneLivingEntity _datEntSetS)
									_datEntSetS.getEntityData().set(TombstoneLivingEntity.DATA_chasingEntity, (entityiterator.getStringUUID()));
								break;
							}
						}
					}
				}
			}
		}
	}
}
