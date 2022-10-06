package stasik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Phrases {

    static Properties properties = new Properties();

    public Phrases(String fileName) throws IOException {
        properties.load(new FileReader(fileName));
    }

    public String getRandomPhrase() {
        int n = (int) (Math.random() * properties.size());
        return (String) properties.get(String.valueOf(n));
    }

    public void addPhrase(String phrase) throws IOException {
        properties.setProperty(String.valueOf(properties.size()), phrase);
        try {
            properties.store(new FileWriter("src/main/resources/phrases.properties"), null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
