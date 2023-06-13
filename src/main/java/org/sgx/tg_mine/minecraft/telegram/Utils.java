package org.sgx.tg_mine.minecraft.telegram;


import java.util.HashMap;
import java.util.Random;
import java.util.LinkedList;
import java.util.Set;

public class Utils {


    public static HashMap<String, Long> id_nickname = new HashMap<>();

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

    public static String get_nick(long id) {
        Set<String> nicknames = id_nickname.keySet();
        for (String nick : nicknames) {
            Long id_from_map = id_nickname.get(nick);
            if (id_from_map.equals(id)) {return nick;}
        }
        return null;
    }

}




