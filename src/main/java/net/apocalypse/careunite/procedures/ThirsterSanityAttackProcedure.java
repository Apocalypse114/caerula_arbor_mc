package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.ThirsterEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class ThirsterSanityAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				ThirsterRimedParticleProcedure.execute(world, x, y, z, timedloopiterator * 4);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 5, 1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:thirster_shield_blast")), SoundSource.HOSTILE, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:thirster_shield_blast")), SoundSource.HOSTILE, 3, 1, false);
			}
		}
		if (entity instanceof ThirsterEntity _datEntSetI)
			_datEntSetI.getEntityData().set(ThirsterEntity.DATA_DIZZY_NUM, (int) ((entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_DIZZY_NUM) : 0) + 1));
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					continue;
				}
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 20) {
					DeductPlayerSanityProcedure.execute(entityiterator, 1000);
				}
			}
		}
	}
}
