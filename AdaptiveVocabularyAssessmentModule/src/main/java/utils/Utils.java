/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.AtomBookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.Constants;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.fasttrack.fintetuning.FineTuningBookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.AdVocAsAtomStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;

/**
 *
 * @author olhshk
 */
public class Utils {

    public static void testPrint() {
        AdVocAsAtomStimulus[][] tmpwords = VocabularyFromFiles.getWords();
        System.out.println("Words \n");

        for (int i = 0; i < tmpwords.length; i++) {
            System.out.println(i + 1);
            for (AdVocAsAtomStimulus unit : tmpwords[i]) {
                System.out.println(unit.getLabel());
            }
        }
        ArrayList<AdVocAsAtomStimulus> tmpnonwords = VocabularyFromFiles.getNonwords();
        System.out.println("Non words \n");
        for (AdVocAsAtomStimulus nonword : tmpnonwords) {
            System.out.println(nonword.getLabel());
        }
    }

    public static void writeCsvFileFastTrack(AdVocAsStimuliProvider provider, int stopBand, String outputDir) throws IOException {
        long millis = System.currentTimeMillis();
        int blockSize = Constants.NONWORDS_PER_BLOCK * Constants.AVRERAGE_NON_WORD_POSITION;
        String fileName = "Fast_track_test_stopped_at_band_" + stopBand + "_" + blockSize + "_" + millis + ".csv";
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File csvFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFastTrack("", "\n", "", ";");
        BufferedWriter output = new BufferedWriter(new FileWriter(csvFile));
        output.write(inhoud);
        output.close();
    }

    public static void writeHtmlFileFastTrack(AdVocAsStimuliProvider provider, int stopBand, String outputDir) throws IOException {
        long millis = System.currentTimeMillis();
        int blockSize = Constants.NONWORDS_PER_BLOCK * Constants.AVRERAGE_NON_WORD_POSITION;
        String fileName = "Fast_track_test_stopped_at_band_" + stopBand + "_" + blockSize + "_" + millis + ".html";
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFastTrack("<tr>", "</tr>", "<td>", "</td>");
        String htmlString = "<!DOCTYPE html><html><body><table border=1>" + inhoud + "</table></body></html>";
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        output.write(htmlString);
        output.close();
    }

    public static void writeCsvFileFineTuningPreset(ArrayList<ArrayList<FineTuningBookkeepingStimulus>> stimulae, String outputDir) throws IOException {
        long millis = System.currentTimeMillis();
        String fileName = "Fine_tuning_preset_" + "_" + millis + ".csv";
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File csvFile = new File(outputDir, fileName);
        final FileWriter csvFileWriter = new FileWriter(csvFile, false);
        csvFileWriter.write("QuadrupleNummer;Spelling;BandNumber;UserAnswer;Correctness;isUsed\n");
        int quadrupleCounter = 0;
        for (int bandCounter = 0; bandCounter < stimulae.size(); bandCounter++) {
            for (FineTuningBookkeepingStimulus stimulus : stimulae.get(bandCounter)) {
                for (int i = 0; i < Constants.FINE_TUNING_NUMBER_OF_ATOMS_PER_TUPLE; i++) {
                    AtomBookkeepingStimulus aStimulus = stimulus.getAtomStimulusAt(i);
                    String row = quadrupleCounter + ";" + aStimulus.getSpelling()
                            + ";" + aStimulus.getBandNumber()
                            + ";" + aStimulus.getReaction() + ";" + aStimulus.getCorrectness()
                            + ";" + aStimulus.getIsUsed();
                    //System.out.println(row);
                    //System.out.println(row);
                    csvFileWriter.write(row + "\n");
                }
            }
        }
        csvFileWriter.close();
    }

    public static void writeCsvFileFineTuningHistoryShortened(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("writeCsvFile: " + outputDir + fileName);
        final File csvFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFineTuningHistoryShortened("", "\n", "", ";");
        BufferedWriter output = new BufferedWriter(new FileWriter(csvFile));
        output.write(inhoud);
        output.close();
    }

    public static void writeHtmlFileFineTuningHistoryShortened(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("writeHtmlFile: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        String inhoud = provider.getStringFineTuningHistoryShortened("<tr>", "<tr>", "<td>", "<td>");
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        String htmlString = "<!DOCTYPE html><html><body><table border=1>" + inhoud + "</table></body></html>";
        output.write(htmlString);
        output.close();
    }

    public static void writeHtmlFullUserResults(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("write full history htm file: " + outputDir + fileName);
        final File htmlFile = new File(outputDir, fileName);
        StringBuilder htmlStringBuilder = new StringBuilder();
        String report = provider.getHtmlStimuliReport();
        htmlStringBuilder.append("<!DOCTYPE html><html><body>");
        htmlStringBuilder.append(report);
        htmlStringBuilder.append("</body></html>");
        BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
        output.write(htmlStringBuilder.toString());
        output.close();
    }
    
    public static void writeCsvMapAsOneCsv(AdVocAsStimuliProvider provider, String outputDir, String fileName) throws IOException {
        System.out.println("write full history htm file: " + outputDir + fileName);
        final File txtFile = new File(outputDir, fileName);
        Map<String,String> tables = provider.getStimuliReport();
        StringBuilder txtStringBuilder = new StringBuilder();
        for (String rowName : tables.keySet()){
            txtStringBuilder.append(rowName).append(";").append(tables.get(rowName)).append("\n");
        }
        BufferedWriter output = new BufferedWriter(new FileWriter(txtFile));
        output.write(txtStringBuilder.toString());
        output.close();
    }

}
