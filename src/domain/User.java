package domain;

import java.util.Scanner;

public class User {
    private String name;
    private String email;
    private int age;
    private float height;

    public int getChoice(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
