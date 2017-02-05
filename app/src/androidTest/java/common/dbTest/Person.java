package common.dbTest;

/**
 * 描述:
 * Created by mjd on 2017/1/7.
 */

public class Person {

//    @Column(name = "_id", primaryKey = true)
//    private int id;

    private String name;
    private int age;
    private boolean isGirl;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, boolean isGirl) {
        this.name = name;
        this.age = age;
        this.isGirl = isGirl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGirl() {
        return isGirl;
    }

    public void setGirl(boolean girl) {
        isGirl = girl;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isGirl=" + isGirl +
                '}';
    }
}
