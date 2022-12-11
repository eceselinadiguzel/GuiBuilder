package data;
import builder.PropertyFieldGuiBuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Main {
		
	public static void main(String[] args) {
		
		
        JFrame f = new JFrame("GUI"); 
		f.setSize(480, 280); 
		Container content = f.getContentPane();
		content.setLayout(new FlowLayout());
		content.add(PropertyFieldGuiBuilder.panelBuilder(TestData.class));
		f.setVisible(true); 
		
        
	}

}
