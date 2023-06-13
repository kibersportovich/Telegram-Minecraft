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
import org.sgx.tg_mine.minecraft.telegram.Database;
import java.sql.SQLException;

public class DelTg {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("del_tg").executes(DelTg::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Text error_db = Text.literal("ошибка базы данных").formatted(Formatting.RED);
        Text error_acc = Text.literal("вы не зарегистрировали аккаунт").formatted(Formatting.RED);
        Text success = Text.literal("ваши аккаунты телеграм и майнкрафт больше не связаны").formatted(Formatting.GREEN);

        ServerCommandSource source = context.getSource();
        String nick = source.getName();
        if (Utils.id_nickname.containsKey(nick)){
            Utils.id_nickname.remove(nick);
            try {
            Database.delete(nick);
            } catch (SQLException e){
                source.sendMessage(error_db);
                return 1;
            }
            source.sendMessage(success);
            return 1;
        }
        source.sendMessage(error_acc);
        return 1;
    }

}
