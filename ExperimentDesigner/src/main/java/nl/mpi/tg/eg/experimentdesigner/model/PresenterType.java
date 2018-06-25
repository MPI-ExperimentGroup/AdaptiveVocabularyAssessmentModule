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

import java.util.ArrayList;
import java.util.Arrays;
import static nl.mpi.tg.eg.experimentdesigner.model.FeatureType.*;

/**
 * @since Aug 18, 2015 4:16:06 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public enum PresenterType {

    transmission(new FeatureType[]{
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        sendGroupStoredMessage,
        showStimuliReport,
        sendStimuliReport, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        clearCurrentScore,
        resetStimulus,
        playVideo,
        rewindVideo,
        pauseVideo,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, activateRandomItem, withStimuli, eachStimulus,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        loadStimulus, loadSdCardStimulus,
        localStorageData,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        //        popupMessage,
        stimulusAudio,
        stimulusImage,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulusGrid, matchingStimulusGrid,
        showStimulus,
        pause,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        multipleUsers, singleUser,
        userInfo,
        menuItem
    }),
    metadata(new FeatureType[]{
        stimulusPresent,
        clearPage, activateRandomItem,
        randomMsPause, triggerListener, trigger,
        countdownLabel, playVideo,
        rewindVideo,
        pauseVideo, versionData,
        clearCurrentScore,
        resetStimulus,
        touchInputCaptureStart, cancelPauseTimers,
        displayCompletionCode,
        sendMetadata,
        prevStimulusButton,
        prevStimulus,
        touchInputStimulusButton,
        stimulusButton,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        showStimuliReport, htmlTokenText, sendStimuliReport,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold, withStimuli, eachStimulus,
        loadStimulus, loadSdCardStimulus,
        localStorageData,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        stimulusImage,
        stimulusAudio,
        //        popupMessage,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        userInfo,
        menuItem,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton
    }),
    preload(new FeatureType[]{
        versionData,
        withStimuli, eachStimulus,
        activateRandomItem,
        displayCompletionCode, sendMetadata,
        sendGroupStoredMessage,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        withMatchingStimulus,
        loadStimulus, loadSdCardStimulus,
        localStorageData,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        allMenuItems,
        nextStimulusButton, ratingButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        audioButton,
        userInfo,
        menuItem,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        stimulusPause,
        stimulusLabel,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        //        popupMessage,
        pause,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus}),
    stimulus(new FeatureType[]{
        showColourReport,
        activateRandomItem,
        displayCompletionCode, sendMetadata,
        versionData,
        submitTestResults,
        helpDialogue,
        localStorageData,
        userInfo,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        allMenuItems,
        menuItem,
        addKinTypeGui,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        preloadAllStimuli}),
    colourPicker(new FeatureType[]{
        row, plainText, stimulusPresent,
        countdownLabel, trigger, table, triggerListener, touchInputCaptureStart, column,
        stimulusMetadataField,
        htmlTokenText,
        showStimuliReport,
        sendStimuliReport,
        stimulusButton, touchInputStimulusButton, touchInputReportSubmit,
        stimulusHasResponse, cancelPauseTimers,
        displayCompletionCode,
        sendMetadata, prevStimulusButton,
        prevStimulus,
        hasGetParameter, activateRandomItem,
        randomMsPause, playVideo,
        rewindVideo,
        pauseVideo,
        helpDialogue,
        clearCurrentScore,
        resetStimulus,
        showColourReport,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold, withStimuli, eachStimulus,
        submitTestResults,
        image, htmlText, plainText,
        actionFooterButton, actionButton, targetButton, targetFooterButton, addPadding, centrePage, clearPage,
        startAudioRecorder, stopAudioRecorder, startAudioRecorderTag, endAudioRecorderTag,
        loadKinTypeStringDiagram, localStorageData,
        loadStimulus, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        versionData,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        //        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem}),
    colourReport(new FeatureType[]{
        activateRandomItem,
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        resetStimulus,
        playVideo,
        rewindVideo,
        pauseVideo,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadStimulus, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        //        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem}),
    kindiagram(new FeatureType[]{
        activateRandomItem,
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        resetStimulus,
        playVideo,
        rewindVideo,
        pauseVideo,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, loadSdCardStimulus,
        //        popupMessage,
        localStorageData,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        AnnotationTimelinePanel, VideoPanel,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        userInfo,
        stimulusImage,
        stimulusAudio
    }),
    menu(new FeatureType[]{
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        resetStimulus,
        playVideo,
        rewindVideo,
        pauseVideo,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, loadSdCardStimulus,
        //        popupMessage,
        localStorageData,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        userInfo,
        stimulusImage,
        stimulusAudio}),
    debug(new FeatureType[]{
        activateRandomItem,
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        resetStimulus,
        playVideo,
        rewindVideo,
        pauseVideo,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, loadSdCardStimulus,
        //        popupMessage,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        showCurrentMs,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData, eraseUsersDataButton,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        autoNextPresenter,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        loadKinTypeStringDiagram,
        stimulusImage,
        userInfo,
        stimulusAudio}),
    text(new FeatureType[]{
        htmlTokenText,
        versionData,
        randomMsPause, triggerListener, trigger, stimulusButton, logTimeStamp, touchInputStimulusButton, activateRandomItem, countdownLabel, stimulusPresent, playVideo,
        rewindVideo,
        pauseVideo,
        clearCurrentScore,
        resetStimulus,
        clearPage, showStimuliReport,
        sendStimuliReport, logTimeStamp, touchInputCaptureStart, touchInputReportSubmit,
        stimulusHasResponse, stimulusMetadataField,
        cancelPauseTimers,
        displayCompletionCode,
        sendMetadata, prevStimulusButton,
        prevStimulus,
        hasGetParameter,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadStimulus, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton, withStimuli, eachStimulus,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        pause,
        //        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        AnnotationTimelinePanel, VideoPanel,
        stimulusImage,
        stimulusAudio,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        userInfo,
        menuItem
    }
    ),
    timeline(new FeatureType[]{
        sendStimuliReport,
        activateRandomItem,
        triggerListener, randomMsPause, trigger,
        countdownLabel, stimulusPresent, playVideo,
        rewindVideo,
        pauseVideo, versionData,
        clearCurrentScore,
        resetStimulus,
        clearPage,
        prevStimulusButton,
        htmlTokenText, logTimeStamp, showStimuliReport, stimulusButton, touchInputStimulusButton, sendMetadata, prevStimulus,
        stimulusMetadataField,
        cancelPauseTimers,
        displayCompletionCode,
        stimulusHasResponse, touchInputReportSubmit, logTimeStamp, touchInputCaptureStart,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        belowThreshold,
        loadKinTypeStringDiagram, localStorageData,
        loadStimulus, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, saveMetadataButton, createUserButton, selectUserMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableStimulusButtons,
        disableStimulusButtons,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideStimulusButtons,
        showStimulusButtons,
        ratingFooterButton,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        generateCompletionCode,
        sendAllData,
        eraseLocalStorageOnWindowClosing,
        stimulusRatingButton, clearStimulus, ratingButton,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        stimulusPause, withStimuli, eachStimulus,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
        autoNextPresenter,
        audioButton,
        showStimulusGrid, matchingStimulusGrid,
        showStimulus,
        pause,
        //        popupMessage,
        logTimeStamp,
        onError,
        onSuccess,
        multipleUsers, singleUser,
        kinTypeStringDiagram, editableKinEntitesDiagram,
        stimulusImage,
        stimulusAudio,
        userInfo,
        menuItem});

    private final FeatureType[] featureTypes;

    private PresenterType(FeatureType[] excludedFeatureTypes) {
        ArrayList<FeatureType> features = new ArrayList<>();
        features.addAll(Arrays.asList(FeatureType.values()));
        for (FeatureType excludedFeature : excludedFeatureTypes) {
            features.remove(excludedFeature);
        }
        this.featureTypes = features.toArray(new FeatureType[features.size()]);
    }

    public FeatureType[] getFeatureTypes() {
        return featureTypes;
    }
}
