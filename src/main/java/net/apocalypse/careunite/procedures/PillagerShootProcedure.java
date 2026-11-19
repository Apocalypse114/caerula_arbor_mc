package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.ShotOceanArrowEntity;

public class PillagerShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null)) {
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + enemy.getBbHeight()), (enemy.getZ())));
		}
		if (entity.isAlive()) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.shoot")), SoundSource.HOSTILE, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.shoot")), SoundSource.HOSTILE, 1, 1, false);
				}
			}
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = new Object() {
						public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
							AbstractArrow entityToSpawn = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), level);
							entityToSpawn.setOwner(shooter);
							entityToSpawn.setBaseDamage(damage);
							entityToSpawn.setKnockback(knockback);
							entityToSpawn.setSilent(true);
							entityToSpawn.setPierceLevel(piercing);
							entityToSpawn.setCritArrow(true);
							return entityToSpawn;
						}
					}.getArrow(projectileLevel, entity,
							(float) (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0), 0, (byte) 1);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 2, 5);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		}
	}
}
