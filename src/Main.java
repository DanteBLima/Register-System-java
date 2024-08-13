import domain.FormReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            FormReader formReader = new FormReader("formulario.txt");
            ArrayList<String> form = formReader.readForm();
            form.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
