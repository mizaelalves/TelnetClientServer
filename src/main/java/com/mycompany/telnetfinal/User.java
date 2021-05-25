package com.mycompany.telnetfinal;

import java.io.*;

public class User{

    private String LoginName;
    private String Password;

    public String getLoginName(){
        return LoginName;
    }
    public void setLoginName(String LoginName){
        this.LoginName = LoginName;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }

    public void CadUser(String LoginName, String Password) throws FileNotFoundException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/Passwords.txt", true)));
        try {
            bw.append(LoginName+ " " + Password);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        
    }
}
