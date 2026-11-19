package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.AegirMortarEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class MortarFireExplodeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double radius, double strength, double type) {
		if (entity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double d = 0;
		double angle = 0;
		damage = (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * strength;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * radius) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
					continue;
				}
				if (!(entityiterator instanceof Monster)) {
					if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof AegirMortarEntity)) {
						continue;
					}
				}
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if (new Vec3(x, y, z).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) <= radius) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:brand_bomb"))), entity),
							(float) damage);
				}
			}
		}
		d = radius;
		if (type == 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_0")), SoundSource.HOSTILE, (float) 2.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_0")), SoundSource.HOSTILE, (float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		} else if (type == 1) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_1")), SoundSource.HOSTILE, (float) 2.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_1")), SoundSource.HOSTILE, (float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		} else if (type == 2) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_2")), SoundSource.HOSTILE, (float) 2.75,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mortar_explode_2")), SoundSource.HOSTILE, (float) 2.75, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 4, 1, 1, 1, 1);
		if (type < 2) {
			new Object() {
				void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
					MortarRimPtcProcedure.execute(world, x, y, z, timedloopiterator * 2);
					final int tick2 = ticks;
					CaerulaArborMod.queueServerWork(tick2, () -> {
						if (timedlooptotal > timedloopiterator + 1) {
							timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
						}
					});
				}
			}.timedLoop(0, (int) (d / 2), 1);
		} else {
			new Object() {
				void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
					MortarEwPtcProcedure.execute(world, x, y, z, timedloopiterator * 2);
					final int tick2 = ticks;
					CaerulaArborMod.queueServerWork(tick2, () -> {
						if (timedlooptotal > timedloopiterator + 1) {
							timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
						}
					});
				}
			}.timedLoop(0, (int) (d / 2), 1);
		}
	}
}
