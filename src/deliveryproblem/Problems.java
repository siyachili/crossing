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

import java.io.*;
import java.util.*;
public class Problems {
    static ArrayList<Problem> problems = new ArrayList<Problem>();
	static int currentProblem = 0;
	
	public Problems(String filename){
		//loads all problems from input file
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File(filename));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		if(scanner == null)return;
		
		String line = "";
		String[] gene = null;
		Problem currentProblem = new Problem();
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			
			//System.out.println(line);
			
			if(line.equals("***")){//new problem
				//System.out.println("line ***");
				currentProblem = new Problem();
				
				//setCapacity
				currentProblem.setCapacity(Integer.parseInt(scanner.nextLine().trim()));
				
				//setQuota
				currentProblem.setQuota(Integer.parseInt(scanner.nextLine().trim()));
				
				int numGenes = Integer.parseInt(scanner.nextLine().trim());
				
				for( int i=0 ;i < numGenes ; i++ ){
					line = scanner.nextLine();
					gene = line.split("\\s+");
					//System.out.println("genelength "+gene.length);
					if(gene.length == 3){
						currentProblem.addGene(new Gene(gene[0], Integer.parseInt(gene[1]), Integer.parseInt(gene[2])));
					}
				}
			}else if(line.equals("")){
				//System.out.println("Should add Problem");
				if(currentProblem != null){
					//System.out.println("Adding Problem : "+currentProblem);
					problems.add(currentProblem);
					currentProblem = null;
				}
				continue;
			}
			
		}
		System.out.println("Number of problems : "+problems.size());
		scanner.close();
	}
	
	public static Problem getNextProblem(){
		currentProblem  = ((++currentProblem)%(problems.size()));
		return problems.get(currentProblem);
	}
	
	public static Problem getPreviousProblem(){
		if(currentProblem >= problems.size()){
			System.err.println("]OUT OF PROBLEMS[");
			System.exit(0);
			//return null;
		}
		
		--currentProblem;
		
		if(currentProblem < 0 )currentProblem += problems.size();
		return problems.get((currentProblem)%problems.size());
	}
	
	public static Problem getCurrentProblem(){
		return problems.get(currentProblem);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((new Problems("src/input.txt")).problems);
	}

}
