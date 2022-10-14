package stasik;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ChatsAndChances {

    static Properties properties = new Properties();

    public ChatsAndChances(String file) throws IOException {
        properties.load(new FileReader(file));
    }

    public static void saveChances() {
        try {
            properties.store(new FileWriter("src/main/resources/chatChances.properties"), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addChances(Long chatId) {
        editChances(chatId, 10);
    }

    public static void editChances(Long chatId, int chances) {
        properties.setProperty(chatId.toString(), String.valueOf(chances));
    }

    public static int getChance(Long chatId) {
        String chance = (String) properties.get(chatId.toString());
        if (Objects.isNull(chance)) {
            editChances(chatId, 10);
            return 10;
        }
        return Integer.parseInt(chance);
    }
}
