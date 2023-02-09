package de.thedesigncraft.lookup.user;

import de.thedesigncraft.discord.botstuff.essential.manage.commands.discord.IUserContextMenu;
import de.thedesigncraft.lookup.Lookup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UserLookupContextMenu implements IUserContextMenu {
    @Override
    public @NotNull String name() {
        return IUserContextMenu.super.name();
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
    public void performUserContextMenu(@NotNull UserContextInteractionEvent event) {

        event.replyEmbeds(new EmbedBuilder().setDescription("Fetching Data...").build()).setEphemeral(true).complete()
                .editOriginalEmbeds(Lookup.user(event.getTarget())).queue();

    }

}
