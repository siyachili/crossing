/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliveryproblem;

/**
 *
 * @author Siyabonga
 */

import java.util.*;
public class PowerSet {
    
	HashSet<String> subsets = new HashSet<String>();
	
	public PowerSet(int n){
		boolean[][] temp = new boolean[(int)Math.pow(2, n)][n];
		//HashSet<String> returned = new HashSet<String>();
		
		for(int i=0; i<n; i++){
			int count = (int)Math.pow(2,  i);
			boolean value = false;
			for(int j=0; j<temp.length; j++){
				if(count == 0){
					value = !value;
				}
				
				temp[j][i] = value;
				count  = (count-1)%((int)Math.pow(2, i));
			}
		}
		
		for(int i=0; i<temp.length; i++){
			subsets.add(toString(temp[i]));
		}
		
		//return returned;
	}
	
	public HashSet<String> randomSubset(int size){
		//enumerate elements of 'subsets' and pick size random elements
		if(size == subsets.size()){
			return subsets;
		}else if(size > subsets.size()){
			System.err.println("[FN: randomSubset]Invalid subset size.");
		}
		HashSet<Integer> ints = new HashSet<Integer>();
		while(ints.size()<size){
			//add random number
			ints.add(((int)(Math.random()*1000))%subsets.size());
		}
		
		int ct = 0;
		HashSet<String> returned = new HashSet<String>();
		for(String s: subsets){
			if(ints.contains(ct)){
				returned.add(s);
			}
			ct++;
		}
		
		return returned;
	}
	
	private static String toString(boolean[] comb){
		String chromo = "";
		for(int i=0; i<comb.length; i++){
			if(comb[i]){
				chromo += '1';
			}else{
				chromo += '0';
			}
		}
		return chromo;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((new PowerSet(5)).randomSubset(5));
	}

    
    
}
