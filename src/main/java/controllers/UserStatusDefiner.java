package controllers;

import org.json.JSONObject;
import stasik.StasikBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UserStatusDefiner {

    public static final String URL = "https://api.telegram.org/bot" + StasikBot.BOT_TOKEN +
            "/getchatmember?chat_id=%s&user_id=%s";
    public static boolean isAdmin(Long id, Long chatId) {
        String res;
        try {
            URL url = new URL(String.format(URL, chatId.toString(), id.toString()));
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            res = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(res);
        JSONObject result = jsonObject.getJSONObject("result");

        String status = result.getString("status");

        return status.equals("administrator")||status.equals("creator");
    }
}
