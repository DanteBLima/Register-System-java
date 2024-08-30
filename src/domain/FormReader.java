package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        menu.put(4, this::deleteQuestion);
        menu.put(5, this::searchUser);
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

    public void deleteQuestion() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What question would you like to delete?");
        int question = sc.nextInt();

        if (question < 5) {
            System.out.println("Invalid choice, can only delete question 5 and above.");
            return;
        }

        List<String> menu = new ArrayList<>();
        String filepath = "menu principal.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                menu.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        if (question > 0 && question <= menu.size()) {
            menu.remove(question - 1);
        } else {
            System.out.println("Invalid choice, something went wrong.");
            return;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String s : menu) {
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }

        System.out.println("Question successfully deleted!");
    }

    public void searchUser(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Which user would you like to search?");
        String user = sc.nextLine();
        File users = new File("users");
        List<String> availableUsers = new ArrayList<>();

        if(users.exists() && users.isDirectory()){
            File[] archives = users.listFiles();

            if (archives != null){
                for (File archive : archives) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(archive));
                        String line;
                        while ((line = br.readLine()) != null){
                            if(line.startsWith("Name: ")){
                                String fullName = line.substring(6).trim();
                                String firstName = fullName.split(" ")[0];
                                if(firstName.toLowerCase().contains(user.toLowerCase())) {
                                    availableUsers.add(fullName);
                                }

                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Error reading file: "+archive.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }else {
            System.out.println("Directory users not found");
        }

        if(!availableUsers.isEmpty()){
            System.out.println("Cadastrados: "+ String.join(", ",availableUsers));
        }else System.out.println("No users '"+user + "' found");
    }


}
