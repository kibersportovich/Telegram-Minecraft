package org.sgx.tg_mine.minecraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.util.Formatting;
import org.sgx.tg_mine.minecraft.telegram.Database;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;
import org.sgx.tg_mine.minecraft.telegram.Utils;
import java.lang.NumberFormatException;
import java.sql.SQLException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;

public class SetChat {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("SetChat")
                .then(argument("chat", string())
                        .executes(SetChat::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Text error_privileges = Text.literal("you are not an operator").formatted(Formatting.RED);
        Text error_chat = Text.literal("You entered the wrong chat id").formatted(Formatting.RED);
        Text error_db = Text.literal("database error").formatted(Formatting.RED);
        Text success = Text.literal("chat has been changed").formatted(Formatting.GREEN);

        ServerCommandSource source = context.getSource();
        String nick = source.getName();
        String[] admins = source.getServer().getPlayerManager().getOpNames();
        if (!Utils.contains(admins, nick)){
            source.sendMessage(error_privileges);
            return 1;
        }
        long chat;
        try {
            chat = Long.parseLong(getString(context, "chat"));
        } catch (NumberFormatException e){
            source.sendMessage(error_chat);
            return 1;
        }
        Telegram_bot_pengrad.chatId = chat;
        try{
            Database.writeChat(chat);
        } catch (SQLException e){
            source.sendMessage(error_db);
            return 1;
        }
        source.sendMessage(success);
        return 1;
    }

}
