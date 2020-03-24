/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliveryproblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Siyabonga
 */
public class Problem {
    
	private int capacity, quota;
	private ArrayList<Gene> genes = new ArrayList<Gene>();
	
	
	public Problem(int capacity, int quota, ArrayList<Gene> genes){
		this.capacity = capacity;
		this.quota = quota;
		this.genes  = genes;
	}
	
	public Problem(){}
	
	protected int getCapacity(){
		return capacity;
	}
	
	protected int getQuota(){
		return quota;
	}
	
	protected ArrayList<Gene> getGenes(){
		return genes;
	}
	
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public void setQuota(int quota){
		this.quota = quota;
	}
	
	public void addGene(Gene gene){
		genes.add(gene);
	}
	
	public String toString(){
		return "\n---Problem "+(Problems.currentProblem+1)+"---\nCapacity : "+capacity
				+ "\nQuota : "+quota
				+ "\nNumber of Genes : "+genes.size()+"\n-----------";
	}
}
