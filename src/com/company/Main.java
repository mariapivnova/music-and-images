package com.company;

import java.io .*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final String IN_FILE_TXT = "C:\\Users\\User\\IdeaProjects\\music\\src\\inFile.txt";
    private static final String OUT_FILE_TXT = "C:\\Users\\User\\IdeaProjects\\music\\src\\outFile.txt";
    private static final String PATH_TO_MUSIC = "src\\com\\company\\music";

    private static final String IN_FILE_TXT2 = "C:\\Users\\User\\IdeaProjects\\music\\src\\inputImages.txt";

    private static final String OUT_FILE_TXT2 = "C:\\Users\\User\\IdeaProjects\\music\\src\\outImages.txt";

    private static final String PATH_TO_IMAGES = "src\\com\\company\\images";

    public static void main(String[] args) {
        String Url;
        try (BufferedReader inFile = new BufferedReader(new FileReader(IN_FILE_TXT));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(OUT_FILE_TXT))) {
            while ((Url = inFile.readLine()) != null) {
                URL url = new URL(Url);

                String result;
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    result = bufferedReader.lines().collect(Collectors.joining("\n"));
                }

                Pattern email_pattern = Pattern.compile("\\/\\/mp3uks.ru\\/mp3\\/files\\/[\\w./\\-]+");
                Matcher matcher = email_pattern.matcher(result);
                int i = 1;
                while (matcher.find() && i <= 5) {
                    outFile.write("https:" + matcher.group() + "\r\n");
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader musicFile = new BufferedReader(new FileReader(OUT_FILE_TXT))) {
            String music;
            int count = 1;
            try {
                while ((music = musicFile.readLine()) != null) {
                    downloadUsingNIO(music, PATH_TO_MUSIC + String.valueOf(count) + ".mp3");
                    count++;
                }
                System.out.println("Работает");
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        String Urle;
        try (BufferedReader inFile = new BufferedReader(new FileReader(IN_FILE_TXT2));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(OUT_FILE_TXT2))) {
            while ((Urle = inFile.readLine()) != null) {
                URL url = new URL(Urle);

                String result;
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    result = bufferedReader.lines().collect(Collectors.joining("\n"));
                }

                Pattern email_pattern = Pattern.compile("\\/\\/www.freestockimages.ru\\/media\\/632d61_[\\w./\\-\\$\\?\\_\\%\\~]+");
                Matcher matcher = email_pattern.matcher(result);
                int i = 1;
                while (matcher.find() && i <= 5) {
                    outFile.write("https:" + matcher.group() + "\r\n");
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader musicFile = new BufferedReader(new FileReader(OUT_FILE_TXT))) {
            String music;
            int count = 1;
            try {
                while ((music = musicFile.readLine()) != null) {
                    downloadUsingNIO(music, PATH_TO_IMAGES + String.valueOf(count) + ".jpg");
                    count++;
                }
                System.out.println("Работает");
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private static void downloadUsingNIO(String strUrl, String file) throws IOException {
        URL url = new URL(strUrl);
        ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
        FileOutputStream stream = new FileOutputStream(file);
        stream.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        stream.close();
        byteChannel.close();
    }
}