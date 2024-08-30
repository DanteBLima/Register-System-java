package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class RegisterUser {
    private HashMap<Integer, Runnable> register;
    private User user;
    private Scanner scanner;


    public RegisterUser(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
        register = new HashMap<>();
        AtomicReference<File> userInfo = new AtomicReference<>();

        File directory = new File("users");
        if(!directory.exists()){
            directory.mkdir();
        }

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
            if(name.length() < 10){
                do{
                    System.out.println("Error, name must have at least 10 characters.");
                    name = scanner.nextLine();
                }while (name.length() < 10);
            }
            user.setName(name);
            String fileName = "users/" + getNextUserNumber(directory) + "-" + user.getName().replaceAll("\\s+", "").toUpperCase();
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

        });

        register.put(2, () -> {
            System.out.println("Email: ");
            String email = scanner.nextLine();
            String regexPattern = "^(.+)@(\\S+)$";
            if(!Pattern.compile(regexPattern).matcher(email).matches()){
                do {
                    System.out.println("Error, email address must contain '@'");
                    System.out.println("Email:");
                    email = scanner.nextLine();
                }while(!Pattern.compile(regexPattern).matcher(email).matches());
            }
            File users = new File("users");
            if(users.exists() && users.isDirectory()){
                File[] archives = users.listFiles();

                if(archives != null){
                    for (File archive : archives) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(archive));
                            String line;
                            while ((line = br.readLine()) != null){
                                if(line.startsWith("Email: ") && line.substring(7).equalsIgnoreCase(email.trim())){
                                    System.out.println("Error, this email already exists");
                                    return;
                                }
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }

            user.setEmail(email);
            writeToFile("Email: " + user.getEmail(),userInfo);
        });

        register.put(3, () -> {
            System.out.println("Age: ");
            int age = scanner.nextInt();
            if(age <= 18){
                do {
                    System.out.println("You must be above 18 to register");
                    System.out.println("Age:");
                    age = scanner.nextInt();
                }while (age <= 18);
            }
            user.setAge(age);
            scanner.nextLine();
            writeToFile("Age: " + user.getAge(),userInfo);
        });

        register.put(4, () -> {

            float height = 0;
            boolean check = false;
            while(!check){
                System.out.println("Height: ");
                String h = scanner.nextLine();
                if (h.matches("\\d+,\\d+")){
                    h = h.replace(",",".");
                    height = Float.parseFloat(h);
                    check = true;
                }else{
                    System.out.println("Error, use a ',' instead of a '.'");
                }

            }
            user.setHeight(height);
            writeToFile("Height: " + user.getHeight(),userInfo);
        });

        register.put(0, () -> {

            System.out.println("Exitting program");
            System.exit(0);
        });
    }

    private int getNextUserNumber(File directory) {
        int userCount = directory.list().length;
        return userCount + 1;
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
