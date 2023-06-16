package org.sgx.tg_mine.minecraft.telegram;
import com.mojang.datafixers.kinds.IdF;
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
import net.minecraft.text.MutableText;

public class Telegram_bot_pengrad {

    public static MinecraftServer server;

    public static boolean reg;

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
                    String code = " " + Utils.random();
                    String text_mess;
                    if (Utils.id_nickname.get(user_id) == null){
                        Utils.id_nickname.put(chat, code);
                        Utils.codes.add(code);
                        text_mess = "привет, введи код на сервере:" + code;
                    } else if (Utils.codes.contains(Utils.id_nickname.get(user_id))){
                        String old_code = Utils.id_nickname.get(user_id);
                        Utils.id_nickname.replace(user_id, code);
                        Utils.codes.remove(old_code);
                        Utils.codes.add(code);
                        text_mess = "держи новый код:" + code;
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
                    PlayerManager pm = server.getPlayerManager();
                    String nick = Utils.id_nickname.get(user_id);
                    String str_mess = "(from_tg): " + text;
                    MutableText text_mess = Text.literal(str_mess).formatted(Formatting.AQUA);
                    if (!(nick == null || Utils.codes.contains(nick)))
                    {
                        MutableText green_nick = Text.literal(nick).formatted(Formatting.GREEN);
                        MutableText final_text = green_nick.append(text_mess);
                        pm.broadcast(final_text, false);
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                    if (!reg)
                    {
                        String tg_nick = Utils.tg_nick(message.from());
                        MutableText dark_blue_nick = Text.literal(tg_nick).formatted(Formatting.BLUE);
                        MutableText final_text = dark_blue_nick.append(text_mess);
                        pm.broadcast(final_text, false);
                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });



    }

}
