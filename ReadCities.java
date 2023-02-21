package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadCities {
	
	public int n = 0;
	public File myObj;
	public ArrayList<String> cities = new ArrayList<String>();
	
	public ReadCities() {
		myObj = new File("\\Users\\danie\\OneDrive\\Desktop\\Univeras\\antras semestras\\OOP\\ADS4\\src\\main\\data.txt");

	}
	
	public int findCities() throws FileNotFoundException {
		
		try (Scanner myReader = new Scanner(myObj)) {
			while(myReader.hasNextLine() != false) {
				
				String firstCity = myReader.next();
				String secondCity = myReader.next();
				int distance = myReader.nextInt();
					
				if(cities.contains(firstCity) == false) {
					cities.add(firstCity);
					n++;
				}
				if(cities.contains(secondCity) == false) {
					cities.add(secondCity);
					n++;
				}
				
			}
		}
		
		return n;
		
		
	}
}