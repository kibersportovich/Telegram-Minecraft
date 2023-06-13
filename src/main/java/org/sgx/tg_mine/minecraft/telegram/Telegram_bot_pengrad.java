package org.sgx.tg_mine.minecraft.telegram;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.UpdatesListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import java.io.IOException;
import java.util.List;
import net.minecraft.server.PlayerManager;
import net.minecraft.util.Formatting;

public class Telegram_bot_pengrad {

    public static MinecraftServer server;

    public static long chatId;

    public static TelegramBot bot;

    public static void start_bot(){
        bot.execute(new SendMessage(chatId, "<b><i>made by @kibersportovich</i></b>").parseMode(ParseMode.HTML));
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                Update update = updates.get(0);
                Message message = update.message();
                if (message == null) {return UpdatesListener.CONFIRMED_UPDATES_ALL;}
                if (message.text() == null) {return UpdatesListener.CONFIRMED_UPDATES_ALL;}
                String text = message.text();
                long chat = message.chat().id();
                long user_id = message.from().id();
                if (text.charAt(0) != '-' && text.equals("/reg")){
                    String code = Utils.random();
                    String text_mess;
                    if (!(Utils.id_nickname.containsValue(user_id))){
                        Utils.id_nickname.put(code, chat);
                        Utils.codes.add(code);
                        text_mess = "привет, введи код на сервере: " + code;
                    } else if (Utils.codes.contains(Utils.get_nick(user_id))){
                        String old_code = Utils.get_nick(user_id);
                        Utils.codes.remove(old_code);
                        Utils.id_nickname.remove(old_code);
                        Utils.id_nickname.put(code, user_id);
                        Utils.codes.add(code);
                        text_mess = "держи новый код: " + code;
                    }
                    else {
                        text_mess = "ты уже зарегистрирован";
                    }
                    SendMessage request = new SendMessage(chat, text_mess)
                            .parseMode(ParseMode.HTML)
                            .disableWebPagePreview(true)
                            .disableNotification(true);

                    bot.execute(request, new Callback<SendMessage, SendResponse>() {
                        @Override
                        public void onResponse(SendMessage request, SendResponse response) {}

                        @Override
                        public void onFailure(SendMessage request, IOException e) {}
                    });
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }

                if (chatId == chat) {
                    if (!(Utils.id_nickname.containsValue(user_id))) {
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                    String nick = Utils.get_nick(user_id);
                    if (Utils.codes.contains(nick)) {
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                    String str_mess = nick + "(from_tg): " + text;
                    Text text_mess = Text.literal(str_mess).formatted(Formatting.AQUA);
                    PlayerManager pm = server.getPlayerManager();
                    pm.broadcast(text_mess, false);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });



    }

}
