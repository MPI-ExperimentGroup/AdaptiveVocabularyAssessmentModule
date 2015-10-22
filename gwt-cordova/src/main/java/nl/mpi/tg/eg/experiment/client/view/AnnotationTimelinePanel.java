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
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.autobean.shared.AutoBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;
import nl.mpi.tg.eg.experiment.client.model.AnnotationSet;
import nl.mpi.tg.eg.experiment.client.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.service.DataFactory;
import nl.mpi.tg.eg.experiment.client.service.ServiceLocations;

/**
 * @since Sep 21, 2015 11:56:46 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AnnotationTimelinePanel extends AbsolutePanel {

    protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    private final HashMap<AnnotationData, Label> annotationLebels = new HashMap<>();
    private final HashMap<Stimulus, ButtonBase> stimulusButtons = new HashMap<>();
    private final HashMap<Stimulus, Integer> tierTopPositions = new HashMap<>();
    final Label timelineCursor = new Label();
    private int currentOffsetWidth = -1;
    final int tierHeight;

    public AnnotationTimelinePanel() {
        this.setStylePrimaryName("annotationTimelineTier");
        tierHeight = 30;
        timelineCursor.setStylePrimaryName("annotationTimelineCursor");
        this.add(timelineCursor);
    }

    public void startUpdating(final VideoPanel videoPanel) {
        Timer timer = new Timer() {

            @Override
            public void run() {
                resizeTimeline(videoPanel.getCurrentTime(), videoPanel.getDurationTime());
                final double currentTime = videoPanel.getCurrentTime();
//                labelticker.setText("" + currentTime);
                AnnotationTimelinePanel.this.setWidgetPosition(timelineCursor, getLeftPosition(videoPanel.getCurrentTime(), videoPanel.getDurationTime()), AnnotationTimelinePanel.this.getOffsetHeight() - timelineCursor.getOffsetHeight());
                // to folling section is going to be a bit time critical, so might need some attention in the future
                ArrayList<Stimulus> intersectedStimuli = new ArrayList<>();
                for (AnnotationData annotationData : annotationLebels.keySet()) {
                    if (intersectsTime(annotationData, currentTime)) {
                        intersectedStimuli.add(annotationData.getStimulus());
                    }
                }
                // since we dont have an included and excluded list, we instead clear all highlights then set the known highlights after that
                for (ButtonBase button : stimulusButtons.values()) {
                    button.removeStyleName("stimulusButtonHighlight");
                }
                for (Stimulus intersectedStimulus : intersectedStimuli) {
                    final ButtonBase stimulusButton = stimulusButtons.get(intersectedStimulus);
                    if (stimulusButton != null) {
                        stimulusButton.addStyleName("stimulusButtonHighlight");
                    }
                }
                this.schedule(10);
            }
        };
        timer.schedule(10);
    }

    public void setTierCount(int tierCount) {
        this.setHeight(tierHeight * tierCount + "px");
    }

    public void addStimulusButton(final Stimulus stimulus, final StimulusGrid stimulusGrid, final VideoPanel videoPanel, final DataFactory dataFactory, final int stimulusCounter, final int columnCount, final String imageWidth) {
        stimulusButtons.put(stimulus, stimulusGrid.addImageItem(new PresenterEventListner() {
            @Override
            public String getLabel() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                final double clickedTime = videoPanel.getCurrentTime();
                AnnotationData foundAnnotationData = null;
                for (AnnotationData currentAnnotationData : annotationLebels.keySet()) {
                    if (currentAnnotationData.getStimulus().equals(stimulus)) {
                        if (intersectsTime(currentAnnotationData, clickedTime)) {
                            foundAnnotationData = currentAnnotationData;
                            break;
                        }
                    }
                }
                if (foundAnnotationData != null) {
                    foundAnnotationData.setOutTime(clickedTime);
                    annotationLebels.get(foundAnnotationData).setWidth(getWidth(foundAnnotationData, videoPanel.getDurationTime()) + "px");
                } else {
                    final AutoBean<AnnotationData> annotationDataBean = dataFactory.annotation();
                    final AnnotationData annotationData = annotationDataBean.as();
                    annotationData.setInTime(clickedTime);
                    annotationData.setOutTime(videoPanel.getDurationTime());
                    annotationData.setAnnotationHtml("" + videoPanel.getCurrentTime());
                    annotationData.setStimulus(stimulus);
                    insertTierAnnotation(annotationData, videoPanel);
                }
                singleShotEventListner.resetSingleShot();
            }
        }, UriUtils.fromString(serviceLocations.staticFilesUrl() + stimulus.getImage()), stimulusCounter / columnCount, 1 + stimulusCounter % columnCount, imageWidth));
    }

    private int getLeftPosition(final AnnotationData annotationData, final double durationTime) {
        return getLeftPosition(annotationData.getInTime(), durationTime);
    }

    private int getWidth(final AnnotationData annotationData, final double durationTime) {
        return getLeftPosition(annotationData.getOutTime() - annotationData.getInTime(), durationTime);
    }

    private int getLeftPosition(final double currentTime, final double durationTime) {
        return (int) ((currentOffsetWidth - 1) * (currentTime / durationTime));
    }

    public boolean intersectsTime(final AnnotationData annotationData, final double currentTime) {
        return (currentTime >= annotationData.getInTime() && currentTime <= annotationData.getOutTime());
    }

    public Set<AnnotationData> getAnnotations() {
        return annotationLebels.keySet();
    }

    public Set<Stimulus> setAnnotations(AnnotationSet annotationSet, final VideoPanel videoPanel) {
        Set<Stimulus> foundStimulus = new HashSet<>();
        final Set<AnnotationData> annotations = annotationSet.getAnnotations();
        if (annotations != null) {
            for (AnnotationData annotationData : annotations) {
                insertTierAnnotation(annotationData, videoPanel);
                foundStimulus.add(annotationData.getStimulus());
            }
        }
        return foundStimulus;
    }

    private void insertTierAnnotation(final AnnotationData annotationData, final VideoPanel videoPanel) {
        final Label label1 = new Label(annotationData.getAnnotationHtml());
        label1.setStylePrimaryName("annotationTimelineTierSegment");
        final SingleShotEventListner tierSegmentListner = new SingleShotEventListner() {

            @Override
            protected void singleShotFired() {
                videoPanel.playSegment(annotationData);
                resetSingleShot();
            }
        };
        label1.addClickHandler(tierSegmentListner);
        label1.addTouchStartHandler(tierSegmentListner);
        label1.addTouchMoveHandler(tierSegmentListner);
        label1.addTouchEndHandler(tierSegmentListner);
        label1.setWidth(getWidth(annotationData, videoPanel.getDurationTime()) + "px");
        final int topPosition;
        if (tierTopPositions.containsKey(annotationData.getStimulus())) {
            topPosition = tierTopPositions.get(annotationData.getStimulus());
        } else {
            int stimulusCounter = tierTopPositions.size();
            topPosition = tierHeight * stimulusCounter; // absolutePanel.getOffsetHeight() / stimulusProvider.getTotalStimuli() * stimulusCounter;
            tierTopPositions.put(annotationData.getStimulus(), topPosition);
        }
        AnnotationTimelinePanel.this.add(label1, getLeftPosition(annotationData, videoPanel.getDurationTime()), topPosition);
        annotationLebels.put(annotationData, label1);
    }

    public void resizeTimeline(final double currentTime, final double durationTime) {
        if (currentOffsetWidth < 1 || currentOffsetWidth != AnnotationTimelinePanel.this.getOffsetWidth()) {
            currentOffsetWidth = AnnotationTimelinePanel.this.getOffsetWidth();
            for (AnnotationData annotationData : annotationLebels.keySet()) {
                final Label label = annotationLebels.get(annotationData);
                label.setWidth(getWidth(annotationData, durationTime) + "px");
                final int topPosition = AnnotationTimelinePanel.this.getWidgetTop(label);
                AnnotationTimelinePanel.this.setWidgetPosition(label, getLeftPosition(annotationData, durationTime), topPosition);
            }
        }
    }
}
