package com.mycompany.telnetfinal;

import java.net.*;
import java.io.*;
import java.time.LocalTime;

class Client {
	private int port;
	private String end;

	public Client(){
		
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	

	public void RunClient() throws IOException{
		User add = new User();
		System.out.println("Esperando comunicação com o servidor");
		Socket soc = new Socket(end, port);	

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream out = new DataOutputStream(soc.getOutputStream()); // sends output to the socket
		DataInputStream in = new DataInputStream(new BufferedInputStream(soc.getInputStream())); // takes input from
		LocalTime dTime = LocalTime.now();
		DataInputStream result = null;

		System.out.println("Requisitando conexão com o servidor "+ this.getEnd()+":"+this.getPort()+" :(TIME)>>" + dTime.getHour() + ":" + dTime.getMinute());

		try {
			String check = "";
			check = in.readUTF();
			if (check.equals("ok")) {
				System.out.println("Conectado ao servidor :(TIME)>>" + dTime.getHour() + ":" + dTime.getMinute());

				String Command = "";
				String line = "";
				String chat_r = "";
				String chat_s = "";
				System.out.println("Bem-vindo");
				try {

					OUTER: while (true) {
						System.out.println("use o comando \'help\' para mostrar possiveis comandos");
						try {
							System.out.print(">>>");
							line = input.readLine();
							out.writeUTF(line);
						} catch (IOException i) {
							System.out.println(i);
						}
						// ----------------------------------------------------------------------------------------------
						switch (line) {
							case "help":
								System.out.println("Help");
								System.out.println("chatbox	-	Chatbox");
								System.out.println("telnet	-	Telnet");
								System.out.println("quit	- 	fechar a conexão ");
								break;
							case "chatbox":
								System.out.println("Bem-vindo ao chat");
								System.out.println("Para sair digite over");
								while (true) {
									try {
										System.out.print(">>>client:	");
										chat_s = input.readLine();
										out.writeUTF(chat_s);
										if (chat_s.equals("over"))
											break;
										chat_r = in.readUTF();
										System.out.println(">>>admin:	" + chat_r);
									} catch (IOException i) {
										System.out.println(i);
									}
								}
								System.out.println("Chatbox Fechado");
								break;
							case "telnet":
								try {
									System.out.print("Já está cadastrado?\nSim(1)/Não(0)");
									System.out.print(">>>");
									line = input.readLine();
									out.writeUTF(line);
									Console console = System.console();
									switch (line) {
										case "0":
											try {
												add.setLoginName(null);
												add.setPassword(null);
												System.out.println("Cadastro:");
												System.out.print(">>>Login Name :	");
												add.setLoginName(input.readLine());
												out.writeUTF(add.getLoginName());
												System.out.print(">>>Password :	");
												add.setPassword(input.readLine());
												out.writeUTF(add.getPassword());
												System.out.println("Usuário cadastrado!");
												break;
											} catch (Exception e) {
												System.out.println("Não cadastrado!");
											}

										case "1":
											String login = "";
											do {
												add.setLoginName(null);
												add.setPassword(null);
												System.out.println("Bem-vindo ao cliente telnet");
												System.out.println("Suas informações porfavor...");
												// out.writeUTF("login_request");
												System.out.print(">>>Login Name :	");
												add.setLoginName(input.readLine());
												System.out.print(">>>Password :	");
												char[] passwordArray2 = console.readPassword();
												add.setPassword(new String(passwordArray2));
												// Password=input.readLine();
												out.writeUTF(add.getLoginName());
												out.writeUTF(add.getPassword());
												String Permission = in.readUTF();
												System.out.println(Permission);
												if (Permission.equals("ALLOWED")) {
													System.out.println("<< Telnet Prompt >");
													while (true) {
														System.out.print("(telnet)>>> ");
														Command = input.readLine();
														out.writeUTF(Command);
														if (Command.equals("quit")) {
															System.out.println("você foi desconectado");
															login = "0";
															break;
														}
														out.flush();
														result = new DataInputStream(
																new BufferedInputStream(soc.getInputStream()));
														// input
														// from
														// server
														System.out.println(result.readUTF());
													}

												} else {
													System.out.println("Login falhou!!");
												}
												System.out.println("Re-login pressione-1");
												System.out.println("para sair pressione -outra tecla");
												login = input.readLine();
												out.writeUTF(login);
												out.flush();
											} while (login.equals("1"));
											break;
										default:
											break;
									}

								} catch (IOException i) {
									System.out.println();
								}
								break;
							case "quit":
								System.out.println("Saindo");
								break OUTER;
							default:
								System.out.println("Tentativa incorreta");
								break;
						}
					}
					// close the connection
					try {
						input.close();
						out.close();
						soc.close();
						in.close();
						System.out.println("conexão fechada em :(TIME)>>" + dTime.getHour() + ":" + dTime.getMinute());
					} catch (IOException i) {
						System.out.println(i);
					}

				} catch (Exception e) {
					System.out.println(e);
				}
			}

		} catch (Exception e) {
			System.out.println("Impossivel se conectar ao servidor");
			System.out.println(e);
		}
	}
}