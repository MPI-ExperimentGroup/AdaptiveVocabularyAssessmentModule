/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.util.List;
import nl.mpi.tg.eg.experiment.client.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.Stimulus;

/**
 * @since Jul 23, 2016 2:14:16 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MatchingStimuliGroup {

    private final Stimulus correctStimulus;
    private int stimulusIndex;
    private final List<Stimulus> stimulusArray;
    final boolean randomise;
    final int repeatCount;
    final TimedStimulusListener hasMoreStimulusListener;
    final TimedStimulusListener endOfStimulusListener;

    public MatchingStimuliGroup(final Stimulus correctStimulus, final List<Stimulus> stimuliArray, boolean randomise, int repeatCount, TimedStimulusListener hasMoreStimulusListener, TimedStimulusListener endOfStimulusListener) {
        this.correctStimulus = correctStimulus;
        this.stimulusArray = stimuliArray;
        this.randomise = randomise;
        this.repeatCount = repeatCount;
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = endOfStimulusListener;
//        stimuliArray.add(correctStimulus);
        stimulusIndex = -1;
    }

    public int getStimulusCount() {
        return stimulusArray.size();
    }

    public boolean isCorrect(Stimulus testableStimulus) {
        return correctStimulus.equals(testableStimulus);
    }

    public boolean getNextStimulus(final StimulusProvider stimulusProvider) {
        stimulusIndex++;
        stimulusProvider.setCurrentStimulus((stimulusArray.size() <= stimulusIndex) ? correctStimulus : stimulusArray.get(stimulusIndex));
        return (stimulusArray.size() > stimulusIndex);
    }

    public void showNextStimulus() {
        if ((stimulusArray.size() <= stimulusIndex)) {
            endOfStimulusListener.postLoadTimerFired();
        } else {
            hasMoreStimulusListener.postLoadTimerFired();
        }
    }
}
