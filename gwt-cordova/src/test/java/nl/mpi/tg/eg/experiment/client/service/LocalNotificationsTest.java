/*
 * Copyright (C) 2019 Max Planck Institute for Psycholinguistics, Nijmegen
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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Aug 13, 2019 4:00:24 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class LocalNotificationsTest {

    public LocalNotificationsTest() {
    }

    /**
     * Test of findNotificationRepetitions method, of class LocalNotifications.
     */
    @Test
    public void testFindNotificationRepetitions() {
        System.out.println("findNotificationRepetitions");
        int hourFromInt = 10;
        int minuteFromInt = 30;
        int hourUntilInt = 22;
        int minuteUntilInt = 30;
        int repetitionCount = 24;
        LocalNotifications instance = new LocalNotificationsImpl();
        int[][] result = instance.findNotificationRepetitions(hourFromInt, minuteFromInt, hourUntilInt, minuteUntilInt, repetitionCount);
        assertEquals(repetitionCount, result.length);
//        for (int[] values : result) {
//            System.out.println(values[0] + ":" + values[1]);
//        }
        int expectedHour = hourFromInt;
        for (int repetitionIndex = 0; repetitionIndex < 10; repetitionIndex++) {
            System.out.println(result[repetitionIndex][0] + ":" + result[repetitionIndex][1]);
            if (result[repetitionIndex][1] < 31) {
                assertTrue(result[repetitionIndex][1] >= 2);
                assertTrue(result[repetitionIndex][1] <= 28);
            } else {
                assertTrue(result[repetitionIndex][1] >= 32);
                assertTrue(result[repetitionIndex][1] <= 58);
            }
            System.out.println("expectedHour: " + expectedHour);
            assertEquals(expectedHour, result[repetitionIndex][0]);
            if (repetitionIndex % 2 == 0) {
                expectedHour++;
            }
            if (result[repetitionIndex][0] == hourFromInt) {
                assertTrue(result[repetitionIndex][1] >= minuteFromInt);
            } else if (result[repetitionIndex][0] == hourUntilInt) {
                assertTrue(result[repetitionIndex][1] <= minuteUntilInt);
            }
        }
    }

    public class LocalNotificationsImpl extends LocalNotifications {

        @Override
        public void setNotificationSucceded() {
        }

        @Override
        public void setNotificationFailed() {
        }

        @Override
        public void notificationLog(String logString) {
        }
    }
}