/*
 * Copyright (C) 2015 Language In Interaction
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

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.SourceElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.safehtml.shared.SafeUri;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.listener.AudioEventListner;
import nl.mpi.tg.eg.experiment.client.listener.AudioExceptionListner;

/**
 * @since Jan 6, 2015 10:27:57 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class AudioPlayer {

    private Audio audioPlayer;
    private AudioEventListner audioEventListner;
    final private AudioExceptionListner audioExceptionListner;
    final private boolean autoPlay;
    private boolean hasTriggeredOnLoaded = false;
    private int sourceLoadingCounter = 0;

    public AudioPlayer(AudioExceptionListner audioExceptionListner, SafeUri ogg, SafeUri mp3, boolean autoPlay) throws AudioException {
        this.audioExceptionListner = audioExceptionListner;
        this.autoPlay = autoPlay;
        try {
            createPlayer();
            if (ogg != null) {
                final SourceElement sourceElement = audioPlayer.addSource(ogg.asString(), AudioElement.TYPE_OGG);
                onNoFoundSetup(sourceElement);
            }
            if (mp3 != null) {
                final SourceElement sourceElement = audioPlayer.addSource(mp3.asString(), AudioElement.TYPE_MP3);
                onNoFoundSetup(sourceElement);
            }
            //audioPlayer.setCurrentTime(0); // on android the if the ready state is not correct then this will fail and audio will not play
            audioPlayer.load();
//            audioPlayer.pause();
        } catch (AudioException audioException) {
            audioExceptionListner.audioExceptionFired(audioException);
        }
    }

    public Audio getAudioPlayer() {
        audioPlayer.setVisible(true);
        return audioPlayer;
    }

    private void createPlayer() throws AudioException {
        audioPlayer = Audio.createIfSupported();
        if (audioPlayer == null) {
            throw new AudioException("audio not supportered");
        }
        final AudioElement audioElement = audioPlayer.getAudioElement();
        onEndedSetup(audioElement);
    }

    private void incrementSourceLoadingCounter() {
        sourceLoadingCounter++;
    }

    private void registerSourceLoadingError() {
        sourceLoadingCounter--;
        if (sourceLoadingCounter <= 0) {
            onAudioFailed();
        }
    }

    private native void onNoFoundSetup(final SourceElement sourceElement) /*-{
        var audioPlayer = this;
        audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::incrementSourceLoadingCounter()();
        sourceElement.addEventListener("error", function(){
            audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::registerSourceLoadingError()();
        }, false);
    }-*/;

    private native void onEndedSetup(final AudioElement audioElement) /*-{
     var audioPlayer = this;
     audioElement.addEventListener("play", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onStartedAction()();
     }, false);
     audioElement.addEventListener("ended", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onEndedAction()();
     }, false);
     audioElement.addEventListener("canplaythrough", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onLoadedAction()();
     }, false);
     audioElement.addEventListener("error", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onAudioFailed()();
     }, false);
     }-*/;

    public void onStartedAction() {
        if (audioEventListner != null) {
            audioEventListner.audioStarted();
        }
    }

    public void onEndedAction() {
        if (audioEventListner != null) {
            audioEventListner.audioEnded();
        }
    }

    public void onAudioFailed() {
        if (audioEventListner != null) {
            audioEventListner.audioFailed();
        }
    }

    public void onLoadedAction() {
        if (audioEventListner != null && !hasTriggeredOnLoaded) {
            hasTriggeredOnLoaded = true;
            audioEventListner.audioLoaded();
            if (autoPlay) {
                audioPlayer.play();
            }
        }
    }

    public void setEventListner(AudioEventListner audioEventListner) {
        this.audioEventListner = audioEventListner;
    }

//    public void playSampleAudio(RoundSample roundSample) {
//        final String[] soundFiles = roundSample.getLanguageSample().getSoundFiles();
//        playSample(soundFiles[roundSample.getSampleIndex()]);
//    }
//    @Deprecated
//    private void playSample(String sample) {
//        if (audioPlayer == null) {
//            try {
//                createPlayer();
//            } catch (AudioException audioException) {
//                audioExceptionListner.audioExceptionFired(audioException);
//                return;
//            }
//        }
//        audioPlayer.setSrc(sample);
//        //audioPlayer.setCurrentTime(0); // on android the if the ready state is not correct then this will fail and audio will not play
//        audioPlayer.play();
//    }
    public double getCurrentTime() {
        return (audioPlayer != null) ? audioPlayer.getCurrentTime() : -1;
//        return audioPlayer.getCurrentTime();
    }

    public void play() {
        if (audioPlayer != null) {
            audioPlayer.play();
        };
    }

    public void rewind() {
        if (audioPlayer != null) {
            audioPlayer.setCurrentTime(0);
        };
    }

    public void stopAll() {
        if (audioPlayer != null) {
            audioPlayer.pause();
//            audioPlayer.setSrc("");
            audioPlayer.removeFromParent();
            audioPlayer = null;
        }
        //onEndedAction();
        audioEventListner = null;
    }

    public void stop() {
        if (audioPlayer != null) {
            audioPlayer.pause();
        }
        //onEndedAction();
    }
}
