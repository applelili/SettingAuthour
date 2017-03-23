package com.gfd.xml;

/**
 * 类描述：
 * 作者：郭富东
 * 创建日期： 2017/3/23 0023 上午 9:33
 * 更新日期：
 */
public class Person {

    private int age;
    private int id;
    private String name;
    private String sex;

    public Person(int age, int id, String name, String sex) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Person() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
