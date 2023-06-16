package org.sgx.tg_mine.minecraft;
import net.fabricmc.api.ModInitializer;
import org.sgx.tg_mine.minecraft.commands.AuthCommand;
import org.sgx.tg_mine.minecraft.commands.DelTg;
import org.sgx.tg_mine.minecraft.commands.SetBot;
import org.sgx.tg_mine.minecraft.commands.SetChat;
import org.sgx.tg_mine.minecraft.commands.SetReg;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.sgx.tg_mine.minecraft.telegram.Database;
import java.sql.SQLException;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;


public class Tg_mine implements ModInitializer{

	public static final String MOD_ID = "tg_mine";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(
				server -> {
					Telegram_bot_pengrad.server = server;
				});
		try {
			Database.conn();
			Database.createDB();
			Database.readBot();
			Database.readUsers();
		} catch (SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
		Telegram_bot_pengrad.start_bot();

		CommandRegistrationCallback.EVENT.register(AuthCommand::register);
		CommandRegistrationCallback.EVENT.register(DelTg::register);
		CommandRegistrationCallback.EVENT.register(SetBot::register);
		CommandRegistrationCallback.EVENT.register(SetChat::register);
		CommandRegistrationCallback.EVENT.register(SetReg::register);
	}

}
