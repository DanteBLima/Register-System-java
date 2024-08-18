package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class FormReader {
    private String filePath;
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
        menu.put(3, this::addQuestion);

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

    public void addQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What question would you like to add: ");

        String newQuestion = scanner.nextLine();

        try {
            int currentQuestion = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("menu principal.txt"))) {
                while (br.readLine() != null) {
                    currentQuestion++;
                }
            }
            currentQuestion++;

            String formattedQuestion = currentQuestion + " - " + newQuestion;

            try (FileWriter fw = new FileWriter("menu principal.txt", true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.newLine();
                bw.write(formattedQuestion);
                bw.newLine();
                System.out.println("Question sucessfully added!");

            }

        } catch (IOException e) {
            System.out.println("Error trying to add your question: " + e.getMessage());
        }
    }



}
