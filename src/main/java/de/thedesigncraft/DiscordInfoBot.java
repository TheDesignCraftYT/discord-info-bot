package de.thedesigncraft;

import de.thedesigncraft.discord.botstuff.essential.manage.Main;
import de.thedesigncraft.discord.botstuff.essential.manage.commands.discord.manage.categories.CommandCategory;
import de.thedesigncraft.discord.botstuff.essential.manage.commands.discord.manage.categories.CommandCategoryBuilder;
import de.thedesigncraft.discord.botstuff.essential.manage.versions.Version;
import de.thedesigncraft.discord.botstuff.essential.manage.versions.VersionBuilder;
import de.thedesigncraft.discord.botstuff.essential.setup.StartupSetup;
import de.thedesigncraft.discord.botstuff.someMoreCommands.SomeMoreCommandsPackage;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.ArrayList;
import java.util.List;

public class DiscordInfoBot extends StartupSetup {

    public static void main(String[] args) {

        setMainPackage("de.thedesigncraft");
        setProjectName("discord-info-bot");
        setVersions(versions());
        setCommandCategories(categories());

        setToken(Version.Type.ALPHA, "MTA2MjA1Mjk0NjYyOTYzMjA1MQ.GPm3cq.6yR7Y6SKMESLAkjFrrZunEqDpyA0PaGMwWGiNQ");

        addActivatedStuffPackages(new SomeMoreCommandsPackage());

        new Main();

    }

    private static List<GatewayIntent> intents() {
        List<GatewayIntent> returnList = new ArrayList<>();

        returnList.add(GatewayIntent.GUILD_MEMBERS);
        returnList.add(GatewayIntent.GUILD_PRESENCES);

        return returnList;
    }

    private static List<Version> versions() {
        List<Version> returnList = new ArrayList<>();

        returnList.add(new VersionBuilder()
                .setName("v1.0.0-alpha.1")
                .setType(Version.Type.ALPHA)
                .build()
        );

        return returnList;
    }

    private static List<CommandCategory> categories() {
        List<CommandCategory> returnList = new ArrayList<>();

        returnList.add(new CommandCategoryBuilder()
                .setName("Snowflake Decoding")
                .setEmoji(Emoji.fromUnicode("U+2744"))
                .build()
        );

        returnList.add(new CommandCategoryBuilder()
                .setName("Utility")
                .setEmoji(Emoji.fromUnicode("U+2728"))
                .build()
        );

        return returnList;
    }

}
