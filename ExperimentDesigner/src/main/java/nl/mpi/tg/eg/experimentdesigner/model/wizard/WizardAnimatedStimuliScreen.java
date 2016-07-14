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

    public WizardAnimatedStimuliScreen() {
        super("AnimatedStimuli", "AnimatedStimuli", "AnimatedStimuli");
    }

    public WizardAnimatedStimuliScreen(String screenName, String[] screenTextArray, int maxStimuli, final boolean randomiseStimuli, final String buttonLabelEventTag, final String backgroundImage) {
        super(screenName, screenName, screenName);
        this.screenTitle = screenName;
        this.stimuliSet = screenTextArray;
        this.stimuliRandomTags = null;
        this.stimulusMsDelay = 0;
        this.stimuliCount = maxStimuli;
        this.randomiseStimuli = randomiseStimuli;
        this.buttonLabelEventTag = buttonLabelEventTag;
        this.backgroundImage = backgroundImage;
    }
    private static final String BASE_FILE_REGEX = "\\.[a-zA-Z34]+$";

    @Override
    public PresenterScreen populatePresenterScreen(Experiment experiment, boolean obfuscateScreenNames, long displayOrder) {
        final List<Stimulus> stimuliList = experiment.getStimuli();
        if (stimuliSet != null) {
            for (String stimulusLine : stimuliSet) {
                final HashSet<String> tagSet = new HashSet<>(Arrays.asList(new String[]{screenTitle}));
                final Stimulus stimulus;
                tagSet.addAll(Arrays.asList(stimulusLine.split("/")));
                stimulus = new Stimulus(stimulusLine.replace(".png", ""), null, null, stimulusLine, null, null, 0, tagSet, null);
                stimuliList.add(stimulus);
            }
        }
        super.populatePresenterScreen(experiment, obfuscateScreenNames, displayOrder);
        presenterScreen.setPresenterType(PresenterType.stimulus);
        List<PresenterFeature> presenterFeatureList = presenterScreen.getPresenterFeatureList();
        if (centreScreen) {
            presenterFeatureList.add(new PresenterFeature(FeatureType.centrePage, null));
        }
        final PresenterFeature loadStimuliFeature = new PresenterFeature(FeatureType.loadStimulus, null);
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
        // show stimulus a
        final PresenterFeature imageFeature1 = addStimulusImage(hasMoreStimulusFeature, 80, false, false);
        PresenterFeature nextButtonFeature1 = getNextButtonFeature();
        imageFeature1.getPresenterFeatureList().add(nextButtonFeature1);
        // show small stimulus on background
        final PresenterFeature imageFeature2 = addStimulusImage(nextButtonFeature1, 30, true, true);
        PresenterFeature nextButtonFeature2 = getNextButtonFeature();
        imageFeature2.getPresenterFeatureList().add(nextButtonFeature2);
//        nextButtonFeature1.getPresenterFeatureList().add(imageFeature2);
        // show stimulus b
        final PresenterFeature imageFeature3 = addStimulusImage(nextButtonFeature2, 80, false, false);
        PresenterFeature nextButtonFeature3 = getNextButtonFeature();
        imageFeature3.getPresenterFeatureList().add(nextButtonFeature3);
//        nextButtonFeature2.getPresenterFeatureList().add(imageFeature3);
        // show small stimulus a & b on background
        final PresenterFeature imageFeature4 = addStimulusImage(nextButtonFeature3, 30, true, true);

        PresenterFeature nextButtonFeature4 = getNextButtonFeature();
        imageFeature4.getPresenterFeatureList().add(nextButtonFeature4);
        final PresenterFeature nextStimulusFeature = new PresenterFeature(FeatureType.nextStimulus, null);
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.norepeat, "true");
        nextStimulusFeature.addFeatureAttributes(FeatureAttribute.eventTag, "nextStimulus" + screenTitle);
        nextButtonFeature4.getPresenterFeatureList().add(nextStimulusFeature);
        loadStimuliFeature.getPresenterFeatureList().add(hasMoreStimulusFeature);

        final PresenterFeature endOfStimulusFeature = new PresenterFeature(FeatureType.endOfStimulus, null);
        final PresenterFeature autoNextPresenter = new PresenterFeature(FeatureType.autoNextPresenter, null);
        endOfStimulusFeature.getPresenterFeatureList().add(autoNextPresenter);
        loadStimuliFeature.getPresenterFeatureList().add(endOfStimulusFeature);
        experiment.getPresenterScreen().add(presenterScreen);
        return presenterScreen;
    }

    private PresenterFeature getNextButtonFeature() {
        final PresenterFeature nextButtonFeature = new PresenterFeature(FeatureType.actionButton, buttonLabelEventTag);
        nextButtonFeature.addFeatureAttributes(FeatureAttribute.eventTag, buttonLabelEventTag);
        nextButtonFeature.addFeatureAttributes(FeatureAttribute.hotKey, "SPACE");
        return nextButtonFeature;
    }

    private PresenterFeature addStimulusImage(final PresenterFeature hasMoreStimulusFeature, final int stimulusSize, final boolean animate, final boolean background) {
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
        return imageFeature;
    }
}
