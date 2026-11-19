package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.TribunalHealerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class HealerSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double count = 0;
		double atk = 0;
		if (entity.isAlive()) {
			sklp1 = entity instanceof TribunalHealerEntity _datEntI ? _datEntI.getEntityData().get(TribunalHealerEntity.DATA_skillp1) : 0;
			sklp2 = entity instanceof TribunalHealerEntity _datEntI ? _datEntI.getEntityData().get(TribunalHealerEntity.DATA_skillp2) : 0;
			if (sklp1 > 0) {
				if (entity instanceof TribunalHealerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TribunalHealerEntity.DATA_skillp1, (int) (sklp1 - 1));
			} else {
				if (entity.tickCount % 5 == 0) {
					atk = entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity5.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof Player && IsInquisitionSuitProcedure.execute(entityiterator) > 0.5 || entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens")))
									|| entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")))) {
								if (!(entity == entityiterator)) {
									if (entity instanceof TribunalHealerEntity _datEntSetI)
										_datEntSetI.getEntityData().set(TribunalHealerEntity.DATA_skillp1, 100);
									if (entity instanceof TribunalHealerEntity) {
										((TribunalHealerEntity) entity).setAnimation("animation.tribunal_healer.concentratedheal");
									}
									CaerulaArborMod.queueServerWork(20, () -> {
										if (entity.isAlive()) {
											RangedStrengthenedHEalProcedure.execute(world, x, y, z, entity);
										}
									});
									break;
								}
							}
						}
					}
				}
			}
			if (sklp2 > 0) {
				if (entity instanceof TribunalHealerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TribunalHealerEntity.DATA_skillp2, (int) (sklp2 - 1));
			} else {
				if (entity.tickCount % 5 == 0 && GetAggresiveMobAroundProcedure.execute(world, x, y, z, entity)) {
					if (entity instanceof TribunalHealerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TribunalHealerEntity.DATA_skillp2, 240);
					if (entity instanceof TribunalHealerEntity) {
						((TribunalHealerEntity) entity).setAnimation("animation.tribunal_healer.shockwave");
					}
					CaerulaArborMod.queueServerWork(40, () -> {
						if (entity.isAlive()) {
							HealerFlamesProcedure.execute(world, x, y, z, entity);
						}
					});
				}
			}
		}
	}
}
