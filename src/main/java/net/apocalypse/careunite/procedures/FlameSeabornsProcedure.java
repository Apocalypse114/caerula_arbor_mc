package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class FlameSeabornsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.activate")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.activate")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				for (int index0 = 0; index0 < 120; index0++) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (x + 2 * (timedloopiterator + 1) * Math.sin(Math.toRadians(index0 * 3))), y,
								(z + 2 * (timedloopiterator + 1) * Math.cos(Math.toRadians(index0 * 3))), 4, 0.15, 0.2, 0.15, 0.1);
				}
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 9, 1);
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(36 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))
						&& !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanpet"))) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 18) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 200, 0, false, false));
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 400, 0, false, false));
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage"))), entity),
							(float) Math.max((entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.15,
									15));
					entityiterator.setSecondsOnFire(5);
				}
			}
		}
		for (int index1 = 0; index1 < 23; index1++) {
			for (int index2 = 0; index2 < 5; index2++) {
				for (int index3 = 0; index3 < 5; index3++) {
					dy = index2 - 2;
					dx = index1 - 11;
					dz = index3 - 2;
					BurndownTrailProcedure.execute(world, world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)), x + dx, y + dy, z + dz);
					BurndownTrailProcedure.execute(world, world.getBlockState(BlockPos.containing(x + dz, y + dy, z + dx)), x + dz, y + dy, z + dx);
				}
			}
		}
		if (!(new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity))) {
			{
				ItemStack _ist = itemstack;
				if (_ist.hurt(10, RandomSource.create(), null)) {
					_ist.shrink(1);
					_ist.setDamageValue(0);
				}
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 400);
		}
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
	}
}
