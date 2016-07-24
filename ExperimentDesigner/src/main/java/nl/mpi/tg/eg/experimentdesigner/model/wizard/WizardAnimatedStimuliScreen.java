/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute;
import nl.mpi.tg.eg.experimentdesigner.model.FeatureType;
import nl.mpi.tg.eg.experimentdesigner.model.Metadata;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterFeature;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterType;
import nl.mpi.tg.eg.experimentdesigner.model.RandomGrouping;
import nl.mpi.tg.eg.experimentdesigner.model.Stimulus;

/**
 * @since Jul 12, 2016 4:01:37 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardAnimatedStimuliScreen extends AbstractWizardScreen {

    private String[] stimuliSet = null;
    private String[] stimuliRandomTags = null;
    private int stimulusMsDelay = 0;
    private boolean randomiseStimuli = false;
    private int stimuliCount = 1;
    private String buttonLabelEventTag;
    private String backgroundImage;
    private boolean sdCardStimuli;
    private boolean isSecondTask = false;

    public WizardAnimatedStimuliScreen() {
        super("AnimatedStimuli", "AnimatedStimuli", "AnimatedStimuli");
    }

    public WizardAnimatedStimuliScreen(String screenName, String[] screenTextArray, boolean sdCardStimuli, int maxStimuli, final boolean randomiseStimuli, final String buttonLabelEventTag, final String backgroundImage, boolean isSecondTask) {
        super(screenName, screenName, screenName);
        this.screenTitle = screenName;
        this.stimuliSet = screenTextArray;
        this.stimuliRandomTags = null;
        this.stimulusMsDelay = 0;
        this.stimuliCount = maxStimuli;
        this.randomiseStimuli = randomiseStimuli;
        this.buttonLabelEventTag = buttonLabelEventTag;
        this.backgroundImage = backgroundImage;
        this.sdCardStimuli = sdCardStimuli;
        this.isSecondTask = isSecondTask;
    }
    private static final String BASE_FILE_REGEX = "\\.[a-zA-Z34]+$";

    @Override
    public PresenterScreen populatePresenterScreen(Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        final List<Stimulus> stimuliList = experiment.getStimuli();
        if (stimuliSet != null) {
            for (String stimulusLine : stimuliSet) {
                final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{screenTitle}));
                final Stimulus stimulus;
                tagSet.addAll(Arrays.asList(stimulusLine.replaceAll(":", "/").split("/")));
//                final String[] splitLine = stimulusLine.split(":");
//                final String audioPath = splitLine[1];
//                final String imagePath = splitLine[0];
                stimulus = new Stimulus(stimulusLine.replace(".png", ""), stimulusLine.replace(".png", ""), null, stimulusLine, null, stimulusLine.replace(".png", ""), 0, tagSet, null);
                stimuliList.add(stimulus);
            }
        }
        super.populatePresenterScreen(experiment, obfuscateScreenNames, displayOrder);
        presenterScreen.setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        if (centreScreen) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature((!sdCardStimuli) ? FeatureType.loadStimulus : FeatureType.loadSdCardStimulus, null);
        loadStimuliFeature.addStimulusTag(screenTitle);
        final RandomGrouping randomGrouping = new RandomGrouping();
        if (stimuliRandomTags != null) {
            for (String randomTag : stimuliRandomTags) {
                randomGrouping.addRandomTag(randomTag);
            }
            final String metadataFieldname = "groupAllocation_" + getScreenTag();
            randomGrouping.setStorageField(metadataFieldname);
            loadStimuliFeature.setRandomGrouping(randomGrouping);
            experiment.getMetadata().add(new Metadata(metadataFieldname, metadataFieldname, ".*", ".", false, null));
        }
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.eventTag, screenTitle);
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(randomiseStimuli));
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
        loadStimuliFeature.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(stimuliCount));
        presenterFeatureList.add(loadStimuliFeature);
        final PresenterFeature hasMoreStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
        // start the audio recorder
        final PresenterFeature startRecorderFeature = new PresenterFeature(FeatureType.startAudioRecorder, null);
        startRecorderFeature.addFeatureAttributes(FeatureAttribute.wavFormat, "true");
        startRecorderFeature.addFeatureAttributes(FeatureAttribute.filePerStimulus, "false");
        startRecorderFeature.addFeatureAttributes(FeatureAttribute.eventTag, this.screenTitle);
        hasMoreStimulusFeature.getPresenterFeatureList().add(startRecorderFeature);

        if (!isSecondTask) {
            final PresenterFeature withMatchingStimulus = new PresenterFeature(FeatureType.withMatchingStimulus, null);
            withMatchingStimulus.addFeatureAttributes(FeatureAttribute.matchingRegex, MATCHING_REGEX);
            withMatchingStimulus.addFeatureAttributes(FeatureAttribute.maxStimuli, "1000");
            withMatchingStimulus.addFeatureAttributes(FeatureAttribute.repeatCount, "1");
            withMatchingStimulus.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(randomiseStimuli));
            withMatchingStimulus.addFeatureAttributes(FeatureAttribute.eventTag, "");

            final PresenterFeature hasMoreMatchingStimulusFeature = new PresenterFeature(FeatureType.hasMoreStimulus, null);
            final PresenterFeature endOfMatchingStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);

            withMatchingStimulus.getPresenterFeatureList().add(hasMoreMatchingStimulusFeature);
            withMatchingStimulus.getPresenterFeatureList().add(endOfMatchingStimulusFeature);
            hasMoreStimulusFeature.getPresenterFeatureList().add(withMatchingStimulus);

            // show stimulus a
            final PresenterFeature imageFeature1 = addStimulusImage(hasMoreMatchingStimulusFeature, 80, false, false, false);
            PresenterFeature nextButtonFeature1 = getNextButtonFeature();
            imageFeature1.getPresenterFeatureList().add(nextButtonFeature1);

            final PresenterFeature endAudioRecorderTagFeature1 = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
            endAudioRecorderTagFeature1.addFeatureAttributes(FeatureAttribute.eventTier, "1");
            endAudioRecorderTagFeature1.addFeatureAttributes(FeatureAttribute.eventTag, "no background");
            nextButtonFeature1.getPresenterFeatureList().add(endAudioRecorderTagFeature1);

            final PresenterFeature startTagFeature1 = new PresenterFeature(FeatureType.startAudioRecorderTag, null);
            startTagFeature1.addFeatureAttributes(FeatureAttribute.eventTier, "1");
            imageFeature1.getPresenterFeatureList().add(startTagFeature1);

            // show small stimulus on background
            final PresenterFeature imageFeature2 = addStimulusImage(nextButtonFeature1, 30, true, true, true);
            PresenterFeature nextButtonFeature2 = getNextButtonFeature();
            imageFeature2.getPresenterFeatureList().add(nextButtonFeature2);
//        nextButtonFeature1.getPresenterFeatureList().add(imageFeature2);

            final PresenterFeature endAudioRecorderTagFeature2 = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
            endAudioRecorderTagFeature2.addFeatureAttributes(FeatureAttribute.eventTier, "2");
            endAudioRecorderTagFeature2.addFeatureAttributes(FeatureAttribute.eventTag, "bounce and background");
            nextButtonFeature2.getPresenterFeatureList().add(endAudioRecorderTagFeature2);
            nextButtonFeature2.getPresenterFeatureList().add(new PresenterFeature(FeatureType.nextMatchingStimulus, null));

            final PresenterFeature startTagFeature2 = new PresenterFeature(FeatureType.startAudioRecorderTag, null);
            startTagFeature2.addFeatureAttributes(FeatureAttribute.eventTier, "2");
            imageFeature2.getPresenterFeatureList().add(startTagFeature2);

            // show small stimulus a & b on background
            final PresenterFeature imageFeature4 = addStimulusGrid(endOfMatchingStimulusFeature, 30, true, true, true);

            PresenterFeature nextButtonFeature4 = getNextButtonFeature();
            imageFeature4.getPresenterFeatureList().add(nextButtonFeature4);

            final PresenterFeature endAudioRecorderTagFeature4 = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
            endAudioRecorderTagFeature4.addFeatureAttributes(FeatureAttribute.eventTier, "4");
            endAudioRecorderTagFeature4.addFeatureAttributes(FeatureAttribute.eventTag, "exit group");
            nextButtonFeature4.getPresenterFeatureList().add(endAudioRecorderTagFeature4);

            final PresenterFeature startTagFeature4 = new PresenterFeature(FeatureType.startAudioRecorderTag, null);
            startTagFeature4.addFeatureAttributes(FeatureAttribute.eventTier, "4");
            imageFeature4.getPresenterFeatureList().add(startTagFeature4);

            final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
            nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + screenTitle);
            nextButtonFeature4.getPresenterFeatureList().add(nextStimulusFeature);
            loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        } else {
            // show group without background or animation and play code audio
            final PresenterFeature gridFeature = addStimulusGrid(hasMoreStimulusFeature, 100, false, false, false);

            final PresenterFeature stimulusCodeAudio = new PresenterFeature(FeatureType.stimulusCodeAudio, null);
            stimulusCodeAudio.addFeatureAttributes(FeatureAttribute.codeFormat, "<code>_question");
            stimulusCodeAudio.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            gridFeature.getPresenterFeatureList().add(stimulusCodeAudio);
            if (!isSecondTask) {
                PresenterFeature nextButtonFeature4 = getNextButtonFeature();
                gridFeature.getPresenterFeatureList().add(nextButtonFeature4);

                final PresenterFeature endAudioRecorderTagFeature4 = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
                endAudioRecorderTagFeature4.addFeatureAttributes(FeatureAttribute.eventTier, "4");
                endAudioRecorderTagFeature4.addFeatureAttributes(FeatureAttribute.eventTag, "exit group");
                nextButtonFeature4.getPresenterFeatureList().add(endAudioRecorderTagFeature4);

                final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
                nextStimulusFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
                nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + screenTitle);
                nextButtonFeature4.getPresenterFeatureList().add(nextStimulusFeature);
            }
            final PresenterFeature startTagFeature4 = new PresenterFeature(FeatureType.startAudioRecorderTag, null);
            startTagFeature4.addFeatureAttributes(FeatureAttribute.eventTier, "4");
            gridFeature.getPresenterFeatureList().add(startTagFeature4);

            // show correct image and sound with background
            loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);
        }
        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        endOfStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.stopAudioRecorder, null));
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.autoNextPresenter, null);
        endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(presenterScreen);
        return presenterScreen;
    }
    private static final String MATCHING_REGEX = "([^_]*_)*";

    private PresenterFeature getNextButtonFeature() {
        final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, buttonLabelEventTag);
        nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, buttonLabelEventTag);
        nextButtonFeature.addFeatureAttributes(FeatureAttribute.styleName, "hiddenBottomRight");
        nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, "SPACE");
        return nextButtonFeature;
    }

    private PresenterFeature addStimulusImage(final PresenterFeature hasMoreStimulusFeature, final int stimulusSize, final boolean animate, final boolean background, final boolean playSound) {
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        final PresenterFeature imageFeature = new PresenterFeature(FeatureType.stimulusImage, null);
        imageFeature.addFeatureAttributes(FeatureAttribute.maxHeight, Integer.toString(stimulusSize));
        imageFeature.addFeatureAttributes(FeatureAttribute.maxWidth, Integer.toString(stimulusSize));
        imageFeature.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        imageFeature.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(stimulusMsDelay));
        imageFeature.addFeatureAttributes(FeatureAttribute.animate, (animate) ? "bounce" : "none");
        if (background) {
            final PresenterFeature backgroundImageFeature = new PresenterFeature(FeatureType.backgroundImage, null);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.src, backgroundImage);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "1");
            hasMoreStimulusFeature.getPresenterFeatureList().add(backgroundImageFeature);
            backgroundImageFeature.getPresenterFeatureList().add(imageFeature);
        } else {
            hasMoreStimulusFeature.getPresenterFeatureList().add(imageFeature);
        }
        imageFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (playSound) {
            final PresenterFeature presenterFeature = new PresenterFeature(FeatureType.actionButton, "Play Sound");
            presenterFeature.addFeatureAttributes(FeatureAttribute.styleName, "hiddenTopRight");
            final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature1.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            presenterFeature.getPresenterFeatureList().add(presenterFeature1);
            final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            imageFeature.getPresenterFeatureList().add(presenterFeature2);
            imageFeature.getPresenterFeatureList().add(presenterFeature);
        }
        return imageFeature;
    }

    private PresenterFeature addStimulusGrid(final PresenterFeature hasMoreStimulusFeature, final int stimulusSize, final boolean animate, final boolean background, final boolean playSound) {
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        final PresenterFeature matchingStimulusGrid = new PresenterFeature(FeatureType.matchingStimulusGrid, null);
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(1000));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(randomiseStimuli));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.maxWidth, Integer.toString(stimulusSize));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.animate, (animate) ? "bounce" : "none");
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.columnCount, "10");
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(stimulusMsDelay));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.matchingRegex, MATCHING_REGEX);
        final PresenterFeature returnFeature;
        if (background) {
            final PresenterFeature backgroundImageFeature = new PresenterFeature(FeatureType.backgroundImage, null);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.src, backgroundImage);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "1");
            hasMoreStimulusFeature.getPresenterFeatureList().add(backgroundImageFeature);
            backgroundImageFeature.getPresenterFeatureList().add(matchingStimulusGrid);
            returnFeature = backgroundImageFeature;
        } else {
            hasMoreStimulusFeature.getPresenterFeatureList().add(matchingStimulusGrid);
            returnFeature = hasMoreStimulusFeature;
        }
        returnFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (playSound) {
            final PresenterFeature presenterFeature = new PresenterFeature(FeatureType.actionButton, "Play Sound");
            presenterFeature.addFeatureAttributes(FeatureAttribute.styleName, "hiddenTopRight");
            final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature1.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            presenterFeature.getPresenterFeatureList().add(presenterFeature1);
            final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            returnFeature.getPresenterFeatureList().add(presenterFeature2);
            returnFeature.getPresenterFeatureList().add(presenterFeature);
        }
        final PresenterFeature responseCorrect = new PresenterFeature(FeatureType.responseCorrect, null);
        matchingStimulusGrid.getPresenterFeatureList().add(responseCorrect);
        final PresenterFeature responseIncorrect = new PresenterFeature(FeatureType.responseIncorrect, null);
        matchingStimulusGrid.getPresenterFeatureList().add(responseIncorrect);

        final PresenterFeature endAudioRecorderTagFeatureCorrect = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
        endAudioRecorderTagFeatureCorrect.addFeatureAttributes(FeatureAttribute.eventTier, "4");
        endAudioRecorderTagFeatureCorrect.addFeatureAttributes(FeatureAttribute.eventTag, "correct group");
        responseCorrect.getPresenterFeatureList().add(endAudioRecorderTagFeatureCorrect);

        final PresenterFeature endAudioRecorderTagFeatureIncorrect = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
        endAudioRecorderTagFeatureIncorrect.addFeatureAttributes(FeatureAttribute.eventTier, "4");
        endAudioRecorderTagFeatureIncorrect.addFeatureAttributes(FeatureAttribute.eventTag, "incorrect group");
        responseIncorrect.getPresenterFeatureList().add(endAudioRecorderTagFeatureIncorrect);
        if (!isSecondTask) {
            final PresenterFeature nextStimulusCorrectFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusCorrectFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
            nextStimulusCorrectFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextCorrect" + screenTitle);
            responseCorrect.getPresenterFeatureList().add(nextStimulusCorrectFeature);

            final PresenterFeature nextStimulusIncorrectFeature = new PresenterFeature(FeatureType.nextStimulus, null);
            nextStimulusIncorrectFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
            nextStimulusIncorrectFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextIncorrect" + screenTitle);
            responseIncorrect.getPresenterFeatureList().add(nextStimulusIncorrectFeature);
        } else {
            addTask2StimulusGrid(responseIncorrect, 30, true, true, true);
            addTask2StimulusGrid(responseCorrect, 30, true, true, true);
        }
        return returnFeature;
    }

    private PresenterFeature addTask2StimulusGrid(final PresenterFeature hasMoreStimulusFeature, final int stimulusSize, final boolean animate, final boolean background, final boolean playSound) {
        hasMoreStimulusFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.clearPage, null));
        final PresenterFeature matchingStimulusGrid = new PresenterFeature(FeatureType.stimulusImage, null);
//        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.maxStimuli, Integer.toString(1000));
//        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.randomise, Boolean.toString(randomiseStimuli));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.maxWidth, Integer.toString(stimulusSize));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.animate, (animate) ? "bounce" : "none");
//        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.columnCount, "10");
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.msToNext, Integer.toString(stimulusMsDelay));
//        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.matchingRegex, MATCHING_REGEX);
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.maxHeight, Integer.toString(stimulusSize));
        matchingStimulusGrid.addFeatureAttributes(FeatureAttribute.percentOfPage, "0");
        final PresenterFeature returnFeature;
        if (background) {
            final PresenterFeature backgroundImageFeature = new PresenterFeature(FeatureType.backgroundImage, null);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.src, backgroundImage);
            backgroundImageFeature.addFeatureAttributes(FeatureAttribute.msToNext, "1");
            hasMoreStimulusFeature.getPresenterFeatureList().add(backgroundImageFeature);
            backgroundImageFeature.getPresenterFeatureList().add(matchingStimulusGrid);
            returnFeature = backgroundImageFeature;
        } else {
            hasMoreStimulusFeature.getPresenterFeatureList().add(matchingStimulusGrid);
            returnFeature = hasMoreStimulusFeature;
        }
        returnFeature.getPresenterFeatureList().add(new PresenterFeature(FeatureType.addPadding, null));
        if (playSound) {
            final PresenterFeature presenterFeature = new PresenterFeature(FeatureType.actionButton, "Play Sound");
            presenterFeature.addFeatureAttributes(FeatureAttribute.styleName, "hiddenTopRight");
            final PresenterFeature presenterFeature1 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature1.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            presenterFeature.getPresenterFeatureList().add(presenterFeature1);
            final PresenterFeature presenterFeature2 = new PresenterFeature(FeatureType.stimulusAudio, null);
            presenterFeature2.addFeatureAttributes(FeatureAttribute.msToNext, "0");
            returnFeature.getPresenterFeatureList().add(presenterFeature2);
            returnFeature.getPresenterFeatureList().add(presenterFeature);
        }

        PresenterFeature nextButtonFeature1 = getNextButtonFeature();
        returnFeature.getPresenterFeatureList().add(nextButtonFeature1);

        final PresenterFeature endAudioRecorderTagFeature1 = new PresenterFeature(FeatureType.endAudioRecorderTag, null);
        endAudioRecorderTagFeature1.addFeatureAttributes(FeatureAttribute.eventTier, "1");
        endAudioRecorderTagFeature1.addFeatureAttributes(FeatureAttribute.eventTag, "task 2 animated");
        nextButtonFeature1.getPresenterFeatureList().add(endAudioRecorderTagFeature1);

        final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + screenTitle);
        nextButtonFeature1.getPresenterFeatureList().add(nextStimulusFeature);
        return returnFeature;
    }
}
