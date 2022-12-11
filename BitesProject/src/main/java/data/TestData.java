package data;

import annotation.PropertyField;


public class TestData extends BaseData {

	@PropertyField(displayName = "X value: ",type = int.class)
	@PropertyField.ValidationIntegerRange(max = 100, min = -100)
	private int x; 
	
	
	@PropertyField(displayName = "Y value: ",type = double.class)
	@PropertyField.ValidationDoubleRange(max = 100, min = -100, message = "Error")
	private double y; 
	
	
	@PropertyField(displayName = "Z value: ",type = String.class)
	@PropertyField.ValidationStringLength(length = 50, message = "Error")
	private String z;  
			
	public enum Color_enum {
	
		    RED,
		    GREEN,
		    BLUE;
		
	}
	
	@PropertyField(displayName = "T value: ",type = Enum.class)
	private Color_enum t; 


	@Override
	public String toString() {
		return "TestData [x=" + x + " y=" + y + " z=" + z +" t=" + t +"]";
	}
}