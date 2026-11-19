package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import java.util.List;
import java.util.Comparator;

public class CastDragonSpellProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		Entity recentAttacker = null;
		Entity recentVictim = null;
		Entity t0 = null;
		Entity t1 = null;
		Entity t2 = null;
		double gap = 0;
		boolean IsCreative = false;
		boolean ApocataMode = false;
		gap = 30;
		if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get(), itemstack) != 0) {
			gap = Math.max(gap - itemstack.getEnchantmentLevel(CaerulaArborModEnchantments.SYNESTHESIA.get()) * 4, 10);
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.APOCATA_SWORD.get()) {
			gap = 2;
			ApocataMode = true;
		}
		if ((entity instanceof LivingEntity _entUseTicks6 ? _entUseTicks6.getTicksUsingItem() : 0) % gap == 0 && (entity instanceof LivingEntity _entUseTicks7 ? _entUseTicks7.getTicksUsingItem() : 0) > 0) {
			IsCreative = new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity) || ApocataMode;
			if ((entity instanceof Player _plr ? _plr.totalExperience : 0) >= 6 || IsCreative) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(entityiterator instanceof LivingEntity)) {
							continue;
						}
						if (entityiterator == entity) {
							continue;
						}
						if (!(entityiterator instanceof Monster)) {
							recentVictim = (entity instanceof LivingEntity _entity) ? _entity.getLastHurtMob() : null;
							recentAttacker = (entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
							if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity || entityiterator == recentVictim || entityiterator == recentAttacker)) {
								continue;
							}
						}
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 24) {
							if (t0 == null) {
								t0 = entityiterator;
							} else if (t1 == null) {
								t1 = entityiterator;
							} else if (t2 == null) {
								t2 = entityiterator;
							} else {
								break;
							}
						}
					}
				}
				if (t0 == null) {
					return;
				}
				if (t1 == null) {
					t1 = t0;
				}
				if (t2 == null) {
					t2 = t1;
				}
				if (!world.isClientSide()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_cast")), SoundSource.PLAYERS, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_cast")), SoundSource.PLAYERS, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
				CastDragonBreathProcedure.execute(world, x, y, z, entity, t0, 1);
				CastDragonBreathProcedure.execute(world, x, y, z, entity, t1, 0);
				CastDragonBreathProcedure.execute(world, x, y, z, entity, t2, 0);
				if (ApocataMode) {
					for (int index0 = 0; index0 < 2; index0++) {
						CastDragonBreathProcedure.execute(world, x, y, z, entity, t0, 1);
						CastDragonBreathProcedure.execute(world, x, y, z, entity, t1, 0);
						CastDragonBreathProcedure.execute(world, x, y, z, entity, t2, 0);
					}
				}
				if (!IsCreative) {
					if (entity instanceof Player _player)
						_player.giveExperiencePoints(-(6));
				}
			}
		}
	}
}
