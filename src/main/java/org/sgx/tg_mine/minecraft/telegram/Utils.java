package org.sgx.tg_mine.minecraft.telegram;

import java.util.*;
import com.pengrad.telegrambot.model.User;

public class Utils {

    public static HashMap<Long, String> id_nickname = new HashMap<>();

    public static LinkedList<String> codes = new LinkedList<>();

    public static String random() {
        Random random = new Random();
        String number = String.valueOf(random.nextInt(100000));
        while (codes.contains(number)) {
            number = String.valueOf(random.nextInt(100000));
        }
        return number;
    }

    public static boolean contains(String[] list, String word){
        for(String str: list){
            if(str.equals(word)){return true;}
        }
        return false;
    }

    public static Long get_id(String code){
        Set<Long> ids = id_nickname.keySet();
        for (long id: ids){
            String value = id_nickname.get(id);
            if (code.equals(value)) {return id;}
        }
        return null;
    }

    public static String tg_nick(User user){
        StringBuilder tg_nick = new StringBuilder();
        tg_nick.append(user.firstName());
        if (user.lastName() != null){
            String lastname = " " + user.lastName();
            tg_nick.append(lastname);
        }
        return tg_nick.toString();
    }
    }






