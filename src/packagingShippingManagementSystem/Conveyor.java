/* 
  Name: Joshua Frazer
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 13, 2022 
 
  Class: Enterprise Computing  
*/ 
package packagingShippingManagementSystem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
	public int number;
	private Lock lock = new ReentrantLock();
	
	public Conveyor(int number) {
		this.number = number;
	}
	
	public boolean lockConveyor() {
		return lock.tryLock();		
	}
	
	public boolean unlockConveyor() {
		lock.unlock(); // Check back later
		return true;
	}
}
