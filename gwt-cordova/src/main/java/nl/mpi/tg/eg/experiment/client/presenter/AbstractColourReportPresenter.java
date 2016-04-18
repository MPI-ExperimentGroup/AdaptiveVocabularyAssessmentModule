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
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.util.Date;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.model.colour.StimulusResponseGroup;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.view.ReportView;
import nl.ru.languageininteraction.synaesthesia.client.model.GroupScoreData;
import nl.ru.languageininteraction.synaesthesia.client.util.ScoreCalculator;

/**
 * @since Mar 7, 2016 4:10:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractColourReportPresenter extends AbstractPresenter implements Presenter {

    protected final UserResults userResults;
    final LocalStorage localStorage;

    public AbstractColourReportPresenter(RootLayoutPanel widgetTag, DataSubmissionService submissionService, UserResults userResults) {
        super(widgetTag, new ReportView());
        this.localStorage = new LocalStorage();
        this.userResults = userResults;
    }
    public void showReport(){
        StringBuilder stringBuilder = new StringBuilder();
        final DateTimeFormat format = DateTimeFormat.getFormat(messages.reportDateFormat());
        final NumberFormat numberFormat2 = NumberFormat.getFormat("0.00");
//        final NumberFormat numberFormat3 = NumberFormat.getFormat("0.000");
        final ScoreCalculator scoreCalculator = new ScoreCalculator(userResults);
        for (final StimulusResponseGroup stimuliGroup : userResults.getStimulusResponseGroups()) {
            final GroupScoreData calculatedScores = scoreCalculator.calculateScores(stimuliGroup);
            ((ReportView) simpleView).showResults(stimuliGroup, calculatedScores);
            ((ReportView) simpleView).addText(messages.reportScreenScore(numberFormat2.format(calculatedScores.getScore())));
            ((ReportView) simpleView).addText(messages.userfeedbackscreentext());
            userResults.getUserData().updateBestScore(calculatedScores.getScore());
//            ((ReportView) simpleView).addText(messages.reportScreenSCT());
//            ((ReportView) simpleView).addText(messages.reportScreenSCTaccuracy(numberFormat2.format(calculatedScores.getAccuracy())));
//            ((ReportView) simpleView).addText(messages.reportScreenSCTmeanreactionTime(numberFormat3.format(calculatedScores.getMeanReactionTime() / 1000), numberFormat3.format(calculatedScores.getReactionTimeDeviation() / 1000)));
//            stringBuilder.append(userResults.getUserData().getMetadataValue(MetadataFieldProvider.));
            stringBuilder.append("\t");
            stringBuilder.append(stimuliGroup.getPostName());
            stringBuilder.append("\t");
            stringBuilder.append(format.format(new Date()));
            stringBuilder.append("\t");
            stringBuilder.append(calculatedScores.getScore());
            stringBuilder.append("\t");
            stringBuilder.append(calculatedScores.getMeanReactionTime());
            stringBuilder.append("\t");
            stringBuilder.append(calculatedScores.getReactionTimeDeviation());
            stringBuilder.append("\n");
            
        }
//        userResults.getUserData().setScoreLog(stringBuilder.toString());
//        ((ReportView) simpleView).addText(messages.reportScreenPostSCTtext());

        if (userResults.getUserData().getBestScore() <= Float.parseFloat(messages.positiveresultsThreshold())) {
//            ((ReportView) simpleView).addHighlightedText(messages.positiveresultscreentext1());
//            ((ReportView) simpleView).addHighlightedText(messages.positiveresultscreentext2());
//            ((ReportView) simpleView).addHighlightedText(messages.positiveresultscreentext3());
        } else {
//            ((ReportView) simpleView).addHighlightedText(messages.negativeresultscreentext1());
//            ((ReportView) simpleView).addHighlightedText(messages.negativeresultscreentext2());
//            ((ReportView) simpleView).addHighlightedText(messages.negativeresultscreentext3());
        }
        ((ReportView) simpleView).addPadding();
    }
}
