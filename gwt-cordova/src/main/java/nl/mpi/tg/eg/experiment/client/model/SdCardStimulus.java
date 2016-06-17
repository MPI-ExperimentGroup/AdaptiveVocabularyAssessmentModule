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
package nl.mpi.tg.eg.experiment.client.model;

import java.util.List;
import java.util.Objects;

/**
 * @since Jan 11, 2016 4:07:26 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SdCardStimulus implements Stimulus {

    final private String uniqueId;
    final private String stimulusPath;
    final private String label;
    final private String code;
    final private int pauseMs;
    final private boolean mp3;
    final private String vidoPath;
    final private boolean image;

    public SdCardStimulus(String uniqueId, String stimulusPath, String label, String code, int pauseMs, boolean mp3, String vidoPath, boolean image) {
        this.uniqueId = uniqueId;
        this.stimulusPath = stimulusPath;
        this.label = label;
        this.code = code;
        this.pauseMs = pauseMs;
        this.mp3 = mp3;
        this.vidoPath = vidoPath;
        this.image = image;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getPauseMs() {
        return pauseMs;
    }

    @Override
    public String getMp3() {
        return mp3 ? stimulusPath : null;
    }

    @Override
    public String getImage() {
        return image ? stimulusPath : null;
    }

    @Override
    public String getMp4() {
        return vidoPath + ".mp4";
    }

    @Override
    public String getOgg() {
        return vidoPath + ".ogg";
    }

    @Override
    public boolean hasImage() {
        return image;
    }

    @Override
    public boolean hasAudio() {
        return mp3;
    }

    @Override
    public boolean hasVideo() {
        return vidoPath != null;
    }

    @Override
    public boolean hasRatingLabels() {
        return false;
    }

    @Override
    public String getRatingLabels() {
        return null;
    }

    @Override
    public List<?> getTags() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Stimulus o) {
        return this.uniqueId.compareTo(o.getUniqueId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.uniqueId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stimulus other = (Stimulus) obj;
        if (!Objects.equals(this.uniqueId, other.getUniqueId())) {
            return false;
        }
        return true;
    }
}
