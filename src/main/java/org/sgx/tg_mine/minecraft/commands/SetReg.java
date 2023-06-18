package org.sgx.tg_mine.minecraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.sgx.tg_mine.minecraft.telegram.Database;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;

import java.sql.SQLException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;

public class SetReg {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("SetReg")
                .then(argument("on/off", string())
                        .executes(SetReg::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Text error_args = Text.literal("аргументами могут быть только \"on\" или \"off\" ").formatted(Formatting.RED);
        Text error_db = Text.literal("ошибка базы данных").formatted(Formatting.RED);
        Text on = Text.literal("привязка ника теперь обязательна").formatted(Formatting.GREEN);
        Text off = Text.literal("привязка ника теперь необязательна").formatted(Formatting.GREEN);

        ServerCommandSource source = context.getSource();
        String value = getString(context, "on/off");
        if (!(value.equals("on") || value.equals("off"))){
            source.sendMessage(error_args);
            return 1;
        }
        try{
            switch (value) {
                case "on" ->
                {
                    Telegram_bot_pengrad.reg = true;
                    source.sendMessage(on);
                    Database.change_reg("true");
                }
                case "off" ->
                {
                    Telegram_bot_pengrad.reg = false;
                    source.sendMessage(off);
                    Database.change_reg("false");
                }
                default -> source.sendMessage(error_args);
            }
        } catch (SQLException e){
            source.sendMessage(error_db);
            return 1;
        }

        return 1;
    }

}
