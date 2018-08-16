/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @since Jun 7, 2018 5:02:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class XpathExperimentValidatorTest {

    public XpathExperimentValidatorTest() {
    }

    private Document getDocument(final String stringXml) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(stringXml.getBytes("UTF-8"));
        return builder.parse(inputStream);
    }

    /**
     * Test of validateInternalName method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidateInternalName() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validateInternalName");
        String fileOkName1 = "generic_example.xml";
        String fileFailName1 = "geNeric_example.xml";
        String fileFailName2 = "geNeric_example.xml";
        String fileOkName2 = "generic_example";
        Document xmlOkDocument = getDocument("<experiment appNameDisplay=\"generic_example\" appNameInternal=\"generic_example\"/>\n");
        Document xmlFailDocument = getDocument("<experiment appNameDisplay=\"generic_example\" appNameInternal=\"generic_exaMple\"/>\n");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validateInternalName(fileOkName1, xmlOkDocument));
        assertEquals("", instance.validateInternalName(fileOkName2, xmlOkDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName1, xmlOkDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName2, xmlOkDocument));
        assertNotEquals("", instance.validateInternalName(fileOkName1, xmlFailDocument));
        assertNotEquals("", instance.validateInternalName(fileOkName2, xmlFailDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName1, xmlFailDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName2, xmlFailDocument));
    }

    /**
     * Test of validatePresenterNames method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidatePresenterNames() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validatePresenterNames");
        Document xmlOkDocument = getDocument("<experiment><presenter self=\"Toestemming\"></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        Document xmlFailDocument = getDocument("<experiment><presenter self=\"Informatie\"></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validatePresenterNames(xmlOkDocument));
        assertNotEquals("", instance.validatePresenterNames(xmlFailDocument));
    }

    /**
     * Test of validatePresenterLinks method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidatePresenterLinks() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validatePresenterLinks");
        Document xmlOkDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        Document xmlFailNextDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Missing\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailBackDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Missing\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailTarget1Document = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"missing\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailTarget2Document = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"missing\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validatePresenterLinks(xmlOkDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailNextDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailBackDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailTarget1Document));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailTarget2Document));
    }

    /**
     * Test of validateStimuliTags method, of class XpathExperimentValidator.
     */
    @Test
    public void testValidateStimuliTags() throws Exception {
        System.out.println("validateStimuliTags");
        Document xmlOkTagDocument = getDocument("<experiment><presenter self=\"Informatie\"><loadStimulus eventTag=\"loadTargetPicture\" randomise=\"false\" repeatRandomWindow=\"0\" maxStimuli=\"1000\" repeatCount=\"1\"><hasMoreStimulus></hasMoreStimulus><endOfStimulus></endOfStimulus><randomGrouping/><stimuli><tag>Testrun1</tag></stimuli></loadStimulus></presenter><stimuli><stimulus audioPath=\"daga3_cut\" identifier=\"daga3_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga3_cut\"/><stimulus audioPath=\"daga11_cut\" identifier=\"daga11_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga11_cut Testrun1\"/><stimulus audioPath=\"daga5_cut\" identifier=\"daga5_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga5_cut\"/></stimuli></experiment>");
        Document xmlFailTagSpaceDocument = getDocument("<experiment><presenter self=\"Informatie\"><loadStimulus eventTag=\"loadTargetPicture\" randomise=\"false\" repeatRandomWindow=\"0\" maxStimuli=\"1000\" repeatCount=\"1\"><hasMoreStimulus></hasMoreStimulus><endOfStimulus></endOfStimulus><randomGrouping/><stimuli><tag>Testrun1 space</tag></stimuli></loadStimulus></presenter><stimuli><stimulus audioPath=\"daga3_cut\" identifier=\"daga3_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga3_cut\"/><stimulus audioPath=\"daga11_cut\" identifier=\"daga11_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga11_cut Testrun1\"/><stimulus audioPath=\"daga5_cut\" identifier=\"daga5_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga5_cut\"/></stimuli></experiment>");
        Document xmlFailTagDocument = getDocument("<experiment><presenter self=\"Informatie\"><loadStimulus eventTag=\"loadTargetPicture\" randomise=\"false\" repeatRandomWindow=\"0\" maxStimuli=\"1000\" repeatCount=\"1\"><hasMoreStimulus></hasMoreStimulus><endOfStimulus></endOfStimulus><randomGrouping/><stimuli><tag>Testrun1</tag></stimuli></loadStimulus></presenter><stimuli><stimulus audioPath=\"daga3_cut\" identifier=\"daga3_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga3_cut\"/><stimulus audioPath=\"daga11_cut\" identifier=\"daga11_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga11_cut\"/><stimulus audioPath=\"daga5_cut\" identifier=\"daga5_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga5_cut\"/></stimuli></experiment>");
        Document xmlOkTagsDocument = getDocument("<experiment><presenter self=\"Informatie\"><loadStimulus eventTag=\"loadTargetPicture\" randomise=\"false\" repeatRandomWindow=\"0\" maxStimuli=\"1000\" repeatCount=\"1\"><hasMoreStimulus><currentStimulusHasTag tags=\"ประเพณีบุญบั้งไฟ Rocket Festival Lao Thai ບຸນບັ້ງໄຟ\" msToNext=\"60\"><conditionTrue><plainText featureText=\"plainText in conditionTrue\"/></conditionTrue><conditionFalse><plainText featureText=\"plainText in conditionFalse\"/></conditionFalse></currentStimulusHasTag></hasMoreStimulus><endOfStimulus></endOfStimulus><randomGrouping/><stimuli></stimuli></loadStimulus></presenter><stimuli><stimulus audioPath=\"daga3_cut\" identifier=\"daga3_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"ประเพณีบุญบั้งไฟ Rocket daga3_cut\"/><stimulus audioPath=\"daga11_cut\" identifier=\"daga11_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"Festival Lao Thai daga11_cut\"/><stimulus audioPath=\"daga5_cut\" identifier=\"daga5_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"ບຸນບັ້ງໄຟ daga5_cut\"/></stimuli></experiment>");
        Document xmlFailTagsDocument = getDocument("<experiment><presenter self=\"Informatie\"><loadStimulus eventTag=\"loadTargetPicture\" randomise=\"false\" repeatRandomWindow=\"0\" maxStimuli=\"1000\" repeatCount=\"1\"><hasMoreStimulus><currentStimulusHasTag tags=\"ประเพณีบุญบั้งไฟ Rocket Festival Lao Thai ບຸນບັ້ງໄຟ\" msToNext=\"60\"><conditionTrue><plainText featureText=\"plainText in conditionTrue\"/></conditionTrue><conditionFalse><plainText featureText=\"plainText in conditionFalse\"/></conditionFalse></currentStimulusHasTag></hasMoreStimulus><endOfStimulus></endOfStimulus><randomGrouping/><stimuli></stimuli></loadStimulus></presenter><stimuli><stimulus audioPath=\"daga3_cut\" identifier=\"daga3_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga3_cut\"/><stimulus audioPath=\"daga11_cut\" identifier=\"daga11_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga11_cut\"/><stimulus audioPath=\"daga5_cut\" identifier=\"daga5_cut\" correctResponses=\"1\" pauseMs=\"0\" tags=\"daga5_cut\"/></stimuli></experiment>");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validateStimuliTags(xmlOkTagDocument));
        assertNotEquals("", instance.validateStimuliTags(xmlFailTagDocument));
        assertEquals("", instance.validateStimuliTags(xmlOkTagsDocument));
        assertNotEquals("", instance.validateStimuliTags(xmlFailTagsDocument));
        assertNotEquals("", instance.validateStimuliTags(xmlFailTagSpaceDocument));
    }
}
