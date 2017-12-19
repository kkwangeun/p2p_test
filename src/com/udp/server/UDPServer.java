package com.udp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
	public static void main(String[] args){
        if(args.length != 1){
               System.out.println("사용법 : java UDPEchoServer port");
               System.exit(1);
        }
        int port = 0;
        try{
               port = Integer.parseInt(args[0]);
        }catch(Exception e){
               System.out.println("Port 번호는 양의 정수로 입력해서 주세요.");
               System.exit(1);
        }     
  // 클라이언트에게 DatagramPacket을 전송하거나 수신하기 위해 DatagramSocket 객체 생성
        DatagramSocket dsock = null;
        try{
               System.out.println("접속 대기상태입니다.");
               dsock = new DatagramSocket(port);
               String line = null;   
               while(true){
                      /**********************************
                      ///////////////수신/////////////////
                      **********************************/
                      byte[] buffer = new byte[1024];
                      DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                      // 클라이언트로부터 DatagramPacket을 전송 받기 위해서 DatagramPacket 객체 하나를 생성하고 패킷을 전송할 때까지 대기
                      dsock.receive(receivePacket);       
                      // 전송받은 데이터를 String 객체에 지정하고 출력
                      String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                      System.out.println("전송 받은 문자열 : " + msg);
                      if(msg.equals("quit")) break;  
                      /**********************************
                      ///////////////전송/////////////////
                      **********************************/
                      // 전송받은 데이터를 그대로 반송
                      DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getData().length, receivePacket.getAddress(), receivePacket.getPort());
                      dsock.send(sendPacket);

               }
               System.out.println("UDPEchoServer를 종료합니다.");            

        }catch(Exception e){
               System.out.println(e);

        }finally{

               if(dsock != null)
                      dsock.close();

        }

  }

}
