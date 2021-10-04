package fr.extasio.main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    public static ArrayList<String> mails = new ArrayList<String>();
    public static ArrayList<File> fileList = new ArrayList<File>();
    public static final String regex = "^(.+)@(.+)$";
    public static int nb = 0;
    public static void main(String[] args) throws IOException {
        String path = args[0];
        Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .forEach(path1 -> fileList.add(new File(String.valueOf(path1))));
        File f = new File(path);
        if(f.isFile()){
            System.out.println("File");
            extractMail(path);
        } else if(f.isDirectory()){
            System.out.println("Folder");
            for (File file:
            fileList) {
                System.out.println(file);
                extractMail(file.getAbsolutePath());
            }
        }
        System.out.println(nb);
    }

    private static void extractMail(String path) throws IOException {
        Pattern p = Pattern.compile(
                "[a-zA-Z0-9]"
                        + "[a-zA-Z0-9_.]"
                        + "*@[a-zA-Z0-9]"
                        + "+([.][a-zA-Z]+)+");

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        while(line != null){
            Matcher m = p.matcher(line);
            while(m.find()) {
                String tmp = m.group();
                checkAdd(tmp);
            }
            line = br.readLine();
        }
    }

    /**
     * If not contain on mailist just pass
     */
    private static void checkAdd(String mail) throws IOException {
        if(!mails.contains(mail)){
            mails.add(mail);
            nb++;
            FileWriter writer = new FileWriter("output.txt",true);
            writer.write(mail + System.lineSeparator());
            writer.close();
        } else {
            //System.out.println(mail);
        }
    }
}
