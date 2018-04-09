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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
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
public class BookkeepingStimulusTest {

    public final BookkeepingStimulus<BandStimulus> instance;
     public final BandStimulus stimulus;

    public BookkeepingStimulusTest() {
        String uniqueId = "smoer";
        String label = "smoer";

        this.stimulus = new BandStimulus(uniqueId, new Stimulus.Tag[0], label, "", 900, "aud", "vid", "img",
                "a,b,c", "b,c", "plus10db", 10);
        this.instance = new BookkeepingStimulus<BandStimulus>(stimulus);
        
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
     * Test of getStimulus method, of class BookkeepingStimulus.
     */
    @Test
    public void testGetStimulus() {
        System.out.println("getStimulus");
        BandStimulus result = instance.getStimulus();
        assertEquals(this.stimulus, result);
    }

    /**
     * Test of getReaction method, of class BookkeepingStimulus.
     */
    @Test
    public void testGetSetReaction() {
        System.out.println("getReaction");
        String result = instance.getReaction();
        assertEquals(null, result);
        this.instance.setReaction("false");
        assertEquals("false",instance.getReaction());
        this.instance.setReaction("Disagree!");
        assertEquals("Disagree!",instance.getReaction());
    }

    /**
     * Test of getTimeStamp method, of class BookkeepingStimulus.
     */
    @Test
    public void testGetSetTimeStamp() {
        System.out.println("getTimeStamp");
        long result = instance.getTimeStamp();
        assertEquals(0, result);
        long now=System.currentTimeMillis();
        this.instance.setTimeStamp(now);
        assertEquals(now, this.instance.getTimeStamp());
    }

    /**
     * Test of getCorrectness method, of class BookkeepingStimulus.
     */
    @Test
    public void testGetSetCorrectness() {
        System.out.println("getCorrectness");
        Boolean result = instance.getCorrectness();
       assertEquals(null, result);
        this.instance.setCorrectness(false);
        assertFalse(this.instance.getCorrectness());
        this.instance.setCorrectness(true);
        assertTrue(this.instance.getCorrectness());
    }

 
    /**
     * Test of toString method, of class BookkeepingStimulus.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "{fields=[stimulus, userReaction, correctness, timeStamp], stimulus=smoer, userReaction=null, correctness=null, timeStamp=0}";
        String result = this.instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toObject method, of class BookkeepingStimulus.
     */
    @Test
    public void testToObject() throws Exception{
        System.out.println("toObject");
        long now=System.currentTimeMillis();
        String input="{fields=[stimulus, userReaction, correctness, timeStamp], stimulus=smoer, userReaction=yes, correctness=false, timeStamp="+now+"}";
        LinkedHashMap<String,BandStimulus> map = new LinkedHashMap<String,BandStimulus>();
        map.put("smoer", this.stimulus);
        BookkeepingStimulus<BandStimulus> result = this.instance.toObject(input, map);
        assertEquals("yes",result.getReaction());
        assertFalse(result.getCorrectness());
        assertEquals(now,result.getTimeStamp());
        assertEquals(this.stimulus, result.getStimulus());
    }

}
