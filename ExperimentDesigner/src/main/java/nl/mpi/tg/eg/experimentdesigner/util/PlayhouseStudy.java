/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics
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

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAudioTestScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardExistingUserCheckScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardGridStimulusScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardSelectUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardTextScreen;

/**
 * @since Jan 09, 2018 10:33 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class PlayhouseStudy {

    private final WizardController wizardController = new WizardController();
    final String completionScreenText1 = "Dit is het einde van het experiment.<br/>"
            + "Hartelijk dank voor uw deelname! <br/>"
            + "<br/>";
    final String completionScreenText2 = "<br/>"
            + "Het bovenstaande nummer is het bewijs dat u het experiment heeft voltooid, en is vereist voor het in orde maken van uw vergoeding.";

    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("Playhouse Study");
        wizardData.setShowMenuBar(false);
        wizardData.setTextFontSize(17);
        wizardData.setObfuscateScreenNames(false);

        final WizardTextScreen bluetoothInstructionsScreen = new WizardTextScreen("Bluetooth Instructions", "When the bluetooth controller is connected the virtual keyboard will not show, to enter participant metadata please turn off the bluetooth controller so that the virtual keyboard can be shown. To start the bluetooth controller turn it on and press the button combination M+A.", "Volgende");
        bluetoothInstructionsScreen.setNextHotKey("ENTER");
        wizardData.addScreen(bluetoothInstructionsScreen);

        final WizardExistingUserCheckScreen existingUserCheckScreen = new WizardExistingUserCheckScreen("Start", "New interview", "Resume interview", "Begin a new interview with a new participant", "Resume an interview with an existing participant");
        final WizardSelectUserScreen selectUserScreen = new WizardSelectUserScreen("Select Participant");
        wizardData.addScreen(existingUserCheckScreen);
        wizardData.addScreen(selectUserScreen);
        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Gegevens");
        wizardEditUserScreen.setMenuLabel("Terug");
        wizardEditUserScreen.setScreenTag("Edit User");
        wizardEditUserScreen.setNextButton("Volgende");
        wizardEditUserScreen.setSendData(false);
        wizardEditUserScreen.setOn_Error_Text("Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.");
        wizardEditUserScreen.setCustomFields(new String[]{
            "workerId:ppcode:.'{'3,'}':Voer minimaal drie letters.", // @todo: update the regex to date format and in the future add a calandar popup
            "datOfBirth:Geboortedatum:[0-3][0-9]/[0-1][0-9]/[1-2][0-9][0-9][0-9]:Voer een getal.",
            "groupCode:Group Code:[01]?[0-9]?[0-9]:Voer een nummer (000-199).",
            "experimentVersion:Experiment Version:1|2|3|4:Kies en experiment."
        });

        final WizardMenuScreen menuScreen = new WizardMenuScreen("Menu", "Menu", "Menu");
        wizardData.addScreen(menuScreen);
        wizardData.addScreen(wizardEditUserScreen);
        String backgroundImage = "huisje_02.jpg";
//        WizardAudioTestScreen introductionAudio1 = new WizardAudioTestScreen("Introduction 1", "&nbsp;", "continue button", "intro_1");
//        wizardData.addScreen(introductionAudio1);
//        WizardAudioTestScreen introductionAudio2 = new WizardAudioTestScreen("Introduction 2", "&nbsp;", "continue button", "intro_2");
//        wizardData.addScreen(introductionAudio2);
//        WizardAudioTestScreen introductionAudio3 = new WizardAudioTestScreen("Introduction 3", "&nbsp;", "continue button", "intro_3");
//        wizardData.addScreen(introductionAudio3);
//        introductionAudio1.setBackgroundImage(backgroundImage);
//        introductionAudio2.setBackgroundImage(backgroundImage);
//        introductionAudio3.setBackgroundImage(backgroundImage);
//        introductionAudio1.setAutoNext(true);
//        introductionAudio2.setAutoNext(true);
//        introductionAudio3.setAutoNext(true);
//        introductionAudio1.setAudioHotKey("ENTER");
//        introductionAudio2.setAudioHotKey("ENTER");
//        introductionAudio3.setAudioHotKey("ENTER");
//        introductionAudio1.setStyleName("titleBarButton");
//        introductionAudio2.setStyleName("titleBarButton");
//        introductionAudio3.setStyleName("titleBarButton");
        String[][][][] testList = new String[][][][]{
            {{{"Practice", "zoomToGarden", "Practice", "P_00.jpg", null, "1000", "Correct"}, {}},
            {{"P_01_NL", "P_01_NL.jpg"}, {"P_02_NL", "P_02_NL.jpg"}, {"P_03_Eng", "P_03_Eng.jpg"}, {"P_04_Eng", "P_04_Eng.jpg"}}},
            {{{"Matching1", "zoomToBlock1", "Matching1", null, "AudioAB", "2000", null}, {}},
            {{"M_01", "M_01.jpg"}, {"M_02", "M_02.jpg"}, {"M_03", "M_03.jpg"}, {"M_04", "M_04.jpg"}, {"M_05", "M_05.jpg"}, {"M_06", "M_06.jpg"}}},
            {{{"Matching2", "zoomToBlock2", "Matching2", null, "AudioAB", "2000", null}, {}},
            {{"M_07", "M_07.jpg"}, {"M_08", "M_08.jpg"}, {"M_09", "M_09.jpg"}, {"M_10", "M_10.jpg"}, {"M_11", "M_11.jpg"}, {"M_12", "M_12.jpg"}}},
            {{{"Test3", "zoomToBlock3", "Test3", null, null, "1000", "Correct"}, {}},
            {{"T_01", "T_01.jpg"}, {"T_02", "T_02.jpg"}, {"T_03", "T_03.jpg"}, {"T_04", "T_04.jpg"}, {"T_05", "T_05.jpg"}, {"T_06", "T_06.jpg"}, {"T_07", "T_07.jpg"}, {"T_08", "T_08.jpg"}, {"T_09", "T_09.jpg"}}},
            {{{"Test4", "zoomToBlock4", "Test4", null, null, "1000", "Correct"}, {}},
            {{"T_10", "T_10.jpg"}, {"T_11", "T_11.jpg"}, {"T_12", "T_12.jpg"}, {"T_13", "T_13.jpg"}, {"T_14", "T_14.jpg"}, {"T_15", "T_15.jpg"}, {"T_16", "T_16.jpg"}, {"T_17", "T_17.jpg"}, {"T_18", "T_18.jpg"}}},};
//        final WizardMenuScreen textMenuScreen = new WizardMenuScreen("TestMenu", "TestMenu", "TestMenu");
//        textMenuScreen.setJumpToRandomScreen(true);
//        wizardData.addScreen(textMenuScreen);
        WizardScreen backScreen = wizardEditUserScreen;
        for (String[][][] testSubList : testList) {
            final WizardGridStimulusScreen testStimulusScreen = new WizardGridStimulusScreen(testSubList[0][0][0], false, testSubList[1],
                    null, 1000, false, null, 0, 0, null);
            testStimulusScreen.setBackgroundImage(backgroundImage);
            testStimulusScreen.setCodeAudio(false);
            testStimulusScreen.setBackgroundStyle(testSubList[0][0][1]);
            testStimulusScreen.setRewardImage(testSubList[0][0][3]);
            testStimulusScreen.setAudioAB(testSubList[0][0][4] != null);
            testStimulusScreen.setSelectedPause(Integer.parseInt(testSubList[0][0][5]));
            testStimulusScreen.setCorrectAudio(testSubList[0][0][6]);
//            testStimulusScreen.setIntroAudio(testSubList[0][2]);
//            testStimulusScreen.setIntroAudioDelay(2000);
//            textMenuScreen.addTargetScreen(testStimulusScreen);
            wizardData.addScreen(testStimulusScreen);
            testStimulusScreen.setBackWizardScreen(backScreen);
            backScreen.setNextWizardScreen(testStimulusScreen);
            backScreen = testStimulusScreen;
//            testStimulusScreen.setNextWizardScreen(textMenuScreen);
        }
        WizardCompletionScreen completionScreen = new WizardCompletionScreen(completionScreenText1, false, true, true, completionScreenText2,
                "Opnieuw beginnen",
                "Einde van het experiment",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");
        completionScreen.setSendData(false);
        wizardData.addScreen(completionScreen);
        completionScreen.setScreenTag("completion");

        WizardAudioTestScreen atticScreen = new WizardAudioTestScreen("Attic", "&nbsp;", "continue button", "room_5");
        wizardData.addScreen(atticScreen);
        atticScreen.setBackgroundImage(backgroundImage);
        atticScreen.setBackgroundStyle("zoomToAttic");
        atticScreen.setAutoPlay(true);
        atticScreen.setAutoNext(true);
        atticScreen.setAutoNextDelay(2000);
        atticScreen.setAudioHotKey("R1_MA_A");
        atticScreen.setImageName("intro_1.jpg");
        atticScreen.setNextHotKey("ENTER");
        atticScreen.setStyleName("titleBarButton");
        atticScreen.setBackWizardScreen(menuScreen);
        atticScreen.setNextWizardScreen(completionScreen);
        bluetoothInstructionsScreen.setBackWizardScreen(menuScreen);
        bluetoothInstructionsScreen.setNextWizardScreen(existingUserCheckScreen);

//        existingUserCheckScreen.setNextWizardScreen(selectUserScreen);
        selectUserScreen.setBackWizardScreen(existingUserCheckScreen);
        selectUserScreen.setNextWizardScreen(wizardEditUserScreen);

//        wizardTextScreen.setNextWizardScreen(wizardEditUserScreen);
//        agreementScreen.setNextWizardScreen(wizardTextScreen);
//        wizardTextScreen.setBackWizardScreen(agreementScreen);
//        wizardEditUserScreen.setNextWizardScreen(trainingStimulusScreen);
//        introductionAudio1.setNextWizardScreen(introductionAudio2);
//        introductionAudio2.setNextWizardScreen(introductionAudio3);
//        introductionAudio3.setNextWizardScreen(trainingStimulusScreen);
//        fillerStimulusScreen.setNextWizardScreen(trainingStimulusScreen);
//        trainingStimulusScreen.setNextWizardScreen(textMenuScreen);
//        introductionAudio1.setBackWizardScreen(menuScreen);
//        introductionAudio2.setBackWizardScreen(menuScreen);
//        introductionAudio3.setBackWizardScreen(menuScreen);
//        fillerStimulusScreen.setBackWizardScreen(introductionAudio3);
//        trainingStimulusScreen.setBackWizardScreen(menuScreen);
//        textMenuScreen.setBackWizardScreen(menuScreen);
//        textMenuScreen.setNextWizardScreen(atticScreen);
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen("Over", false);
        wizardAboutScreen.setBackWizardScreen(menuScreen);
        completionScreen.setBackWizardScreen(menuScreen);
        completionScreen.setNextWizardScreen(wizardEditUserScreen);
        wizardData.addScreen(wizardAboutScreen);
        wizardEditUserScreen.setBackWizardScreen(selectUserScreen);

        return wizardData;
    }

    public Experiment getExperiment() {
        return wizardController.getExperiment(getWizardData());
    }
}
