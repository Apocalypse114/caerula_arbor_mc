package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.world.inventory.PlayerEvoMenu;
import net.apocalypse.careunite.network.CaerulaArborModVariables;

import io.netty.buffer.Unpooled;

public class CheckNodesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String title = "";
		double quantity = 0;
		double quality = 0;
		double quantity_cost = 0;
		double quality_cost = 0;
		double add_def = 0;
		quantity = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reserve_quantity;
		quality = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reserve_quality;
		title = entity.getPersistentData().getString("showcasingEvoNode");
		if (!IsNexusNoRejectionSelectedProcedure.execute(entity) && (title).equals("nexus.no_rejection") && quality >= 1) {
			{
				boolean _setval = true;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.PEVO_NEXUS_no_rejection = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			quality_cost = 1;
		} else if (!IsNexusRegSanitySelectedProcedure.execute(entity) && (title).equals("nexus.reg_sanity") && quantity >= 2) {
			{
				boolean _setval = true;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.PEVO_NEXUS_reg_sanity = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			quantity_cost = 2;
		} else if (!IsNexusRegLightsSelectedProcedure.execute(entity) && (title).equals("nexus.reg_lights") && quality >= 2) {
			{
				boolean _setval = true;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.PEVO_NEXUS_reg_lights = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			quality_cost = 2;
		} else if (!IsNexusPercDamageSelectedProcedure.execute(entity) && (title).equals("nexus.perc_damage") && quality >= 3) {
			{
				boolean _setval = true;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.PEVO_NEXUS_perc_damage = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			quality_cost = 3;
		} else if (!IsNexusExpoShieldSelectedProcedure.execute(entity) && (title).equals("nexus.expo_shield") && quality >= 4) {
			{
				boolean _setval = true;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.PEVO_NEXUS_expo_shield = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			quality_cost = 4;
		} else if (title.contains("node.add_def")) {
			add_def = GetNodeAddDefProcedure.execute(entity);
			for (int index0 = 0; index0 < 4; index0++) {
				if (add_def < index0 + 1) {
					if (quantity >= index0 + 1) {
						{
							double _setval = index0 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_def = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						quantity_cost = index0 + 1;
						break;
					}
				}
			}
		} else if (title.contains("node.add_resis")) {
			add_def = GetNodeAddResisProcedure.execute(entity);
			for (int index1 = 0; index1 < 4; index1++) {
				if (add_def < index1 + 1) {
					if (quantity >= index1 + 1) {
						{
							double _setval = index1 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_resis = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						quantity_cost = index1 + 1;
						break;
					}
				}
			}
		} else if (title.contains("node.add_speed")) {
			add_def = GetNodeAddSpeedProcedure.execute(entity);
			for (int index2 = 0; index2 < 4; index2++) {
				if (add_def < index2 + 1) {
					if (quantity >= index2 + 1) {
						{
							double _setval = index2 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_speed = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						quantity_cost = index2 + 1;
						break;
					}
				}
			}
		} else if (title.contains("node.add_sanity")) {
			add_def = GetNodeAddSanityProcedure.execute(entity);
			for (int index3 = 0; index3 < 4; index3++) {
				if (add_def < index3 + 1) {
					if (quantity >= index3 + 1) {
						{
							double _setval = index3 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_sanity = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						quantity_cost = index3 + 1;
						break;
					}
				}
			}
		} else if (title.contains("node.add_damage")) {
			add_def = GetNodeAddDamageProcedure.execute(entity);
			for (int index4 = 0; index4 < 4; index4++) {
				if (add_def < index4 + 1) {
					if (index4 + 1 <= 2) {
						quantity_cost = 2;
					} else {
						quantity_cost = 4;
					}
					if (quantity >= quantity_cost) {
						{
							double _setval = index4 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_damage = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.less_damage")) {
			add_def = GetNodeLessDamageProcedure.execute(entity);
			for (int index5 = 0; index5 < 4; index5++) {
				if (add_def < index5 + 1) {
					if (index5 + 1 <= 2) {
						quantity_cost = 2;
					} else {
						quantity_cost = 4;
					}
					if (quantity >= quantity_cost) {
						{
							double _setval = index5 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_less_damage = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.living_barrier")) {
			add_def = GetNodeLivingBarrierProcedure.execute(entity);
			for (int index6 = 0; index6 < 4; index6++) {
				if (add_def < index6 + 1) {
					quantity_cost = index6 + 2;
					if (quantity >= quantity_cost) {
						{
							double _setval = index6 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_living_barrier = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.add_miss")) {
			add_def = GetNodeAddMissProcedure.execute(entity);
			for (int index7 = 0; index7 < 4; index7++) {
				if (add_def < index7 + 1) {
					quantity_cost = index7 + 2;
					if (quantity >= quantity_cost) {
						{
							double _setval = index7 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_add_miss = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.real_damage")) {
			add_def = GetNodeRealDamageProcedure.execute(entity);
			for (int index8 = 0; index8 < 4; index8++) {
				if (add_def < index8 + 1) {
					quantity_cost = 2 * index8 + 2;
					if (quantity >= quantity_cost) {
						{
							double _setval = index8 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_real_damage = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.heal_damage")) {
			add_def = GetNodeHealDamageProcedure.execute(entity);
			for (int index9 = 0; index9 < 4; index9++) {
				if (add_def < index9 + 1) {
					quantity_cost = 2 * index9 + 2;
					if (quantity >= quantity_cost) {
						{
							double _setval = index9 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_heal_damage = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.worse_break")) {
			add_def = GetNodeWorseBreakProcedure.execute(entity);
			for (int index10 = 0; index10 < 4; index10++) {
				if (add_def < index10 + 1) {
					quantity_cost = 2 * index10 + 2;
					if (quantity >= quantity_cost) {
						{
							double _setval = index10 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_worse_break = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.eunectes")) {
			add_def = GetNodeEutectesProcedure.execute(entity);
			for (int index11 = 0; index11 < 4; index11++) {
				if (add_def < index11 + 1) {
					quantity_cost = 2 * index11 + 3;
					if (quantity >= quantity_cost) {
						{
							double _setval = index11 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_eunectes = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		} else if (title.contains("node.less_armor")) {
			add_def = GetNodeLessArmorProcedure.execute(entity);
			for (int index12 = 0; index12 < 4; index12++) {
				if (add_def < index12 + 1) {
					quantity_cost = 2 * index12 + 3;
					if (quantity >= quantity_cost) {
						{
							double _setval = index12 + 1;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.PEVO_NODE_less_armor = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						break;
					}
					quantity_cost = 0;
				}
			}
		}
		if (quantity_cost > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:tap")), SoundSource.PLAYERS, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:tap")), SoundSource.PLAYERS, 2, 1, false);
				}
			}
			{
				double _setval = quantity - quantity_cost;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.reserve_quantity = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				NetworkHooks.openScreen((ServerPlayer) _ent, new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("PlayerEvo");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new PlayerEvoMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		}
		if (quality_cost > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alert")), SoundSource.PLAYERS, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alert")), SoundSource.PLAYERS, 2, 1, false);
				}
			}
			{
				double _setval = quality - quality_cost;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.reserve_quality = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof ServerPlayer _ent) {
				BlockPos _bpos = BlockPos.containing(x, y, z);
				NetworkHooks.openScreen((ServerPlayer) _ent, new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("PlayerEvo");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						return new PlayerEvoMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
					}
				}, _bpos);
			}
		}
	}
}
