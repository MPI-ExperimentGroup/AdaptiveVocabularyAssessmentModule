/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.service;

import nl.mpi.tg.eg.experiment.client.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.Stimulus.Speaker;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class StimulusProviderTest {

    /**
     * Test of getSubset method, of class StimulusProvider.
     */
    @Test
    public void testGetSubset_0args() {
        System.out.println("getSubset");
        StimulusProvider instance = new StimulusProvider();
        instance.getSubset();
        checkStimulus(instance, 12);
    }

    private void checkStimulus(StimulusProvider instance, final int expectedSpeakerCount) {
        int speakerCount = 0;
        int wordCount = 0;
        while (instance.hasNextStimulus()) {
            final Stimulus nextStimulus = instance.getNextStimulus();
            if (nextStimulus.getSpeaker().equals(Speaker.hielke)) {
                speakerCount++;
            }
            if (nextStimulus.getWord().equals("kijf")) {
                wordCount++;
            }
            System.out.println("nextStimulus: " + nextStimulus.getSpeaker() + ", " + nextStimulus.getWord() + ", " + nextStimulus.getSpeakerSimilarity());
        }
        assertEquals(expectedSpeakerCount, speakerCount);
        assertEquals(6, wordCount);
    }

    /**
     * Test of getSubset method, of class StimulusProvider.
     */
    @Test
    public void testGetSubset_StimulusSimilarity() {
        System.out.println("getSubset");
        StimulusProvider instance = new StimulusProvider();
        instance.getSubset(Stimulus.Similarity.diff);
        checkStimulus(instance, 36);
    }
}
