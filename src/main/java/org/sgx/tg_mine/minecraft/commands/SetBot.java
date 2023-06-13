package org.sgx.tg_mine.minecraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;
import com.pengrad.telegrambot.TelegramBot;
import org.sgx.tg_mine.minecraft.telegram.Utils;
import org.sgx.tg_mine.minecraft.telegram.Database;
import java.sql.SQLException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;


public class SetBot {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("SetBot")
                .then(argument("token", string())
                        .executes(SetBot::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        Text error_privileges = Text.literal("у вас недостаточно прав").formatted(Formatting.RED);
        Text error_db = Text.literal("ошибка базы данных").formatted(Formatting.RED);
        Text success = Text.literal("бот перезапущен с новой конфигурацией").formatted(Formatting.GREEN);

        ServerCommandSource source = context.getSource();
        String nick = source.getName();
        String[] admins = source.getServer().getPlayerManager().getOpNames();
        if (!Utils.contains(admins, nick)){
            source.sendMessage(error_privileges);
            return 1;
        }
        String token = getString(context, "token");
        Telegram_bot_pengrad.bot.shutdown();
        Telegram_bot_pengrad.bot = new TelegramBot(token);
        Telegram_bot_pengrad.start_bot();
        try{
            Database.writeToken(token);
        } catch (SQLException e){
            source.sendMessage(error_db);
            return 1;
        }
        source.sendMessage(success);
        return 1;
    }
}
