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

import nl.mpi.tg.eg.experimentdesigner.controller.WizardController;
import nl.mpi.tg.eg.experimentdesigner.model.Experiment;
import nl.mpi.tg.eg.experimentdesigner.model.WizardData;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAboutScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardAgreementScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardCompletionScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardEditUserScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMenuScreen;
import nl.mpi.tg.eg.experimentdesigner.model.wizard.WizardMultiParticipantScreen;

/**
 * @since Oct 21, 2016 11:52:03 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MultiParticipant {

    private final WizardController wizardController = new WizardController();
    // @todo: scoring for each participant 
    // @todo: the allocated member id must maintained thoughout the experiment
    // @todo: show the experiment total score not the participants score
    // @todo: round 0 is the naming screen and does not collect guesses and therefore no scores
    // @todo: make the stimuli list common between all group members
    //  @todo: limit the communication to within each channel
    // @todo: add the group data collection
    // @todo: add the group data CSV output
    // @todo: add an option in the admin reporting app to "hide ugly details" which hides strings like UUID and browser version strings

//,Round,Dyad,Game.no,Item.ID,Shape,Size,RawSize,ItemCurrentAge,Producer,Word,ACC
//165,1,AB,1,10,2,small,2.74,2,A,flup,0
//167,1,CD,1,10,2,small,2.74,2,C,mozel,1
//65,1,AB,2,5,1,small,2.94,2,B,mozel,1
//67,1,CD,2,5,1,small,2.94,2,D,tjalp,1
//297,1,AB,3,19,4,medium,4.4,2,A,flolp,1
//299,1,CD,3,19,4,medium,3.95,2,C,vlolp,1
//147,1,AB,4,9,2,medium,4.51,2,B,kimi,0
//149,1,CD,4,9,2,medium,4.51,2,D,vlalp,0
//85,1,AB,5,6,2,big,7.6,2,A,dauft,0
//87,1,CD,5,6,2,big,7.6,2,C,potmik,1
//    int[] possibleAngles = new int[]{30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};   
//    private String[] getStimuli(){
//        for (int textureIndex = 1; textureIndex < 29; textureIndex++) {
//            
//        }
//    }
    final int numberOfStimuli = 23;
    final int repeatCountStimuli = 3;
    final int randomWindowStimuli = 3;
    final String preStimuliText = "&nbsp;";
    final String postStimuliText = "&nbsp;";

    private final String[] stimuliArray = new String[]{
        "2.png:shape1:version1:quadrant3:moveRotated270",
        "6.png:shape1:version1:quadrant4:moveRotated300",
        "27.png:shape4:version1:quadrant2:moveRotated120",
        "12.png:shape2:version1:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated30",
        "24.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version4:quadrant2:moveRotated180",
        "23.png:shape4:version1:version1round2:version1round3:version1round4:version1round5:version5:quadrant4:moveRotated360",
        "3.png:shape1:version1:version1round3:version1round4:version1round5:quadrant1:moveRotated90",
        "11.png:shape2:version1:version1round3:version1round4:version1round5:quadrant4:moveRotated330",
        "20.png:shape3:version1:version1round3:version1round4:version1round5:quadrant3:moveRotated240",
        "14.png:shape2:version1:version1round4:version1round5:quadrant2:moveRotated120",
        "21.png:shape3:version1:version1round4:version1round5:quadrant4:moveRotated360",
        "4.png:shape1:version1:version1round4:version1round5:version5:version5zero:quadrant4:moveRotated330",
        "25.png:shape4:version1:version1round5:quadrant4:moveRotated315",
        "16.png:shape3:version1:version1round5:version4:version4zero:quadrant1:moveRotated45",
        "1.png:shape1:version1:version1round5:version5:quadrant3:moveRotated210",
        "7.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135",
        "9.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated270",
        "10.png:shape2:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated150",
        "19.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant2:moveRotated135",
        "22.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant1:moveRotated60",
        "28.png:shape4:version1:version1zero:version1round2:version1round3:version1round4:version1round5:quadrant3:moveRotated225",
        "17.png:shape3:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version2:quadrant4:moveRotated315",
        "5.png:shape1:version1:version1zero:version1round2:version1round3:version1round4:version1round5:version6:quadrant1:moveRotated30"
    };

    // @todo: server shared variables to be used in animations and interactions concurrently displayed on multiple users devices 
    public WizardData getWizardData() {
        WizardData wizardData = new WizardData();
        wizardData.setAppName("MultiParticipant");
        wizardData.setShowMenuBar(true);
        wizardData.setObfuscateScreenNames(false);
        wizardData.setTextFontSize(24);

        final WizardAgreementScreen wizardAgreementScreen = new WizardAgreementScreen("Agreement", "<b>This is a prototype multiparticipant experiment.</b><br/><br/>"
                + "With this prototype you can:<br/>"
                + "<li>view the group activity and add dummy users to a group for testing purposes <a href='/multiparticipant/grouptestpage.html'>/multiparticipant/grouptestpage.html</a>.</li>"
                + "<li>erase the local data for this browser <a href='/multiparticipant?debug'>/multiparticipant?debug</a></li>"
                + "<li>random data can be generated with the testing robot <a href='/multiparticipant/TestingFrame.html'>/multiparticipant/TestingFrame.html</a></li><br/>"
                + "<li>eight users intracting can be randomly simulated testing robot <a href='/multiparticipant/grouptestframes.html'>/multiparticipant/grouptestframes.html</a></li><br/>"
                + "The group name must be allocated with the following with <a href='/multiparticipant/?group=a_group_name'>/multiparticipant/?group=a_group_name</a> where a_group_name should be replaced with a suitable string. A second user can be tested on one computer via the incognito browser window with this link, providing the group name matches.<br/><br/>"
                + "There needs to be eight users connected for the group process to begin. Once a group is full, any subsequent users will need to be allocated a different group via the a_group_name parameter.<br/><br/><br/>"
                + "You can view the collected group data with <a href='/multiparticipant-admin/groupdataviewer'>/multiparticipant-admin/groupdataviewer</a> with the user and password you have been supplied.<br/><br/><br/>",
                "Continue");
        final WizardAboutScreen wizardAboutScreen = new WizardAboutScreen(true);

        final WizardEditUserScreen wizardEditUserScreen = new WizardEditUserScreen();
        wizardEditUserScreen.setScreenTitle("Edit User");
        wizardEditUserScreen.setMenuLabel("Edit User");
        wizardEditUserScreen.setScreenTag("Edit_User");
        wizardEditUserScreen.setNextButton("Next");
        wizardEditUserScreen.setSendData(true);
        wizardEditUserScreen.setOn_Error_Text("Could not contact the server, please check your internet connection and try again.");
        wizardEditUserScreen.setAgeField();
        wizardEditUserScreen.setFirstNameField();
        wizardEditUserScreen.setMandatoryGenderField();
        wizardEditUserScreen.setWorkerIdField();

        final String groupMembers4 = "A,B,C,D";
        final String groupMembers8 = "A,B,C,D,E,F,G,H";

        WizardMenuScreen menuScreen4or8Members = new WizardMenuScreen("GroupSizeMenu", "GroupSizeMenu", "GroupSizeMenu");
        menuScreen4or8Members.setBranchOnGetParam(true, "A choice must be provided out of the following:<br/>");
        WizardCompletionScreen completionScreen = new WizardCompletionScreen("Einde van het experiment", false, false,
                //                                "Wil nog iemand op dit apparaat deelnemen aan dit onderzoek, klik dan op de onderstaande knop.",
                "Einde van het experiment1",
                "Opnieuw beginnen",
                "Einde van het experiment2",
                "Geen verbinding met de server. Controleer alstublieft uw internetverbinding en probeer het opnieuw.",
                "Probeer opnieuw");

        wizardData.addScreen(wizardAgreementScreen);
        wizardData.addScreen(wizardEditUserScreen);
        wizardData.addScreen(menuScreen4or8Members);

        String[][] groupOfFourCommunicationChannels = new String[][]{
            {"0", "A,B,C,D", "naming", "version1zero"},
            {"1", "A,B|D,C", "play", "version1zero"},
            {"2", "C,A|D,B", "play", "version1round2"},
            {"3", "B,C|A,D", "play", "version1round3"},
            {"4", "A,B|D,C", "play", "version1round4"},
            {"5", "C,A|D,B", "play", "version1round5"},
            {"6", "B,C|A,D", "play", "version1"},
            {"7", "A,B|D,C", "play", "version1"},
            {"8", "A|B|C|D", "test", "version1"}
        };
        WizardMultiParticipantScreen roundOfFourScreenOuter = null;
        for (String[] currentChannel : groupOfFourCommunicationChannels) {
            final WizardMultiParticipantScreen roundScreen;
            if ("naming".equals(currentChannel[2])) {
                roundScreen = getNamingRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1], "A:-:B:-:C:-:D:-", "B,C,D:-:A,C,D:-:B,A,D:-:B,C,A:-", "-:A,B,C,D:-:A,B,C,D:-:A,B,C,D:-:A,B,C,D", "-:A:-:B:-:C:-:D");
            } else if ("test".equals(currentChannel[2])) {
                roundScreen = getTestRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1], "A,B,C,D:-", "-:A,B,C,D");
            } else {
                roundScreen = getPlayingRound("R" + currentChannel[0] + "-4", groupMembers4, currentChannel[1]);
            }
            roundScreen.setStimuliSet(stimuliArray);
            roundScreen.getWizardScreenData().setStimuliRandomTags(new String[]{currentChannel[3]});
            roundScreen.setStimulusFreeText(true, "[wetuiopasfghknm ]{2,}", "You can only use the letters 'wetuiopasfghknm'.", "The key '<keycode>' is not allowed.");
            roundScreen.setAllowedCharCodes("wetuiopasfghknm");
            wizardData.addScreen(roundScreen);
            if (roundOfFourScreenOuter == null) {
                menuScreen4or8Members.addTargetScreen(roundScreen);
            } else {
                roundOfFourScreenOuter.setNextWizardScreen(roundScreen);
            }
//            roundScreen.setBackWizardScreen(wizardAgreementScreen);
            roundOfFourScreenOuter = roundScreen;
        }
        if (roundOfFourScreenOuter != null) {
            roundOfFourScreenOuter.setNextWizardScreen(completionScreen);
        }
        String[][] groupOfEightCommunicationChannels = new String[][]{
            {"0", "A,B,C,D,E,F,G,H", "naming", "version1zero"},
            {"1", "A,B|C,D|E,F|G,H", "play", "version1zero"},
            {"2", "H,A|D,E|F,C|B,G", "play", "version1round2"},
            {"3", "A,D|G,F|E,B|C,H", "play", "version1round3"},
            {"4", "C,A|B,D|E,G|F,H", "play", "version1round4"},
            {"5", "A,E|D,H|B,F|G,C", "play", "version1round5"},
            {"6", "F,A|G,D|C,B|H,E", "play", "version1round5"},
            {"7", "A,G|H,B|E,C|D,F", "play", "version1round5"},
            {"8", "A|B|C|D|E|F|G|H", "test", "version1round5"},
            {"9", "B,A|D,C|F,E|H,G", "play", "version1round5"},
            {"10", "A,H|E,D|C,F|G,B", "play", "version1round5"},
            {"11", "D,A|F,G|B,E|H,C", "play", "version1round5"},
            {"12", "A,C|D,B|G,E|H,F", "play", "version1round5"},
            {"13", "E,A|H,D|F,B|C,G", "play", "version1round5"},
            {"14", "A,F|D,G|B,C|E,H", "play", "version1round5"},
            {"15", "G,A|B,H|C,E|F,D", "play", "version1round5"},
            {"16", "A|B|C|D|E|F|G|H", "test", "version1round5"}
        };
        WizardMultiParticipantScreen roundOfEightScreenOuter = null;
        for (String[] currentChannel : groupOfEightCommunicationChannels) {
            final WizardMultiParticipantScreen roundScreen;
            if ("naming".equals(currentChannel[2])) {
                roundScreen = getNamingRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1], "A:-:B:-:C:-:D:-:E:-:F:-:G:-:H:-", "B,C,D,E,F,G,H:-:A,C,D,E,F,G,H:-:B,A,D,E,F,G,H:-:B,C,A,E,F,G,H:-:B,C,D,A,F,G,H:-:B,C,D,E,A,G,H:-:B,C,D,E,F,A,H:-:B,C,D,E,F,G,A:-", "-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H:-:A,B,C,D,E,F,G,H", "-:A:-:B:-:C:-:D:-:E:-:F:-:G:-:H");
            } else if ("test".equals(currentChannel[2])) {
                roundScreen = getTestRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1], "A,B,C,D,E,F,G,H:-", "-:A,B,C,D,E,F,G,H");
            } else {
                roundScreen = getPlayingRound("R" + currentChannel[0] + "-8", groupMembers8, currentChannel[1]);
            }
            roundScreen.setStimuliSet(stimuliArray);
            roundScreen.getWizardScreenData().setStimuliRandomTags(new String[]{currentChannel[3]});
            roundScreen.setStimulusFreeText(true, "[wetuiopasfghknm ]{2,}", "You can only use the letters 'wetuiopasfghknm'.", "The key '<keycode>' is not allowed.");
            roundScreen.setAllowedCharCodes("wetuiopasfghknm");
            wizardData.addScreen(roundScreen);
            if (roundOfEightScreenOuter == null) {
                menuScreen4or8Members.addTargetScreen(roundScreen);
            } else {
                roundOfEightScreenOuter.setNextWizardScreen(roundScreen);
            }
//            roundScreen.setBackWizardScreen(wizardAgreementScreen);
            roundOfEightScreenOuter = roundScreen;
        }
        if (roundOfEightScreenOuter != null) {
            roundOfEightScreenOuter.setNextWizardScreen(completionScreen);
        }

        wizardData.addScreen(wizardAboutScreen);
        wizardData.addScreen(completionScreen);

        wizardAgreementScreen.setNextWizardScreen(wizardEditUserScreen);
        wizardEditUserScreen.setBackWizardScreen(wizardAgreementScreen);
        wizardEditUserScreen.setNextWizardScreen(menuScreen4or8Members);

//        endTextScreen.setNextWizardScreen(wizardAboutScreen);
        wizardAboutScreen.setBackWizardScreen(wizardAgreementScreen);

        return wizardData;
    }

    protected WizardMultiParticipantScreen getTestRound(final String screenName, final String groupMembers4, final String communicationChannels, final String textEntryPhaseRoles, final String groupRecordSubmitionPhaseRoles) {
        // done: test round needs to submit dat to the group table even though its not a group interaction
        // done: in the testing round there are cases where the entered text is not recorded but the interaction is!!!
        return new WizardMultiParticipantScreen(screenName,
                groupMembers4, 2,
                communicationChannels, textEntryPhaseRoles,
                "&nbsp;",
                "",
                true,
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                groupRecordSubmitionPhaseRoles,
                preStimuliText, postStimuliText,
                numberOfStimuli, repeatCountStimuli, randomWindowStimuli,
                0,
                0, 0, null
        );
    }

    protected WizardMultiParticipantScreen getNamingRound(final String screenName, final String groupMembers4, final String communicationChannels, final String textEntryPhaseRoles, final String waitingForProducerPhaseRoles, final String outcomeDisplayedPhaseRoles, final String groupRecordSubmitionPhaseRoles) {
        return new WizardMultiParticipantScreen(screenName,
                groupMembers4, 2,
                communicationChannels, textEntryPhaseRoles,
                "&nbsp;", waitingForProducerPhaseRoles,
                true,
                "&nbsp;",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen",
                "",
                "This phase is not used in this screen", outcomeDisplayedPhaseRoles,
                "&nbsp;",
                groupRecordSubmitionPhaseRoles,
                "",
                preStimuliText, postStimuliText,
                numberOfStimuli, repeatCountStimuli, randomWindowStimuli,
                7000,
                0, 0, null);
    }

    protected WizardMultiParticipantScreen getPlayingRound(final String screenName, final String groupMembers, final String communicationChannels) {
        final String textEntryPhaseText = "&nbsp;";
        final String textWaitPhaseText = "&nbsp;";
        final String gridWaitPhaseText = "&nbsp;";
        final String responseGridPhaseText = "&nbsp;";
        final String mutualFeedbackPhaseText = "&nbsp;";
        final int timerCountDownProducerMs = 30 * 1000;
        final int timerCountDownGuesserMs = 20 * 1000;
        final String timerCountDownLabel = "Time is up! Play now!";

        String phaseRoleA = "";
        String phaseRoleB = "";
        for (final String channel : communicationChannels.split("\\|")) {
            System.out.println("channel:" + channel);
            final String[] members = channel.split(",");
            for (int index = 0; index < members.length; index += 2) {
                if (!phaseRoleA.isEmpty()) {
                    phaseRoleA += ",";
                }
                if (!phaseRoleB.isEmpty()) {
                    phaseRoleB += ",";
                }
                phaseRoleA += members[index];
                phaseRoleB += members[index + 1];
            }
        }
        final String responseGridPhaseRoles = "-:" + phaseRoleB + ":-:-:" + phaseRoleA + ":-";
        final String mutualFeedbackPhaseRoles = "-:-:" + phaseRoleA + "," + phaseRoleB + ":-:-:" + phaseRoleA + "," + phaseRoleB;
        final String textWaitPhaseRoles = phaseRoleB + ":-:-:" + phaseRoleA + ":-:-";
        final String textEntryPhaseRoles = phaseRoleA + ":-:-:" + phaseRoleB + ":-:-";
        final String gridWaitPhaseRoles = "-:" + phaseRoleA + ":-:-:" + phaseRoleB + ":-";

        return new WizardMultiParticipantScreen(screenName,
                groupMembers, 3,
                communicationChannels,
                textEntryPhaseRoles,
                textEntryPhaseText,
                textWaitPhaseRoles,
                false,
                textWaitPhaseText,
                gridWaitPhaseRoles,
                gridWaitPhaseText,
                responseGridPhaseRoles,
                responseGridPhaseText,
                mutualFeedbackPhaseRoles,
                mutualFeedbackPhaseText,
                "",
                "This phase is not used in this screen",
                "",
                "",
                preStimuliText, postStimuliText,
                numberOfStimuli, repeatCountStimuli, randomWindowStimuli,
                0,
                timerCountDownProducerMs, timerCountDownGuesserMs, timerCountDownLabel);
    }

    public Experiment getExperiment() {
        final Experiment experiment = wizardController.getExperiment(getWizardData());
        experiment.setIsScalable(false);
        experiment.setDefaultScale(1.2f);
        return experiment;
    }
}
