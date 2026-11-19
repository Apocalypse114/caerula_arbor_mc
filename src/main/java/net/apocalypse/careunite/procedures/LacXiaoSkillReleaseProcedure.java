package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class LacXiaoSkillReleaseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double count = 0;
		if (!IsLancXiaoReadyProcedure.execute(itemstack)) {
			return;
		}
		for (int index0 = 0; index0 < 9; index0++) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Monster || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
						if (entityiterator instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get())) {
							continue;
						}
						if (!entityiterator.isAlive()) {
							continue;
						}
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 16) {
							count = count + 1;
							CaerulaArborMod.queueServerWork((int) (count * 2), () -> {
								if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 16) {
									LancXiaoChopProcedure.execute(world, entityiterator, entity);
								}
							});
						}
					}
					if (count >= 9) {
						break;
					}
				}
			}
			if (count >= 9) {
				break;
			}
		}
		if (count > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skill_release")), SoundSource.PLAYERS, (float) 0.75, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skill_release")), SoundSource.PLAYERS, (float) 0.75, 1, false);
				}
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 2, false, false));
			CaerulaArborMod.queueServerWork((int) ((count + 2) * 2), () -> {
				EndspeakerLinkPtcToProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), x, y, z);
				{
					Entity _ent = entity;
					_ent.teleportTo(x, y, z);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
				}
			});
			itemstack.getOrCreateTag().putDouble("sklp", 6);
		}
	}
}
