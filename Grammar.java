//Trevor Nichols
//Grammar
//Section: AF

import java.util.*;

//Creates an instance of a grammar given a list of rules in the Backus-Naur 
//Form. Generates random sentences from the given grammar
public class Grammar {
    private Map<String, List<String>> currentRuleSet;
    private Random numGenerator;
    private String generatedString;
     
     //Constructs the instance for a given list of BNF rules.
     //throws IllegalArgumentException if the list passed is null or empty.
     public GrammarSolver(List<String> grammar){
         if(grammar == null || grammar.isEmpty()){
             throw new IllegalArgumentException("The list is null or empty.");
         }
         this.currentRuleSet = new TreeMap<String,List<String>>(); 
         this.numGenerator = new Random();
         
         for(int i = 0; i < grammar.size();i++){
             String[] currentRules = grammar.get(i).split("::=");
             String[] tempSymbols = currentRules[1].split("\\|");
             
             if(!this.currentRuleSet.containsKey(currentRules[0])){
                 this.currentRuleSet.put
                     (currentRules[0].trim(), new ArrayList<String>());
             }  
             this.currentRuleSet.get(currentRules[0]).addAll
                                     (Arrays.asList(tempSymbols));
         }
     }
     
     //Returns a boolean if a symbol is non terminal(true) or terminal(false).
     //throws an IllegalArgumentException if the string is null or empty.
     public boolean isNonTerminal(String symbol){
         if(symbol == null || symbol.isEmpty()){
             throw new IllegalArgumentException();
         }
         return this.currentRuleSet.containsKey(symbol);
     }
     
     //returns a string representation of the non terminals for the current
     //grammar.
     //example
     //[sentence, object, article]
     @Override
    public String getSymbols(){
         return this.currentRuleSet.keySet().toString();
     } 
     
     //creates a finite number of sentences for a given non terminal 
     //and integer.
     //throws IllegalArgumentException if a non terminal is passed or 
     //a null non terminal is passed or if the number is less than zero.
     public String[] generate(String symbol, int times){
         this.determineLegality(symbol);
        
         if(times < 0){
             throw new IllegalArgumentException
                         ("the number is less than zero");
         }
         String[] tempStorage = new String[times];
         
         for(int i = 0; i < times; i++){
             this.generatedString = "";
             tempStorage[i]= this.buildStatement(symbol).trim() ;
         }
         return tempStorage;
     }
    
     //Returns a random sentence from the current grammar.
     private String buildStatement(String symbol){
         int randomIndex = this.numGenerator.nextInt
                                 (this.currentRuleSet.get(symbol).size());
         String tempRule = this.currentRuleSet.get(symbol).get
                                                (randomIndex).trim();
         
         String[] splitRules = tempRule.split("\\s+");
         
         for(String rule : splitRules){
             //recursive case first
             if(this.isNonTerminal(rule)){
                  this.generatedString += this.buildStatement(rule);   
             
                  //base Case
                 }else{
                  return rule + " ";
              }  
         }
         return this.generatedString;
     }
     
     //determines if a given symbol is a terminal or null
     private void determineLegality(String symbol){
         if(symbol == null || !this.isNonTerminal(symbol)){
             throw new IllegalArgumentException
                     ("The non terminal is null or it is a terminal");
         }
     }
}