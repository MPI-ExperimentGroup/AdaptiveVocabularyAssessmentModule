/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics
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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;

/**
 * @since Aug 18, 2015 1:42:03 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class PresenterScreen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String menuLabel;
    private String backPresenterTag;
    private String selfPresenterTag;
    private String nextPresenterTag;

    @Enumerated(EnumType.STRING)
    private PresenterType presenterType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresenterFeature> presenterFeatures = new ArrayList<>();

    public PresenterScreen() {
    }

    public PresenterScreen(String title, String menuLabel, String backPresenterTag, String selfPresenterTag, String nextPresenterTag, PresenterType presenterType) {
        this.title = title;
        this.menuLabel = menuLabel;
        this.backPresenterTag = backPresenterTag;
        this.selfPresenterTag = selfPresenterTag;
        this.nextPresenterTag = nextPresenterTag;
        this.presenterType = presenterType;
    }

    public void setPresenterType(PresenterType presenterType) {
        this.presenterType = presenterType;
    }

    public long getId() {
        return id;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    @XmlAttribute(name = "back")
    public String getBackPresenterTag() {
        return backPresenterTag;
    }

    public void setBackPresenterTag(String backPresenterTag) {
        this.backPresenterTag = backPresenterTag;
    }

    @XmlAttribute(name = "self")
    public String getSelfPresenterTag() {
        return selfPresenterTag;
    }

    public void setSelfPresenterTag(String selfPresenterTag) {
        this.selfPresenterTag = selfPresenterTag;
    }

    @XmlAttribute(name = "next")
    public String getNextPresenterTag() {
        return nextPresenterTag;
    }

    public void setNextPresenterTag(String nextPresenterTag) {
        this.nextPresenterTag = nextPresenterTag;
    }

    @XmlAttribute(name = "type")
    public PresenterType getPresenterType() {
        return presenterType;
    }

    public List<PresenterFeature> getPresenterFeatureList() {
        return presenterFeatures;
    }

    @XmlElement(name = "feature")
    public List<PresenterFeature> getPresenterFeature() {
        return null;
    }

    @XmlAnyElement
    public List<JAXBElement<PresenterFeature>> getPresenterFeatures() {
        List<JAXBElement<PresenterFeature>> elements = new ArrayList<>();
        presenterFeatures.stream().forEach((feature) -> {
            elements.add(new JAXBElement<>(new QName(feature.getFeatureType().name()), PresenterFeature.class, feature));
        });
        return elements;
    }

//    @XmlElements({
//        @XmlElement(name = "htmlText", type = PresenterFeature.class),
//        @XmlElement(name = "padding", type = PresenterFeature.class),
//        @XmlElement(name = "AudioRecorderPanel", type = PresenterFeature.class),
//        @XmlElement(name = "optionButton", type = PresenterFeature.class),
//        @XmlElement(name = "padding", type = PresenterFeature.class),
//        @XmlElement(name = "versionData", type = PresenterFeature.class)
//    })
//    public void setPresenterFeatures(List<PresenterFeature> presenterFeatures) {
//        this.presenterFeatures = presenterFeatures;
//    }
}
