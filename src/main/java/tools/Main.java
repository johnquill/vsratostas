package tools;

import entity.Caption;
import org.hibernate.Session;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import stasik.StasikBot;

import java.util.Map;

public class Main {
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new StasikBot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
