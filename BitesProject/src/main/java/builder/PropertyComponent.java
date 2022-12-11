package builder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import annotation.PropertyField;
import data.TestData;

public abstract class PropertyComponent<T> extends JPanel {
	
	protected Field field;

	public PropertyComponent(Field field) {
		this.field = field;
	}
	
	public void save (Object o) {
		try {
			field.setAccessible(true);
			field.set(o, this.getComponentValue());
		} catch(IllegalArgumentException e) {
			Logger.getLogger(PropertyComponent.class.getName());
		} catch (IllegalAccessException e) {
			Logger.getLogger(PropertyComponent.class.getName());
		}
	}
	
	public abstract void load(List<Object> o);
	
	public abstract T getComponentValue();
	
	public static class PropertComponentIntValue extends PropertyComponent<Integer>{ 
		
		private JSlider sliderValue;
		private JLabel label;
		
		public PropertComponentIntValue(Field field) {
			super(field);
			this.sliderValue = new JSlider(JSlider.HORIZONTAL,MinIntRange(this.field), MaxIntRange(this.field),(MaxIntRange(this.field)+MinIntRange(this.field))/2);
			this.label = new JLabel();
			this.setLayout(new BorderLayout());
			this.setLayout(new FlowLayout(FlowLayout.LEFT,5,15));
			PropertyField annotation = field.getAnnotation(PropertyField.class);
			String displayName = annotation.displayName();
			Class type = annotation.type();
			JLabel displayLabel = new JLabel(displayName);
			this.add(displayLabel,BorderLayout.WEST);
			this.add(this.sliderValue,BorderLayout.CENTER);
		}
		

		@Override
		public void load(List<Object> o) {
				
		}

		@Override
		public Integer getComponentValue() {
			return Integer.valueOf(this.sliderValue.getValue());
		}
		
		
	}
	
	public static class PropertComponentDoubleText extends PropertyComponent<Double>{
		
		private JTextField textValue;
		private JLabel label;
		
		public PropertComponentDoubleText(Field field) {
			
			super(field);
			this.textValue = new JTextField(35);
			this.label = new JLabel();
			this.setLayout(new BorderLayout());
			this.setLayout(new FlowLayout(FlowLayout.LEFT,5,15));
			PropertyField annotation = field.getAnnotation(PropertyField.class);
			String displayName = annotation.displayName();
			Class type = annotation.type();
			JLabel displayLabel = new JLabel(displayName);
			this.add(displayLabel,BorderLayout.WEST);
			this.add(this.textValue,BorderLayout.CENTER);
			
		}

		@Override
		public void load(List<Object> o) {
				
		}

		@Override
		public Double getComponentValue() {
			PropertyField.ValidationDoubleRange annotation = this.field.getAnnotation(PropertyField.ValidationDoubleRange.class);
			if(isNumber(this.textValue.getText())){
				if(ValidationDoubleRange(this.field,this.textValue.getText())){
					double value = Double.parseDouble(this.textValue.getText());
					return Double.valueOf(this.textValue.getText());
				}
				else {
					String message = annotation.message();
					JOptionPane.showMessageDialog(label, message);
				}
			}
			else {
				String message = annotation.message();
				JOptionPane.showMessageDialog(label, message);
			}
			return null;
		}
		
	}
	
	
	public static class PropertComponentStringText extends PropertyComponent<String>{
		
		private JTextField textValue;
		private JLabel label;
		
		public PropertComponentStringText(Field field) {
			
			super(field);
			this.textValue = new JTextField(35);
			this.label = new JLabel();
			this.setLayout(new BorderLayout());
			this.setLayout(new FlowLayout(FlowLayout.LEFT,5,15));
			PropertyField annotation = field.getAnnotation(PropertyField.class);
			String displayName = annotation.displayName();
			Class type = annotation.type();
			JLabel displayLabel = new JLabel(displayName);
			this.add(displayLabel,BorderLayout.WEST);
			this.add(this.textValue,BorderLayout.CENTER);
			
		}

		public void load(List<Object> o) {
			
		}

