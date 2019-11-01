/*
 * John (JJ) Mase
 * O(n^2) Delivey Algorithm across 2 different days
 * 
 */


package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	
	private static Delivery[] deliveries;
	private static int[][] edgeLookup;
	private static House[][] houseList;
	private static int lastDay1 = 0;
	private static int lastDay2 = 0;

	
	public static void main(String [] args) {
		
		// Get that good inputs
		Scanner input = new Scanner(System.in);
		int numCustomers = input.nextInt();
		deliveries = new Delivery[numCustomers];
		
		// Populate each row from standard input
		for(int i = 0 ; i < numCustomers; i++) {
			Delivery d = new Delivery(input.nextInt(), input.nextInt(), input.nextInt());
			deliveries[i] = d;
		}
		
		// Size the edge list array correctly from the input
		edgeLookup = new int[deliveries.length][deliveries.length];
		
		// Size the houseList array correctly
		houseList = new House[2][deliveries.length];
		
		// Find the edge weights 
		for(int i = 0; i < deliveries.length; i++)
			for(int j = 0; j < deliveries.length; j++)
				populateEdgeWeights(i, j);
		
		// Do the DPing
		findCheapestRoute(0, 0);
		findCheapestRoute(0, 1);
		
		// Print the Edge Lookup Array
		for(int i = 0; i < edgeLookup.length; i++) {
			for(int j = 0; j < edgeLookup[i].length; j++)
				System.out.print(edgeLookup[j][i] + " ");
			System.out.println();
		}
		
		// Print the House List Array
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < houseList.length; j++)
				System.out.print((houseList[i][j] != null) ? (houseList[i][j].cost + " ") : "Null ");
			System.out.println();
		}
		
	
		/*
		 
			OPT = The minimum amount of fuel and credits the store must spend to make all of the deliveries 
				within 2 days and leave all customers satisfied.
				
			OPT1 = The minimum amount of fuel the store must spend to make all of the deliveries in 1 day
			OPT(i, j) = The minimum amount of fuel to the store must spend to make i deliveries to j customers.
				
			dpCost Array:
				D3: Day 1 or Day 2
				D2: 
				D1:
				
			Choices:
				1) Deliver to this customer now, taking fuel cost f |xi - xj| + |yi - yj| and not paying c
				2) Deliver to this customer tomorrow, and pay c.
		
			  0 1 2 3
			0 _ _ _ _ 
			1 _ _ _ _
			2 _ _ _ _ 
			3 _ _ _ _ 
		
		
		*/
	}
	
	public static void populateEdgeWeights(int i, int j) {
		// Base Case - Find the distance from the warehouse to customer i
		if(j == 0)
			edgeLookup[i][j] = Math.abs(deliveries[i].x) + Math.abs(deliveries[i].y);
		else
			edgeLookup[i][j] = Math.abs(deliveries[i].x - deliveries[j].x) + Math.abs(deliveries[i].y + deliveries[j].y);
	}
	
	public static int findCheapestRoute(int i, int j) {
		System.out.println("I, J: " + i + ", " + j);
		// Find Starting Point
		if(i == 0) {
			// Day 2
			if(edgeLookup[i][j+1] + deliveries[j+1].day2Comp < edgeLookup[i][j]) {
				houseList[1][lastDay2] = new House(j+1, edgeLookup[i][j+1] + deliveries[j+1].day2Comp);
				return houseList[1][lastDay2++].cost;
			}
			
			// Day 1
			else {
				houseList[0][lastDay1] = new House(i+1, edgeLookup[i][j]);
				return houseList[0][lastDay1++].cost;
			}
		}
		
		// Find Intermediary Point
		else {
			// Find which is smaller - first day or second day
			// Day 2
			if(edgeLookup[i][j+1] + deliveries[j+1].day2Comp < edgeLookup[i+1][j] + edgeLookup[i][j]) {
				houseList[1][lastDay2] = new House(j+1, edgeLookup[i][j+1] + deliveries[j+1].day2Comp);
				return houseList[1][lastDay2++].cost;
			}
			
			// Day 1
			else {
				houseList[0][lastDay1] = new House(i+1, edgeLookup[i+1][j] + edgeLookup[i][j]);
				return houseList[0][lastDay1++].cost;
			}		
		}
	}
}

class House {
	int customerNum;
	int cost;
	
	public House() {}

	public House(int customerNum, int cost) {
		this.customerNum = customerNum;
		this.cost = cost;
	}
}

	
class Delivery {
	int x;
	int y;
	int day2Comp;
	public boolean firstDay;
	
	public Delivery() {}
	
	public Delivery(int x, int y, int day2Comp) {
		this.x = x;
		this.y = y;
		this.day2Comp = day2Comp;
	}
}
