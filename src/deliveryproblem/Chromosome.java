/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deliveryproblem;
import java.util.*;
/**
 *
 * @author Siyabonga
 */
public class Chromosome {
    
    	protected static double MUTATION_RATE = 0.1,
				   CROSSOVER_RATE = 0.85;
	
	private int value = 0,
				weight = 0,
				fitness;
	
	private String chromosome;
	
	public Chromosome(String chromosome){
		this.chromosome = chromosome;
		
		//weight = 0; value = 0;
		if(Problems.getCurrentProblem().getGenes().size() == chromosome.length()){
			for(int i=0; i<chromosome.length(); i++){
				if(chromosome.charAt(i) == '1'){
					value += Problems.getCurrentProblem().getGenes().get(i).value;
					weight += Problems.getCurrentProblem().getGenes().get(i).weight;
				}
			}
			//System.out.println("Weight : "+weight+"\nValue : "+value);
		}
		fitness(Problems.getCurrentProblem().getCapacity(), Problems.getCurrentProblem().getQuota());
		//System.out.println("Fitness : "+fitness);
		
	}
	
	public Chromosome(String chromosome, int capacity, int quota){
		this.chromosome = chromosome;
		//set weight and value
		//weight = 0; value = 0;
		if(Problems.getCurrentProblem().getGenes().size() == chromosome.length()){
			for(int i=0; i<chromosome.length(); i++){
				if(chromosome.charAt(i) == '1'){
					value += Problems.getCurrentProblem().getGenes().get(i).value;
					weight += Problems.getCurrentProblem().getGenes().get(i).weight;
				}
			}
			//System.out.println("Weight : "+weight+"\nValue : "+value);
		}
		fitness(capacity, quota);
		//System.out.println("Fitness : "+fitness);
	}
	
	public int length(){
		return chromosome.length();
	}
	
	/**mutates the Chromosome with probability MUTATION_RATE*/
	public void mutate(){
		//flip each bit with MUTATION_RATE probability
		String newChromo = "";
		for(int i=0; i<chromosome.length(); i++){
			if(Math.random()<=MUTATION_RATE){
				//flip the ith bit
				if(chromosome.charAt(i)=='0'){
					newChromo += '1';
				}else{
					newChromo += '0';
				}
			}else{
				newChromo += chromosome.charAt(i);
			}
		}
		chromosome = newChromo;
		
		value = 0; weight = 0;
		if(Problems.getCurrentProblem().getGenes().size() == chromosome.length()){
			for(int i=0; i<chromosome.length(); i++){
				if(chromosome.charAt(i) == '1'){
					value += Problems.getCurrentProblem().getGenes().get(i).value;
					weight += Problems.getCurrentProblem().getGenes().get(i).weight;
				}
			}
			//System.out.println("Attribs after mutation; Weight : "+weight+", Value : "+value);
		}
		fitness(Problems.getCurrentProblem().getCapacity(), Problems.getCurrentProblem().getQuota());
	}
	
	//|---------------------------------|
	//|FITNESS FUNCTION					|
	//|---------------------------------|
	/**evaluates the fitness of a chromosome for an instance of the the GA*/
	public void fitness(int capacity, int quota){
		int sum = 0;
		
		//weight difference
		if(weight > capacity){
			sum += Math.abs(weight - capacity);
		}
		
		//value difference
		if(value < quota){
			sum += Math.abs(quota - value);
		}
		
		fitness = sum;
	}
	
	/**The parents give rise to offspring with probability CROSOVER_RATE*/
	public static ArrayList<Chromosome> crossOver(Chromosome parent1, Chromosome parent2){
		ArrayList<Chromosome> children  = new ArrayList<Chromosome>();
		
		//random cross-over point(between 1 and 1 less than 0-index length of chromosome)
		int rand = ((int)(Math.random()*1000))%parent1.length();
		int crossPoint = 1 +  rand;
		
		//new children CROSSOVER_RATE of the time
		if(Math.random()<=CROSSOVER_RATE){
			children.add(new  Chromosome(parent1.chromosome.substring(0,crossPoint)+
					parent2.chromosome.substring(crossPoint)));
			children.add(new  Chromosome(parent2.chromosome.substring(0,crossPoint)+
					parent1.chromosome.substring(crossPoint)));

			children.get(0).mutate();
			children.get(1).mutate();
		}else{
			//otherwise parents don't mate and THEY go to next generation
			children.add(parent1);
			children.add(parent2);
			
		}
		
		
		return children;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getValue(){
		return value;
	}
	
	public int getFitness(){
		return fitness;
	}
	
	public boolean equals(Object other){
		return chromosome.equals(((Chromosome)other).chromosome);
	}
	
	public int hashCode(){
		return chromosome.hashCode();
	}
	
	public String toString(){
		String chromo = "{";
		for(int i=0;i<chromosome.length() && chromosome.length() == Problems.getCurrentProblem().getGenes().size(); i++){
			if(chromosome.charAt(i)=='1'){
				chromo += Problems.getCurrentProblem().getGenes().get(i).name+", ";
			}
			
		}
		if(chromo.length()>1){
			chromo = chromo.substring(0, chromo.length()-2)+"}";//up to length-1 removes added comma
		}else{
			chromo += "}";
		}
		return chromo;
		//return chromosome;
	}
    
    
    
    
}
