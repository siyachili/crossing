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
import java.io.*;
public class GeneticAlgorithm {
    	int capacity;
	int quota;
	
	public HashSet<Chromosome> population = new HashSet<Chromosome>();
	
	
	public GeneticAlgorithm(double crossoverRate, double mutationRate){
		Chromosome.MUTATION_RATE = mutationRate;
		Chromosome.CROSSOVER_RATE = crossoverRate;
	}
	
	public GeneticAlgorithm(){}
	
	
	//|---------------------------------|
	//|MATING							|
	//|---------------------------------|
	/**Every individual mates with every other indivual*/
	protected HashSet<Chromosome> crossBreed(HashSet<Chromosome> potentialParents){
		HashSet<Chromosome> offspring = new HashSet<Chromosome>();
		
		for(Chromosome parent1:potentialParents){
			for(Chromosome parent2: potentialParents){
				if(!parent1.equals(parent2)){
					offspring.addAll(Chromosome.crossOver(parent1, parent2));
				}
			}
		}
		
		potentialParents.addAll(offspring);
		return potentialParents;
	}
	
	
	
	//|---------------------------------|
	//|SELECTION FUNCTION(Tournament)	|
	//|---------------------------------|
	//take best 30% of the population
	protected HashSet<Chromosome> tournament(HashSet<Chromosome> population, int tournamentSize){
		Set<Chromosome> tournament = randomSubset(population, tournamentSize);
		Set<Chromosome> winners = new HashSet<Chromosome>();
		
		while(winners.size()<tournamentSize){
			Chromosome victor = getMin(tournament);
			tournament = randomSubset(population, tournamentSize);
			winners.add(victor);
			
		}
		return (HashSet<Chromosome>)winners;
	}
	
	
	private class Compare implements Comparator{

		public int compare(Object arg0, Object arg1) {
			// TODO Auto-generated method stub
			return ((Chromosome)arg0).getFitness() - ((Chromosome)arg1).getFitness();
		}
		
	}
	

	public void findSolution(Problem problem){
		//kill, mate and look for best child in new population
		//intial population size?
		Set<String> chromosomeIDs = (new PowerSet(problem.getGenes().size())).randomSubset(100);
		
		Set<Chromosome> population = new HashSet<Chromosome>();
		
		for(String chromID: chromosomeIDs){
			population.add(new Chromosome(chromID, problem.getCapacity(),problem.getQuota()));
		}
		
		int numIters = 1;
		
		HashSet<Chromosome> bestChildren = new HashSet<Chromosome>();
		int maxIters = (int)Math.pow(2, Problems.getCurrentProblem().getGenes().size() );
		while(numIters <= maxIters){
			//take top 20 of the population;
			population = tournament((HashSet<Chromosome>)population, 20);
			//mate
			population = crossBreed((HashSet<Chromosome>)population);
			
			//check for best child
			Chromosome bestChild = getMin(population);
			
			if(bestChild.getFitness() == 0 && bestChild.getWeight() != 0 && bestChild.getValue() != 0 ){
				
				//current Problem
				System.out.printf("CURRENT PROBLEM -- Problem : %d\n",Problems.currentProblem+1);
				
				//solution set
				System.out.println("Solution Set : "+ bestChild);
				
				//totalWeight
				System.out.println("Total Weight : "+bestChild.getWeight()+"\tCapacity: "+Problems.getCurrentProblem().getCapacity());
				
				//Value
				System.out.println("Total Value : "+bestChild.getValue()+"\tQuota : "+Problems.getCurrentProblem().getQuota());
				
				//Generation
				System.out.println("Generation : "+numIters);
				break;
			}
			
			//next generation
			numIters++;
		}
		
		if(numIters > (int)Math.pow(2, Problems.getCurrentProblem().getGenes().size() )){
			System.out.println("NO SOLUTION FOUND FOR PROBLEM "+(Problems.currentProblem+1));
		}
	}
	
	protected HashSet<Chromosome> randomSubset(HashSet<Chromosome> pop, int size){
		//enumerate elements of 'subsets' and pick size random elements
		if(size == pop.size()){
			return pop;
		}else if(size > pop.size()){
			System.err.println("[FN: randomSubset]Invalid subset size.");
		}
		HashSet<Integer> ints = new HashSet<Integer>();
		while(ints.size()<size){
			//add random number
			ints.add(((int)(Math.random()*1000))%pop.size());
		}
		
		int ct = 0;
		HashSet<Chromosome> returned = new HashSet<Chromosome>();
		for(Chromosome s: pop){
			if(ints.contains(ct)){
				returned.add(s);
			}
			ct++;
		}
		
		return returned;
	}
	
	protected Chromosome getMin(Set<Chromosome> pop) {
		Chromosome min = null;
		for(Chromosome chromosome:pop){
			if(min == null){
				min = chromosome;
			}
			
			if(min != null){
				if(chromosome.getFitness() < min.getFitness() && (((int)(Math.random()*1000))%45)==0){
					min = chromosome;
				}
			}
		}
		
		return min;
	}
	protected HashSet<Chromosome> getBelow(Set<Chromosome> pop, int below) {
		//return chromosomes with fitness <= below
		HashSet<Chromosome> best = new HashSet<Chromosome>();
		for(Chromosome chromosome:pop){
			if(chromosome.getFitness() <=  below ){
				best.add(chromosome);
			}
		}
		
		return best;
	}
	
	//|---------------------------------|
	//|MAIN FUNCTION					|
	//|---------------------------------|
	public static void main(String[] args) {
		System.out.println("Please make sure that input.txt file is in the same directory as the jar file: ");
		
		Problems probs = new Problems("input.txt");
		GeneticAlgorithm  ga = new GeneticAlgorithm();
	
		Scanner scanner = new Scanner(System.in);
		char input , prevInput = 'w';
		do{
			if(prevInput == 'w'){
				System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.println("\n'w' - EVALUATE CURRENT problem, 'd'- NEXT problem,  'a' - PREVIOUS problem, 's' - QUIT evaluation:");
			}
			System.out.printf(" Current Problem : %d of %d\n ",Problems.currentProblem+1, Problems.problems.size());
			input = scanner.next().trim().charAt(0);
			
			switch(input){
			case 'w':
				ga.findSolution(probs.getCurrentProblem());
				System.out.println("----------------------------------------------------------------------------------------------------");
				break;
			case 'd':
				probs.getNextProblem();
				break;
			case 'a':
				probs.getPreviousProblem();
				break;
			
			case 's':
				System.exit(1);
				break;
			}
			prevInput = input;
			
		}while(true);
		
		

	}
    
   
}
