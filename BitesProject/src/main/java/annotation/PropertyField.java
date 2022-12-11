package annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface PropertyField {
	String displayName();
	
	Class <?> type();
		
	
	@Retention(RUNTIME)
	@Target({METHOD,FIELD})
	public @interface ValidationStringLength {
		int length();
		String message();
	}
			
	@Retention(RUNTIME)
	@Target({METHOD,FIELD})
	public @interface ValidationIntegerRange {
		int max();
		int min();
	}
	
	@Retention(RUNTIME)
	@Target({METHOD,FIELD})
	public @interface ValidationDoubleRange {
		double max();
		double min();
		String message();
	}
	
}
