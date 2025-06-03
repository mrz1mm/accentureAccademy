package com.corsojava.mvcdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudenteController {
	
	@RequestMapping("/studente")
	public Studente stud() {
		Studente s = new Studente("Vincenzo","Errante",25,"987");
		return s;
	}

	 @RequestMapping("/home2")	    
	    public String home2() {
	    	String s =
	    	"\n ----------------------------------------- " +
	    	"\n THIS IS HOME2!!!" +
	    	"\n ----------------------------------------- \n";
	    	System.out.print(s);
	        return s;
	    }
	    
	
	
}
