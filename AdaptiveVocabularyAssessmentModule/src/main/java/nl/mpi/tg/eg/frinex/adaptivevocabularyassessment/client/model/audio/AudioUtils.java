/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audioaspool.AudioIndexMap;

/**
 *
 * @author olhshk
 */
public class AudioUtils {
    
     
     public static LinkedHashMap<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> initMatrix(int numbOfBands, int maxLength) {
        LinkedHashMap<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> retVal = new LinkedHashMap<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>>();

        // initialisation: creating empty matrices
        for (TrialCondition trialType : TrialCondition.values()) {
            ArrayList<ArrayList<ArrayList<Trial>>> trialsForCondition = new ArrayList<ArrayList<ArrayList<Trial>>>(numbOfBands);
            for (int i = 0; i < numbOfBands; i++) {
                ArrayList<ArrayList<Trial>> trialsByLength = new ArrayList<ArrayList<Trial>>(maxLength + 1); // maxLength is included
                for (int j = 0; j <= maxLength; j++) {
                    ArrayList<Trial> listOfCurrentLength = new ArrayList<Trial>();
                    trialsByLength.add(j, listOfCurrentLength);
                }
                trialsForCondition.add(i, trialsByLength);
            }
            retVal.put(trialType, trialsForCondition);
        }
        return retVal;
    }
    
    
    
    
    public static LinkedHashMap<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> initialiseTrials(String[] rows, int numberOfBands, int maxLength, String dirName) {
        LinkedHashMap<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> retVal = initMatrix(numberOfBands, maxLength);
        ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(AudioIndexMap.INDEX_ARRAY));
        
