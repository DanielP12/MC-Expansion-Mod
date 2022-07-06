package dinocraft.command.WIP;

/*package dinocraft.command.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dinocraft.capabilities.entity.DinocraftEntity;
import dinocraft.util.DinocraftConfig;
import dinocraft.util.server.DinocraftPlayerList;
import dinocraft.util.server.UserListBansEntry;
import dinocraft.util.server.UserListDataEntry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/*
 * STANDINGS: 7, 14, 30, 360 (final)
 * CT - Cross Teaming - Cross teaming, working with another team or player. 7
 * TG - Team Griefing - Negatively affecting your fellow team members. 7
 * IB - Inappropriate Build - Creating a build or drawing which is not appropriate on the server. 7
 * IN - Inappropriate Item Name - Creating or using an item that has an inappropriate name. 7
 * IC - Inappropriate Cosmetics - Using pets or cosmetics in an inappropriate way. 7
 * IM - Impersonation - Misleading others to believe you are a YouTuber or staff member. 7
 * EC2 - Encouraging Cheating 2 - Discussing or acting in a manner which encourages cheating or rule breaking. 14
 * SC - Scamming - Attempting to obtain information or something of value from players. 14
 * EUD - Extreme User Disrespect - Acting in a manner that is extremely disrespectful to members within the community. 14
 * IS - Inappropriate Skin/Cape - Using inappropriate skins or capes on the server. 14
 * IC2 - Inappropriate Content 2 - Talking or sharing inappropriate content with explicit themes on the server. 14
 * BM - Blacklisted Modifications - Cheating through the use of unfair game advantages. 30
 * WD - Watchdog - WATCHDOG CHEAT DETECTION 30
 * BO - Boosting - Boosting your account to improve your stats. 30
 * PL - Phishing Links - Attempting to gain access to other users' accounts/information. 30
 * AS - Account Selling - Attempting to sell Minecraft accounts. 30
 * ASA - Account Security Alert - Your account has a security alert, please secure it and contact appeals. permanent
 * Maybe add /secure command? (Changes the above punishment to 30 day ban with:)
 * SA - Account Security Alert - Your account's security appeal was processed and the account has entered a recovery phase and will be able to access the server again after 30 days. Use this time to change passwords, emails, and security questions.
 */