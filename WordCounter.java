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
                break;
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
                content.append(fileScanner.nextLine()).append(" ");
            }
        } catch (Exception e) {}

        // empty file check
        if (content.length() == 0) {
            throw new EmptyFileException(path);
        }

        return content;
    }

    
