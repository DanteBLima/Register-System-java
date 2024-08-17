import domain.FormReader;
import domain.RegisterUser;
import domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {

            FormReader menuReader = new FormReader("menu principal.txt");
            ArrayList<String> form = menuReader.readForm();
            menuReader.getFormQuestions(form);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scanner scanner = new Scanner(System.in);
        User user = new User();
        FormReader mainMenu = new FormReader(user, scanner);
        mainMenu.run(user);
        
    }
}
