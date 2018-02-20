/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
package nl.mpi.tg.eg.experimentdesigner.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.PresenterScreen;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Aug 2, 2016 2:24:59 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AllWizardDataTest {

    public AllWizardDataTest() {
    }

    public void testGetWizardData(Experiment experiment) throws IOException, JAXBException, URISyntaxException {
        System.out.println("getWizardData: " + experiment.getAppNameInternal());
        final String name = "/frinex-rest-output/" + experiment.getAppNameInternal() + ".xml";
        System.out.println(name);
        URI testXmlUri = this.getClass().getResource(name).toURI();
        String expResult = new String(Files.readAllBytes(Paths.get(testXmlUri)), StandardCharsets.UTF_8);
        experiment.getPresenterScreen().sort(new Comparator<PresenterScreen>() {
            // because the experiment has not been stored and retrieved from the DB we need to sort this manually
            @Override
            public int compare(PresenterScreen o1, PresenterScreen o2) {
                return Long.compare(o1.getDisplayOrder(), o2.getDisplayOrder());
            }
        });
        JAXBContext jaxbContext = JAXBContext.newInstance(Experiment.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        final String testOutputName = experiment.getAppNameInternal() + "-testoutput.xml";
//        jaxbMarshaller.marshal(result, System.out);
        FileWriter fileWriter = new FileWriter(new File(new File(testXmlUri).getParentFile(), testOutputName));
        jaxbMarshaller.marshal(experiment, fileWriter);
        jaxbMarshaller.marshal(experiment, stringWriter);
        assertEquals(testOutputName, expResult, stringWriter.toString());
    }

    /**
     * Test of getWizardData method, of multiple wizard classes.
     *
     * @throws java.io.IOException
     * @throws javax.xml.bind.JAXBException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testAllGetWizardData() throws IOException, JAXBException, URISyntaxException {
        System.out.println("testAllGetWizardData");
        final DefaultTranslations defaultTranslations = new DefaultTranslations();
//        defaultTranslations.insertTranslations();
//        testGetWizardData(new DefaultExperiments().getAllOptionsExperiment(null, null, null));
        testGetWizardData(new DobesAnnotator().getExperiment());
        testGetWizardData(new JenaFieldKit().getExperiment());
//        testGetWizardData(new TransmissionChain().getExperiment());
        testGetWizardData(new ShawiFieldKit().getShawiExperiment());
        testGetWizardData(new Sara01().getExperiment());
        testGetWizardData(new FactOrFiction().getExperiment());
//        testGetWizardData(defaultTranslations.applyTranslations(new SynQuiz2().getExperiment()));
        testGetWizardData(new RdExperiment02().getExperiment());
        testGetWizardData(new NblExperiment01().getExperiment());
        testGetWizardData(new HRExperiment01().getExperiment());
        testGetWizardData(new HRPretest().getExperiment());
        testGetWizardData(new HRPretest02().getExperiment());
        testGetWizardData(new HROnlinePretest().getExperiment());
        testGetWizardData(new KinOathExample().getExperiment());
//        testGetWizardData(new RosselFieldKit().getExperiment());
        testGetWizardData(new Parcours().getExperiment());
        testGetWizardData(new MultiParticipant().getExperiment());
        testGetWizardData(new ShortMultiparticipant01().getExperiment());
        testGetWizardData(new ManipulatedContours().getExperiment());
        testGetWizardData(new FrenchConversation().getExperiment());
        testGetWizardData(new NonWacq().getExperiment());
        testGetWizardData(new SentencesRatingTask().getExperiment());
        testGetWizardData(new WellspringsSamoanFieldKit().getExperiment());
        testGetWizardData(new GuineaPigProject().getExperiment());
        testGetWizardData(new PlayhouseStudy().getExperiment());
        testGetWizardData(new Joost01().getExperiment());
        testGetWizardData(new Joost02().getExperiment());
        testGetWizardData(new PlaybackPreferenceMeasureExperiment().getExperiment());
    }
}
