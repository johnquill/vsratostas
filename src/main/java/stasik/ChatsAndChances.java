package stasik;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ChatsAndChances {

    Properties properties = new Properties();

    public ChatsAndChances(File file) throws IOException {
        properties.load(new FileReader(file));
    }
}
