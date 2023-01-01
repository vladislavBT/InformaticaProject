package com.example.demo.entities;

import com.example.demo.Exceptions.IncorrectException;
import com.example.demo.entities.enums.Gender;

public class UserProperties {
    private int id;

    private int age;

    private int height;

    private int weight;

    private Gender gender;

    public UserProperties(int id, int age, int height, int weight, Gender gender) {
        setId(id);
        setAge(age);
        setHeight(height);
        setWeight(weight);
        setGender(gender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age<15 || age>100){
            throw new IncorrectException("Invalid age");
        }else {
            this.age = age;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if(height>250 || height<130) {
            throw new IncorrectException("Invalid height");
        }else {
            this.height = height;
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if(weight>500 || weight<20) {
            throw new IncorrectException("Invalid weight");
        }else {
            this.weight = weight;
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if(gender==null){
            throw new IncorrectException("Invalid gender");
        }
        this.gender = gender;
    }
}
