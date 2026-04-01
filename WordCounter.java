import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class WordCounter {
    public static int processText(StringBuffer text, String stopword)
            throws InvalidStopwordException, TooSmallText {

        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher matcher = regex.matcher(text);

        int count = 0;
        boolean foundStopword = false;

        while (matcher.find()) {
            String word = matcher.group();
            count++;

            if (stopword != null && word.equals(stopword)) {
                foundStopword = true;
            }
        }

        if (count < 5) {
            throw new TooSmallText(count);
        }

        if (stopword != null && !foundStopword) {
            throw new InvalidStopwordException(stopword);
        }

        return count;
    }

    public static StringBuffer processFile(String path)
            throws EmptyFileException {

        Scanner input = new Scanner(System.in);
        File file = new File(path);

        while (!file.exists()) {
            System.out.println("File not found. Enter again:");
            path = input.nextLine();
            file = new File(path);
        }

        StringBuffer content = new StringBuffer();

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                content.append(fileScanner.nextLine());
                if (fileScanner.hasNextLine()) {
                    content.append(" ");
                }
            }
        }
        } catch (Exception e) {}

        if (content.length() == 0) {
            throw new EmptyFileException(path);
        }

        return content;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String option = "";
      
        while (true) {
            option = input.nextLine();
            if (option.equals("1") || option.equals("2")) {
                break;
            }
        }

        String stopword = (args.length > 1) ? args[1] : null;
        StringBuffer text = new StringBuffer();

        if (option.equals("1")) {
            String path = (args.length > 0) ? args[0] : "";

            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println(e);
                text = new StringBuffer("");
            }

        } else {
            if (args.length > 0) {
                text = new StringBuffer(args[0]);
            } else {
                text = new StringBuffer(input.nextLine());
            }
        }

        try {
            int count = processText(text, stopword);
            System.out.println("Found " + count + " words.");

        } catch (InvalidStopwordException e) {
            System.out.println(e);

            String newStop = input.nextLine();

            try {
                int count = processText(text, newStop);
                System.out.println("Found " + count + " words.");
            } catch (Exception ex) {
                System.out.println("Stopword still not found.");
            }

        } catch (TooSmallText e) {
            System.out.println(e);
        }
    }
}
    
