
package net.apocalypse.careunite.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
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
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import net.apocalypse.careunite.procedures.GetLightProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class LargeArrowEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.ARROW);
	private int age;

	public LargeArrowEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.LARGE_ARROW.get(), world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public LargeArrowEntity(EntityType<? extends LargeArrowEntity> type, Level world) {
		super(type, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public LargeArrowEntity(EntityType<? extends LargeArrowEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public LargeArrowEntity(EntityType<? extends LargeArrowEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
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

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		Entity e = entityHitResult.getEntity();
		if (e != this.getOwner() && e != null) e.invulnerableTime = 0;
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
			this.isCritArrow() ? CaerulaArborModSounds.TYPHEOUS_AFLOAT_HIT.get(): CaerulaArborModSounds.TYPHEOUS_HIT.get(), 
			SoundSource.PLAYERS, 1, 1f / (this.level().getRandom().nextFloat() * 0.5f + 1));

		super.onHitEntity(entityHitResult);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount >= 100) this.discard();
		if (this.inGround && this.inGroundTime > age)
 this.discard();
	}

	public static LargeArrowEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 1f, 5, 0);
	}

	public static LargeArrowEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 1f, 5, 0);
	}

	public static LargeArrowEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		LargeArrowEntity entityarrow = new LargeArrowEntity(CaerulaArborModEntities.LARGE_ARROW.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}
	public static LargeArrowEntity shoot(LivingEntity entity, LivingEntity target){
		return shoot(entity, target, false);
	}

	public static LargeArrowEntity shoot(LivingEntity entity, LivingEntity target, boolean skilled) {
		LargeArrowEntity entityarrow = new LargeArrowEntity(CaerulaArborModEntities.LARGE_ARROW.get(), entity, entity.level());
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getBbHeight() * 0.5 - 1.1;
		//if (skilled) entityarrow.setPos(entityarrow.getX(), entityarrow.getY() + 0.6, entityarrow.getZ());
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2f + (skilled ? 0.25f: 0), 1F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(skilled);
		entity.level().addFreshEntity(entityarrow);
		return entityarrow;
	}
}
