package com.udp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {	
	public static void main(String args[]) throws Exception{
	 if(args.length != 2){
         System.out.println("사용법 : java UDPEchoClient ip port");
         System.exit(1);

	 }
	 String ip = args[0];
	 int port = 0;
	  try{	         
		  port = Integer.parseInt(args[1]);
	
	  }catch(Exception e){	         
		  System.out.println("port 번호는 양의 정수로 입력해주세요");
		  System.exit(1);
	  }  
	  InetAddress inetaddr = null; 	  
	  try{
          // DatagramPacket에 들어갈 ip 주소가
		  //InetAddress 형태여야 함        
		  inetaddr = InetAddress.getByName(ip);
	  }catch(UnknownHostException e){	         
		  System.out.println("잘못된 도메인이나 ip입니다.");	        
		  System.exit(1);	
	  }
	  
	  System.out.println("접속 되었습니다!!");
	  /**********************************	
	  ///////////////전송/////////////////	
	  **********************************/
	  DatagramSocket dsock = null;
  
	  try{
         // 키보드로부터 서버에게 전송할 문자열을 입력받기 위해
         // System.in을 BufferedReader 형태로 변환
	         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	         dsock = new DatagramSocket();
	         String line = null;
	         while((line = br.readLine())!=null){  
	        	 	// DatagramPacket에 각 정보를 담고 전송
	            DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, inetaddr, port);
	            dsock.send(sendPacket);                            
	            if(line.equals("quit")) break;
	              /**********************************
	               ///////////////수신/////////////////
	               **********************************/
	             byte[] buffer = new byte[line.getBytes().length];
	             // 반송되는 DatagramPacket을 받기 위해
	             //receivePacket 생성한 후 대기
	             DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
	             dsock.receive(receivePacket);              
	
	             // 받은 결과 출력
	             String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);
	             System.out.println("전송받은 문자열 : "+msg);    
	         }
	         System.out.println("UDPEchoClient를 종료합니다.");
	
	  }catch(Exception ex){
	         System.out.println(ex);
	
	  }finally{	         
		  if(dsock != null)
			  dsock.close();
	  }
	}
}
