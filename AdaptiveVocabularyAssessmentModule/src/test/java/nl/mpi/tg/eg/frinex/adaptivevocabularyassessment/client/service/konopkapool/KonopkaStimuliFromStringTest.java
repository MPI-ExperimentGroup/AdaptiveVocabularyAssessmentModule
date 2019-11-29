/*
 * Copyright (C) 2019 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.konopkapool;

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
public class KonopkaStimuliFromStringTest {
    
    public KonopkaStimuliFromStringTest() {
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
     * Test of parseTrialsInputCSVStringIntoXml method, of class KonopkaStimuliFromString.
     */
    @Test
    public void testParseTrialsInputCSVStringIntoXml() throws Exception {
        System.out.println("parseTrialsInputCSVStringIntoXml");
        KonopkaStimuliFromString instance = new KonopkaStimuliFromString();
        String result = instance.parseTrialsInputCSVStringIntoXml(KonopkaCsv.CSV_STRING,"Pictures/", "main");
        assertTrue(result.startsWith("<stimulus "));
        assertTrue(result.endsWith(" />\n"));
        System.out.println(result);
        
      
    }
    
}