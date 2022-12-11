package builder;
import data.TestData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import annotation.PropertyField;

import data.BaseData;
import data.TestDataChild;
import data.TestData;


public class PropertyFieldGuiBuilder{
	

	public static JPanel panelBuilder (Class<? extends BaseData> type) {
		Class t = type;
		final Class tClass = type;
		List<Field> fieldList = new ArrayList<Field>();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,5));
		JButton saveButton = new JButton("Save");
		//JButton deleteButton = new JButton("Delete");
		JButton editButton = new JButton("Edit");
		buttonPanel.add(saveButton);
		//buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);
		
		final List<PropertyComponent> components = new ArrayList<PropertyComponent>();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		do {
			for(Field declaredField : t.getDeclaredFields()) {
				fieldList.add(declaredField);
			}
			t = t.getSuperclass();
		}while(t!=Object.class);
		
		for(Field field : fieldList) {
			if(field.isAnnotationPresent(PropertyField.class)) {
				PropertyComponent buildPanelFromField = PropertyComponent.build(field);
				components.add(buildPanelFromField);
				mainPanel.add(buildPanelFromField);
			}
		}
		mainPanel.add(buttonPanel);
		// save
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Object newInstance = tClass.getDeclaredConstructor(null).newInstance(null);
					
					for(PropertyComponent c: components) {
						c.save(newInstance);
					}
					System.out.println(newInstance.toString()); 
					
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				}
			}
		});	
		
		//edit 
		final List<PropertyComponent> editComponents = new ArrayList<PropertyComponent>();
		
		for(Field field : fieldList) {
			if(field.isAnnotationPresent(PropertyField.class)) {
				PropertyComponent buildPanelFromField = PropertyComponent.build(field);
				editComponents.add(buildPanelFromField);
			}
		}
		
		editButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					for(PropertyComponent c: components) {
						for(PropertyComponent cm: editComponents) {
							if(c.equals(cm)==false) {
								c = cm;
							}
						}
					}
					
					try {
						Object newInstance = tClass.getDeclaredConstructor(null).newInstance(null);
						
						for(PropertyComponent c: components) {
							c.save(newInstance);
						}
						System.out.println(newInstance.toString());
						
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});

		return mainPanel;
	}
	
	
/*
	private static boolean isExist(List<Object> list,Object object) {
		Iterator<Object> iter = list.iterator();
		boolean value = false;
		while (iter.hasNext()) {
			if(iter.equals((object))) {
				list.remove(object);
				value = true;
			}
			iter.next();
		}
		return value;
	}
	
	
	private static void focusListener(final JComponent base, final Class type) {
		base.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(((JTextField) base).getText().equals("Entered the text") || ((JTextField) base).getText().equals("Entered the number")) {
					((JTextField) base).setText("");
				}
				if(((JTextField) base).getText().equals("") == false) {
					((JTextField) base).setText("");
				}
			}

			public void focusLost(FocusEvent e) {
			
			}	
		});
	}

*/
}