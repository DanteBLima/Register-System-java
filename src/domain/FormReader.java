package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class FormReader {
    private String filePath;
    private HashMap<Integer, Runnable> formQuestions = new HashMap<>();

    public FormReader(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> readForm() throws IOException{
        ArrayList<String> form = new ArrayList<>();
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine();

            while (line != null) {
                form.add(line);
                line = br.readLine();
            }
        }
        return form;
    }

    public HashMap<Integer, Runnable> getFormQuestions() {
        return formQuestions;
    }
}
