package de.thedesigncraft.lookup.guild;

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
import java.util.Collections;
import java.util.List;

public class GuildLookupSlashCommand implements ISlashCommand {
    @Override
    public @NotNull String name() {
        return ISlashCommand.super.name();
    }

    @Override
    public @Nullable List<OptionData> options() {
        return Collections.singletonList(new OptionData(OptionType.STRING, "guildid", "The id of the guild you want to lookup."));
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
        return "Looks up a guild for you.";
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
        return Emoji.fromUnicode("U+1F465");
    }

    @Override
    public @Nullable List<Permission> requiredPermissions() {
        return null;
    }

    @Override
    public void performSlashCommand(@NotNull SlashCommandInteractionEvent event) {

        InteractionHook hook = event.replyEmbeds(new EmbedBuilder().setDescription("Fetching Data...").build()).setEphemeral(true).complete();

        if (event.getOption("guildid") != null) {
            try {

                Guild guild = Main.jda.getGuildById(event.getOption("guildid").getAsLong());

                if (guild != null) {
                    hook.editOriginalEmbeds(Lookup.guild(guild)).queue();
                } else {
                    hook.editOriginalEmbeds(EmbedTemplates.issueEmbed("""
                            The specified server could not be loaded. This can have several reasons:
                            >  - The bot is not on this server
                            >  - The server does not exist
                            >  - There was some other error loading the server (Please contact the developer if you are sure this is the case)
                            """, false)).queue();
                }

            } catch (Exception e) {
                hook.editOriginalEmbeds(EmbedTemplates.issueEmbed(e.getMessage(), false)).queue();
            }
        } else {
            try {
                hook.editOriginalEmbeds(Lookup.guild(event.getGuild())).queue();
            } catch (Exception e) {
                hook.editOriginalEmbeds(EmbedTemplates.issueEmbed(e.getMessage(), false)).queue();
            }
        }

    }

}
