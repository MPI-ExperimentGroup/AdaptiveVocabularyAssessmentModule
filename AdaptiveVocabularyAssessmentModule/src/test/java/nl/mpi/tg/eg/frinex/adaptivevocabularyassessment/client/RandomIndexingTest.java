/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author olhshk
 */
public class RandomIndexingTest {
    
    public RandomIndexingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updateAndGetIndices method, of class RandomIndexing.
     */
    @Test
    public void testUpdateAndGetIndices() {
        System.out.println("updateAndGetIndices");
        int startBand = 20;
        int nonWordsAvailable = 100;
        int averageNonwordPosition = 3;
        int nonWordsPerBlock = 4; // smotheness regulator
        RandomIndexing instance = new RandomIndexing(startBand, nonWordsPerBlock, averageNonwordPosition, nonWordsAvailable);
        ArrayList<Integer> result = instance.updateAndGetIndices();
        int allWords = (Constants.NUMBER_OF_BANDS - startBand)+1;
        int expectedFastTrackSequenceLength = (allWords* 3)/2;
        int expectedAmountOfNonWords = expectedFastTrackSequenceLength/3; 
        assertEquals(expectedAmountOfNonWords, result.size());
        HashSet<Integer> set = new HashSet(result);
        assertEquals(set.size(), result.size());
        for (Integer index: result) {
            assertTrue(index>=0);
            assertTrue(index<expectedFastTrackSequenceLength);
        }
    }

    /**
     * Test of getIndices method, of class RandomIndexing.
     */
    @Test
    public void testGetIndices() {
        System.out.println("getIndices");
        int startBand = 20;
        int nonWordsAvailable = 100;
        int averageNonwordPosition = 3;
        int nonWordsPerBlock = 4; // smotheness regulator
        RandomIndexing instance = new RandomIndexing(startBand, nonWordsPerBlock, averageNonwordPosition, nonWordsAvailable);
        ArrayList<Integer> result = instance.getIndices();
        assertEquals(null, result); // not initialised yet, look for testUpdateAndGetIndices for non empty index-list
    }

    /**
     * Test of updateFrequencesOfNonWordIndices method, of class RandomIndexing.
     */
    @Test
    public void testGetUpdateFrequencesOfNonWordIndices() {
        System.out.println("update and get FrequencesOfNonWordIndices");
        int startBand = 20;
        int nonWordsAvailable = 100;
        int averageNonwordPosition = 3;
        int nonWordsPerBlock = 4; // smotheness regulator
        RandomIndexing instance = new RandomIndexing(startBand, nonWordsPerBlock, averageNonwordPosition, nonWordsAvailable);
        ArrayList<Integer> result = instance.updateAndGetIndices();
        instance.updateFrequencesOfNonWordIndices();
        double[] frequences= instance.getFrequencesOfNonWordindices();
        int allWords = ((Constants.NUMBER_OF_BANDS - startBand)+1)*2;
        int expectedFastTrackSequenceLength = (allWords* 3)/2;
        assertEquals(expectedFastTrackSequenceLength, frequences.length);
        double oneThird = 1.0/3.0;
        System.out.println(Math.abs(frequences[expectedFastTrackSequenceLength-1]- oneThird) );
        assertTrue(Math.abs(frequences[expectedFastTrackSequenceLength-1]- oneThird)<0.05);
    }

  

    /**
     * Test of getFastTrackSequenceLength method, of class RandomIndexing.
     */
    @Test
    public void testGetSequenceLength() {
        System.out.println("getSequenceLength");
        int startBand = 20;
        int nonWordsAvailable = 100;
        int averageNonwordPosition = 3;
        int nonWordsPerBlock = 4; // smotheness regulator
        RandomIndexing instance = new RandomIndexing(startBand, nonWordsPerBlock, averageNonwordPosition, nonWordsAvailable);
        int allWords = ((Constants.NUMBER_OF_BANDS - startBand)+1)*2;
        int expectedFastTrackSequenceLength = (allWords*3)/2;
        assertEquals(expectedFastTrackSequenceLength, instance.getFastTrackSequenceLength());
    }

    /**
     * Test of generateRandomArray method, of class RandomIndexing.
     */
    @Test
    public void testGenerateRandomArray() {
        System.out.println("generateRandomArray");
        int n = Constants.WORDS_PER_BAND -1;
        int[] result = RandomIndexing.generateRandomArray(n);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<n; i++){
            list.add(result[i]);
        }
        HashSet<Integer> set = new HashSet(list);
        assertEquals(n, set.size()); // fails if the permutation is to short/long or gives repetitive values
        
        // checking if the Equality is implemented OK on Integers
        ArrayList<Integer> testEqualityList = new ArrayList<>(2);
        testEqualityList.add(1);
        testEqualityList.add(1);
        assertEquals(2, testEqualityList.size());
        HashSet<Integer> testEqualitySet = new HashSet(testEqualityList);
        assertEquals(1, testEqualitySet.size());
        
    }
    
}
