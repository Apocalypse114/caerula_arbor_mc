package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

public class CarmenTeleportProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double xx = 0;
		double yy = 0;
		double zz = 0;
		double dama = 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy == null) {
			return;
		}
		if (!enemy.isAlive()) {
			return;
		}
		if ((enemy != null ? entity.distanceTo(enemy) : -1) > 24) {
			return;
		}
		xx = enemy.getX();
		yy = enemy.getY();
		zz = enemy.getZ();
		dama = entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		{
			Entity _ent = entity;
			_ent.teleportTo((xx + Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), yy, (zz + Mth.nextDouble(RandomSource.create(), -0.5, 0.5)));
			if (_ent instanceof ServerPlayer _serverPlayer)
				_serverPlayer.connection.teleport((xx + Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), yy, (zz + Mth.nextDouble(RandomSource.create(), -0.5, 0.5)), _ent.getYRot(), _ent.getXRot());
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_melee")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_melee")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), entity),
				(float) (dama * 2));
	}
}
