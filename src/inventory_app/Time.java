package inventory_app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Time {
	// fields
	String minutes;
	String hours;
	String day;
	String month;
	String year;
	
	// constructors
	public Time () {
		minutes = "";
		hours = "";
		day = "";
		month = "";
		year = "";
	}// Time()
	
	public Time (String minutes, String hours, String day, String month, 
			String year) {
		this.minutes = minutes;
		this.hours = hours;
		this.day = day;
		this.month = month;
		this.year = year;
	}// Time(int, int, int, int, int)
	
	public Time(String[] time) {
	  	  Time newTime = new Time();
	  	  
	  	  newTime.setMinutes(time[0]);
	  	  newTime.setHours(time[1]);
	  	  newTime.setDay(time[2]);
	  	  newTime.setMonth(time[3]);
	  	  newTime.setYear(time[4]);
	}// Time(String[])
	
	public String getMinutes() {
		return minutes;
	}// getMinutes()
	
	public String getHours() {
		return hours;
	}// getHours()
	
	public String getDay() {
		return day;
	}// getDay()
	
	public String getMonth() {
		return month;
	}// getMonth() 
	
	public String getYear() {
		return year;
	}// getYear()
	
	public void setMinutes(String newMin) {
		minutes = newMin;
	}// setMinutes(int)
	
	public void setHours(String newHour) {
		hours = newHour;
	}// setHours(int)
	
	public void setDay(String newDay) {
		day = newDay;
	}// setDay(int)
	
	public void setMonth(String newMonth) {
		month = newMonth;
	}// setMonth(int)
	
	public void setYear(String newYear) {
		year = newYear;
	}// setYear(int)
	
	@Override
	public String toString() {
		return " | " + getMonth() + "/" + getDay() + "/" + getYear() + " " 
				+ getHours() + ":" + getMinutes();
				
	}// toString()
	
	public void tempTime() {
		  try {
			 String data = this.toString();
			 File file = new File("temp.txt");
			 
			 // if the file doesn't exist, then create it
			 if (!file.exists()) {
				 file.createNewFile();
			 }// if
			 
			 FileWriter fileWriter = new FileWriter(file, true);
			 BufferedWriter bw = new BufferedWriter(fileWriter);
			 
			 bw.write(data);
			 bw.flush();
			 bw.close();
			 
			 System.out.println("Time added to temp.txt");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }// tempTime()
	
}// class Time