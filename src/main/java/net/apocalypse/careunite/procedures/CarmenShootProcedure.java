package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

public class CarmenShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double rate) {
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
		if ((enemy != null ? entity.distanceTo(enemy) : -1) > 8) {
			return;
		}
		xx = enemy.getX();
		yy = enemy.getY();
		zz = enemy.getZ();
		entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(xx, (yy + 1.8), zz));
		dama = entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), entity),
				(float) (dama * rate));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_shoot")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carmen_shoot")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.END_ROD, xx, (yy + 0.75), zz, 32, 0.75, 0.75, 0.75, 0.1);
	}
}
