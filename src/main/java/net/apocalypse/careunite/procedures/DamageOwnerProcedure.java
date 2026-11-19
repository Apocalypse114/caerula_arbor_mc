package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IzumikEntity;

import java.util.Comparator;

public class DamageOwnerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity izumik = null;
		double dama = 0;
		if (entity.isAlive()) {
			izumik = (Entity) world.getEntitiesOfClass(IzumikEntity.class, AABB.ofSize(new Vec3(x, y, z), 27, 27, 27), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			dama = 12;
			if (!(izumik == null)) {
				dama = izumik instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
			}
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:izumik_skill")))), (float) (dama * 0.12));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, x, (y + 0.75), z, 24, 0.75, 0.75, 0.75, 0.1);
			if (Math.random() < 0.33) {
				if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
					_livingEntity8.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(
							((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()) ? _livingEntity7.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue() : 0)
									+ 1));
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.FIREWORK, x, (y + 0.75), z, 24, 0.75, 0.75, 0.75, 0.1);
			}
		}
	}
}
