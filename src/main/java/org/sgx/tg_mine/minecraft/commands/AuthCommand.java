package org.sgx.tg_mine.minecraft.commands;

import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.util.Formatting;
import org.sgx.tg_mine.minecraft.telegram.Utils;
import net.minecraft.text.Text;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import org.sgx.tg_mine.minecraft.telegram.Database;
import java.sql.SQLException;

public class AuthCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("reg")
            .then(argument("code", string())
                    .executes(AuthCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Text error_db = Text.literal("ошибка базы данных").formatted(Formatting.RED);
        Text error_code = Text.literal("вы ввели неверный код").formatted(Formatting.RED);
        Text success = Text.literal("ваш ник успешно привязан к id вашего tg аккаунта").formatted(Formatting.GREEN);

        ServerCommandSource source = context.getSource();
        String nick = source.getName();
        String code = getString(context, "code");
        if (Utils.codes.contains(code)) {
            Long id = Utils.id_nickname.get(code);
            Utils.id_nickname.put(nick, id);
            Utils.id_nickname.remove(code);
            Utils.codes.remove(code);
            try {
                Database.writeUsers(id, nick);
            } catch (SQLException e){
                source.sendMessage(error_db);
                return 1;
            }
            source.sendMessage(success);
            return 1;
        }

        source.sendMessage(error_code);
        return 1;
    }

}
