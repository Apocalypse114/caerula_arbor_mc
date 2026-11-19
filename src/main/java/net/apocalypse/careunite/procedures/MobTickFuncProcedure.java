package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.entity.MartusEntity;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Comparator;

@Mod.EventBusSubscriber
public class MobTickFuncProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean valid = false;
		double modifi = 0;
		double suitArchfi = 0;
		double suitKing = 0;
		double amplifi = 0;
		double enchant = 0;
		double pnt = 0;
		double damage = 0;
		if (IsSeabornProcedure.execute(entity)) {
			SeabornAttackHuntersProcedure.execute(world, x, y, z, entity);
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 3) {
				if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(MobEffects.DAMAGE_RESISTANCE))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 9999, (int) (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting - 3)));
				}
			}
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get()))) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence > 0) {
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 3) {
						if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get()))) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get(), 9999, (int) (CaerulaArborModVariables.MapVariables.get(world).strategy_silence - 1)));
						}
						if (!(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get())) && entity.tickCount % 10 == 0) {
							amplifi = -1;
							if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 4) {
								{
									final Vec3 _center = new Vec3(x, y, z);
									List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
											.toList();
									for (Entity entityiterator : _entfound) {
										if (!(entityiterator == entity) && IsSeabornProcedure.execute(entityiterator)) {
											amplifi = amplifi + 2;
										}
										if (amplifi > 29) {
											amplifi = 29;
											break;
										}
									}
								}
							} else {
								{
									final Vec3 _center = new Vec3(x, y, z);
									List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
											.toList();
									for (Entity entityiterator : _entfound) {
										if (!(entityiterator == entity) && IsSeabornProcedure.execute(entityiterator)) {
											amplifi = amplifi + 1;
										}
										if (amplifi > 9) {
											amplifi = 9;
											break;
										}
									}
								}
							}
							if (amplifi >= 0) {
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get(), 20, (int) amplifi, false, false));
							}
						}
					} else {
						if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
							if (!(entity instanceof LivingEntity _livEnt14 && _livEnt14.hasEffect(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get()))) {
								if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get(), 9999, (int) (CaerulaArborModVariables.MapVariables.get(world).strategy_silence - 1)));
							}
						} else {
							if (entity instanceof LivingEntity _entity)
								_entity.removeEffect(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get());
						}
						if (entity instanceof LivingEntity _entity)
							_entity.removeEffect(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get());
					}
					if (!(entity instanceof LivingEntity _livEnt18 && _livEnt18.hasEffect(MobEffects.REGENERATION)) && !(entity instanceof MartusEntity)) {
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 9999, (int) (CaerulaArborModVariables.MapVariables.get(world).strategy_silence - 1)));
					}
					if (entity instanceof Mob _mobEnt21 && _mobEnt21.isAggressive()) {
						if (!(entity instanceof LivingEntity _livEnt22 && _livEnt22.hasEffect(CaerulaArborModMobEffects.MOVE_FASTER.get()))) {
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MOVE_FASTER.get(), 9999, (int) (CaerulaArborModVariables.MapVariables.get(world).strategy_silence - 1)));
						}
					} else {
						if (entity instanceof LivingEntity _entity)
							_entity.removeEffect(CaerulaArborModMobEffects.MOVE_FASTER.get());
					}
				}
			}
			if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.NATURAL_EVOLUTION)) {
				if (entity.tickCount % 10 == 0 && !IsSeabornPetProcedure.execute(entity)) {
					if (Math.random() < 0.16 && !world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).isEmpty()) {
						if (!world.isClientSide()) {
							pnt = Mth.nextDouble(RandomSource.create(), 0, 0.01);
							CaerulaArborModVariables.MapVariables.get(world).evo_point_migration = CaerulaArborModVariables.MapVariables.get(world).evo_point_migration + pnt * Math
									.max(CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting + CaerulaArborModVariables.MapVariables.get(world).strategy_grow + CaerulaArborModVariables.MapVariables.get(world).strategy_breed, 1);
							CaerulaArborModVariables.MapVariables.get(world).syncData(world);
							UpgradeMigraProcedure.execute(world);
							UpgradeSilenceProcedure.execute(world, pnt);
							UpgradeSublimationProcedure.execute(world, GetPointFromHeightProcedure.execute(entity, pnt));
						}
					}
				}
			}
			if (entity.isAlive()) {
				OnSublimationTickProcedure.execute(world, entity);
			}
		}
		if (entity.tickCount % 10 == 0) {
			OnMobEchoTickProcedure.execute(world, entity);
		}
	}
}
