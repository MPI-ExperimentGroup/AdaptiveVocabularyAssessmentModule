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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment;

import java.util.ArrayList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.fasttrack.fintetuning.FineTuningBookkeepingStimulus;
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
public class UtilsTest {
    
    public UtilsTest() {
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
     * Test of shiftFIFO method, of class Utils.
     */
    @Test
    public void testShiftFIFO() {
        System.out.println("shiftFIFO");
        int[] fifo = {0,1,2,3,4,5,6};
        int newelement = 7;
        Utils.shiftFIFO(fifo, newelement);
        for (int i=0; i<7; i++){
            assertEquals(i+1,fifo[i]);
        }
    }

    /**
     * Test of detectLoop method, of class Utils.
     */
    @Test
    public void testDetectLoop() {
        System.out.println("detectLoop");
        int[] arr1 = {42,43,42,43,42,43,42};
        boolean result1 = Utils.detectLoop(arr1);
        assertEquals(true, result1);
        int[] arr2 = {42,43,42,43,42,43,45};
        boolean result2 = Utils.detectLoop(arr2);
        assertEquals(false, result2);
        int[] arr3 = {43,42,43,42,43,42,45,42};
        boolean result3 = Utils.detectLoop(arr3);
        assertEquals(false, result3);
    }
    
      /**
     * Test of detectLoop method, of class Utils.
     */
    @Test
    public void testTimeToCountVisits() {
        System.out.println("timeToCountVisits");
        boolean result1 = Utils.timeToCountVisits(34);
        assertEquals(true, result1);
        boolean result2 = Utils.timeToCountVisits(32);
        assertEquals(false, result2);
        
    }
    
     /**
     * Test of detectLoop method, of class Utils.
     */
    @Test
    public void testMostOftenVisited() {
        System.out.println("mostOftenVisited");
        int[] arr1 = {1,1,2,2,2,2,1};
        int result1 = Utils.mostOftenVisited(arr1);
        assertEquals(3+1, result1); // output is the band nummer
        // and bund nemmer is band index+1
        int[] arr2 = {0,1,2,2,2,1,1};
        // 2 or 3 ?
        // 0+1+2 vs 2+1+1
        // 3
        // 3 or 4
        //0+1+2+2 vs 1+1
        //3
        int result2 = Utils.mostOftenVisited(arr2);
        assertEquals(3+1, result2);
    }
    
     /**
     * Test of detectLoop method, of class Utils.
     */
    @Test
    public void testChooseBand() {
        System.out.println("mostChooseBAnd");
        int[] arr1 = {1,1,2,2,2,2,1};
        int result1 = Utils.chooseBand(2,3,arr1);
        // 1+1+2 vs 2+2+1
        assertEquals(3, result1);
        int result2 = Utils.chooseBand(3,4,arr1);
        // 1+1+2+2 vs 2+1
        assertEquals(3, result2);
    }

    /**
     * Test of testPrint method, of class Utils.
     */
    @Test
    public void testTestPrint() {
        System.out.println("testPrint");
        Vocabulary bands = null;
        //Utils.testPrint(bands);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of writeCsvFileFastTrack method, of class Utils.
     */
    @Test
    public void testWriteCsvFileFastTrack() throws Exception {
        System.out.println("writeCsvFileFastTrack");
        ArrayList<AtomBookkeepingStimulus> stimulae = null;
        int stopBand = 0;
        //Utils.writeCsvFileFastTrack(stimulae, stopBand);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of writeCsvFileFineTuningPreset method, of class Utils.
     */
    @Test
    public void testWriteCsvFileFineTuningPreset() throws Exception {
        System.out.println("writeCsvFileFineTuningPreset");
        ArrayList<ArrayList<FineTuningBookkeepingStimulus>> stimulae = null;
        //Utils.writeCsvFileFineTuningPreset(stimulae);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of orderFineTuningHistory method, of class Utils.
     */
    @Test
    public void testOrderFineTuningHistory() {
        System.out.println("orderFineTuningHistory");
        //ArrayList<ArrayList<FineTuningStimulus>> stimulae = null;
        ArrayList<FineTuningBookkeepingStimulus> expResult = null;
        //ArrayList<FineTuningStimulus> result = Utils.orderFineTuningHistory(stimulae);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertSortFineTuningHistory method, of class Utils.
     */
    @Test
    public void testInsertSortFineTuningHistory() {
        System.out.println("insertSortFineTuningHistory");
        ArrayList<FineTuningBookkeepingStimulus> stimulae = null;
        FineTuningBookkeepingStimulus stimulus = null;
        //Utils.insertSortFineTuningHistory(stimulae, stimulus);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of writeCsvFileFineTuningHistoryShortened method, of class Utils.
     */
    @Test
    public void testWriteCsvFileFineTuningHistoryShortened() throws Exception {
        System.out.println("writeCsvFileFineTuningHistoryShortened");
        ArrayList<ArrayList<FineTuningBookkeepingStimulus>> stimulae = null;
        //Utils.writeCsvFileFineTuningHistoryShortened(stimulae);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
