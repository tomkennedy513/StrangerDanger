package edu.villanova.tomkennedy.strangerdanger;
/**
 * Created by tom on 12/7/2015.
 */
public class Contact {
    private int id;
    private String name;
    private String number;

    public Contact(){};

    public Contact(String name, String number){
        super();
        this.name = name;
        this.number = number;
    }
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", number=" + number
                + "]";
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public int getID(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
