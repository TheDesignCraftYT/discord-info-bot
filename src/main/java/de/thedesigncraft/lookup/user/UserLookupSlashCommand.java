package de.thedesigncraft.lookup.user;

import de.thedesigncraft.discord.botstuff.essential.EmbedTemplates;
import de.thedesigncraft.discord.botstuff.essential.manage.Main;
import de.thedesigncraft.discord.botstuff.essential.manage.commands.discord.slash.ISlashCommand;
import de.thedesigncraft.lookup.Lookup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserLookupSlashCommand implements ISlashCommand {
    @Override
    public @NotNull String name() {
        return ISlashCommand.super.name();
    }

    @Override
    public @Nullable List<OptionData> options() {
        List<OptionData> returnList = new ArrayList<>();

        returnList.add(new OptionData(OptionType.USER, "user", "The user you want to look up"));
        returnList.add(new OptionData(OptionType.STRING, "userid", "The id of the user you want to look up"));

        return returnList;
    }

    @Override
    public @NotNull String version() {
        return "v1.0.0-alpha.1";
    }

    @Override
    public @NotNull String category() {
        return "Snowflake Decoding";
    }

    @Override
    public @NotNull String description() {
        return "Looks up a user for you";
    }

    @Override
    public boolean globalCommand() {
        return true;
    }

    @Override
    public @Nullable List<Guild> guilds() {
        return null;
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public @NotNull Emoji commandEmoji() {
        return Emoji.fromUnicode("U+1F464");
    }

    @Override
    public @Nullable List<Permission> requiredPermissions() {
        return null;
    }

    @Override
    public void performSlashCommand(@NotNull SlashCommandInteractionEvent event) {

        InteractionHook hook = event.replyEmbeds(new EmbedBuilder().setDescription("Fetching Data...").build()).setEphemeral(true).complete();

        if (event.getOption("user") != null) {
            hook.editOriginalEmbeds(Lookup.user(event.getOption("user").getAsUser())).queue();
        } else if (event.getOption("userid") != null) {
            try {
                hook.editOriginalEmbeds(Lookup.user(Main.jda.retrieveUserById(event.getOption("userid").getAsLong()).useCache(false).complete())).queue();
            } catch (Exception e) {
                hook.editOriginalEmbeds(EmbedTemplates.issueEmbed(e.getMessage(), false)).queue();
            }
        } else {
            hook.editOriginalEmbeds(EmbedTemplates.issueEmbed("We need a user or a user-id to lookup.", false)).queue();
        }

    }

}
