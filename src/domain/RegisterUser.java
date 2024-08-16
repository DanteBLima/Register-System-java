package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class RegisterUser {
    private HashMap<Integer, Runnable> register;

    public RegisterUser(User user, Scanner scanner){
        register = new HashMap<>();
        File userInfo = null;
        try {

            FormReader formReader = new FormReader("formulario.txt");
            ArrayList<String> form = formReader.readForm();
            formReader.getFormQuestions(form);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File userInfo = new File(user.getName());
        register.put(1,()->{
            System.out.println("Name: ");
            user.setName(scanner.nextLine());
            userInfo = new File(user.getName() + ".txt");

            try {

                if (userInfo.createNewFile()) {
                    System.out.println("File created: " + userInfo.getName());
                } else {
                    System.out.println("File already exists. Overwriting it");
                }
//                FileWriter writer = new FileWriter(userInfo);
//                writer.write(user.getName());
//                writer.close();

            }
            catch (IOException e){
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        });

        register.put(2,()->{
            System.out.println("Email: ");
            user.setEmail(scanner.nextLine());
            try {
                FileWriter writer = new FileWriter(user.getName());
                BufferedWriter writer2 = new BufferedWriter(writer);
                writer2.write(user.getEmail());
                writer2.close();
            }catch (IOException e){
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        });

        register.put(3,()->{
            System.out.println("Age: ");
            user.setAge(scanner.nextInt());
            try {
                FileWriter writer = new FileWriter(userInfo);
                writer.write(user.getAge());
                writer.close();
            }catch (IOException e){
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        });

        register.put(4,()->{
            System.out.println("Height: ");
            user.setHeight(scanner.nextFloat());
            try {
                FileWriter writer = new FileWriter(userInfo);
                writer.write((int)user.getHeight());
                writer.close();
            }catch (IOException e){
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        });
        register.put(0,()->{
            System.out.println("Exitting program");
            System.exit(0);
        });
    }

    public void run(User user){
        int choice;
        do {
            choice = user.getChoice();
            executeAction(choice);
            System.out.println("Choose an option: ");

        } while (choice != 0);

    }


    public void executeAction(int choice){
        Runnable reg = register.get(choice);
        if(reg != null)
        {
            reg.run();
        }
        else System.out.println("Invalid choice.");

    }

}