        for (String row : rows) {
            
            String[] parts = row.split(",");
            String[] wordBuffer = new String[maxLength];

            String word = "";
            String targetNonWord = "";
            int nOfSyllables = 0;
            TrialCondition condition = null;
            int length = 0;
            String foil = null;
            String bandLabel = null;

            for (String part : parts) {
                String help = part.trim();

                if (!help.isEmpty()) {
                    String[] attrVal = help.split(":");
                    switch (attrVal[0].trim()) {
                        case "word":
                            word = attrVal[1].trim();
                            break;
                        case "targetNonWord":
                            targetNonWord = attrVal[1].trim();
                            break;
                        case "nOfSyllables":
                            nOfSyllables = Integer.parseInt(attrVal[1].trim());
                            break;
                        case "condition":
                            condition = TrialCondition.stringToCondition(attrVal[1].trim());
                            break;
                        case "length":
                            length = Integer.parseInt(attrVal[1].trim());
                            break;
                        case "Word1":
                            wordBuffer[0] = attrVal[1].trim();
                            break;
                        case "Word2":
                            wordBuffer[1] = attrVal[1].trim();
                            break;
                        case "Word3":
                            wordBuffer[2] = attrVal[1].trim();
                            break;
                        case "Word4":
                            wordBuffer[3] = null;
                            if (thereIsSecondPart(attrVal)) {
                                wordBuffer[3] = attrVal[1].trim();
                            }
                            break;
                        case "Word5":
                            wordBuffer[4] = null;
                            if (thereIsSecondPart(attrVal)) {
                                wordBuffer[4] = attrVal[1].trim();
                            }
                            break;
                        case "Word6":
                            wordBuffer[5] = null;
                            if (thereIsSecondPart(attrVal)) {
                                wordBuffer[5] = attrVal[1].trim();
                            }
                            break;
                        case "foil":
                            if (thereIsSecondPart(attrVal)) {
                                foil = attrVal[1].trim();
                            }
                            break;
                        case "bandLabel":
                            bandLabel = attrVal[1].trim();
                            break;
                        default:
                            break;
                    }
                }
            }
          
            LinkedHashMap<String, WordType> words = makeClassifiedWordList(wordBuffer, word, targetNonWord, foil);
            //Trial(String word, String targetNonword, int nOfSyllables, TrialCondition condition, int length, LinkedHashMap<String,WordType> words, String bandLabel, int bandIndex, String dirName)
            
            int bandIndex = tmp.indexOf(bandLabel);
            Trial trial = new Trial(word, targetNonWord, nOfSyllables, condition, length, words, bandLabel, bandIndex, dirName);
            retVal.get(condition).get(bandIndex).get(length).add(trial);
        }
        return retVal;

    }
    
    // returns list of satisfying the requirements permutation pairs ((type-1, ..., type-tupleSize), (length-1, ..., length-tupleSize)) for a given bandIndex, 
    // such that there ARE trials of type-i of length-i for tuples of tupleSize,  for bandIndex
    public static ArrayList<PermutationPair> initialiseAvailabilityList(Map<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> trials,
            ArrayList<ArrayList<Integer>> lengthPermuations, ArrayList<ArrayList<TrialCondition>> trialTypePermutations, int bandIndex, int tupleSize) {

        ArrayList<PermutationPair> retVal = new ArrayList<PermutationPair>();
        for (int i = 0; i < trialTypePermutations.size(); i++) {
            ArrayList<TrialCondition> trialPermutation = trialTypePermutations.get(i);
            for (int j = 0; j < lengthPermuations.size(); j++) {
                ArrayList<Integer> sizePermutation = lengthPermuations.get(j);
                Boolean enough = true;
                int k = 0;
                while (enough && k < tupleSize) {
                    TrialCondition currentTrialCondition = trialPermutation.get(k);
                    Integer currentLength = sizePermutation.get(k);
                    //x[contdition][i][j] is the list of all trials satisfying "condition" for the band i of the length j
                    ArrayList<Trial> possibleTrials = trials.get(currentTrialCondition).get(bandIndex).get(currentLength);
                    if (possibleTrials.size() < 1) {
                        enough = false;
                    } else {
                        k++;
                    }
                }
                if (enough) {
                    PermutationPair permPair = new PermutationPair(trialPermutation, sizePermutation);
                    retVal.add(permPair);
                }
            }

        }
        return retVal;
    }

   
     public static LinkedHashMap<String, WordType> makeClassifiedWordList(String[] wrds, String word, String targetNonWord, String foil) {
        LinkedHashMap<String, WordType> retVal = new LinkedHashMap<String, WordType>();
        for (int i = 0; i < wrds.length; i++) {
            if (wrds[i] != null) {

                WordType wtype = WordType.NON_WORD;
                if (wrds[i].equals(word)) {
                    wtype = WordType.WORD;
                }
                if (wrds[i].equals(targetNonWord)) {
                    wtype = WordType.TARGET_NON_WORD;
                }
                if (foil != null) {
                    if (wrds[i].equals(foil)) {
                        wtype = WordType.FOIL;
                    }
                }
                retVal.put(wrds[i], wtype);
            }

        }
        return retVal;
    }

 
       public static ArrayList<PermutationPair> emptiedPossibilities(ArrayList<PermutationPair> oldList, Map<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> trialMatrix, int bandIndex, int tupleSize) {
        ArrayList<PermutationPair> toRemove = new ArrayList<PermutationPair>();
        listLoop:
        for (PermutationPair permPair : oldList) {
            for (int i = 0; i < tupleSize; i++) {
                TrialCondition trialType = permPair.trialTypes.get(i);
                Integer length = permPair.trialLengths.get(i);
                ArrayList<Trial> possibilities = trialMatrix.get(trialType).get(bandIndex).get(length);
                if (possibilities.size() < 1) {
                    toRemove.add(permPair);
                    continue listLoop;
                }
            }
        }
        return toRemove;
    }
       
       private static boolean thereIsSecondPart(String[] parts){
           if (parts.length<2) {
               return false;
           }
           if (parts[0] == null) {
               return false;
           }
           if (parts[0].isEmpty()) {
               return false;
           }
           return true;
       }

}
