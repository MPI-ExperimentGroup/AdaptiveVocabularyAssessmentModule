/*
 * Copyright (C) 2015 Pivotal Software, Inc.
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
package nl.mpi.tg.eg.experimentdesigner.model;

import static nl.mpi.tg.eg.experimentdesigner.model.FeatureAttribute.*;

/**
 * this can be updated with the output of: grep match=
 * ~/Documents/ExperimentTemplate/gwt-cordova/src/main/xsl/config2java.xsl
 *
 * @since Aug 18, 2015 4:29:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl,
 */
public enum FeatureType {

    htmlText(false, true, null),
    text(false, true, null),
    image(false, false, new FeatureAttribute[]{width, src}),
    menuItem(false, true, new FeatureAttribute[]{target}),
    popupMessage(false, true, new FeatureAttribute[]{condition}),
    loadSubsetStimulus(false, false, new FeatureAttribute[]{setCount}),
    loadNoiseStimulus(false, false, null),
    optionButton(false, true, new FeatureAttribute[]{target}),
    //    endOfStimulusButton(false, true, new FeatureAttribute[]{eventTag, target}),
    padding(false, false, null),
    localStorageData(false, false, null),
    allMetadataFields(false, false, null),
    eraseLocalStorageButton(false, false, null),
    showCurrentMs(false, false, null),
    enableStimulusButtons(false, false, null),
    disableStimulusButtons(false, false, null),
    showStimulusProgress(false, false, null),
    hideStimulusButtons(false, false, null),
    showStimulusButtons(false, false, null),
    generateCompletionCode(false, false, null),
    sendAllData(false, false, null),
    eraseLocalStorageOnWindowClosing(false, false, null),
    nextStimulus(false, false, null),
    keepStimulus(false, false, null),
    removeStimulus(false, false, null),
    clearStimulus(false, false, null),
    centrePage(false, false, null),
    allMenuItems(false, false, null),
    nextStimulusButton(false, true, new FeatureAttribute[]{eventTag, condition}),
    autoNextStimulus(false, false, new FeatureAttribute[]{eventTag, condition}),
    conditionalHtml(false, true, new FeatureAttribute[]{condition}),
    addKinTypeGui(false, false, new FeatureAttribute[]{diagramName}),
    autoNextPresenter(false, false, new FeatureAttribute[]{target}),
    logTimeStamp(false, false, new FeatureAttribute[]{eventTag}),
    audioButton(false, false, new FeatureAttribute[]{eventTag, mp3, ogg, poster}),
    preloadAllStimuli(true, false, null),
    showStimulusGrid(true, false, new FeatureAttribute[]{columnCount, imageWidth, eventTag}, true, true),
    pause(true, false, new FeatureAttribute[]{timeToNext}),
    onError(true, false, null),
    onSuccess(true, false, null),
    kinTypeStringDiagram(true, false, new FeatureAttribute[]{timeToNext, kintypestring}),
    loadKinTypeStringDiagram(true, false, new FeatureAttribute[]{timeToNext, diagramName}),
    responseCorrect(true, false, new FeatureAttribute[]{timeToNext}),
    responseIncorrect(true, false, new FeatureAttribute[]{timeToNext}),
    hasMoreStimulus(true, false, new FeatureAttribute[]{timeToNext}),
    endOfStimulus(true, false, new FeatureAttribute[]{timeToNext}),
    stimulusImage(true, false, new FeatureAttribute[]{width, timeToNext}),
    stimulusAudio(true, false, new FeatureAttribute[]{timeToNext, mp3,}),
    VideoPanel(false, false, new FeatureAttribute[]{mp4, ogg, webm, width, poster}),
    AnnotationTimelinePanel(true, false, new FeatureAttribute[]{mp4, ogg, webm, poster, eventTag, columnCount, maxStimuli}, true, false),
    AudioRecorderPanel(false, false, new FeatureAttribute[]{wav}),
    userInfo(false, false, null),
    versionData(false, false, null),
    preventWindowClose(false, false, null);
    private final boolean canHaveFeatures;
    private final boolean canHaveText;
    private final boolean canHaveStimulus;
    private final FeatureAttribute[] featureAttributes;
    private final boolean hasCorrectIncorrect;

    private FeatureType(boolean canHaveFeatures, boolean canHaveText, FeatureAttribute[] featureAttributes) {
        this.canHaveFeatures = canHaveFeatures;
        this.canHaveText = canHaveText;
        this.featureAttributes = featureAttributes;
        this.hasCorrectIncorrect = false;
        this.canHaveStimulus = false;
    }

    private FeatureType(boolean canHaveFeatures, boolean canHaveText, FeatureAttribute[] featureAttributes, boolean canHaveStimulus, boolean hasCorrectIncorrect) {
        this.canHaveFeatures = canHaveFeatures;
        this.canHaveText = canHaveText;
        this.featureAttributes = featureAttributes;
        this.canHaveStimulus = canHaveStimulus;
        this.hasCorrectIncorrect = hasCorrectIncorrect;
    }
    
    public boolean canHaveFeatures() {
        return canHaveFeatures;
    }

    public boolean canHaveText() {
        return canHaveText;
    }

    public boolean canHaveStimulus() {
        return canHaveStimulus;
    }

    public FeatureAttribute[] getFeatureAttributes() {
        return featureAttributes;
    }

    public boolean requiresCorrectIncorrect() {
        return hasCorrectIncorrect;
    }
}
