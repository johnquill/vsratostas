package stasik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Captions {

    static Properties properties = new Properties();

    public Captions(String fileName) throws IOException {
        properties.load(new FileReader(fileName));
    }

    public String getRandomCaption() {
        int n = (int) (Math.random() * properties.size());
        return (String) properties.get(String.valueOf(n));
    }

    public void addCaption(String caption) throws IOException {
        properties.setProperty(String.valueOf(properties.size()), caption);
        try {
            properties.store(new FileWriter("src/main/resources/captions.properties"), null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
