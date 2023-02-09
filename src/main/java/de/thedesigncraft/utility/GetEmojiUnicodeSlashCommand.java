package de.thedesigncraft.utility;

import de.thedesigncraft.discord.botstuff.essential.Checks;
import de.thedesigncraft.discord.botstuff.essential.EmbedTemplates;
import de.thedesigncraft.discord.botstuff.essential.manage.commands.discord.slash.ISlashCommand;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEmojiUnicodeSlashCommand implements ISlashCommand {
    @Override
    public @NotNull String name() {
        return ISlashCommand.super.name();
    }

    @Override
    public @Nullable List<OptionData> options() {
        return Collections.singletonList(new OptionData(OptionType.STRING, "emoji", "The emoji(s) you want the unicode from.").setRequired(true));
    }

    @Override
    public @NotNull String version() {
        return "v1.0.0-alpha.1";
    }

    @Override
    public @NotNull String category() {
        return "Utility";
    }

    @Override
    public @NotNull String description() {
        return "Sends you the unicode of a provided emoji.";
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
        return Emoji.fromUnicode("U+2139");
    }

    @Override
    public @Nullable List<Permission> requiredPermissions() {
        return null;
    }

    @Override
    public void performSlashCommand(@NotNull SlashCommandInteractionEvent event) {

        InteractionHook hook = event.replyEmbeds(new EmbedBuilder().setDescription("Loading...").build()).setEphemeral(true).complete();

        if (event.getOption("emoji") == null) {
            hook.editOriginalEmbeds(EmbedTemplates.issueEmbed("An internal error occured.", false)).queue();
            throw new IllegalStateException("Emoji option cannot be null.");
        }

        StringBuilder results = new StringBuilder();

        List<String> inputs = new ArrayList<>();
        Arrays.stream(event.getOption("emoji").getAsString().split(" ")).toList().forEach(s -> {
            String add = s.replaceAll(" ", "");

            if (!Checks.isEmptyOrNull(add))
                inputs.add(add);

        });

        inputs.forEach(s -> results.append(result(s)));

        EmbedBuilder embedBuilder = new EmbedBuilder(
                EmbedTemplates.standardEmbed(commandEmoji().getName() + " GetEmojiUnicode", "")
        );

        embedBuilder.addField("Unicode(s):", "```" + results + "```", true);

        hook.editOriginalEmbeds(embedBuilder.build()).queue();

    }

    @NotNull
    private static String result(@NotNull String s) {

        try {

            Emoji emoji = Emoji.fromFormatted(s);
            if (!emoji.getType().equals(Emoji.Type.UNICODE))
                return resultReturn(s, error("The emoji has to be a unicode emoji."));

            String result = "[U+" + emoji.getFormatted().codePoints().mapToObj(Integer::toHexString).toList().toString().replace("[", "");

            if (result.contains(","))
                return resultReturn(s, error("You must provide a unicode emoji."));

            return resultReturn(s, result);

        } catch (Exception e) {
            return resultReturn(s, error(e.getMessage()));
        }

    }

    @NotNull
    private static String error(@NotNull String error) {
        return "❌ Error: " + error;
    }

    @NotNull
    private static String resultReturn(@NotNull String s, @NotNull String result) {
        return s + ": " + result + "\n";
    }

}
