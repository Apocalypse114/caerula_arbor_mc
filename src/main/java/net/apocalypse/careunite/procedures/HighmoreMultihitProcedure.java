package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class HighmoreMultihitProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		entity.invulnerableTime = 0;
		if (!(sourceentity == entity)) {
			if ((sourceentity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 0) {
				CaerulaArborMod.queueServerWork(3, () -> {
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							entity.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:highmore_attack"))), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* 0.33));
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 2, 3);
				});
			} else if ((sourceentity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 1) {
				CaerulaArborMod.queueServerWork(3, () -> {
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							entity.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:highmore_attack"))), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* 0.33));
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 4, 3);
				});
			} else {
				CaerulaArborMod.queueServerWork(3, () -> {
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							entity.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:highmore_attack"))), sourceentity),
									(float) ((sourceentity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity14.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* 0.33));
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 6, 3);
				});
			}
			CaerulaArborMod.queueServerWork(10, () -> {
				if (!(immediatesourceentity == null)) {
					if (!immediatesourceentity.level().isClientSide())
						immediatesourceentity.discard();
				}
			});
		}
	}
}
