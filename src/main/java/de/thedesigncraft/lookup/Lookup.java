package de.thedesigncraft.lookup;

import de.thedesigncraft.discord.botstuff.essential.Checks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lookup {

    @NotNull
    public static MessageEmbed user(@NotNull User user) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setAuthor(user.getAsTag() + " (" + user.getId() + ")", "https://discord.com/users/" + user.getId() + "/", user.getEffectiveAvatarUrl());
        embedBuilder.setThumbnail(user.retrieveProfile().useCache(false).complete().getBannerUrl());

        String description = """
                **Is bot:** {isBot}
                **Is System:** {isSystem}
                **Time Created:** {timeCreated}
                **Accent Color:** {accentColor}
                """

                .replace("{timeCreated}", "<t:" + (Date.from(user.getTimeCreated().toInstant()).getTime() / 1000L) + ":R>");

        List<User.UserFlag> flags = user.getFlags().stream().toList();

        if (!flags.isEmpty()) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < user.getFlags().size(); i++) {
                stringBuilder
                        .append("> - ")
                        .append(flags.get(i).getName())
                        .append(" ")
                        .append(getUserFlagIcon(flags.get(i)))
                        .append("\n");
            }

            embedBuilder.addField("Flags", stringBuilder.toString(), true);

        }

        if (user.isBot()) {
            description = description.replace("{isBot}", "<a:yes:961975010367508491>");
        } else {
            description = description.replace("{isBot}", "<a:no:961974993703538708>");
        }

        if (user.isSystem()) {
            description = description.replace("{isSystem}", "<a:yes:961975010367508491>");
        } else {
            description = description.replace("\n**Is System:** {isSystem}", "");
        }

        Color color = user.retrieveProfile().useCache(false).complete().getAccentColor();
        if (color != null) {
            description = description.replace("{accentColor}", "`#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue()) + "`");
        } else {
            description = description.replace("{accentColor}", "--");
        }

        embedBuilder.setDescription(description);

        return embedBuilder.build();

    }

    @NotNull
    public static MessageEmbed guild(@NotNull Guild guild) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setAuthor(guild.getName() + " (" + guild.getId() + ")", "https://discord.com/channels/" + guild.getId(), guild.getIconUrl());

        List<Member> members = guild.getMembers();
        List<Member> onlineMembers = new ArrayList<>();
        List<Member> bots = new ArrayList<>();

        members.forEach(member -> {
            if (!member.getOnlineStatus().equals(OnlineStatus.OFFLINE)) {
                onlineMembers.add(member);
            }
        });

        members.forEach(member -> {
            if (member.getUser().isBot()) {
                bots.add(member);
            }
        });

        String description = "{description} <:green_dot:1062387574515171359>**" + onlineMembers.size() + " Online**<:__:1062384862574686279><:__:1062384862574686279> <:gray_dot:1062387576813670450>**" + (members.size() - bots.size()) + " Members + " + bots.size() + " Bots**";

        if (!Checks.isEmptyOrNull(guild.getDescription())) {
            description = description.replace("{description}", guild.getDescription());
        } else {
            description = description.replace("{description}", "");
        }

        embedBuilder.setDescription(description);

        embedBuilder.getDescriptionBuilder()
                .append("\n\n**Owner:** `")
                .append(guild.getOwner().getUser().getAsTag())
                .append("`\n");

        embedBuilder.getDescriptionBuilder()
                .append("**Boosts:** ")
                .append(guild.getBoostCount())
                .append("/14 (Tier ")
                .append(guild.getBoostTier().getKey())
                .append("/3)\n");

        if (guild.getVanityUrl() != null)
            embedBuilder.getDescriptionBuilder()
                    .append("**VanityURL:** ")
                    .append(guild.getVanityUrl())
                    .append("\n");

        embedBuilder.getDescriptionBuilder()
                .append("**VerificationLevel:** ")
                .append(guild.getVerificationLevel().name())
                .append("\n");

        embedBuilder.setThumbnail(guild.getBannerUrl());

        StringBuilder features = new StringBuilder();

        guild.getFeatures().forEach(s -> features
                .append("> - ")
                .append(s)
                .append("\n"));

        embedBuilder.addField("Features", features.toString(), true);

        return embedBuilder.build();

    }

    @NotNull
    private static String getUserFlagIcon(@NotNull User.UserFlag flag) {

        if (flag.equals(User.UserFlag.STAFF)) {
            return "<:staff:1062091775684120706>";
        } else if (flag.equals(User.UserFlag.PARTNER)) {
            return "<:partner:1062091798543077406>";
        } else if (flag.equals(User.UserFlag.HYPESQUAD)) {
            return "<:hypesquad:1062091763004747806>";
        } else if (flag.equals(User.UserFlag.BUG_HUNTER_LEVEL_1)) {
            return "<:bug_hunter_level_1:1062091750514118706>";
        } else if (flag.equals(User.UserFlag.HYPESQUAD_BRAVERY)) {
            return "<:hypesquad_bravery:1062091740070281317>";
        } else if (flag.equals(User.UserFlag.HYPESQUAD_BRILLIANCE)) {
            return "<:hypesquad_brilliance:1062091729886527568>";
        } else if (flag.equals(User.UserFlag.HYPESQUAD_BALANCE)) {
            return "<:hypesquad_balance:1062091718490595459>";
        } else if (flag.equals(User.UserFlag.EARLY_SUPPORTER)) {
            return "<:early_supporter:1062091708818530394>";
        } else if (flag.equals(User.UserFlag.BUG_HUNTER_LEVEL_2)) {
            return "<:bug_hunter_level_2:1062092450786705428>";
        } else if (flag.equals(User.UserFlag.VERIFIED_BOT)) {
            return "<:verified_bot:1062091695660990515>";
        } else if (flag.equals(User.UserFlag.VERIFIED_DEVELOPER)) {
            return "<:verified_developer:1062091651838922762>";
        } else if (flag.equals(User.UserFlag.CERTIFIED_MODERATOR)) {
            return "<:certified_moderator:1062091663603945502>";
        } else if (flag.equals(User.UserFlag.ACTIVE_DEVELOPER)) {
            return "<:active_developer:1062091673821265961>";
        } else {
            return "";
        }

    }

}
