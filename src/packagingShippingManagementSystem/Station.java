/* 
  Name: Joshua Frazer
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 13, 2022 
 
  Class: Enterprise Computing  
*/ 
package packagingShippingManagementSystem;

import java.util.Random;

public class Station implements Runnable {
	public Conveyor input, output;
	public int workload, number;
	private int sleepTime;
	private static Random generator = new Random();
	
	public Station(int number, int workload, Conveyor inputConveyor, Conveyor outputConveyor) {
		this.number = number;
		this.workload = workload;
		input = inputConveyor;
		output = outputConveyor;
		sleepTime = generator.nextInt(5000);
	}

	@Override
	public void run() { // While loop needs to be implemented this.workload != 0
		// Constructs station
		try {
			Thread.sleep(sleepTime);
			System.out.println("\n% % % % % ROUTING STATION " + this.number + " Coming Online - Initializing Conveyors % % % % %\n");
			System.out.println("Routing Station " + this.number + ": Input conveyor set to conveyor number C" + this.input.number + ".");
			System.out.println("Routing Station " + this.number + ": Output conveyor set to conveyor number C" + this.output.number + ".");
			System.out.println("Routing Station " + this.number + ": Workload set. Station " + this.number + " has a total of " + this.workload + " package groups to move.");
			System.out.println("\n\nRouting Station " + this.number + ": Now Online\n\n");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		while (this.workload != 0) {
			// Try to lock both conveyors to station
			System.out.println("Routing Station " + this.number + ": Entering Lock Aquisition Phase.");
			this.input.lockConveyor();
			System.out.println("Routing Station " + this.number + ": holds lock on input conveyor C" + this.input.number + ".");
			if (this.output.lockConveyor())
				System.out.println("Routing Station " + this.number + ": hold lock on output conveyor C" + this.output.number + ".");
			else {
				this.input.unlockConveyor();
				System.out.println("Routing Station " + this.number + ": unable to lock output conveyor C" + this.output.number + ", unlocks input conveyor C" + this.input.number + ".");
			}
			System.out.println("\n* * * * * Routing Station " + this.number + " holds locks on both input conveyor C" + this.input.number + " and output conveyor C" + this.output.number + ". * * * * *\n");
			
			// Station moves a package through
			System.out.println("\n* * * * * * Routing Station " + this.number + ": * * * * CURRENTLY HARD AT WORK MOVING PACKAGES. * * * * * *\n");
			System.out.println("Routing Station " + this.number + ": successfully moves packages into station on input conveyor C" + this.input.number + ".");
			System.out.println("Routing Station " + this.number + ": successfully moves packages out of station on output conveyor C" + this.output.number + ".");
			
			// Decrement workload and check how much left
			this.workload--;
			if (this.workload != 0)
				System.out.println("\n\nRouting Station " + this.number + ": has " + this.workload + " package groups left to move.\n\n");
			else
				System.out.println("\n\n*  *  Station  " + this.number + ":  Workload  successfully  completed.  *  *  Station  " + this.number + " releasing locks and going offline * *\n\n");
			
			// Release locks after pushed a package through station
			try {
				System.out.println("Routing Station " + this.number + ": Entering Lock Release Phase");
				this.input.unlockConveyor();
				System.out.println("Routing Station " + this.number + ": unlocks/releases input conveyor C" + this.input.number + ".");
				this.output.unlockConveyor();
				System.out.println("Routing Station " + this.number + ": unlocks/releases output conveyor C" + this.output.number + ".");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		System.out.println("\n\n@ @ @ @ @ @ @ ROUTING STATION " + this.number + ": OFFLINE @ @ @ @ @ @ @\n\n");
	}
}
