/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliveryproblem;

import java.util.*;
import java.io.*;
/**
 *
 * @author Siyabonga
 */
public class Gene {
    
     static ArrayList<Gene> genes = new ArrayList<Gene>();
		 
		 public String name;
		 
		 public int value,
		 			 weight;
		 
		 public Gene(String name, int weight, int value){
			 this.name = name;
			 this.value = value;
			 this.weight = weight;
			 
			 genes.add(this);
		 }
		 
		 /**erase all stored genes*/
		 public static void erase(){
			 genes.clear();
		 }
		 
		 public String toString(){
			 return "Name : "+name+", Weight : "+weight+", Value : "+value; 
		}
}
