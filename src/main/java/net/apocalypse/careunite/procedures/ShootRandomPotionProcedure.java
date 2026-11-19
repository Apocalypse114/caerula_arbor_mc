package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModPotions;

public class ShootRandomPotionProcedure {
	public static void execute(Entity obj) {
		if (obj == null)
			return;
		double potion = 0;
		Entity enemy = null;
		boolean isUndead = false;
		if (obj.isAlive()) {
			enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null)) {
				obj.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + enemy.getBbHeight() * 0.9), (enemy.getZ())));
			}
			if (obj instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, 9, false, false));
			isUndead = enemy instanceof LivingEntity _livEnt9 && _livEnt9.getMobType() == MobType.UNDEAD;
			potion = Mth.nextInt(RandomSource.create(), 0, 4);
			if (potion == 0) {
				if (isUndead) {
					{
						Entity _shootFrom = obj;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getPotion(Level level, Entity shooter) {
									ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
									entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), CaerulaArborModPotions.INST_SANITY_II.get()));
									entityToSpawn.setOwner(shooter);
									return entityToSpawn;
								}
							}.getPotion(projectileLevel, obj);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				} else {
					{
						Entity _shootFrom = obj;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getPotion(Level level, Entity shooter) {
									ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
									entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.HARMING));
									entityToSpawn.setOwner(shooter);
									return entityToSpawn;
								}
							}.getPotion(projectileLevel, obj);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
			} else if (potion == 1) {
				{
					Entity _shootFrom = obj;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getPotion(Level level, Entity shooter) {
								ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
								entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), CaerulaArborModPotions.INST_SANITY.get()));
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getPotion(projectileLevel, obj);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			} else if (potion == 2) {
				{
					Entity _shootFrom = obj;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getPotion(Level level, Entity shooter) {
								ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
								entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.POISON));
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getPotion(projectileLevel, obj);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			} else if (potion == 3) {
				{
					Entity _shootFrom = obj;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getPotion(Level level, Entity shooter) {
								ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
								entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.LONG_WEAKNESS));
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getPotion(projectileLevel, obj);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			} else if (potion == 4) {
				{
					Entity _shootFrom = obj;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getPotion(Level level, Entity shooter) {
								ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
								entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.SLOWNESS));
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getPotion(projectileLevel, obj);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
			if (Math.random() < 0.5) {
				if (isUndead) {
					{
						Entity _shootFrom = obj;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getPotion(Level level, Entity shooter) {
									ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
									entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), CaerulaArborModPotions.INST_SANITY_II.get()));
									entityToSpawn.setOwner(shooter);
									return entityToSpawn;
								}
							}.getPotion(projectileLevel, obj);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				} else {
					{
						Entity _shootFrom = obj;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getPotion(Level level, Entity shooter) {
									ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
									entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), Potions.HARMING));
									entityToSpawn.setOwner(shooter);
									return entityToSpawn;
								}
							}.getPotion(projectileLevel, obj);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				}
			} else {
				{
					Entity _shootFrom = obj;
					Level projectileLevel = _shootFrom.level();
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getPotion(Level level, Entity shooter) {
								ThrownPotion entityToSpawn = new ThrownPotion(EntityType.POTION, level);
								entityToSpawn.setItem(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), CaerulaArborModPotions.INST_SANITY.get()));
								entityToSpawn.setOwner(shooter);
								return entityToSpawn;
							}
						}.getPotion(projectileLevel, obj);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 2);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
		}
	}
}
