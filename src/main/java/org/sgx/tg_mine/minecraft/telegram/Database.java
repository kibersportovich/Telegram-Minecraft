package org.sgx.tg_mine.minecraft.telegram;

import java.sql.*;

import com.pengrad.telegrambot.TelegramBot;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;
public class Database {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static void conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Telegram-minecraft.s3db");
    }

    public static void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'server_users' ('chat_id' INTEGER, 'username' TEXT);");
        statmt.execute("CREATE TABLE if not exists 'bot_settings' ('num' INTEGER, 'token' TEXT, 'chat' INTEGER);");
        resSet = statmt.executeQuery("SELECT * FROM bot_settings");
        if (!resSet.next()){
            statmt.execute("INSERT INTO bot_settings(num) VALUES(1);");
        }
    }

    public static void writeUsers(Long chat_id, String username) throws SQLException
    {

        PreparedStatement statement = conn.prepareStatement("INSERT INTO 'server_users' ('chat_id', 'username') VALUES (?, ?); ");
        statement.setObject(1, chat_id);
        statement.setObject(2, username);
        statement.execute();
    }

    public static void writeToken(String token) throws SQLException
    {
        statmt = conn.createStatement();
        statmt.execute(String.format("UPDATE bot_settings SET token='%s' WHERE num=1", token));
    }

    public static void writeChat(long chat) throws SQLException
    {
        statmt = conn.createStatement();
        statmt.execute(String.format("UPDATE bot_settings SET chat='%d' WHERE num=1", chat));
    }

    public static void readUsers() throws SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM server_users");
        while(resSet.next())
        {
            String username = resSet.getString("username");
            Long chat_id = resSet.getLong("chat_id");
            Utils.id_nickname.put(username, chat_id);
        }
    }

    public static void readBot() throws SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM 'bot_settings' ");
        resSet.next();
        String token = resSet.getString("token");
        long chat = resSet.getLong("chat");
        Telegram_bot_pengrad.bot = new TelegramBot(token);
        Telegram_bot_pengrad.chatId = chat;
    }

    public static void delete(String nick) throws SQLException{
        String sql_text = String.format("DELETE FROM server_users WHERE username = '%s';", nick);
        statmt.execute(sql_text);
    }

}
