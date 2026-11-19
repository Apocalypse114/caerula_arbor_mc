
package net.apocalypse.careunite.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.apocalypse.careunite.procedures.SummonPtcProcedure;
import net.apocalypse.careunite.procedures.HighmoreMultihitProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.ai.attributes.Attributes;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class HighmoreShootEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(Blocks.REDSTONE_BLOCK);

	public HighmoreShootEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), world);
	}

	public HighmoreShootEntity(EntityType<? extends HighmoreShootEntity> type, Level world) {
		super(type, world);
	}

	public HighmoreShootEntity(EntityType<? extends HighmoreShootEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public HighmoreShootEntity(EntityType<? extends HighmoreShootEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected ItemStack getPickupItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	protected float getWaterInertia() {
      return 0.999F;
   }

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		HighmoreMultihitProcedure.execute(this.level(), entityHitResult.getEntity(), this, this.getOwner());
	}

	@Override
	public void tick() {
		super.tick();
		SummonPtcProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		if (this.inGround)
			this.discard();
	}

	public static HighmoreShootEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 1.5f, 3.5, 0);
	}

	public static HighmoreShootEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 1.5f, 3.5, 0);
	}

	public static HighmoreShootEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		HighmoreShootEntity entityarrow = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.squirt")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static HighmoreShootEntity shoot(LivingEntity entity, LivingEntity target) {
		HighmoreShootEntity entityarrow = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), entity, entity.level());
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.8f, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE) / 1.8);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.squirt")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
