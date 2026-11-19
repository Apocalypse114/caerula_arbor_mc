package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class TeleportInquisitionsProcedure {
	public static void execute(LevelAccessor world, Entity chief, ItemStack itemstack, double tx, double ty, double tz) {
		if (chief == null)
			return;
		double num = 0;
		double tX = 0;
		double tZ = 0;
		double rand = 0;
		double tY = 0;
		double dx = 0;
		double dz = 0;
		String log = "";
		String name = "";
		if (!(chief instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			tX = tx;
			tY = ty;
			tZ = tz;
			name = chief.getDisplayName().getString();
			if (world instanceof ServerLevel _server) {
				for (Entity entityiterator : _server.getAllEntities()) {
					if (entityiterator instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
						continue;
					}
					if (!((entityiterator.level().dimension()) == (chief.level().dimension()))) {
						continue;
					}
					if (((entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == chief || (entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == null)
							&& entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition")))) {
						for (int index0 = 0; index0 < 8; index0++) {
							dx = Mth.nextDouble(RandomSource.create(), -2, 2);
							dz = Mth.nextDouble(RandomSource.create(), -2, 2);
							if (IsValidForManProcedure.execute(world, tx + dx, tY, tz + dz)) {
								num = num + 1;
								entityiterator.getPersistentData().putString("recentCommander", name);
								RemoveTargetProcedure.execute(entityiterator);
								{
									Entity _ent = entityiterator;
									_ent.teleportTo((tx + dx), tY, (tz + dz));
									if (_ent instanceof ServerPlayer _serverPlayer)
										_serverPlayer.connection.teleport((tx + dx), tY, (tz + dz), _ent.getYRot(), _ent.getXRot());
								}
								if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 300, 0, false, false));
								break;
							}
						}
						if (num >= 9) {
							break;
						}
					}
				}
			}
			if (num > 0) {
				if (!(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayer _serverPlayer) {
							return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
						} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
							return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
									&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
						}
						return false;
					}
				}.checkGamemode(chief))) {
					if (chief instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack.getItem(), 60);
				}
				log = Component.translatable("interphone.dispatch.teleport").getString();
				log = log.replace("{num}", "" + Math.round(num));
				log = log.replace("{x}", "" + Math.round(Math.pow(10, 2) * tX) / Math.pow(10, 2));
				log = log.replace("{z}", "" + Math.round(Math.pow(10, 2) * tZ) / Math.pow(10, 2));
				log = log.replace("{y}", "" + Math.round(Math.pow(10, 2) * tY) / Math.pow(10, 2));
				if (chief instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(log), true);
				if (chief instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
			}
		}
	}
}
