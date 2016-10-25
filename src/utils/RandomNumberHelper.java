package utils;

import java.util.Random;

public final class RandomNumberHelper {
	
	private RandomNumberHelper(){}
	
	//generate random integer numbers in the range (min, max];
	public static int getRandomNumber(int min, int max){
		return min + (int)Math.round(Math.random() * (max - min));
	}

	//generate random string date in the format "dd-MM-yyyy";
	public static String getRandomDate(){
		int day = getRandomNumber(1, 28);
		int month =  getRandomNumber(1, 12);
		int year = getRandomNumber(1950, 2015);
		String date = day + "-" + month + "-" + year;
		return date;
	}

	//generate random double numbers in the range (min, max] and return number format %.2f;
		public static double getRandomDoubleNumber(double min, double max){
			Random rand = new Random();
			double result = min + ((max - min) * rand.nextDouble());
			result = Double.parseDouble( String.format("%.2f", result).replace(",",".") );
			return result;
		}	
}