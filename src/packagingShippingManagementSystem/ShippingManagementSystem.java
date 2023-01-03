/* 
  Name: Joshua Frazer
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 13, 2022 
 
  Class: Enterprise Computing  
*/ 
package packagingShippingManagementSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ShippingManagementSystem {
	static int MAX = 10;
	public static ArrayList<Conveyor> conveyors = new ArrayList<Conveyor>();
	
	public static void main(String[] args) throws FileNotFoundException {
		PrintStream fileOut = new PrintStream(new File("output.txt"));
		System.setOut(fileOut);
		System.out.println("\n * * * * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS * * * * * * * * * * * * \n");
		ExecutorService application = Executors.newFixedThreadPool(MAX);
		Scanner scanner = new Scanner(new File("src\\config.txt"));
		ArrayList<Integer> configData = new ArrayList<Integer>();
		
		while(scanner.hasNext()) {
			configData.add(Integer.parseInt(scanner.nextLine()));
		}
		scanner.close();
		
		int numberOfStations = configData.get(0);
		
		for (int i = 0; i < numberOfStations; i++) {
			System.out.println("Routing Station " + i + " Has A Total Workload Of " + configData.get(i+1));
			conveyors.add(new Conveyor(i));
		}	
		System.out.println("\n");
		
		for (int i = 0; i < numberOfStations; i++) {
			try {
				application.execute(new Station(i, configData.get(i+1), conveyors.get(i), conveyors.get(i-1)));
			}
			catch(IndexOutOfBoundsException e) {
				application.execute(new Station(i, configData.get(i+1), conveyors.get(i), conveyors.get(2)));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		application.shutdown();
		while(!application.isTerminated()) {
			
		}
		
		System.out.println("\n * * * * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION ENDS * * * * * * * * * * * * \n");
	}
}
