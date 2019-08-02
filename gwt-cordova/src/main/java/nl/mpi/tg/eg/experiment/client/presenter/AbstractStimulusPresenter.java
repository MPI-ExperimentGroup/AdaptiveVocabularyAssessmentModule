/*
 * Copyright (C) 2015 
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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.mpi.tg.eg.experiment.client.ApplicationController;
import nl.mpi.tg.eg.experiment.client.ApplicationController.ApplicationState;
import nl.mpi.tg.eg.experiment.client.exception.DataSubmissionException;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.CancelableStimulusListener;
import nl.mpi.tg.eg.experiment.client.listener.CurrentStimulusListener;
import nl.mpi.tg.eg.experiment.client.listener.DataSubmissionListener;
import nl.mpi.tg.eg.experiment.client.listener.GroupActivityListener;
import nl.mpi.tg.eg.experiment.client.listener.HabituationParadigmListener;
import nl.mpi.tg.eg.experiment.client.listener.MediaSubmissionListener;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.listener.StimulusButton;
import nl.mpi.tg.eg.experiment.client.listener.TouchInputCapture;
import nl.mpi.tg.eg.experiment.client.listener.TouchInputZone;
import nl.mpi.tg.eg.experiment.client.listener.TriggerListener;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.model.DataSubmissionResult;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.SdCardStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.StimulusFreeText;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.GroupParticipantService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.service.MatchingStimuliGroup;
import nl.mpi.tg.eg.experiment.client.service.MetadataFieldProvider;
import nl.mpi.tg.eg.experiment.client.service.SdCardImageCapture;
import nl.mpi.tg.eg.experiment.client.service.TimedEventMonitor;
import nl.mpi.tg.eg.experiment.client.service.TimerService;
import nl.mpi.tg.eg.frinex.common.StimuliProvider;
import nl.mpi.tg.eg.experiment.client.util.HtmlTokenFormatter;
import nl.mpi.tg.eg.experiment.client.view.ComplexView;
import nl.mpi.tg.eg.experiment.client.view.MetadataFieldWidget;
import nl.mpi.tg.eg.experiment.client.view.TimedStimulusView;
import nl.mpi.tg.eg.frinex.common.model.StimulusSelector;

/**
 * @since Jun 23, 2015 11:36:37 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractStimulusPresenter extends AbstractTimedPresenter implements Presenter {

    private static final String LOADED_STIMULUS_LIST = "loadedStimulusList";
    private static final String CONSUMED_TAGS_LIST = "consumedTagsList";
    private static final String SEEN_STIMULUS_INDEX = "seenStimulusIndex";
    private final Duration duration;
    private final TimedEventMonitor timedEventMonitor;
    final ArrayList<StimulusButton> stimulusButtonList = new ArrayList<>();
    private final ArrayList<Timer> pauseTimers = new ArrayList<>();
    private CurrentStimulusListener hasMoreStimulusListener;
    private TimedStimulusListener endOfStimulusListener;
    final private ArrayList<PresenterEventListner> nextButtonEventListnerList = new ArrayList<>();
    private final ArrayList<StimulusFreeText> stimulusFreeTextList = new ArrayList<>();
    private final HashMap<String, TriggerListener> triggerListeners = new HashMap<>();
    MatchingStimuliGroup matchingStimuliGroup = null;
    private boolean hasSubdirectories = false;
    private TouchInputCapture touchInputCapture = null;

    protected enum AnimateTypes {
        bounce, none, stimuliCode
    }

    public AbstractStimulusPresenter(RootLayoutPanel widgetTag, DataSubmissionService submissionService, UserResults userResults, final LocalStorage localStorage, final TimerService timerService) {
        super(widgetTag, new TimedStimulusView(), submissionService, userResults, localStorage, timerService);
        duration = new Duration();
        timedEventMonitor = new TimedEventMonitor(duration);

//        final Label debugLabel = new Label();
//        debugLabel.setStyleName("debugLabel");
//        new Timer() {
//            public void run() {
//                todo: verify that these are cleared correctly: domHandlerArray scaledImagesList
//                final String debugString = "BEL:" + backEventListners.size() + "PT:" + pauseTimers.size() + "NB:" + nextButtonEventListnerList.size() + "FT:" + stimulusFreeTextList.size() + "TL:" + triggerListeners.size() + "BL:" + stimulusButtonList.size();
//                debugLabel.setText(debugString);
//                timedStimulusView.addWidget(debugLabel);
//                schedule(1000);
//            }
//        }.schedule(1000);
    }

    @Override
    public void setState(AppEventListner appEventListner, ApplicationController.ApplicationState prevState, ApplicationController.ApplicationState nextState) {
        super.setState(appEventListner, prevState, null);
        this.nextState = nextState;
    }

    protected void loadSdCardStimulus(final String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final CurrentStimulusListener hasMoreStimulusListener,
            final TimedStimulusListener endOfStimulusListener) {
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        submissionService.submitTimestamp(userResults.getUserData().getUserId(), eventTag, duration.elapsedMillis());
        final String storedStimulusList = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + subDirectory);
        int seenStimulusIndextemp;
        try {
            seenStimulusIndextemp = Integer.parseInt(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag()));
        } catch (NumberFormatException exception) {
            seenStimulusIndextemp = 0;
        }
        final int seenStimulusIndex = seenStimulusIndextemp;
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                if (subDirectory == null || subDirectory.isEmpty()) {
                    endOfStimulusListener.postLoadTimerFired();
                } else {
                    localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                    loadSdCardStimulus(eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, endOfStimulusListener);
                }
            }
        };
        ArrayList<String> directoryTagArray = new ArrayList<>();
        if (subDirectory == null || subDirectory.isEmpty()) {
            for (StimulusSelector directoryTag : selectionTags) {
                directoryTagArray.add(directoryTag.getTag().name().substring("tag_".length()));
            }
        } else {
            // if a sub directory is passed then only load stimuli from that directory
            directoryTagArray.add(subDirectory);
        }
        final List<String[]> directoryList = new ArrayList<>();
        // @todo: add the limits for maxStimulusCount and maxStimulusPerTag -
        final HtmlTokenFormatter htmlTokenFormatter = new HtmlTokenFormatter(null, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray);
        stimulusProvider.getSdCardSubset(directoryTagArray, directoryList, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                clearPage();
                if (directoryList.isEmpty()) {
                    showStimulus(stimulusProvider, null, 0);
                } else {
                    hasSubdirectories = true;
                    for (final String[] directory : directoryList) {
                        final boolean directoryCompleted = Boolean.parseBoolean(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "completed-directory-" + directory[0] + "-" + getSelfTag()));
                        timedStimulusView.addOptionButton(new PresenterEventListner() {
                            @Override
                            public String getLabel() {
                                return directory[1] + ((directoryCompleted) ? " (complete)" : "");
                            }

                            @Override
                            public String getStyleName() {
                                return null;
                            }

                            @Override
                            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                                // show the subdirectorydirectory[0], 
                                localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), directory[0]);
                                loadSdCardStimulus(directory[1], selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, new TimedStimulusListener() {
                                    @Override
                                    public void postLoadTimerFired() {
                                        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "completed-directory-" + directory[0] + "-" + getSelfTag(), Boolean.toString(true));
                                        // go back to the initial directory 
                                        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                                        loadSdCardStimulus(eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName, stimulusProvider, hasMoreStimulusListener, endOfStimulusListener);
                                    }
                                });
                            }

                            @Override
                            public int getHotKey() {
                                return -1;
                            }
                        });
                        timedStimulusView.addPadding();
                    }
                }
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                timedStimulusView.addText("Stimulus loading error");
                // @todo: when sdcard stimuli sub directories are used:  + "Plase make sure that the directory " + stimuliDirectory + "/" + cleanedTag + " exists and has stimuli files."
            }
        }, storedStimulusList, seenStimulusIndex, htmlTokenFormatter);
    }

    @Override
    protected boolean allowBackAction(final AppEventListner appEventListner) {
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        if (subDirectory != null) {
            if (!subDirectory.isEmpty()) {
                localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag(), "");
                setContent(appEventListner);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    protected void withStimuli(String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final TimedStimulusListener beforeStimuliListener,
            final CurrentStimulusListener eachStimulusListener,
            final TimedStimulusListener afterStimuliListener
    ) {
        loadStimulus(stimulusProvider, eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName);
        this.hasMoreStimulusListener = null;
        this.endOfStimulusListener = null;
        beforeStimuliListener.postLoadTimerFired();
        while (stimulusProvider.hasNextStimulus(0)) {
            eachStimulusListener.postLoadTimerFired(stimulusProvider, stimulusProvider.getCurrentStimulus());
            stimulusProvider.nextStimulus(1);
        }
        afterStimuliListener.postLoadTimerFired();
    }

    protected void loadStimulus(String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName,
            final StimuliProvider stimulusProvider,
            final CurrentStimulusListener hasMoreStimulusListener,
            final TimedStimulusListener endOfStimulusListener
    ) {
        loadStimulus(stimulusProvider, eventTag, selectionTags, randomTags, stimulusAllocationField, consumedTagsGroupName);
        this.hasMoreStimulusListener = hasMoreStimulusListener;
        this.endOfStimulusListener = endOfStimulusListener;
        showStimulus(stimulusProvider, null, 0);
    }

    private void loadStimulus(
            final StimuliProvider stimulusProvider,
            String eventTag,
            final StimulusSelector[] selectionTags, // only stimuli with tags in this list can be included
            final StimulusSelector[] randomTags,
            final MetadataField stimulusAllocationField,
            final String consumedTagsGroupName
    ) {
        submissionService.submitTimestamp(userResults.getUserData().getUserId(), eventTag, duration.elapsedMillis());
        final String storedStimulusList = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag());
        int seenStimulusIndex;
        try {
            seenStimulusIndex = Integer.parseInt(localStorage.getStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag()));
        } catch (NumberFormatException exception) {
            seenStimulusIndex = 0;
        }
        final List<Stimulus.Tag> allocatedTags = new ArrayList<>();
//        final List<StimulusSelector> allocatedTags = new ArrayList<>(Arrays.asList(selectionTags));
        for (StimulusSelector selector : selectionTags) {
            allocatedTags.add(selector.getTag());
        }
        if (randomTags.length > 0) {
            final String storedStimulusAllocation = userResults.getUserData().getMetadataValue(stimulusAllocationField);
            if (storedStimulusAllocation != null && !storedStimulusAllocation.isEmpty()) {
                for (StimulusSelector selector : randomTags) {
                    if (storedStimulusAllocation.equals(selector.getAlias())) {
                        allocatedTags.add(selector.getTag());
                    }
                }
            } else {
                final List<StimulusSelector> randomTagsList = new ArrayList();
                String consumedTagsGroupString = (consumedTagsGroupName != null) ? localStorage.getStoredDataValue(userResults.getUserData().getUserId(), CONSUMED_TAGS_LIST + consumedTagsGroupName) : "";
                for (StimulusSelector currentSelector : randomTags) {
                    if (!consumedTagsGroupString.contains("-" + currentSelector.getAlias() + "-")) {
                        randomTagsList.add(currentSelector);
                    }
                }
                StimulusSelector stimulusAllocation = randomTagsList.get(new Random().nextInt(randomTagsList.size()));
                if (consumedTagsGroupName != null) {
                    localStorage.appendStoredDataValue(userResults.getUserData().getUserId(), CONSUMED_TAGS_LIST + consumedTagsGroupName, "-" + stimulusAllocation.getAlias() + "-");
                }
                userResults.getUserData().setMetadataValue(stimulusAllocationField, stimulusAllocation.getAlias());
                localStorage.storeData(userResults, metadataFieldProvider);
                allocatedTags.add(stimulusAllocation.getTag());
                // submit the user metadata so that the selected stimuli group is stored
                submissionService.submitMetadata(userResults, new DataSubmissionListener() {
                    @Override
                    public void scoreSubmissionFailed(DataSubmissionException exception) {
                    }

                    @Override
                    public void scoreSubmissionComplete(JsArray<DataSubmissionResult> highScoreData) {
                    }
                });
            }
        }
        // @todo: add the limits for maxStimulusCount and maxStimulusPerTag -
        stimulusProvider.getSubset(allocatedTags, storedStimulusList, seenStimulusIndex);
    }

    protected void sendStimuliReport(final StimuliProvider stimulusProvider, String reportType, final int dataChannel) {
        final Map<String, String> stimuliReport = stimulusProvider.getStimuliReport(reportType);
        for (String keyString : stimuliReport.keySet()) {
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, reportType, keyString, stimuliReport.get(keyString), duration.elapsedMillis());
        }
    }

    protected void showStimuliReport(final StimuliProvider stimulusProvider) {
        timedStimulusView.addHtmlText(stimulusProvider.getHtmlStimuliReport(), null);
    }

    protected void withMatchingStimulus(String eventTag, final String matchingRegex, final StimuliProvider stimulusProvider, final CurrentStimulusListener hasMoreStimulusListener, final TimedStimulusListener endOfStimulusListener) {
        matchingStimuliGroup = new MatchingStimuliGroup(stimulusProvider.getCurrentStimulus(), stimulusProvider.getMatchingStimuli(matchingRegex), true, hasMoreStimulusListener, endOfStimulusListener);
        matchingStimuliGroup.getNextStimulus(stimulusProvider);
        matchingStimuliGroup.showNextStimulus(stimulusProvider);
    }

    public void logTokenText(final Stimulus currentStimulus, final String reportType, final String headerKey, final int dataChannel, final String dataLogFormat) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, reportType, headerKey, new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(dataLogFormat), duration.elapsedMillis());
    }

    public void actionTokenButton(final Stimulus currentStimulus, final PresenterEventListner presenterListener, final String buttonGroup) {
        final HtmlTokenFormatter htmlTokenFormatter = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray);
        addButtonToGroup(buttonGroup, ((ComplexView) simpleView).addOptionButton(new PresenterEventListner() {
            @Override
            public String getLabel() {
                return htmlTokenFormatter.formatString(presenterListener.getLabel());
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                presenterListener.eventFired(button, shotEventListner);
            }

            @Override
            public String getStyleName() {
                return presenterListener.getStyleName();
            }

            @Override
            public int getHotKey() {
                return presenterListener.getHotKey();
            }
        }));
    }

    protected void timerLabel(final String styleName, final int postLoadMs, final String listenerId, final String msLabelFormat) {
        final DateTimeFormat formatter = DateTimeFormat.getFormat(msLabelFormat);
        final HTML html = timedStimulusView.addHtmlText("", styleName);
        Timer labelTimer = new Timer() {
            @Override
            public void run() {
                final int timerValue = timerService.getTimerValue(listenerId);
                final long remainingMs;
                if (postLoadMs > 0) {
                    if (postLoadMs > timerValue) {
                        remainingMs = (long) postLoadMs - timerValue;
                    } else {
                        remainingMs = 0;
                    }
                } else {
                    remainingMs = (long) timerValue;
                }
                Date msBasedDate = new Date(remainingMs);
                String labelText = formatter.format(msBasedDate);
                html.setHTML(labelText);
                schedule(500);
            }
        };
        labelTimer.schedule(5);
    }

    protected void countdownLabel(final String timesUpLabel, final int postLoadMs, final String msLabelFormat, final TimedStimulusListener timedStimulusListener) {
        countdownLabel(timesUpLabel, null, postLoadMs, msLabelFormat, timedStimulusListener);
    }

    protected void countdownLabel(final String timesUpLabel, final String styleName, final int postLoadMs, final String msLabelFormat, final TimedStimulusListener timedStimulusListener) {
        final Duration labelDuration = new Duration();
        final DateTimeFormat formatter = DateTimeFormat.getFormat(msLabelFormat);
        final HTML html = timedStimulusView.addHtmlText(formatter.format(new Date((long) postLoadMs)), styleName);
        Timer labelTimer = new Timer() {
            @Override
            public void run() {
                final long remainingMs = (long) postLoadMs - labelDuration.elapsedMillis();
                if (remainingMs > 0) {
                    Date msBasedDate = new Date(remainingMs);
                    String labelText = formatter.format(msBasedDate);
                    html.setHTML(labelText);
                    schedule(500);
                } else {
                    html.setHTML(timesUpLabel);
                    timedStimulusListener.postLoadTimerFired();
                }
            }
        };
        labelTimer.schedule(500);
    }

    protected void pause(int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        final Timer timer = new Timer() {
            @Override
            public void run() {
                timedStimulusListener.postLoadTimerFired();
                pauseTimers.remove(this);
            }
        };
        pauseTimers.add(timer);
        timer.schedule(postLoadMs);
    }

    protected void randomMsPause(int minimumMs, int maximumMs, final String ranges, final TimedStimulusListener timedStimulusListener) {
        // todo: handle ranges
        final Timer timer = new Timer() {
            @Override
            public void run() {
                timedStimulusListener.postLoadTimerFired();
                pauseTimers.remove(this);
            }
        };
        pauseTimers.add(timer);
        timer.schedule((int) (Math.random() * (maximumMs - minimumMs) + minimumMs));
    }

    protected void stimulusPause(final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener) {
        pause(currentStimulus.getPauseMs(), timedStimulusListener);
    }

    protected void currentStimulusHasTag(int postLoadMs, final Stimulus.Tag[] tagList, final Stimulus currentStimulus, final TimedStimulusListener hasTagListener, final TimedStimulusListener hasntTagListener) {
// todo: implement randomTags
//        List<Stimulus.Tag> editableList = new LinkedList<Stimulus.Tag>(tagList);
//        editableList.retainAll();
//        if (editableList.isEmpty()) {
        if (currentStimulus.getTags().containsAll(Arrays.asList(tagList))) {
            pause(postLoadMs, hasTagListener);
        } else {
            pause(postLoadMs, hasntTagListener);
        }
    }

    public void stimulusLabel(final Stimulus currentStimulus) {
        stimulusLabel(currentStimulus, null);
    }

    public void stimulusLabel(final Stimulus currentStimulus, String styleName) {
        final String label = currentStimulus.getLabel();
        if (label != null) {
            HTML html = timedStimulusView.addHtmlText(label, styleName);
            timedEventMonitor.addVisibilityListener(widgetTag.getElement(), html.getElement(), "stimulusLabel");
        }
    }

    protected void showStimulus(final StimuliProvider stimulusProvider) {
        showStimulus(stimulusProvider, null, 0);
    }

    protected void showStimulus(final StimuliProvider stimulusProvider, GroupActivityListener groupActivityListener, final int increment) {
        final int currentStimulusIndex = stimulusProvider.getCurrentStimulusIndex();
        final String subDirectory = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "sdcard-directory-" + getSelfTag());
        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), Integer.toString(currentStimulusIndex));
        localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), stimulusProvider.generateStimuliStateSnapshot());
        localStorage.storeUserScore(userResults);
        if (stimulusProvider.hasNextStimulus(increment)) {
            stimulusProvider.nextStimulus(increment);
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "NextStimulus", stimulusProvider.getCurrentStimulus().getUniqueId(), duration.elapsedMillis());
//            super.startAudioRecorderTag(STIMULUS_TIER);
            hasMoreStimulusListener.postLoadTimerFired(stimulusProvider, stimulusProvider.getCurrentStimulus());
//        } else if (!hasSubdirectories) {
        } else {
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "completed-screen-" + getSelfTag(), Boolean.toString(true));
            submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "showStimulus.endOfStimulusListener", (currentStimulusIndex + increment) + "/" + stimulusProvider.getTotalStimuli(), duration.elapsedMillis()); // todo: this is sent
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), Integer.toString(currentStimulusIndex + increment));
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag() + ((subDirectory != null) ? subDirectory : ""), stimulusProvider.generateStimuliStateSnapshot());
            endOfStimulusListener.postLoadTimerFired();
        }
    }
//    private static final int STIMULUS_TIER = 2;

    @Deprecated // @todo: is this really used anymore? -
    protected void removeStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus) {
        stimulusProvider.nextStimulus(1);
        //localStorage.setStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + getSelfTag(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()));
    }

    protected void nextMatchingStimulus(final StimuliProvider stimulusProvider) {
        matchingStimuliGroup.getNextStimulus(stimulusProvider);
        matchingStimuliGroup.showNextStimulus(stimulusProvider);
    }

    protected void removeMatchingStimulus(final String matchingRegex) {
        throw new UnsupportedOperationException("todo: removeMatchingStimulus");
    }

    protected void keepStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus) {
        stimulusProvider.pushCurrentStimulusToEnd();
    }

    protected void groupNetwork(final AppEventListner appEventListner, final ApplicationState selfApplicationState, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String groupMembers, final String groupCommunicationChannels, final int phasesPerStimulus, final TimedStimulusListener timedStimulusListener) {
        if (groupParticipantService == null) {
            final Timer groupKickTimer = new Timer() {
                @Override
                public void run() {
                    groupParticipantService.messageGroup(0, 0, stimulusProvider.getCurrentStimulusUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null, (int) userResults.getUserData().getCurrentScore(), groupMembers);
                }
            };
            groupParticipantService = new GroupParticipantService(
                    userResults.getUserData().getUserId().toString(),
                    getSelfTag(), groupMembers, groupCommunicationChannels,
                    phasesPerStimulus,
                    stimulusProvider.generateStimuliStateSnapshot(),
                    new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // do not clear the screen at this point because reconnects when the stimuli list is at the end will need to keep its UI items
                    clearPage();
                    ((ComplexView) simpleView).addPadding();
                    if (groupParticipantService.isConnected()) {
                        ((ComplexView) simpleView).addText("connected, waiting for other members");
                    } else {
                        ((ComplexView) simpleView).addText("not connected");
                    }
                    timedStimulusListener.postLoadTimerFired();
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    clearPage();
                    ((ComplexView) simpleView).addPadding();
//                    ((ComplexView) simpleView).addText("connected: " + groupParticipantService.isConnected());
                    ((ComplexView) simpleView).addHtmlText("Group not ready", "highlightedText");
                    ((ComplexView) simpleView).addPadding();
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    ((ComplexView) simpleView).addPadding();
                    ((ComplexView) simpleView).addText("synchronising the stimuli");
                    final String stimuliListGroup = groupParticipantService.getStimuliListGroup();
                    // when the stimuli list for this screen does not match that of the group, this listener is fired to: save the group stimuli list and then load the group stimuli list
                    stimulusProvider.initialiseStimuliState(stimuliListGroup);
                    final String loadedStimulusString = stimulusProvider.generateStimuliStateSnapshot();
                    localStorage.setStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + getSelfTag(), loadedStimulusString);
                    groupParticipantService.setStimuliListLoaded(loadedStimulusString);
                    groupKickTimer.schedule(1000);
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    if (groupParticipantService.getStimulusIndex() < stimulusProvider.getTotalStimuli()) {
                        if (groupParticipantService.getStimulusIndex() != stimulusProvider.getCurrentStimulusIndex()) {
                            groupParticipantService.setResponseStimulusId(null);
                            groupParticipantService.setResponseStimulusOptions(null);
                        } else if (groupParticipantService.getMessageString() != null && !groupParticipantService.getMessageString().isEmpty()) {
                            JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
                            storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
                            storedStimulusJSONObject.put("groupMessage", new JSONString(groupParticipantService.getMessageString()));
                            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, storedStimulusJSONObject);
                            // submissionService.writeJsonData would be called on next stimulus anyway: submissionService.writeJsonData(userResults.getUserData().getUserId().toString(), currentStimulus.getUniqueId(), storedStimulusJSONObject.toString());
                        }
                        // when a valid message has been received the current stimuli needs to be synchronised with the group
                        stimulusProvider.setCurrentStimuliIndex(groupParticipantService.getStimulusIndex());
                    } else {
                        // if the group message puts the stimuli list at the end then fire the end of stimulus listner
                        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "group message puts the stimuli list at the end", stimulusProvider.getCurrentStimulusUniqueId() + ":" + stimulusProvider.getCurrentStimulusIndex() + "/" + stimulusProvider.getTotalStimuli(), duration.elapsedMillis());
                        groupParticipantService.setEndOfStimuli(true); // block further messages
                        if (endOfStimulusListener != null) {
                            endOfStimulusListener.postLoadTimerFired();
                        }
                    }
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    ((ComplexView) simpleView).addInfoButton(new PresenterEventListner() {
                        @Override
                        public String getLabel() {
                            final Integer stimulusIndex = groupParticipantService.getStimulusIndex();
//                            final String activeChannel = groupParticipantService.getActiveChannel();
                            return groupParticipantService.getMemberCode()
                                    //                                    + ((stimulusIndex != null) ? "("
                                    //                                            + activeChannel
                                    //                                            + ")" : "")
                                    + ((stimulusIndex != null) ? "-T"
                                            + (stimulusIndex + 1) : "");
                        }

                        @Override
                        public void eventFired(ButtonBase button, final SingleShotEventListner shotEventListner) {
                            groupParticipantService.messageGroup(0, 0, stimulusProvider.getCurrentStimulusUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null, (int) userResults.getUserData().getCurrentScore(), groupMembers);
//                            ((ComplexView) simpleView).showHtmlPopup(null,
//                                    "Group Members\n" + groupParticipantService.getAllMemberCodes()
//                                    + "\n\nGroup Communication Channels\n" + groupParticipantService.getGroupCommunicationChannels()
//                                    + "\n\nGroupId\n" + groupParticipantService.getGroupId()
//                                    + "\n\nGroupUUID\n" + groupParticipantService.getGroupUUID()
//                                    + "\n\nMemberCode\n" + groupParticipantService.getMemberCode()
//                                    + "\n\nMessageSender\n" + groupParticipantService.getMessageSenderId()
//                                    + "\n\nMessageString\n" + groupParticipantService.getMessageString()
//                                    + "\n\nStimulusId\n" + groupParticipantService.getStimulusId()
//                                    + "\n\nStimuliListLoaded\n" + groupParticipantService.getStimuliListLoaded()
//                                    + "\n\nStimuliListGroup\n" + groupParticipantService.getStimuliListGroup()
//                                    + "\n\nResponseStimulusOptions\n" + groupParticipantService.getResponseStimulusOptions()
//                                    + "\n\nResponseStimulusId\n" + groupParticipantService.getResponseStimulusId()
//                                    + "\n\nStimulusIndex\n" + groupParticipantService.getStimulusIndex()
//                                    + "\n\nRequestedPhase\n" + groupParticipantService.getRequestedPhase()
//                                    + "\n\nUserLabel\n" + groupParticipantService.getUserLabel()
//                                    + "\n\nGroupReady\n" + groupParticipantService.isGroupReady()
//                            );
                            shotEventListner.resetSingleShot();
                        }

                        @Override
                        public String getStyleName() {
                            return null;
                        }

                        @Override
                        public int getHotKey() {
                            return -1;
                        }
                    });
                }
            }
            //                    , endOfStimulusListener
            );
            groupParticipantService.joinGroupNetwork(serviceLocations.groupServerUrl());
        } else {
            timedStimulusListener.postLoadTimerFired();
//            groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, null);
//              groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId());
        }
    }

    protected void groupNetworkActivity(final GroupActivityListener timedStimulusListener) {
        groupParticipantService.addGroupActivity(timedStimulusListener);
    }

    public void submitGroupEvent() {
        submissionService.submitGroupEvent(userResults.getUserData().getUserId(),
                groupParticipantService.getGroupUUID(),
                groupParticipantService.getGroupId(),
                groupParticipantService.getAllMemberCodes(),
                groupParticipantService.getGroupCommunicationChannels(),
                getSelfTag(),
                groupParticipantService.getMemberCode(),
                groupParticipantService.getUserLabel(),
                groupParticipantService.getStimulusId(),
                groupParticipantService.getStimulusIndex(),
                groupParticipantService.getMessageSenderId(),
                groupParticipantService.getMessageString(),
                groupParticipantService.getResponseStimulusId(),
                groupParticipantService.getResponseStimulusOptions(),
                groupParticipantService.getMessageSenderId(),
                groupParticipantService.getMessageSenderMemberCode(),
                duration.elapsedMillis());
    }

    protected void scoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final Integer correctStreak, final Integer errorStreak, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getCurrentScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getPotentialScore() - userResults.getUserData().getCurrentScore() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (correctStreak != null) {
            if (userResults.getUserData().getCorrectStreak() > correctStreak) {
                isWithin = false;
            }
        }
        if (errorStreak != null) {
            if (userResults.getUserData().getErrorStreak() > errorStreak) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void bestScoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final Integer correctStreak, final Integer errorStreak, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getMaxScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getMaxErrors() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getMaxPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (correctStreak != null) {
            if (userResults.getUserData().getMaxCorrectStreak() > correctStreak) {
                isWithin = false;
            }
        }
        if (errorStreak != null) {
            if (userResults.getUserData().getMaxErrorStreak() > errorStreak) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void totalScoreAboveThreshold(final Integer scoreThreshold, final Integer errorThreshold, final Integer potentialThreshold, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        boolean isWithin = true;
        if (scoreThreshold != null) {
            if (userResults.getUserData().getTotalScore() > scoreThreshold) {
                isWithin = false;
            }
        }
        if (errorThreshold != null) {
            if (userResults.getUserData().getTotalPotentialScore() - userResults.getUserData().getTotalScore() > errorThreshold) {
                isWithin = false;
            }
        }
        if (potentialThreshold != null) {
            if (userResults.getUserData().getTotalPotentialScore() > potentialThreshold) {
                isWithin = false;
            }
        }
        if (isWithin) {
            withinThreshold.postLoadTimerFired();
        } else {
            aboveThreshold.postLoadTimerFired();
        }
    }

    protected void resetStimulus(final String stimuliScreenToReset) {
        if (stimuliScreenToReset != null) {
            localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), LOADED_STIMULUS_LIST + stimuliScreenToReset);
            localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_INDEX + stimuliScreenToReset);
        }
    }

    protected void clearCurrentScore() {
        if (userResults.getUserData().getPotentialScore() > 0) {
            userResults.getUserData().addGamePlayed();
        }
        userResults.getUserData().clearCurrentScore();
        localStorage.storeUserScore(userResults);
    }

    protected void scoreIncrement(final boolean isCorrect) {
        userResults.getUserData().addPotentialScore(isCorrect);
        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "scoreIncrement", userResults.getUserData().getCurrentScore() + "/" + userResults.getUserData().getPotentialScore(), duration.elapsedMillis());
    }

    protected void scoreLabel() {
        scoreLabel(null);
    }

    protected void scoreLabel(String styleName) {
        timedStimulusView.addHtmlText("Current Score: " + userResults.getUserData().getCurrentScore() + "/" + userResults.getUserData().getPotentialScore(), styleName);
        timedStimulusView.addHtmlText("Best Score: " + userResults.getUserData().getMaxScore(), styleName);
    }

    protected void groupChannelScoreLabel() {
        groupChannelScoreLabel(null);
    }

    protected void groupChannelScoreLabel(String styleName) {
        if (groupParticipantService != null) {
            timedStimulusView.addHtmlText("Channel Score: " + groupParticipantService.getChannelScore(), styleName);
        }
    }

    protected void groupMessageLabel() {
        groupMessageLabel(null);
    }

    protected void groupMessageLabel(String styleName) {
        timedStimulusView.addHtmlText(groupParticipantService.getMessageString(), styleName);
    }

    protected void groupScoreLabel() {
        groupScoreLabel(null);

    }

    protected void groupScoreLabel(String styleName) {
        if (groupParticipantService != null) {
            timedStimulusView.addHtmlText("Group Score: " + groupParticipantService.getGroupScore(), styleName);
        }
    }

    protected void groupMemberLabel() {
        groupMemberLabel(null);
    }

    protected void groupMemberLabel(String styleName) {
        timedStimulusView.addHtmlText(groupParticipantService.getUserLabel(), styleName);
    }

    protected void groupMemberCodeLabel() {
        groupMemberCodeLabel(null);
    }

    protected void groupMemberCodeLabel(String styleName) {
        timedStimulusView.addHtmlText(groupParticipantService.getMemberCode(), styleName);
    }

    protected void groupResponseFeedback(final AppEventListner appEventListner, int postLoadMs1, final TimedStimulusListener correctListener, int postLoadMs2, final TimedStimulusListener incorrectListener) {
        // todo: make use of the postLoadMs 
        groupResponseFeedback(appEventListner, correctListener, incorrectListener);
    }

    protected void groupResponseFeedback(final AppEventListner appEventListner, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (groupParticipantService.getStimulusId().equals(groupParticipantService.getResponseStimulusId())) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }

    }

    public void stimulusHasResponse(final AppEventListner appEventListner, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus) != null) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }
    }

    protected void stimulusMetadataField(final Stimulus currentStimulus, MetadataField metadataField, final int dataChannel) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final String fieldValue;
        if (storedStimulusJSONObject != null) {
            fieldValue = storedStimulusJSONObject.containsKey(metadataField.getPostName()) ? storedStimulusJSONObject.get(metadataField.getPostName()).isString().stringValue() : "";
        } else {
            fieldValue = "";
        }
        final MetadataFieldWidget metadataFieldWidget = new MetadataFieldWidget(metadataField, currentStimulus, fieldValue, dataChannel);
        timedStimulusView.addWidget(metadataFieldWidget.getLabel());
        timedStimulusView.addWidget(metadataFieldWidget.getWidget());
        stimulusFreeTextList.add(metadataFieldWidget);
    }

    protected void stimulusFreeText(final Stimulus currentStimulus, String validationRegex, String keyCodeChallenge, String validationChallenge, final String allowedCharCodes, final int hotKey, String styleName, final int dataChannel) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final String postName = "freeText";
        final JSONValue freeTextValue = (storedStimulusJSONObject == null) ? null : storedStimulusJSONObject.get(postName);
        StimulusFreeText stimulusFreeText = timedStimulusView.addStimulusFreeText(currentStimulus, postName, validationRegex, keyCodeChallenge, validationChallenge, allowedCharCodes, new SingleShotEventListner() {
            @Override
            protected void singleShotFired() {
                for (PresenterEventListner nextButtonEventListner : nextButtonEventListnerList) {
                    // this process is to make sure that group events are submitted and not just call nextStimulus
                    if (nextButtonEventListner.getHotKey() == hotKey) {
                        nextButtonEventListner.eventFired(null, this);
                    } else {
//                    nextStimulus("stimulusFreeTextEnter", false);
                    }
                }
                this.resetSingleShot();
            }
        }, hotKey, styleName, dataChannel, ((freeTextValue != null) ? freeTextValue.isString().stringValue() : null));
        stimulusFreeTextList.add(stimulusFreeText);
    }

    protected void stimulusImageCapture(final StimuliProvider stimulusProvider, final Stimulus currentStimulusO, final String captureLabel, int percentOfPage, int maxHeight, int maxWidth, int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        final SdCardStimulus currentStimulus = (SdCardStimulus) currentStimulusO;
        final SdCardImageCapture sdCardImageCapture = new SdCardImageCapture(new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
                clearPage();
                // cause the taken image to be shown
                hasMoreStimulusListener.postLoadTimerFired(stimulusProvider, currentStimulusO);
            }
        }, currentStimulus, userResults.getUserData().getUserId(), localStorage);
        if (sdCardImageCapture.hasBeenCaptured()) {
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    timedStimulusListener.postLoadTimerFired();
                }
            };
            timedStimulusView.addTimedImage(timedEventMonitor, UriUtils.fromTrustedString(sdCardImageCapture.getCapturedPath()), percentOfPage, maxHeight, maxWidth, null, null, postLoadMs, shownStimulusListener, shownStimulusListener, null, null);
        }
        timedStimulusView.addOptionButton(new PresenterEventListner() {
            @Override
            public String getLabel() {
                return captureLabel;
            }

            @Override
            public String getStyleName() {
                return null;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                sdCardImageCapture.captureImage();
            }

            @Override
            public int getHotKey() {
                return -1;
            }
        });
    }

    protected void backgroundImage(final String imageSrc, String styleName, int postLoadMs, final TimedStimulusListener timedStimulusListener) {
        timedStimulusView.addBackgroundImage((imageSrc == null || imageSrc.isEmpty()) ? null : UriUtils.fromTrustedString((imageSrc.startsWith("file")) ? imageSrc : serviceLocations.staticFilesUrl() + imageSrc), styleName, postLoadMs, timedStimulusListener);
    }

    protected void stimulusImage(final Stimulus currentStimulus, final String styleName, int postLoadMs, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        final String imageString = currentStimulus.getImage();
        final String uniqueId = currentStimulus.getUniqueId();
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusImageShown", uniqueId, imageString, duration.elapsedMillis());
            }
        };
        timedStimulusView.addTimedImage(timedEventMonitor, UriUtils.fromString(imageString), styleName, postLoadMs, shownStimulusListener, loadedStimulusListener, failedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final boolean showControls, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, AnimateTypes.none, showControls, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, null, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, String regex, String replacement, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, null, regex, replacement, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, null);
    }

    @Deprecated
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final Integer fixedPositionY, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, true, fixedPositionY, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, null);
    }

//    @Deprecated
//    protected void stimulusPresent(final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, final Integer fixedPositionY, String regex, String replacement, final TimedStimulusListener timedStimulusListener, final TimedStimulusListener clickedStimulusListener) {
//        stimulusPresent(currentStimulus, percentOfPage, maxHeight, maxWidth, animateType, showControls, fixedPositionY, regex, replacement, timedStimulusListener, clickedStimulusListener);
//    }
    protected void stimulusPresent(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final boolean showControls, final Integer fixedPositionY, String regex, String replacement, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener, final CancelableStimulusListener clickedStimulusListener) {
        if (currentStimulus.hasImage()) {
            final String image;
            if (regex != null && replacement != null) {
                image = currentStimulus.getImage().replaceAll(regex, replacement);
            } else {
                image = currentStimulus.getImage();
            }
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send image shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusImageShown", currentStimulus.getUniqueId(), image, duration.elapsedMillis());
                }
            };
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusImage", image, duration.elapsedMillis());
            final String animateStyle;
            if (animateType == AnimateTypes.stimuliCode) {
                animateStyle = currentStimulus.getCode() + "Animation";
            } else if (animateType != AnimateTypes.none) {
                animateStyle = animateType.name() + "Animation";
            } else {
                animateStyle = null;
            }
            timedStimulusView.addTimedImage(timedEventMonitor, UriUtils.fromTrustedString(image), percentOfPage, maxHeight, maxWidth, animateStyle, fixedPositionY, 0, shownStimulusListener, new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    loadedStimulusListener.postLoadTimerFired();
                    playedStimulusListener.postLoadTimerFired();
                }
            }, failedStimulusListener, clickedStimulusListener);
//        timedStimulusView.addText("addStimulusImage: " + duration.elapsedMillis() + "ms");
        } else if (currentStimulus.hasAudio()) {
            String mp3 = currentStimulus.getAudio() + ".mp3";
            String ogg = currentStimulus.getAudio() + ".ogg";
            if (regex != null && replacement != null) {
                mp3 = mp3.replaceAll(regex, replacement);
                ogg = ogg.replaceAll(regex, replacement);
            }
            final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString(ogg);
            final SafeUri mp3TrustedString = (mp3 == null) ? null : UriUtils.fromTrustedString(mp3);
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send audio shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusAudioShown", currentStimulus.getUniqueId(), currentStimulus.getAudio(), duration.elapsedMillis());
                    loadedStimulusListener.postLoadTimerFired();
                }
            };
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", currentStimulus.getAudio(), duration.elapsedMillis());
            timedStimulusView.addTimedAudio(timedEventMonitor, oggTrustedString, mp3TrustedString, false, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, true, "autoStimulus");
        } else if (currentStimulus.hasVideo()) {
            String ogv = currentStimulus.getVideo() + ".ogv";
            String mp4 = currentStimulus.getVideo() + ".mp4";
            if (regex != null && replacement != null) {
                mp4 = mp4.replaceAll(regex, replacement);
                ogv = ogv.replaceAll(regex, replacement);
            }
//            submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusVideo", currentStimulus.getVideo(), duration.elapsedMillis());
            final SafeUri ogvTrustedString = (ogv == null) ? null : UriUtils.fromTrustedString(ogv);
            final SafeUri mp4TrustedString = (mp4 == null) ? null : UriUtils.fromTrustedString(mp4);
            final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                @Override
                protected void trigggerCancelableEvent() {
                    // send video shown tag
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusVideoShown", currentStimulus.getUniqueId(), currentStimulus.getVideo(), duration.elapsedMillis());
                    loadedStimulusListener.postLoadTimerFired();
                }
            };
            timedStimulusView.addTimedVideo(timedEventMonitor, ogvTrustedString, mp4TrustedString, percentOfPage, maxHeight, maxWidth, null, false, false, showControls, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, "stimulusPresent");
        } else if (currentStimulus.getLabel() != null) {
            timedStimulusView.addHtmlText(currentStimulus.getLabel(), null);
            // send label shown tag
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusLabelShown", currentStimulus.getUniqueId(), currentStimulus.getLabel(), duration.elapsedMillis());
            loadedStimulusListener.postLoadTimerFired();
            playedStimulusListener.postLoadTimerFired();
        } else {
            final String incorrect_stimulus_format = "incorrect stimulus format";
            nextStimulusButton(stimulusProvider, currentStimulus, incorrect_stimulus_format, incorrect_stimulus_format + " " + currentStimulus.getLabel(), null, true, -1, "incorrectStimulusFormat");
        }
    }

    protected void regionCodeStyle(final Stimulus currentStimulus, final String regionId, final String codeStyleName) {
        final String styleName = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeStyleName);
        ((ComplexView) simpleView).setRegionStyle(regionId, styleName);
    }

    public void stimulusCodeImageButton(final Stimulus currentStimulus, final String codeStyleName, String codeFormat, final String buttonGroup, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener clickedStimulusListener) {
        final String formattedCode = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeFormat);
        final String styleName = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeStyleName);
        addButtonToGroup(buttonGroup, timedStimulusView.addTimedImage(timedEventMonitor, UriUtils.fromString((formattedCode.startsWith("file")) ? formattedCode : serviceLocations.staticFilesUrl() + formattedCode), styleName, 0, loadedStimulusListener, null, failedStimulusListener, clickedStimulusListener));
    }

    protected void stimulusCodeImage(final Stimulus currentStimulus, final String codeStyleName, int postLoadMs, String codeFormat, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener) {
        final String formattedCode = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeFormat);
        final String styleName = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeStyleName);
        final String uniqueId = currentStimulus.getUniqueId();
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeImage", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeImageShown", uniqueId, formattedCode, duration.elapsedMillis());
            }
        };
        timedStimulusView.addTimedImage(timedEventMonitor, UriUtils.fromString((formattedCode.startsWith("file")) ? formattedCode : serviceLocations.staticFilesUrl() + formattedCode), styleName, postLoadMs, shownStimulusListener, loadedStimulusListener, failedStimulusListener, null);
//        timedStimulusView.addText("addStimulusImage: " + duration.elapsedMillis() + "ms");
    }

    protected void stimulusCodeAudio(final Stimulus currentStimulus, final boolean autoPlay, final String mediaId, String codeFormat, boolean showPlaybackIndicator, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String formattedCode = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeFormat);
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        final String uniqueId = currentStimulus.getUniqueId();

        String mp3 = formattedCode + ".mp3";
        String ogg = formattedCode + ".ogg";
        final SafeUri oggTrustedString = (ogg == null) ? null : UriUtils.fromTrustedString((ogg.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + ogg);
        final SafeUri mp3TrustedString = (mp3 == null) ? null : UriUtils.fromTrustedString((mp3.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + mp3);
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeAudio", formattedCode, duration.elapsedMillis());
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeAudioShown", uniqueId, formattedCode, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
        timedStimulusView.addTimedAudio(timedEventMonitor, oggTrustedString, mp3TrustedString, showPlaybackIndicator, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, autoPlay, formattedMediaId);
    }

    protected void stimulusVideo(final Stimulus currentStimulus, final String styleName, final boolean autoPlay, final String mediaId, final boolean loop, final boolean showControls, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String videoName = currentStimulus.getVideo();
        final String uniqueId = currentStimulus.getUniqueId();
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        String mp4 = videoName + ".mp4";
        String ogv = videoName + ".ogv";
        final SafeUri ogvTrustedString = (ogv == null) ? null : UriUtils.fromTrustedString(ogv);
        final SafeUri mp4TrustedString = (mp4 == null) ? null : UriUtils.fromTrustedString(mp4);
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeVideo", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusVideoShown", uniqueId, videoName, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", formattedCode, duration.elapsedMillis());
        Element videoElement = timedStimulusView.addTimedVideo(timedEventMonitor, ogvTrustedString, mp4TrustedString, 0, 0, 0, styleName, autoPlay, loop, showControls, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, formattedMediaId);
        if (videoElement != null) {
            timedEventMonitor.addVisibilityListener(widgetTag.getElement(), videoElement, "stimulusVideo");
        }
    }

    protected void stimulusCodeVideo(final Stimulus currentStimulus, int percentOfPage, int maxHeight, int maxWidth, final String codeStyleName, final boolean autoPlay, final String mediaId, final boolean loop, final boolean showControls, String codeFormat, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String formattedCode = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeFormat);
        final String styleName = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeStyleName);
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        final String uniqueId = currentStimulus.getUniqueId();
        String mp4 = formattedCode + ".mp4";
        String ogv = formattedCode + ".ogv";
        final SafeUri ogvTrustedString = (ogv == null) ? null : UriUtils.fromTrustedString((ogv.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + ogv);
        final SafeUri mp4TrustedString = (mp4 == null) ? null : UriUtils.fromTrustedString((mp4.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + mp4);
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusCodeVideo", formattedCode, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusCodeVideoShown", uniqueId, formattedCode, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", formattedCode, duration.elapsedMillis());
        timedStimulusView.addTimedVideo(timedEventMonitor, ogvTrustedString, mp4TrustedString, percentOfPage, maxHeight, maxWidth, styleName, autoPlay, loop, showControls, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, formattedMediaId);
    }

    protected void stimulusAudio(final Stimulus currentStimulus, final boolean autoPlay, final String mediaId, boolean showPlaybackIndicator, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String audio = currentStimulus.getAudio();
        final String uniqueId = currentStimulus.getUniqueId();
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        String ogg = audio + ".ogg";
        String mp3 = audio + ".mp3";
//        submissionService.submitTagValue(userResults.getUserData().getUserId(), "StimulusAudio", ogg, duration.elapsedMillis());
        final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
            @Override
            protected void trigggerCancelableEvent() {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusAudioShown", uniqueId, audio, duration.elapsedMillis());
                loadedStimulusListener.postLoadTimerFired();
            }
        };
        timedStimulusView.addTimedAudio(timedEventMonitor, UriUtils.fromTrustedString(ogg), UriUtils.fromTrustedString(mp3), showPlaybackIndicator, shownStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, autoPlay, formattedMediaId);
//        timedStimulusView.addText("playStimulusAudio: " + duration.elapsedMillis() + "ms");
    }

    public void stimulusHasRatingOptions(final AppEventListner appEventListner, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener) {
        if (currentStimulus.hasRatingLabels()) {
            correctListener.postLoadTimerFired();
        } else {
            incorrectListener.postLoadTimerFired();
        }
    }

    public void touchInputStimulusButton(final PresenterEventListner presenterListener, final String eventTag, final String imagePath, final String buttonGroup) {
        final StimulusButton buttonItem;
        if (imagePath == null || imagePath.isEmpty()) {
            buttonItem = optionButton(presenterListener, buttonGroup);
        } else {
            buttonItem = imageButton(presenterListener, UriUtils.fromString((imagePath.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + imagePath), true, buttonGroup);
        }
        stimulusButtonList.add(buttonItem);
        touchInputCapture.addTouchZone(new TouchInputZone() {
            boolean isTriggered = false;

            @Override
            public String getEventTag() {
                return eventTag;
            }

            @Override
            public String getGroupName() {
                return buttonGroup;
            }

            @Override
            public boolean intersects(int xPos, int yPos) {
                boolean returnValue = (yPos >= buttonItem.getWidget().getAbsoluteTop()
                        && yPos <= buttonItem.getWidget().getAbsoluteTop() + buttonItem.getWidget().getOffsetHeight()
                        && xPos >= buttonItem.getWidget().getAbsoluteLeft()
                        && xPos <= buttonItem.getWidget().getAbsoluteLeft() + buttonItem.getWidget().getOffsetWidth());
                return returnValue;
            }

            @Override
            public void triggerEvent() {
                if (!isTriggered) {
                    isTriggered = true;
                    buttonItem.addStyleName(presenterListener.getStyleName() + "Intersection");
                    buttonItem.triggerSingleShotEventListner();
                }
            }

            @Override
            public void clearEvent() {
                buttonItem.removeStyleName(presenterListener.getStyleName() + "Intersection");
                isTriggered = false;
            }
        });
    }

    public void stimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final PresenterEventListner presenterListener, final int dataChannel, final String buttonGroup) {
        final StimulusButton buttonItem = optionButton(new PresenterEventListner() {
            @Override
            public String getLabel() {
                // this stimulusButton label comes from featureText
                return presenterListener.getLabel();
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                timedEventMonitor.registerEvent("stimulusButton");
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "StimulusButton", currentStimulus.getUniqueId(), presenterListener.getLabel(), duration.elapsedMillis());
                Boolean isCorrect = null;
                if (currentStimulus.hasCorrectResponses()) {
                    final boolean correctness = currentStimulus.isCorrect(presenterListener.getLabel());
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, presenterListener.getLabel(), currentStimulus.getUniqueId(), (correctness) ? "correct" : "incorrect", duration.elapsedMillis());
                    // if there are correct responses to this stimulus then increment the score
                    userResults.getUserData().addPotentialScore(correctness);
                    isCorrect = correctness;
                }
                submissionService.submitStimulusResponse(userResults.getUserData(), getSelfTag(), dataChannel, currentStimulus, presenterListener.getLabel(), isCorrect, duration.elapsedMillis());
                presenterListener.eventFired(button, shotEventListner);
            }

            @Override
            public String getStyleName() {
                return presenterListener.getStyleName();
            }

            @Override
            public int getHotKey() {
                return presenterListener.getHotKey();
            }
        }, buttonGroup);
        stimulusButtonList.add(buttonItem);
    }

    public void stimulusRatingButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String buttonGroup, final TimedStimulusListener timedStimulusListener, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ratingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), currentStimulus.getRatingLabels(), dataChannel), ratingLabelLeft, ratingLabelRight, false, styleName, null, null, buttonGroup, null);
    }

    public void stimulusRatingRadio(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String buttonGroup, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel, final String buttonGroupName) {
        ratingRadioButton(appEventListner, stimulusProvider, currentStimulus, buttonGroup, currentStimulus.getRatingLabels(), ratingLabelLeft, ratingLabelRight, styleName, dataChannel, buttonGroupName);
    }

    public void ratingRadioButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String buttonGroup, final String ratingLabels, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel, final String buttonGroupName) {
        final List<PresenterEventListner> ratingEventListners = new ArrayList<>();//getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), currentStimulus.getRatingLabels(), dataChannel);
        final String[] splitRatingLabels = ratingLabels.split(",");
        for (final String ratingItem : splitRatingLabels) {
            ratingEventListners.add(new PresenterEventListner() {
                @Override
                public String getLabel() {
                    return ratingItem;
                }

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                    JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
                    storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
                    storedStimulusJSONObject.put("stimulusRatingRadio", new JSONString(ratingItem));
                    localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, storedStimulusJSONObject);
                }

                @Override
                public String getStyleName() {
                    return null; // should this one return styleName or a sibling object?
                }

                @Override
                public int getHotKey() {
                    return -1;
                }
            });
        }
        final HorizontalPanel buttonsPanel = new HorizontalPanel();
        final StimulusFreeText stimulusFreeText = new StimulusFreeText() {
            @Override
            public Stimulus getStimulus() {
                return currentStimulus;
            }

            @Override
            public String getPostName() {
                return "stimulusRatingRadio";
            }

            @Override
            public String getResponseTimes() {
                return null;
            }

            @Override
            public String getValue() {
                final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
                final String fieldValue;
                if (storedStimulusJSONObject != null) {
                    fieldValue = storedStimulusJSONObject.containsKey("stimulusRatingRadio") ? storedStimulusJSONObject.get("stimulusRatingRadio").isString().stringValue() : "";
                } else {
                    fieldValue = "";
                }
                return fieldValue;
            }

            @Override
            public boolean isValid() {
                if (getValue().isEmpty()) {
                    buttonsPanel.setStylePrimaryName("metadataError");
                    return false;
                } else {
                    buttonsPanel.setStylePrimaryName("metadataOK");
                    return true;
                }
            }

            @Override
            public int getDataChannel() {
                return dataChannel;
            }

            @Override
            public void setFocus(boolean wantsFocus) {
            }
        };
        ratingButtons(ratingEventListners, ratingLabelLeft, ratingLabelRight, false, styleName, buttonGroupName, stimulusFreeText.getValue(), buttonGroup, buttonsPanel);
        stimulusFreeTextList.add(stimulusFreeText);
    }

    public void ratingButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String buttonGroup, final TimedStimulusListener timedStimulusListener, final String ratingLabels, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ratingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), ratingLabels, dataChannel), ratingLabelLeft, ratingLabelRight, false, styleName, null, null, buttonGroup, null);
    }

    public void ratingFooterButton(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String buttonGroup, final TimedStimulusListener timedStimulusListener, final String ratingLabels, final String ratingLabelLeft, final String ratingLabelRight, final String styleName, final int dataChannel) {
        ratingButtons(getRatingEventListners(appEventListner, stimulusProvider, currentStimulus, timedStimulusListener, currentStimulus.getUniqueId(), ratingLabels, dataChannel), ratingLabelLeft, ratingLabelRight, true, styleName, null, null, buttonGroup, null);
    }

    public List<PresenterEventListner> getRatingEventListners(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener timedStimulusListener, final String stimulusString, final String ratingLabels, final int dataChannel) {
        ArrayList<PresenterEventListner> eventListners = new ArrayList<>();
        final String[] splitRatingLabels = ratingLabels.split(",");
        for (final String ratingItem : splitRatingLabels) {
            int derivedHotKey = -1;
            if (ratingItem.equals("0")) {
                derivedHotKey = KeyCodes.KEY_ZERO;
            } else if (ratingItem.equals("1")) {
                derivedHotKey = KeyCodes.KEY_ONE;
            } else if (ratingItem.equals("2")) {
                derivedHotKey = KeyCodes.KEY_TWO;
            } else if (ratingItem.equals("3")) {
                derivedHotKey = KeyCodes.KEY_THREE;
            } else if (ratingItem.equals("4")) {
                derivedHotKey = KeyCodes.KEY_FOUR;
            } else if (ratingItem.equals("5")) {
                derivedHotKey = KeyCodes.KEY_FIVE;
            } else if (ratingItem.equals("6")) {
                derivedHotKey = KeyCodes.KEY_SIX;
            } else if (ratingItem.equals("7")) {
                derivedHotKey = KeyCodes.KEY_SEVEN;
            } else if (ratingItem.equals("8")) {
                derivedHotKey = KeyCodes.KEY_EIGHT;
            } else if (ratingItem.equals("9")) {
                derivedHotKey = KeyCodes.KEY_NINE;
            } else if (splitRatingLabels.length == 2) {
                // if there are only two options then use z and . as the hot keys
                if (splitRatingLabels[0].equals(ratingItem)) {
                    derivedHotKey = KeyCodes.KEY_Z;
                } else {
                    derivedHotKey = KeyCodes.KEY_NUM_PERIOD;
                }
            }

            final int hotKey = derivedHotKey;
            eventListners.add(new PresenterEventListner() {
                @Override
                public String getLabel() {
                    return ratingItem;
                }

                @Override
                public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                    timedEventMonitor.registerEvent("ratingButton");
                    endAudioRecorderTag(dataChannel, ratingItem, currentStimulus);
                    submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "RatingButton", stimulusString, ratingItem, duration.elapsedMillis());
                    Boolean isCorrect = null;
                    if (currentStimulus.hasCorrectResponses()) {
                        final boolean correctness = currentStimulus.isCorrect(ratingItem);
                        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, ratingItem, stimulusString, (correctness) ? "correct" : "incorrect", duration.elapsedMillis());
                        // if there are correct responses to this stimulus then increment the score
                        userResults.getUserData().addPotentialScore(correctness);
                        isCorrect = correctness;
                    }
                    submissionService.submitStimulusResponse(userResults.getUserData(), getSelfTag(), dataChannel, currentStimulus, ratingItem, isCorrect, duration.elapsedMillis());
                    timedStimulusListener.postLoadTimerFired();
                }

                @Override
                public String getStyleName() {
                    return null;
                }

                @Override
                public int getHotKey() {
                    return hotKey;
                }
            });
        }
        return eventListners;
    }

    protected void showCurrentMs() {
//        timedStimulusView.addText(duration.elapsedMillis() + "ms");
    }

    protected void logTimeStamp(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, String eventTag, final int dataChannel) {
        logTimeStamp(stimulusProvider, currentStimulus, "logTimeStamp", eventTag, dataChannel);
    }

    protected void logTimeStamp(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, String eventName, String eventTag, final int dataChannel) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, currentStimulus.getUniqueId(), eventName, duration.elapsedMillis());
    }

    protected void endAudioRecorderTag(int tier, String tagString, final Stimulus currentStimulus) {
        super.endAudioRecorderTag(tier, currentStimulus.getUniqueId(), currentStimulus.getUniqueId(), tagString);
    }

    @Override
    protected void startAudioRecorderTag(int tier) {
        super.startAudioRecorderTag(tier); //((tier < 1) ? 1 : tier) + 2); //  tier 1 and 2 are reserved for stimulus set loading and stimulus display events
    }

    protected void startAudioRecorder(final String recordingFormat, final int downloadPermittedWindowMs, final String mediaId, final String deviceRegex, boolean filePerStimulus, String directoryName, final Stimulus currentStimulus, final TimedStimulusListener onError, final TimedStimulusListener onSuccess, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
//        final String subdirectoryName = userResults.getUserData().getUserId().toString();
        final String subdirectoryName = userResults.getUserData().getMetadataValue(new MetadataFieldProvider().workerIdMetadataField);
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        final MediaSubmissionListener mediaSubmissionListener = new MediaSubmissionListener() {
            @Override
            public void recorderStarted() {
                onSuccess.postLoadTimerFired();
            }

            @Override
            public void submissionFailed(final String message, final String userIdString, final String screenName, final String stimulusIdString, final Uint8Array dataArray) {
                // todo: consider storing unsent data for retries, but keep in mind that the local storage will overfill very quickly
//                timedStimulusView.addText("(debug) Media Submission Failed (retry not implemented): " + message);
                onError.postLoadTimerFired();
                final MediaSubmissionListener mediaSubmissionListener = this;
                actionButton(new PresenterEventListner() {
                    @Override
                    public String getLabel() {
                        return "Media Submission Failed"; // todo: this needs to be parameterised but with internationalisation
                    }

                    @Override
                    public void eventFired(ButtonBase button, SingleShotEventListner shotEventListner) {
                        submissionService.submitAudioData(userIdString, screenName, stimulusIdString, dataArray, mediaSubmissionListener, downloadPermittedWindowMs);
                    }

                    @Override
                    public String getStyleName() {
                        return null;
                    }

                    @Override
                    public int getHotKey() {
                        return -1;
                    }
                }, "startAudioRecorderRetry");
            }

            @Override
            public void recorderFailed(final String message) {
                onError.postLoadTimerFired();
                submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "AudioRecorder", message, duration.elapsedMillis());
            }

            @Override
            public void submissionComplete(String message, String urlAudioData) {
                String replayAudioUrl = serviceLocations.dataSubmitUrl() + "replayAudio/" + message.replaceAll("[^a-zA-Z0-9\\-]", "") + "/" + userResults.getUserData().getUserId();
//                timedStimulusView.addText("(debug) Media Submission OK: " + message);
                timedStimulusView.addTimedAudio(timedEventMonitor, (downloadPermittedWindowMs <= 0) ? UriUtils.fromTrustedString(urlAudioData) : UriUtils.fromString(replayAudioUrl), null, false, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, false, formattedMediaId);
            }
        };
        super.startAudioRecorder(submissionService, "wav".equals(recordingFormat), deviceRegex, subdirectoryName, directoryName, filePerStimulus, currentStimulus.getUniqueId(), userResults.getUserData().getUserId().toString(), getSelfTag(), mediaSubmissionListener, downloadPermittedWindowMs);
    }

    protected void showStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener, final int columnCount, final String imageWidth, final String eventTag, final int dataChannel) {
        final int maxStimuli = -1;
        final AnimateTypes animateType = AnimateTypes.none;
        showStimulusGrid(appEventListner, stimulusProvider, currentStimulus, correctListener, incorrectListener, maxStimuli, columnCount, imageWidth, animateType, eventTag, dataChannel);
    }

    protected void showStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener, final int maxStimuli, final int columnCount, final String imageWidth, final AnimateTypes animateType, final String eventTag, final int dataChannel) {
        timedStimulusView.stopAudio();
        TimedStimulusListener correctTimedListener = new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
                correctListener.postLoadTimerFired();
            }
        };
        TimedStimulusListener incorrectTimedListener = new TimedStimulusListener() {

            @Override
            public void postLoadTimerFired() {
                incorrectListener.postLoadTimerFired();
            }
        };
        final String gridStyle = "stimulusGrid";
        // todo: the appendStoredDataValue should occur in the correct or incorrect response within stimulusListener
        //localStorage.appendStoredDataValue(userResults.getUserData().getUserId(), SEEN_STIMULUS_LIST, currentStimulus.getAudioTag());
        timedStimulusView.startGrid(gridStyle);
        int imageCounter = 0;
//        if (alternativeChoice != null) {
//            stimulusButtonList.add(timedStimulusView.addStringItem(getEventListener(stimulusButtonList, eventTag, currentStimulus, alternativeChoice, correctTimedListener, incorrectTimedListener), alternativeChoice, 0, 0, imageWidth));
//        }
        String groupResponseOptions = null;
        for (final Stimulus nextJpgStimulus : stimulusProvider.getDistractorList(maxStimuli)) {
            final String styleName;
            if (animateType == AnimateTypes.stimuliCode) {
                styleName = nextJpgStimulus.getCode() + "Animation";
            } else if (animateType != AnimateTypes.none) {
                styleName = animateType.name() + "Animation";
            } else {
                styleName = null;
            }
            // collect the distractor list for later reporting
            if (groupResponseOptions == null) {
                groupResponseOptions = "";
            } else {
                groupResponseOptions += ",";
            }
            groupResponseOptions += nextJpgStimulus.getUniqueId();
            final StimulusButton imageItem = timedStimulusView.addImageItem(getEventListener(currentStimulus, stimulusButtonList, eventTag, dataChannel, currentStimulus, nextJpgStimulus, correctTimedListener, incorrectTimedListener), UriUtils.fromString(nextJpgStimulus.getImage()), imageCounter / columnCount, 1 + imageCounter++ % columnCount, imageWidth, styleName, imageCounter);
            stimulusButtonList.add(imageItem);
        }
        if (groupParticipantService != null) {
            groupParticipantService.setResponseStimulusOptions(groupResponseOptions);
        }
        disableStimulusButtons();
        timedStimulusView.endGrid();
        //timedStimulusView.addAudioPlayerGui();
    }

    protected void matchingStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener, final String matchingRegex, final boolean randomise, final int columnCount, int maxWidth, final int dataChannel) {
        final int maxStimulusCount = -1;
        final AnimateTypes animateType = AnimateTypes.none;
        matchingStimulusGrid(appEventListner, stimulusProvider, currentStimulus, correctListener, incorrectListener, matchingRegex, maxStimulusCount, randomise, columnCount, maxWidth, animateType, dataChannel);
    }

    protected void matchingStimulusGrid(final AppEventListner appEventListner, final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final TimedStimulusListener correctListener, final TimedStimulusListener incorrectListener, final String matchingRegex, final int maxStimulusCount, final boolean randomise, final int columnCount, int maxWidth, final AnimateTypes animateType, final int dataChannel) {
        matchingStimuliGroup = new MatchingStimuliGroup(currentStimulus, stimulusProvider.getMatchingStimuli(matchingRegex), true, hasMoreStimulusListener, endOfStimulusListener);
        timedStimulusView.startHorizontalPanel();
        int ySpacing = (int) (100.0 / (matchingStimuliGroup.getStimulusCount() + 1));
        int yPos = 0;
        while (matchingStimuliGroup.getNextStimulus(stimulusProvider)) {
            yPos += ySpacing;
            if (matchingStimuliGroup.isCorrect(currentStimulus)) {
                stimulusPresent(stimulusProvider, currentStimulus, 0, maxWidth, maxWidth, animateType, false, yPos - (maxWidth / 2), null, null, dataChannel,
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        correctListener.postLoadTimerFired();
                    }
                });
            } else {
                stimulusPresent(stimulusProvider, currentStimulus, 0, maxWidth, maxWidth, animateType, false, yPos - (maxWidth / 2), null, null, dataChannel,
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                },
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        incorrectListener.postLoadTimerFired();
                    }
                });
            }
        }
        timedStimulusView.endHorizontalPanel();
    }

    private PresenterEventListner getEventListener(final Stimulus currentStimulus, final ArrayList<StimulusButton> buttonList, final String eventTag, final int dataChannel, final Stimulus correctStimulusItem, final Stimulus currentStimulusItem, final TimedStimulusListener correctTimedListener, final TimedStimulusListener incorrectTimedListener) {
        final String tagValue1 = correctStimulusItem.getImage();
        final String tagValue2 = currentStimulusItem.getImage();
        return new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public String getStyleName() {
                return null;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                for (StimulusButton currentButton : buttonList) {
                    currentButton.setEnabled(false);
                }
                if (groupParticipantService != null) {
                    groupParticipantService.setResponseStimulusId(currentStimulusItem.getUniqueId());
//                    groupParticipantService.messageGroup(0, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), null, null, currentStimulusItem.getUniqueId());
                }
                button.addStyleName("stimulusButtonHighlight");
                // eventTag is set by the user and is different for each state (correct/incorrect).
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, tagValue1, tagValue2, duration.elapsedMillis());
                if (currentStimulus.getImage().equals(tagValue2)) {
                    correctTimedListener.postLoadTimerFired();
                } else {
                    incorrectTimedListener.postLoadTimerFired();
                }
            }
        };
    }

    public void triggerListener(final String listenerId, final int threshold, final int maximum, final TimedStimulusListener triggerListener) {
        triggerListeners.put(listenerId, new TriggerListener(listenerId, threshold, maximum, triggerListener));
    }

    public void habituationParadigmListener(final String listenerId, final int threshold, final int maximum, final TimedStimulusListener triggerListener) {
        triggerListeners.put(listenerId, new HabituationParadigmListener(listenerId, threshold, maximum, triggerListener, triggerListeners.containsKey(listenerId)));
    }

    public void trigger(final String listenerId) {
        triggerListeners.get(listenerId).trigger();
    }

    public void triggerRandom(final String matchingRegex, final TimedStimulusListener endOfTriggersListener) {
        ArrayList<TriggerListener> matchingListners = new ArrayList<>();
        for (TriggerListener triggerListener : triggerListeners.values()) {
            if (triggerListener.canTrigger()) {
                if (triggerListener.getListenerId().matches(matchingRegex)) {
                    matchingListners.add(triggerListener);
                }
            }
        }
        if (!matchingListners.isEmpty()) {
            matchingListners.get(new Random().nextInt(matchingListners.size())).trigger();
        } else {
            endOfTriggersListener.postLoadTimerFired();
        }
    }

    public void resetTrigger(final String listenerId) {
        triggerListeners.get(listenerId).reset();
    }

    protected void /* this could be changed to addTimer or setTimer since it now allows multiple timer listeners */ startTimer(final int msToNext, final String listenerId, final TimedStimulusListener timeoutListener) {
        final String storedDataValue = localStorage.getStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId);
        final long initialTimerStartMs;
        if (storedDataValue == null || storedDataValue.isEmpty()) {
            initialTimerStartMs = new Date().getTime();
            localStorage.setStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId, Long.toString(initialTimerStartMs));
        } else {
            initialTimerStartMs = Long.parseLong(storedDataValue);
        }
        timerService.startTimer(initialTimerStartMs, msToNext, listenerId, timeoutListener);
    }

    protected void compareTimer(final int msToNext, final String listenerId, final TimedStimulusListener aboveThreshold, final TimedStimulusListener withinThreshold) {
        if (timerService.getTimerValue(listenerId) > msToNext) {
            aboveThreshold.postLoadTimerFired();
        } else {
            withinThreshold.postLoadTimerFired();
        }
    }

    protected void clearTimer(final String listenerId) {
        localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), "timer_" + listenerId);
        timerService.clearTimer(listenerId);
    }

    protected void logTimerValue(final String listenerId, final String eventTag, final int dataChannel) {
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, listenerId, Integer.toString(timerService.getTimerValue(listenerId)), duration.elapsedMillis());
    }

    public void cancelPauseAll() {
        cancelPauseTimers();
        timedStimulusView.stopListeners();
        timedStimulusView.stopTimers();
        timedStimulusView.stopAudio();
        timedStimulusView.stopAllMedia();
        timedStimulusView.clearDomHandlers();
        stopAudioRecorder();
        timerService.clearAllTimers(); // clear all callbacks in timerService before exiting the presenter
        submissionService.submitTimestamps(userResults.getUserData().getUserId(), timedEventMonitor);
    }

    public void cancelPauseTimers() {
//        timedStimulusView.stopTimers();
        for (Timer currentTimer : pauseTimers) {
            if (currentTimer != null) {
                currentTimer.cancel();
            }
        }
        pauseTimers.clear();
    }

    public void disableStimulusButtons() {
        for (StimulusButton currentButton : stimulusButtonList) {
            currentButton.setEnabled(false);
        }
//        timedStimulusView.addText("disableStimulusButtons: " + duration.elapsedMillis() + "ms");
    }

    public void showStimulusProgress(final StimuliProvider stimulusProvider) {
        showStimulusProgress(stimulusProvider, null);
    }

    public void showStimulusProgress(final StimuliProvider stimulusProvider, String styleName) {
        timedStimulusView.addHtmlText((stimulusProvider.getCurrentStimulusIndex() + 1) + " / " + stimulusProvider.getTotalStimuli(), styleName);
//        timedStimulusView.addText("showStimulusProgress: " + duration.elapsedMillis() + "ms");
    }

