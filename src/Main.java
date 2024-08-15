import domain.FormReader;
import domain.RegisterUser;
import domain.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {

            FormReader formReader = new FormReader("formulario.txt");
            ArrayList<String> form = formReader.readForm();
            formReader.getFormQuestions(form);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        User user = new User();
        RegisterUser registerUser = new RegisterUser(user, scanner);
        registerUser.executeAction(user.getChoice());
        user.userInfo();
    }
}
