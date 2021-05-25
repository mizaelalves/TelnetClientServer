package com.mycompany.telnetfinal;

// A Java program for a Server 
import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.util.*;

class Server {
    private Integer port;

    public Server() {

    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void RunServer() throws IOException {
        // Server server = new Server(5555);
        LocalTime dTime = LocalTime.now();
        ServerSocket server = new ServerSocket(this.getPort());// open socket
        File file = new File("./data/");// instance ./data on file
        file.mkdir();// create temporary dir
        // create log and password file in the data directory
        BufferedWriter pass = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/Passwords.txt", true)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/log.txt", true)));

        bw.newLine();
        bw.newLine();
        bw.append(dTime.getHour() + ":" + dTime.getMinute() + ":" + dTime.getSecond());
        bw.newLine();
        System.out.println("(host)> Servidor iniciado na porta: " + this.getPort() + " :(TIME)>> " + dTime.getHour()
                + ":" + dTime.getMinute() + ":" + dTime.getSecond());
        bw.append("(host)> Servidor iniciado na porta: " + this.getPort() + " :(TIME)>> " + dTime.getHour() + ":"
                + dTime.getMinute() + ":" + dTime.getSecond());
        bw.newLine();
        System.out.println("(host)> Esperando pelo cliente ...");
        bw.append("(host)> Esperando pelo cliente ...");
        bw.flush();

        try

        {
            while (true) {
                Socket Soc = server.accept();
                AcceptClient ac = new AcceptClient(Soc);
                ac.checkAccess();
            }

        } catch (IOException e) {
            e.printStackTrace();
            server.close();
            bw.close();
            pass.close();
        }
    }
}
// class accept client

class AcceptClient extends Thread {
    Socket ClientSocket;
    // --------------------------------------------------------------------------------------------------------
    private String Command = "";
    //private String Password = "";
    //private String LoginName = "";
    private String line = "";
    private String chat_r = "";
    private String chat_s = "";
    private String Permission = "Not_Allowed";
    private String login = "";
    private String LoginInfo = new String("");
    User add = new User();
    private LocalTime dTime = LocalTime.now();

