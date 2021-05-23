package com.mycompany.telnetfinal;

import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        
        Server server = new Server();
        Client client = new Client();
        System.out.println("*-*-*-*-*Telnet Client/Server*-*-*-*-*");
        System.out.println("*-*-*-*-*    1 - Server      *-*-*-*-*");
        System.out.println("*-*-*-*-*    2 - Client      *-*-*-*-*");
        System.out.println("*-*-*-*-*    3 - Quit        *-*-*-*-*");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        int op = (sc.nextInt());
        if (op == 1){
            System.out.println("*-*-*-*-*-*-*Iniciando servidor*-*-*-*-*-*-*");
            System.out.println("*-*-*-*-*Digite a porta do servidor*-*-*-*-*");
            server.setPort(sc.nextInt());
            server.RunServer();
        }else if (op == 2){
            System.out.println("*-*-*-*-*-*-*-*Iniciando Cliente*-*-*-*-*-*-*-*");
            System.out.println("*-*-*-*-*Digite o endereço do servidor*-*-*-*-*");
            client.setEnd(sc.next());
            System.out.println("*-*-*-*-*Digite a porta do servidor*-*-*-*-*-*-");
            client.setPort(sc.nextInt());
            client.RunClient();
        }else{
            try {
                System.out.println("Fechando telnet...");
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        

        
    }
}
