package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SkadiClapProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double sklp = 0;
		if (new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(sourceentity)) {
			return;
		}
		if (IsNotFakeDyingProcedure.execute(entity)) {
			sklp = entity instanceof SkadiEntity _datEntI ? _datEntI.getEntityData().get(SkadiEntity.DATA_skillp2) : 0;
			if (sklp <= 0 && entity.isAlive()) {
				if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5) {
					if (entity instanceof SkadiEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SkadiEntity.DATA_skillp2, 120);
					if (entity instanceof SkadiEntity) {
						((SkadiEntity) entity).setAnimation("animation.skadi.skill");
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ())));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 9, false, false));
					CaerulaArborMod.queueServerWork(16, () -> {
						if (entity.isAlive()) {
							if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) <= 5) {
								sourceentity.hurt(
										new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity),
										(float) ((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity13.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
												* 2.5));
								if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 100, 0, false, false));
								sourceentity.push((entity.getLookAngle().x + 0.33), 0, (entity.getLookAngle().z + 0.33));
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.HOSTILE, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.HOSTILE, 2, 1, false);
								}
							}
							SkadiSwordClapProcedure.execute(world, x, y, z, entity, sourceentity);
						}
					});
				}
			}
		}
	}
}
