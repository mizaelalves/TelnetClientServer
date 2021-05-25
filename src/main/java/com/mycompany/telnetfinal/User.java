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
        OutputStream os = new FileOutputStream("./data/Passwords.txt", true);
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        try {

            String user = LoginName;
            String pass = Password;

            bw.append(user + " " + pass);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        
    }
}