//    public void popupMessage(final PresenterEventListner presenterListener, String message) {
//        timedStimulusView.showHtmlPopup(presenterListener, message);
//    }

    /*protected boolean stimulusIndexIn(int[] validIndexes) {
        int currentIndex = stimulusProvider.getTotalStimuli() - stimulusProvider.getRemainingStimuli();
        for (int currentValid : validIndexes) {
            if (currentIndex == currentValid) {
                return true;
            }
        }
        return false;
    }*/
    protected void clearStimulus() {
        // when is this used?
        clearPage();
        stimulusButtonList.clear();
    }

    public void stimulusExists(final int offsetInteger, final StimuliProvider stimulusProvider, final TimedStimulusListener conditionTrue, final TimedStimulusListener conditionFalse) {
        if (stimulusProvider.hasNextStimulus(offsetInteger)) {
            conditionTrue.postLoadTimerFired();
        } else {
            conditionFalse.postLoadTimerFired();
        }
    }

    public void validateStimuliResponses(final boolean unusedValue, final TimedStimulusListener conditionTrue, final TimedStimulusListener conditionFalse) {
        if (validateStimuliResponses()) {
            conditionTrue.postLoadTimerFired();
        } else {
            conditionFalse.postLoadTimerFired();
        }
    }

    private boolean validateStimuliResponses(/* this must use the stimuli for each StimulusFreeText and not from the stimulusProvider */) {
        HashMap<Stimulus, JSONObject> jsonStimulusMap = new HashMap<>();
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            if (!jsonStimulusMap.containsKey(stimulusFreeText.getStimulus())) {
                JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), stimulusFreeText.getStimulus());
                storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
                jsonStimulusMap.put(stimulusFreeText.getStimulus(), storedStimulusJSONObject);
            }
            final String value = stimulusFreeText.getValue();
            if (value != null) {
                jsonStimulusMap.get(stimulusFreeText.getStimulus()).put(stimulusFreeText.getPostName(), new JSONString(value));
            }
        }
        for (Stimulus stimulus : jsonStimulusMap.keySet()) { // we save the current responses here, so that a page reload can pre populate the page when allowed
            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), stimulus, jsonStimulusMap.get(stimulus));
        }
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            if (!stimulusFreeText.isValid()) {
                stimulusFreeText.setFocus(true);
                return false;
            }
        }
        // @todo: probably good to check if the data has changed before writing to disk
        for (Stimulus stimulus : jsonStimulusMap.keySet()) {
            submissionService.writeJsonData(userResults.getUserData().getUserId().toString(), stimulus.getUniqueId(), jsonStimulusMap.get(stimulus).toString());
        }
        for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
            // @todo: checking the free text boxes is also done in the group stimulus sync code, therefore this should be shared in a single function
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getPostName(), stimulusFreeText.getStimulus().getUniqueId(), stimulusFreeText.getValue(), duration.elapsedMillis());
            final String responseTimes = stimulusFreeText.getResponseTimes();
            if (responseTimes != null) {
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getPostName() + "_ms", stimulusFreeText.getStimulus().getUniqueId(), responseTimes, duration.elapsedMillis());
            }
            Boolean isCorrect = null;
            if (stimulusFreeText.getStimulus().hasCorrectResponses()) {
                final boolean correctness = stimulusFreeText.getStimulus().isCorrect(stimulusFreeText.getValue());
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getPostName(), stimulusFreeText.getStimulus().getUniqueId(), (correctness) ? "correct" : "incorrect", duration.elapsedMillis());
                // if there are correct responses to this stimulus then increment the score
                userResults.getUserData().addPotentialScore(correctness);
                isCorrect = correctness;
            }
            submissionService.submitStimulusResponse(userResults.getUserData(), getSelfTag(), stimulusFreeText.getDataChannel(), stimulusFreeText.getStimulus(), stimulusFreeText.getValue(), isCorrect, duration.elapsedMillis());
        }
        return true;
    }

    protected void prevStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect) {
        nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, -1);
    }

    protected void nextStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect) {
        nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, 1);
    }

    private void nextStimulus(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final boolean repeatIncorrect, final int increment) {
        if (groupParticipantService != null) {
            ((ComplexView) simpleView).addText("showStimulus should not be used with the groupParticipantService");
            throw new UnsupportedOperationException("showStimulus should not be used with the groupParticipantService");
        }
        if (!validateStimuliResponses()) {
            return;
        }
        if (repeatIncorrect && userResults.getUserData().isCurrentIncorrect()) {
            stimulusProvider.pushCurrentStimulusToEnd();
        }
        userResults.getUserData().clearCurrentResponse();
//        clearPage();
        showStimulus(stimulusProvider, null, increment);
    }

    protected void clearPage() {
        clearPage(null);
    }

    protected void clearPage(String styleName) {
        cancelPauseTimers();
        timedStimulusView.stopListeners();
        timedStimulusView.stopTimers();
        timedStimulusView.stopAudio();
        timedStimulusView.stopAllMedia();
//        timedStimulusView.clearDomHandlers();
        timedStimulusView.clearPageAndTimers(styleName);
        nextButtonEventListnerList.clear(); // clear this now to prevent refires of the event
        stimulusFreeTextList.clear();
        stimulusButtonList.clear();
        clearButtonList();
        backEventListners.clear();
        submissionService.submitTimestamps(userResults.getUserData().getUserId(), timedEventMonitor);
    }

    protected void playMedia(final String mediaId, final Stimulus currentStimulus) {
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        timedStimulusView.startMedia(formattedMediaId);
    }

    protected void rewindMedia(final String mediaId, final Stimulus currentStimulus) {
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        timedStimulusView.rewindMedia(formattedMediaId);
    }

    protected void pauseMedia(final String mediaId, final Stimulus currentStimulus) {
        final String formattedMediaId = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(mediaId);
        timedStimulusView.stopMedia(formattedMediaId);
    }

    protected void groupResponseStimulusImage(final StimuliProvider stimulusProvider, int percentOfPage, int maxHeight, int maxWidth, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final AnimateTypes animateType = AnimateTypes.none;
        groupResponseStimulusImage(stimulusProvider, percentOfPage, maxHeight, maxWidth, animateType, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener);
    }

    protected void groupResponseStimulusImage(final StimuliProvider stimulusProvider, int percentOfPage, int maxHeight, int maxWidth, final AnimateTypes animateType, final int dataChannel, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        stimulusPresent(stimulusProvider, stimulusProvider.getStimuliFromString(groupParticipantService.getResponseStimulusId()), percentOfPage, maxHeight, maxWidth, animateType, false, null, null, null, dataChannel, loadedStimulusListener, failedStimulusListener, playbackStartedStimulusListener, playedStimulusListener, null);
    }

    protected void sendGroupEndOfStimuli(final StimuliProvider stimulusProvider, final String eventTag) {
        groupParticipantService.messageGroup(groupParticipantService.getRequestedPhase(), 1, null, Integer.toString(stimulusProvider.getCurrentStimulusIndex() + 1), groupParticipantService.getMessageString(), groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), "");