		@Override
		public String getComponentValue() {
			
			PropertyField.ValidationStringLength annotation = this.field.getAnnotation(PropertyField.ValidationStringLength.class);
			if(isBlank(this.textValue.getText())){
				if(ValidationStringLength(this.field,this.textValue.getText())) {
					return this.textValue.getText();
				}
				else {
					String message = annotation.message();
					JOptionPane.showMessageDialog(label, message);
				}
			}
			else {
				String message = annotation.message();
				JOptionPane.showMessageDialog(label, message);
			}
			return null;
		}
		
	}
	
	public static class PropertComponentEnumValue extends PropertyComponent<Enum>{
		
		private JComboBox boxValue;
		private JLabel label;
		
		public PropertComponentEnumValue(Field field) {
			
			super(field);
			PropertyField annotation = field.getAnnotation(PropertyField.class);
			String displayName = annotation.displayName();
			Class type = annotation.type();
			JLabel displayLabel = new JLabel(displayName);
			this.boxValue = new JComboBox(TestData.Color_enum.values());
			this.label = new JLabel();
			this.setLayout(new BorderLayout());
			this.setLayout(new FlowLayout(FlowLayout.LEFT,5,15));
			this.add(displayLabel,BorderLayout.WEST);
			this.add(this.boxValue,BorderLayout.CENTER);
		}

		@Override
		public void load(List<Object> o) {
				
		}

		@Override
		public Enum getComponentValue() {
			return (Enum)this.boxValue.getSelectedItem();
		}
		
		
	}
	
	public static PropertyComponent build(Field field) {
		PropertyField annotation = field.getAnnotation(PropertyField.class);
		PropertyComponent component = null;
		if(annotation.type()==int.class || annotation.type()==Integer.class) {
			component = new PropertComponentIntValue(field);
		}
		else if(annotation.type()==double.class || annotation.type()==Double.class) {
			component = new PropertComponentDoubleText(field);
		}
		else if(annotation.type()==String.class) {
			component = new PropertComponentStringText(field);
		}
		else if(annotation.type()==Enum.class) {
			component = new PropertComponentEnumValue(field);
		}
		return component;
	}
	
	private static int MinIntRange(Field field) {
		Field f = field;
		PropertyField.ValidationIntegerRange annotation = f.getAnnotation(PropertyField.ValidationIntegerRange.class);
		int min = annotation.min();
		return min;
	}
	
	private static int MaxIntRange(Field field) {
		Field f = field;
		PropertyField.ValidationIntegerRange annotation = f.getAnnotation(PropertyField.ValidationIntegerRange.class);
		int max = annotation.max();
		return max;
	}
	
	private static boolean isNumber(String str) {
		try {
			double value = Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			System.out.println("Error: \""+str+"\" is not number.");
			return false;
		}
	}
	
	private static boolean ValidationDoubleRange(Field fields,String str) {
		PropertyField.ValidationDoubleRange annotation = fields.getAnnotation(PropertyField.ValidationDoubleRange.class);
		double max = annotation.max();
		double min = annotation.min();
		boolean value = false;
		if(Double.parseDouble(str)<max && Double.parseDouble(str)>min) {
			value = true;
		}
		return value;
	}
	
	private static boolean isBlank(String str) { 
		boolean value = false;
		try {
			if(str.isBlank() || str.isEmpty()) {
				value = false;
				throw new NullPointerException();
			}
			else {
				value = true;
			}
		}catch(NullPointerException e){
			System.out.println("Error: Blank or empty string cannot be entered.");
		}
		return value;
	}
	
	private static boolean ValidationStringLength(Field fields,String str) {
		Field f = fields;
		PropertyField.ValidationStringLength annotation = f.getAnnotation(PropertyField.ValidationStringLength.class);
		int lenght = annotation.length();
		boolean value = false;
		if(str.length() > lenght) {
			value = false;
			System.out.println("Error: "+str+" is not the intended length.");
		}
		else {
			value = true;
		}
		return value;
	}
	
	
	
	
}
