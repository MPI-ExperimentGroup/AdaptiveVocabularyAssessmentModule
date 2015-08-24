/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;

/**
 * @since Jan 29, 2014 3:34:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class VideoPanel extends VerticalPanel {

    final Video video;
// <video poster="Screen Shot 2014-01-16 at 7.46.33 PM.png" controls preload="none">
//                                    <source src="SSL_LM_lex_b.mp4" type="video/mp4" />
//                                </video>

    // GWT video is not yet fully supported and does not allow for event listeners to be registered so for now we are using HTML5 and JS directly
    public VideoPanel(String width, String poster, String mp4) {
        video = Video.createIfSupported();
        if (video != null) {
            video.setPoster(poster);
            video.setControls(true);
            this.add(video);
        } else {
            this.add(new Label("Video is not supported"));
        }
        final Label label = new Label("test output");
        this.add(label);
        Timer timer = new Timer() {
            private int couter = 0;

            @Override
            public void run() {
                label.setText("timer: " + couter++ + ":" + video.getCurrentTime());
            }
        };
        timer.scheduleRepeating(100);
        addSource(mp4, "video/mp4");
    }

    public final void addSource(String source, String type) {
        video.addSource(source, type); // add multiple formats with the format type so that more devices will be supported
    }

    public double getCurrentTime() {
        if (video != null) {
            return video.getCurrentTime();
        } else {
            return 0;
        }
    }

    public void playSegment(AnnotationData annotationData) {
        video.setCurrentTime(annotationData.getInTime());
    }
}
