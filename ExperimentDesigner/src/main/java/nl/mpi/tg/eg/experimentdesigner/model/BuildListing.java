/*
 * Copyright (C) 2020 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.util.Date;

/**
 * @since 04-03-2020 11:27 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class BuildListing {

    final private Experiment experiment;

    public BuildListing(Experiment experiment) {
        this.experiment = experiment;
    }

    public String getExperimentInternalName() {
        return experiment.getAppNameInternal();
    }

    public String getExperimentDisplayName() {
        return experiment.getAppNameDisplay();
    }

    public PublishEvents.PublishState getState() {
        return experiment.getPublishEvents().get(0).getState();
    }

    public String getPublishDate() {
        return experiment.getPublishEvents().get(0).getPublishDate();
    }

    public String getExpiryDate() {
        return experiment.getPublishEvents().get(0).getExpiryDate();
    }

    public boolean isIsWebApp() {
        return experiment.getPublishEvents().get(0).isIsWebApp();
    }

    public boolean isIsiOS() {
        return experiment.getPublishEvents().get(0).isIsiOS();
    }

    public boolean isIsAndroid() {
        return experiment.getPublishEvents().get(0).isIsAndroid();
    }

    public boolean isIsDesktop() {
        return experiment.getPublishEvents().get(0).isIsDesktop();
    }

    public float getDefaultScale() {
        return experiment.getDefaultScale();
    }

    public int isIsScalable() {
        return (experiment.isIsScalable()) ? 1 : 0;
    }
}
