package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class RegisterUser {
    private HashMap<Integer, Runnable> register;
    private User user;
    private Scanner scanner;
    private static int sequence = 1;

    public RegisterUser(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
        register = new HashMap<>();
        AtomicReference<File> userInfo = new AtomicReference<>();

        try {

            FormReader formReader = new FormReader("formulario.txt");
            ArrayList<String> form = formReader.readForm();
            formReader.getFormQuestions(form);
        } catch (IOException e) {
            e.printStackTrace();
        }


        register.put(1, () -> {
            System.out.println("Name: ");
            String name = scanner.nextLine();
            user.setName(name);
            String fileName = sequence + "- " + name.replaceAll("\\s+", "").toUpperCase();
            userInfo.set(new File(fileName + ".TXT"));

            try {
                if (userInfo.get().createNewFile()) {
                    System.out.println("File created: " + userInfo.get().getName());
                } else {
                    System.out.println("File already exists. Overwriting it");
                }
                writeToFile("Name: " + user.getName(),userInfo);
            } catch (IOException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
            sequence++;
        });

        register.put(2, () -> {
            System.out.println("Email: ");
            user.setEmail(scanner.nextLine());
            writeToFile("Email: " + user.getEmail(),userInfo);
        });

        register.put(3, () -> {
            System.out.println("Age: ");
            user.setAge(scanner.nextInt());
            scanner.nextLine();
            writeToFile("Age: " + user.getAge(),userInfo);
        });

        register.put(4, () -> {
            System.out.println("Height: ");
            user.setHeight(scanner.nextFloat());
            scanner.nextLine();
            writeToFile("Height: " + user.getHeight(),userInfo);
        });

        register.put(0, () -> {
            System.out.println("Exitting program");
            System.exit(0);
        });
    }

    private void writeToFile(String data, AtomicReference<File> userInfo) {
        if (userInfo.get() == null) {
            System.out.println("No user file created yet. Please set the name first.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userInfo.get(), true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }


    public void run(User user) {
        int choice;
        do {
            choice = user.getChoice();
            executeAction(choice);
            System.out.println("Choose an option: ");
        } while (choice != 0);
    }

    public void executeAction(int choice) {
        Runnable action = register.get(choice);
        if (action != null) {
            action.run();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
