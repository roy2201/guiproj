package Models;

public class Employee {

    private String username;
    private String password;

    public Employee(){

    }

    public Employee(String username,String password){
        this.username = username;
        this.password = password;
    }

    public  String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return  password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "The name is: " + username + "\nThe pass is: " + password;
    }
}
