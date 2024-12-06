package com.pWn3d1337.techguns.plugins.ftbl;

import java.util.UUID;

import dev.ftb.mods.ftbteams.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.data.Team;
import dev.ftb.mods.ftbteams.data.TeamManager;
import net.minecraft.server.MinecraftServer;


public class TeamSystemIntegration {
		/**
		 * Check if two players are allied.
		 */
		public static boolean isAllied(UUID ply1, UUID ply2) {
			TeamManager manager = FTBTeamsAPI.getManager();
			Team team1 = manager.getPlayerTeam(ply1);
			Team team2 = manager.getPlayerTeam(ply2);

			if (team1 != null && team2 != null) {
				// Check if the players are allies by UUID
				return team1.isAlly(ply2) || team2.isAlly(ply1);
			}

			// Players are not in teams, default to checking direct equality
			return ply1.equals(ply2);
		}


		/**
		 * Check if two players are allied but not in the same team.
		 */
		public static boolean isAlliedNoMember(UUID ply1, UUID ply2) {
			TeamManager manager = FTBTeamsAPI.getManager();
			Team team1 = manager.getPlayerTeam(ply1);
			Team team2 = manager.getPlayerTeam(ply2);

			if (team1 != null && team2 != null) {
				// Check if the players are allies by UUID
				return team1.isAlly(ply2) || team2.isAlly(ply1);
			}

			// Players are not in teams, default to checking direct equality
			return ply1.equals(ply2);
		}

		/**
		 * Check if two players are in the same team.
		 */
		public static boolean isTeamMember(UUID ply1, UUID ply2) {
			TeamManager manager = FTBTeamsAPI.getManager();
			Team team1 = manager.getPlayerTeam(ply1);

			if (team1 != null) {
				return team1.isMember(ply2);
			}

			return ply1.equals(ply2);
		}

		/**
		 * Check if two players are enemies.
		 */
		public static boolean isEnemy(UUID ply1, UUID ply2) {
			TeamManager manager = FTBTeamsAPI.getManager();
			Team team1 = manager.getPlayerTeam(ply1);
			Team team2 = manager.getPlayerTeam(ply2);

			if (team1 != null && team2 != null) {
				// Check if the players are allies by UUID
				return team1.isAlly(ply2) || team2.isAlly(ply1);
			}

			return false; // Default to not being enemies if not in teams
		}
	}
