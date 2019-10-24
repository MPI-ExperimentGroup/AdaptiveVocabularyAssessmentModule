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
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        clearPage,
        sendGroupStoredMessage,
        showStimuliReport,
        sendStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        clearCurrentScore,
        resetStimulus,
        playMedia,
        rewindMedia,
        pauseMedia,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, activateRandomItem, withStimuli, eachStimulus,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        localStorageData, stimuliValidation, addKeyboardDebug,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton,
        showCurrentMs,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        //        popupMessage,
        stimulusAudio,
        stimulusImage,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
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
        countdownLabel, playMedia,
        rewindMedia,
        pauseMedia, versionData,
        clearCurrentScore,
        resetStimulus,
        touchInputCaptureStart, cancelPauseTimers,
        displayCompletionCode,
        sendMetadata,
        prevStimulusButton,
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        touchInputStimulusButton,
        stimulusButton,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        showStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, sendStimuliReport,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold, withStimuli, eachStimulus,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        localStorageData, stimuliValidation, addKeyboardDebug,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
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
        logTimeStamp, logTokenText, actionTokenButton, habituationParadigmListener, triggerRandom, resetTrigger,
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
        withinThreshold,
        withMatchingStimulus,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        localStorageData, stimuliValidation, addKeyboardDebug,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        allMenuItems,
        nextStimulusButton,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        audioButton,
        userInfo,
        menuItem,
        showStimulus,
        showStimulusGrid, matchingStimulusGrid,
        cancelPauseAll, stimulusPause,
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
        preloadAllStimuli,
        showColourReport,
        activateRandomItem,
        displayCompletionCode, sendMetadata,
        versionData,
        submitTestResults,
        helpDialogue,
        localStorageData, stimuliValidation, addKeyboardDebug,
        userInfo,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
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
        region,
        regionClear,
        regionReplace,
        regionStyle,
        gotoPresenter,
        gotoNextPresenter,
        timerLabel, startTimer, clearTimer, logTimerValue, logTokenText, actionTokenButton, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger,
        row, plainText, stimulusPresent,
        countdownLabel, trigger, table, triggerListener, touchInputCaptureStart, column,
        stimulusMetadataField,
        requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText,
        showStimuliReport,
        sendStimuliReport,
        stimulusButton, touchInputStimulusButton, touchInputReportSubmit,
        stimulusHasResponse, cancelPauseTimers,
        displayCompletionCode,
        sendMetadata, prevStimulusButton,
        prevStimulus,
        hasGetParameter, activateRandomItem, showHtmlPopup,
        randomMsPause, playMedia,
        rewindMedia,
        pauseMedia,
        helpDialogue,
        clearCurrentScore,
        resetStimulus,
        showColourReport,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold, withStimuli, eachStimulus,
        submitTestResults,
        image, timerLabel, htmlText, plainText,
        actionFooterButton, actionButton, targetButton, targetFooterButton, addPadding, centrePage, clearPage,
        startAudioRecorder, stopAudioRecorder, startAudioRecorderTag, endAudioRecorderTag,
        loadKinTypeStringDiagram, localStorageData, stimuliValidation, addKeyboardDebug,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        versionData,
        eraseLocalStorageButton, eraseUsersDataButton,
        enableButtonGroup,
        disableButtonGroup,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        hideButtonGroup,
        showButtonGroup,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
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
        timerLabel, startTimer, clearTimer, logTimerValue, logTokenText, actionTokenButton, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger,
        activateRandomItem,
        versionData,
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus,
        clearPage,
        resetStimulus,
        playMedia,
        rewindMedia,
        pauseMedia,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, validateMetadata, transmitResults, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        loadKinTypeStringDiagram, localStorageData, stimuliValidation, addKeyboardDebug,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
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
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        clearPage,
        resetStimulus,
        playMedia,
        rewindMedia,
        pauseMedia,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, validateMetadata, transmitResults, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        //        popupMessage,
        localStorageData, stimuliValidation, addKeyboardDebug,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        AnnotationTimelinePanel, VideoPanel,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
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
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        clearPage,
        resetStimulus,
        playMedia,
        rewindMedia,
        pauseMedia,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, validateMetadata, transmitResults, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        //        popupMessage,
        localStorageData, stimuliValidation, addKeyboardDebug,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showCurrentMs,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
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
        randomMsPause, triggerListener, trigger, countdownLabel, stimulusPresent, clearCurrentScore,
        prevStimulusButton,
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        clearPage,
        resetStimulus,
        playMedia,
        rewindMedia,
        pauseMedia,
        touchInputReportSubmit,
        stimulusHasResponse,
        stimulusMetadataField,
        cancelPauseTimers, validateMetadata, transmitResults, generateCompletionCode, withStimuli, eachStimulus,
        sendGroupStoredMessage,
        displayCompletionCode,
        sendMetadata,
        showStimuliReport,
        sendStimuliReport, requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, stimulusButton,
        touchInputStimulusButton, touchInputCaptureStart,
        touchInputReportSubmit,
        stimulusHasResponse,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        responseCorrect, responseIncorrect, hasMoreStimulus, endOfStimulus,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        //        popupMessage,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        showCurrentMs,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData, eraseUsersDataButton,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        menuItem,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        logTimeStamp,
        audioButton,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
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
        requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText,
        versionData,
        randomMsPause, triggerListener, trigger, stimulusButton, logTimeStamp, touchInputStimulusButton, activateRandomItem, countdownLabel, stimulusPresent, playMedia,
        rewindMedia,
        pauseMedia,
        clearCurrentScore,
        resetStimulus,
        clearPage, showStimuliReport,
        sendStimuliReport, logTimeStamp, touchInputCaptureStart, touchInputReportSubmit,
        stimulusHasResponse, stimulusMetadataField,
        cancelPauseTimers,
        displayCompletionCode,
        sendMetadata, prevStimulusButton,
        prevStimulus, timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        hasGetParameter,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        loadKinTypeStringDiagram, localStorageData, stimuliValidation, addKeyboardDebug,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        withStimuli, eachStimulus,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
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
        countdownLabel, stimulusPresent, playMedia,
        rewindMedia,
        pauseMedia, versionData,
        clearCurrentScore,
        resetStimulus,
        clearPage,
        prevStimulusButton,
        requestNotification, hasMetadataValue, setMetadataValue, htmlTokenText, logTimeStamp, showStimuliReport, stimulusButton, touchInputStimulusButton, sendMetadata, prevStimulus,
        timerLabel, startTimer, compareTimer, habituationParadigmListener, triggerRandom, resetTrigger, clearTimer, logTimerValue, logTokenText, actionTokenButton,
        stimulusMetadataField,
        cancelPauseTimers,
        displayCompletionCode,
        stimulusHasResponse, touchInputReportSubmit, logTimeStamp, touchInputCaptureStart,
        showColourReport, submitTestResults, helpDialogue,
        submitGroupEvent, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel, groupMessageLabel, groupResponseStimulusImage,
        groupResponseFeedback, groupScoreLabel, groupChannelScoreLabel, groupNetworkActivity, groupNetwork, groupMemberCodeLabel, groupMemberLabel,
        groupMessageLabel, groupResponseStimulusImage, groupResponseFeedback, scoreLabel, scoreIncrement, scoreAboveThreshold, bestScoreAboveThreshold, totalScoreAboveThreshold,
        aboveThreshold,
        withinThreshold,
        loadKinTypeStringDiagram, localStorageData, stimuliValidation, addKeyboardDebug,
        loadStimulus, clearStimulusResponses, loadSdCardStimulus,
        allMetadataFields, metadataFieldConnection, metadataField, metadataFieldVisibilityDependant, metadataFieldDateTriggered, saveMetadataButton, createUserButton, switchUserIdButton, selectUserMenu, selectLocaleMenu,
        existingUserCheck,
        eraseLocalStorageButton, eraseUsersDataButton,
        showStimulusProgress, stimulusFreeText, backgroundImage, stimulusImageCapture,
        ratingFooterButton, ratingButton, ratingRadioButton, ratingCheckbox, stimulusRatingButton, stimulusRatingRadio, stimulusRatingCheckbox,
        endAudioRecorderTag, startAudioRecorder, targetFooterButton, actionFooterButton,
        validateMetadata, transmitResults, generateCompletionCode,
        redirectToUrl, sendAllData,
        eraseLocalStorageOnWindowClosing,
        clearStimulus,
        removeStimulus, removeMatchingStimulus, withMatchingStimulus, nextMatchingStimulus,
        keepStimulus,
        nextStimulus,
        allMenuItems,
        nextStimulusButton,
        regionCodeStyle, stimulusCodeImage, stimulusCodeAudio, stimulusCodeVideo,
        currentStimulusHasTag, sendGroupMessageButton, sendGroupMessage, stimulusHasRatingOptions,
        //        autoNextStimulus,
        addKinTypeGui,
        preloadAllStimuli,
        cancelPauseAll, stimulusPause, withStimuli, eachStimulus,
        stimulusLabel,
        responseCorrect,
        responseIncorrect,
        hasMoreStimulus,
        endOfStimulus,
        showCurrentMs,
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
