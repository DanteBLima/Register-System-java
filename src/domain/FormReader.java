package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class FormReader {
    private String filePath;
    //SE DER ERRO TENTA VOLTAR ESSA LINHAprivate HashMap<Integer, Runnable> formQuestions = new HashMap<>();
    private HashMap<Integer, Runnable> menu = new HashMap<>();

    public FormReader(){

    }

    public FormReader(User user, Scanner scanner){
        menu = new HashMap<>();
        menu.put(1,()->{
            System.out.println("Registering user");
            RegisterUser registerUser = new RegisterUser(user, scanner);
            registerUser.run(user);
        });
        menu.put(2, this::listUsers);

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
        Runnable reg = menu.get(choice);
        if(reg != null)
        {
            reg.run();
        }
        else System.out.println("Invalid choice.");

    }


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
    public void getFormQuestions(ArrayList<String> form){
        System.out.println("Choose an option: ");
        form.forEach(System.out::println);
    }

    public void listUsers(){
        File userDirectory = new File("users");

        if (userDirectory.exists() && userDirectory.isDirectory()){
            File[] users = userDirectory.listFiles(((dir, name) -> name.endsWith(".TXT")));

            if(users != null & users.length > 0){
                for (File user : users) {
                    String userName = user.getName().replaceAll(".TXT","");
                    System.out.println(userName);
                }
            }else {
                System.out.println("No registered users");
            }
        } else{
            System.out.println("Directory not found");
        }

    }

}
