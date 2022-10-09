package controllers;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static stasik.StasikBot.BOT_TOKEN;

public class FileDownloader {

    private static final String GET_FILE_URL = "https://api.telegram.org/bot" +
            BOT_TOKEN + "/getFile?file_id=";
    private static final String DOWNLOAD_FILE_URL = "https://api.telegram.org/file/bot" +
            BOT_TOKEN + "/";

    public static File downloadById(String fileId) throws IOException {
        String path = getPath(fileId);
        String fileName = downloadFileByPath(path);
        return new File(fileName);
    }

    private static String getPath(String fileId) {
        String res;
        try {
            URL url = new URL(GET_FILE_URL + fileId);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            res = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject result = new JSONObject(res);
        JSONObject path = result.getJSONObject("result");
        return path.getString("file_path");
    }

    private static String downloadFileByPath(String path) throws IOException {
        URL url = new URL(DOWNLOAD_FILE_URL + path);
        String[] dir = new File("src/main/resources/images").list();
        int n = dir == null ? 0 : dir.length;
        String fileName = "src/main/resources/images/image" + n + ".jpg";
        FileOutputStream fos = new FileOutputStream(fileName);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        rbc.close();
        fos.close();
        return fileName;
    }
}
