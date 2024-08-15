package domain;

import java.util.HashMap;
import java.util.Scanner;


public class RegisterUser {
    private HashMap<Integer, Runnable> register;

    public RegisterUser(User user, Scanner scanner){
        register = new HashMap<>();
        register.put(1,()->{
            System.out.println("Name: ");
            user.setName(scanner.nextLine());
        });

        register.put(2,()->{
            System.out.println("Email: ");
            user.setEmail(scanner.nextLine());
        });

        register.put(3,()->{
            System.out.println("Age: ");
            user.setAge(scanner.nextInt());
        });

        register.put(4,()->{
            System.out.println("Height: ");
            user.setHeight(scanner.nextFloat());
        });
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
