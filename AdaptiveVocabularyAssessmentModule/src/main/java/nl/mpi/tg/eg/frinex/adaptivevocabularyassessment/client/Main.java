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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client;

import utils.VocabularyFromFiles;

/**
 * @since Oct 20, 2017 11:38:57 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        final String WORD_FILE_LOCATION = "2.selection_words_nonwords_w.csv";

        final String NONWORD_FILE_LOCATION = "2.selection_words_nonwords.csv";

        VocabularyFromFiles.initialiseVocabulary(WORD_FILE_LOCATION, NONWORD_FILE_LOCATION);
        
    }

}