//        showStimulusProgress();
    }

    protected void sendGroupStoredMessage(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int originPhase, final int incrementPhase, String expectedRespondents) {
        final JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
        final JSONValue freeTextValue = (storedStimulusJSONObject == null) ? null : storedStimulusJSONObject.get("groupMessage");
        String messageString = ((freeTextValue != null) ? freeTextValue.isString().stringValue() : null);
        groupParticipantService.messageGroup(originPhase, incrementPhase, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
    }

    protected void sendGroupMessage(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int originPhase, final int incrementPhase, String expectedRespondents) {
        submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), eventTag, (groupParticipantService != null) ? groupParticipantService.getMessageString() : null, duration.elapsedMillis());
        final String uniqueId = (stimulusProvider.getCurrentStimulusIndex() < stimulusProvider.getTotalStimuli()) ? currentStimulus.getUniqueId() : null;
        if (groupParticipantService != null) {
            groupParticipantService.messageGroup(originPhase, incrementPhase, uniqueId, Integer.toString(stimulusProvider.getCurrentStimulusIndex()), groupParticipantService.getMessageString(), groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
        }
        clearPage();
        timedStimulusView.addText("Waiting for a group response."); // + eventTag + ":" + originPhase + ":" + incrementPhase + ":" + groupParticipantService.getRequestedPhase());
    }

    // @todo: tag pair data and tag data tables could show the number of stimuli show events and the unique stimuli (grouped by tag strings) show events per screen
    protected void sendGroupMessageButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final int dataChannel, final String buttonLabel, final String styleName, final boolean norepeat, final int hotKey, final int originPhase, final int incrementPhase, final String expectedRespondents, final String buttonGroup) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public String getStyleName() {
                return styleName;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
                    if (!stimulusFreeText.isValid()) {
                        return;
                    }
                }
                String messageString = "";
                for (StimulusFreeText stimulusFreeText : stimulusFreeTextList) {
                    messageString += stimulusFreeText.getValue();
                }
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, (stimulusProvider.getCurrentStimulusIndex() < stimulusProvider.getTotalStimuli()) ? currentStimulus.getUniqueId() : null, messageString, duration.elapsedMillis());
                groupParticipantService.messageGroup(originPhase, incrementPhase, currentStimulus.getUniqueId(), Integer.toString(stimulusProvider.getCurrentStimulusIndex()), messageString, groupParticipantService.getResponseStimulusOptions(), groupParticipantService.getResponseStimulusId(), (int) userResults.getUserData().getCurrentScore(), expectedRespondents);
                clearPage();
            }
        };
        nextButtonEventListnerList.add(eventListner);
        optionButton(eventListner, buttonGroup);
    }

    protected void addStimulusValidation(final Stimulus currentStimulus, final String validationRegex, final String validationChallenge, final int dataChannel) {
        stimulusFreeTextList.add(timedStimulusView.addStimulusValidation(localStorage, userResults.getUserData().getUserId(), currentStimulus, "CodeResponse", validationRegex, validationChallenge, dataChannel));
    }

    protected void setStimulusCodeResponse(
            final Stimulus currentStimulus,
            final String codeFormat,
            final boolean applyScore,
            final int dataChannel
    ) {
        final String formattedCode = new HtmlTokenFormatter(currentStimulus, localStorage, groupParticipantService, userResults.getUserData(), timerService, metadataFieldProvider.metadataFieldArray).formatString(codeFormat);
        HashMap<Stimulus, JSONObject> jsonStimulusMap = new HashMap<>();
        if (!jsonStimulusMap.containsKey(currentStimulus)) {
            JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
            storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
            jsonStimulusMap.put(currentStimulus, storedStimulusJSONObject);
        }
        jsonStimulusMap.get(currentStimulus).put("CodeResponse", new JSONString(formattedCode));
        localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, jsonStimulusMap.get(currentStimulus));
        // @todo: probably good to check if the data has changed before writing to disk
        submissionService.writeJsonData(userResults.getUserData().getUserId().toString(), currentStimulus.getUniqueId(), jsonStimulusMap.get(currentStimulus).toString());
        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "CodeResponse", currentStimulus.getUniqueId(), formattedCode, duration.elapsedMillis());
        Boolean isCorrect = null;
        if (applyScore) {
            if (currentStimulus.hasCorrectResponses()) {
                final boolean correctness = currentStimulus.isCorrect(formattedCode);
                submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "CodeResponse", currentStimulus.getUniqueId(), (correctness) ? "correct" : "incorrect", duration.elapsedMillis());
                // if there are correct responses to this stimulus then increment the score
                userResults.getUserData().addPotentialScore(correctness);
                isCorrect = correctness;
            }
        }
        submissionService.submitStimulusResponse(userResults.getUserData(), getSelfTag(), dataChannel, currentStimulus, formattedCode, isCorrect, duration.elapsedMillis());
    }

    protected void touchInputReportSubmit(final Stimulus currentStimulus, final int dataChannel) {
        if (touchInputCapture != null) {
            final String touchReport = touchInputCapture.getTouchReport(Window.getClientWidth(), Window.getClientHeight());
            submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, "touchInputReport", currentStimulus.getUniqueId(), touchReport, duration.elapsedMillis());
//            // todo: perhaps this is a bit heavy on local storage but at least only one touch event would be stored
//            JSONObject storedStimulusJSONObject = localStorage.getStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus);
//            storedStimulusJSONObject = (storedStimulusJSONObject == null) ? new JSONObject() : storedStimulusJSONObject;
//            storedStimulusJSONObject.put("touchInputReport", new JSONString(touchReport));
//            localStorage.setStoredJSONObject(userResults.getUserData().getUserId(), currentStimulus, storedStimulusJSONObject);
        }
        touchInputCapture = null;
    }

    protected void touchInputCaptureStart(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final int dataChannel, final boolean showDebug, final int msAfterEndOfTouchToNext, final TimedStimulusListener endOfTouchEventListner) {
        if (touchInputCapture == null) {
            final HTML debugHtmlLabel;
            if (showDebug) {
                debugHtmlLabel = timedStimulusView.addHtmlText("&nbsp;", "footerLabel");
            } else {
                debugHtmlLabel = null;
            }
            touchInputCapture = new TouchInputCapture(endOfTouchEventListner, msAfterEndOfTouchToNext) {
                @Override
                public void setDebugLabel(String debugLabel) {
                    if (debugHtmlLabel != null) {
                        timedStimulusView.addWidget(debugHtmlLabel);
                        debugHtmlLabel.setHTML(debugLabel);
                    }
                }

                @Override
                public void endOfTouchEvent(String groupName) {
                    logTimeStamp(stimulusProvider, currentStimulus, "endOfTouchEvent", groupName, dataChannel);
                }

            };
            timedStimulusView.addTouchInputCapture(touchInputCapture);
        }
    }

    protected void prevStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final String buttonGroup) {
        final int hotKey = -1;
        prevStimulusButton(stimulusProvider, currentStimulus, eventTag, buttonLabel, styleName, repeatIncorrect, hotKey, buttonGroup);
    }

    protected void prevStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final int hotKey, final String buttonGroup) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public String getStyleName() {
                return styleName;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, -1);
            }
        };
        nextButtonEventListnerList.add(eventListner);
        final StimulusButton prevButton = optionButton(eventListner, buttonGroup);
        prevButton.setEnabled(stimulusProvider.hasNextStimulus(-1));
    }

    protected void nextStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final String buttonGroup) {
        final int hotKey = -1;
        nextStimulusButton(stimulusProvider, currentStimulus, eventTag, buttonLabel, styleName, repeatIncorrect, hotKey, buttonGroup);
    }

    protected void nextStimulusButton(final StimuliProvider stimulusProvider, final Stimulus currentStimulus, final String eventTag, final String buttonLabel, final String styleName, final boolean repeatIncorrect, final int hotKey, final String buttonGroup) {
//        if (stimulusProvider.hasNextStimulus()) {
        PresenterEventListner eventListner = new PresenterEventListner() {

            @Override
            public String getLabel() {
                return buttonLabel;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public String getStyleName() {
                return styleName;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                nextStimulus(stimulusProvider, currentStimulus, repeatIncorrect, 1);
            }
        };
        nextButtonEventListnerList.add(eventListner);
        optionButton(eventListner, buttonGroup);
    }

    protected void audioButton(final String eventTag, final int dataChannel, final String srcString, final String styleName, final String imagePath, final boolean autoPlay, final int hotKey, final String buttonGroup, final CancelableStimulusListener loadedStimulusListener, final CancelableStimulusListener failedStimulusListener, final CancelableStimulusListener playbackStartedStimulusListener, final CancelableStimulusListener playedStimulusListener) {
        final String mp3Path = srcString + ".mp3";
        final String oggPath = srcString + ".ogg";
        final PresenterEventListner presenterEventListner = new PresenterEventListner() {
            private boolean hasPlayed = false;

            @Override
            public String getLabel() {
                return imagePath;
            }

            @Override
            public int getHotKey() {
                return hotKey;
            }

            @Override
            public String getStyleName() {
                return styleName;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                final CancelableStimulusListener shownStimulusListener = new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {
                        submissionService.submitTagPairValue(userResults.getUserData().getUserId(), getSelfTag(), dataChannel, eventTag, "PlayAudio", mp3Path, duration.elapsedMillis());
                        loadedStimulusListener.postLoadTimerFired();
                    }
                };
                timedStimulusView.addTimedAudio(timedEventMonitor, UriUtils.fromString((oggPath.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + oggPath), UriUtils.fromString((mp3Path.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + mp3Path), false, shownStimulusListener, failedStimulusListener,
                        new CancelableStimulusListener() {
                    @Override
                    protected void trigggerCancelableEvent() {

                    }
                }, new CancelableStimulusListener() {

                    @Override
                    protected void trigggerCancelableEvent() {
                        if (!hasPlayed) {
                            playedStimulusListener.postLoadTimerFired();
                        }
                        hasPlayed = true;
                    }
                }, true, "audioButton");
            }
        };
        imageButton(presenterEventListner, UriUtils.fromString((imagePath.startsWith("file") ? "" : serviceLocations.staticFilesUrl()) + imagePath), false, buttonGroup);
        if (autoPlay) {
            presenterEventListner.eventFired(null, null);
        }
    }

    @Override
    public void savePresenterState() {
        cancelPauseTimers();
        timedStimulusView.stopListeners();
        timedStimulusView.stopTimers();
        timedStimulusView.stopAudio();
        timedStimulusView.stopAllMedia();
        timedStimulusView.clearDomHandlers();
        super.savePresenterState();
        stopAudioRecorder();
        timerService.clearAllTimers(); // clear all callbacks in timerService before exiting the presenter
        triggerListeners.clear();
        submissionService.submitTimestamps(userResults.getUserData().getUserId(), timedEventMonitor);
    }
}
