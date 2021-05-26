package com.mycompany.telnetfinal;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        Server server = new Server();
        Client client = new Client();
        
        while (true) {
            try {
                System.out.println("*-*-*-*-*Telnet Client/Server*-*-*-*-*");
                System.out.println("*-*-*-*-*    1 - Server      *-*-*-*-*");
                System.out.println("*-*-*-*-*    2 - Client      *-*-*-*-*");
                System.out.println("*-*-*-*-*    3 - Quit        *-*-*-*-*");
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
                int op = (sc.nextInt());
                if (op == 1) {
                    System.out.println("\n*-*-*-*-*-*-*Iniciando servidor*-*-*-*-*-*-*");
                    System.out.println("*-*-*-*-*Digite a porta do servidor*-*-*-*-*");
                    server.setPort(sc.nextInt());
                    server.RunServer();
                    break;
                } else if (op == 2) {
                    System.out.println("\n*-*-*-*-*-*-*-*Iniciando Cliente*-*-*-*-*-*-*-*");
                    System.out.println("*-*-*-*-*Digite o endereço do servidor*-*-*-*-*");
                    System.out.print(">>>");
                    client.setEnd(sc.next());
                    System.out.println("*-*-*-*-*Digite a porta do servidor*-*-*-*-*-*-");
                    System.out.print(">>>");
                    client.setPort(sc.nextInt());
                    client.RunClient();
                    
                    break;
                } else if(op == 3){

                    System.out.println("Fechando telnet...");
                    sc.close();
                    break;

                }else{
                    System.out.println("Comando não reconhecido");
                    System.out.println("Fechando telnet...");
                    sc.close();
                    break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Endereço ou porta errada!");
                System.out.println("Tente Novamente");

                break;
            }
        }
    }
}