    public AcceptClient(Socket Soc) throws IOException {

        ClientSocket = Soc;
        
        DataOutputStream out = new DataOutputStream(ClientSocket.getOutputStream());
        out.writeUTF("ok");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/log.txt", true)));

        try {
            System.out.println("(host)> Cliente aceito :(TIME)>> " + dTime.getHour() + ":" + dTime.getMinute() + ":"
                    + dTime.getSecond());
            bw.newLine();
            bw.append("(host)> Cliente aceito :(TIME)>> " + dTime.getHour() + ":" + dTime.getMinute() + ":"
                    + dTime.getSecond());
            bw.newLine();
            bw.flush();
            start();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        // --------------------------------------------------------------------------------------------------------
        DataOutputStream out = null;
        DataInputStream in = null;
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        BufferedReader input = null;
        User add = new User();
        // --------------------------------------------------------------------------------------------------------
        try {
            LocalTime dTime = LocalTime.now();
            // --------------------------------------------------------------------------------------------------------
            in = new DataInputStream(new BufferedInputStream(ClientSocket.getInputStream()));
            os = new FileOutputStream("./data/log.txt", true);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            out = new DataOutputStream(ClientSocket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(System.in));
            // --------------------------------------------------------------------------------------------------------
            // client
            try {
                while (true) {
                    line = in.readUTF();
                    bw.newLine();
                    // --------------------------------------------------------------------------------------------------------
                    if (line.equals("chatbox")) {
                        try {
                            System.out.println("(host)> Chatbox aberto");
                            bw.append("(host)> Chatbox aberto");
                            bw.flush();
                            while (true) {

                                chat_r = in.readUTF();
                                System.out.println(">>>cliente:	" + chat_r);
                                bw.append(">>>cliente:	" + chat_r);
                                bw.newLine();
                                if (chat_r.equals("over"))
                                    break;
                                System.out.print(">>>admin:	");
                                bw.append(">>>admin:	");
                                bw.newLine();
                                chat_s = input.readLine();
                                out.writeUTF(chat_s);
                                bw.flush();
                            }
                        } catch (IOException i) {
                            System.out.println(i);
                            bw.append("" + i);
                            bw.flush();
                        }
                        System.out.println("saindo: Chatbox Fechado :(TIME)>> " + dTime.getHour() + ":"
                                + dTime.getMinute() + ":" + dTime.getSecond());
                        bw.append("saindo: Chatbox Fechado :(TIME)>> " + dTime.getHour() + ":" + dTime.getMinute() + ":"
                                + dTime.getSecond() + "\n");
                        bw.flush();
                    }
                    // --------------------------------------------------------------------------------------------------------
                    else if (line.equals("telnet")) {

                        login = in.readUTF();
                        while (login.equals("0")) {
                            System.out.println("Iniciando cadastro");
                            bw.append("Iniciando cadastro");
                            bw.newLine();
                            System.out.println("Esperando login");
                            add.setLoginName(in.readUTF());
                            System.out.println("Esperando senha");
                            bw.append("Esperando senha");
                            bw.newLine();
                            add.setPassword(in.readUTF());

                            add.CadUser(add.getLoginName(), add.getPassword());

                            bw.append("Usuario: " + add.getLoginName());
                            bw.newLine();
                            bw.append("Client: " + add.getPassword());
                            bw.newLine();
                            bw.flush();
                            break;
                        }
                        // --------------------------------------------------------------------------------------------------------
                        while (login.equals("1")) {

                            add.setLoginName(in.readUTF());
                            add.setPassword(in.readUTF());
                            System.out.println(add.getLoginName());
                            BufferedReader brFin = new BufferedReader(new FileReader("./data/Passwords.txt"));

                            while ((LoginInfo = (brFin).readLine()) != null) {

                                StringTokenizer st = new StringTokenizer(LoginInfo);
                                if (add.getLoginName().equals(st.nextToken()) && add.getPassword().equals(st.nextToken())) {
                                    System.out.println("iniciado");
                                    bw.append("iniciado");
                                    // out.writeUTF("ALLOWED");
                                    Permission = "ALLOWED";
                                    System.out.println(Permission);
                                    bw.append(Permission + "\n");
                                    out.writeUTF(Permission);
                                    bw.flush();
                                    // break OUTER;
                                }

                            }
                            // --------------------------------------------------------------------------------------------------------
                            if (Permission == "ALLOWED") {
                                System.out.println("(host)> conectado a partir de um host remoto :" + add.getLoginName());
                                bw.append("(host)> conectado a partir de um host remoto :" + add.getLoginName());

                                bw.flush();
                                while (true) {
                                    try {
                                        Process process = null;
                                        Command = in.readUTF();
                                        if (Command.equals("quit")) {
                                            System.out.println(add.getLoginName() + " desconectado!");
                                            bw.append(add.getLoginName() + " desconectado!");
                                            bw.newLine();
                                            bw.flush();
                                            login = "0";
                                            break;
                                        }

                                        else if (!Command.equals("quit")) {

                                            String OS = System.getProperty("os.name");
                                            if (OS.contains("Linux")) {
                                                ProcessBuilder processBuilder = new ProcessBuilder();
                                                processBuilder.command("bash", "-c", Command);
                                                process = processBuilder.start();
                                                printResults(process);
                                                System.out.println(process);
                                                bw.append("" + process);
                                                bw.newLine();
                                                bw.flush();
                                            } else {
                                                ProcessBuilder processBuilder = new ProcessBuilder();
                                                processBuilder.command("cmd.exe", "/c", Command);

                                                process = processBuilder.start();
                                                printResults(process);
                                                System.out.println(process);
                                                bw.append("" + process);
                                                bw.newLine();
                                                bw.flush();
                                                
                                            }
                                        }

                                        else

                                            out.writeUTF("Comando inválido digite help");
                                        bw.append("Comando inválido digite help");
                                        bw.newLine();
                                        Command = Command + " comando recebido!";
                                        System.out.println(Command);
                                        bw.append(Command);
                                        bw.newLine();
                                        bw.flush();
                                    } catch (IOException i) {
                                        System.out.println(i);
                                        bw.append("" + i);
                                        bw.newLine();
                                        bw.flush();
                                        break;
                                    }
                                }
                            } else
                                out.writeUTF(Permission);
                            login = in.readUTF();
                            brFin.close();
                        }

                    }
                    // --------------------------------------------------------------------------------------------------------
                    else if (line.equals("quit")) {
                        System.out.println("saindo");
                        bw.append("saindo");
                        bw.newLine();
                        bw.flush();
                        break;
                    }
                }

            } catch (IOException i) {
                System.out.println(i);
                bw.append("" + i);
                bw.newLine();
            }

            // try {
            input.close();
            out.close();
            ClientSocket.close();
            in.close();
            System.out.println("Conexão fechada em :(TIME):	" + dTime.getHour() + ":" + dTime.getMinute() + ":"
                    + dTime.getSecond());
            bw.append("Conexão fechada em :(TIME):	" + dTime.getHour() + ":" + dTime.getMinute() + ":"
                    + dTime.getSecond());
            bw.newLine();
            bw.flush();
        } catch (IOException i) {
            System.out.println(i);
            ;
        }

    }

    // --------------------------------------------------------------------------------------------------------
    /*
     * public void cadUser(String username, String password) throws IOException {
     * OutputStream os = new FileOutputStream("Passwords.txt", true);
     * OutputStreamWriter osw = new OutputStreamWriter(os); BufferedWriter bw = new
     * BufferedWriter(osw); try {
     * 
     * String user = username; String pass = password;
     * 
     * bw.append(user + " " + pass); bw.newLine(); bw.flush();
     * 
     * } catch (IOException i) { bw.close(); } }
     */
    public void printResults(Process process) throws IOException, FileNotFoundException {
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            DataOutputStream dout = new DataOutputStream(ClientSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/log.txt", true)));
            String line = "";
            String result = "";
            while ((line = reader.readLine()) != null) {
                result = result + "\r\n" + line;
                System.out.println(result);
            }
            bw.newLine();
            bw.append(result);
            bw.flush();
            dout.writeUTF(result);
            bw.close();
        }

        // --------------------------------------------------------------------------------------------------------

    }
}
