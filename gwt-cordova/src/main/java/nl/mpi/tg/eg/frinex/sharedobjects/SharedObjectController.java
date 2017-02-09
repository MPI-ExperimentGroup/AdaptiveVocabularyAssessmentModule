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
package nl.mpi.tg.eg.frinex.sharedobjects;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @since Oct 26, 2016 2:03:15 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Controller
public class SharedObjectController {

    private final static GroupManager GROUP_MANAGER = new GroupManager();
//    private String currentGroupId = null;

    @MessageMapping("/shared")
    @SendTo("/shared/animation")
    public SharedData getSharedData(SharedData sharedData) throws Exception {
        return sharedData;
    }

    @MessageMapping("/group")
    @SendTo("/shared/group")
    public GroupMessage getGroupData(GroupMessage groupMessage) throws Exception {
        return updateGroupData(groupMessage);
    }

    private synchronized GroupMessage updateGroupData(GroupMessage incomingMessage) {
        final GroupMessage storedMessage;
        if (incomingMessage == null) {
            System.out.println("incomingMessage == null");
            return null;
        }
        System.out.println("incomingMessage: ");
        System.out.println(incomingMessage.getAllMemberCodes());
        System.out.println(incomingMessage.getGroupCommunicationChannels());
        System.out.println(incomingMessage.getGroupId());
        System.out.println(incomingMessage.getGroupUUID());
        System.out.println(incomingMessage.getMemberCode());
        System.out.println(incomingMessage.getRequestedPhase());
        System.out.println(incomingMessage.getExpectedRespondents());
        System.out.println(incomingMessage.getUserId());
        if (GROUP_MANAGER.isGroupMember(incomingMessage)) {
            storedMessage = GROUP_MANAGER.getGroupMember(incomingMessage.getUserId());

            System.out.println("storedMessage: ");
            System.out.println(storedMessage.getAllMemberCodes());
            System.out.println(storedMessage.getGroupCommunicationChannels());
            System.out.println(storedMessage.getGroupId());
            System.out.println(storedMessage.getGroupUUID());
            System.out.println(storedMessage.getMemberCode());
            System.out.println(storedMessage.getRequestedPhase());
            System.out.println(storedMessage.getExpectedRespondents());
            System.out.println(storedMessage.getUserId());

            // if the message is a reconnect request then send the last message for that chanel
            final GroupMessage latestGroupMessage = GROUP_MANAGER.updateChannelMessageIfOutOfDate(incomingMessage, storedMessage);

            storedMessage.setStimulusId(latestGroupMessage.getStimulusId());
            storedMessage.setScreenId(latestGroupMessage.getScreenId());
            storedMessage.setStimulusIndex(latestGroupMessage.getStimulusIndex());
            storedMessage.setRequestedPhase(latestGroupMessage.getRequestedPhase());
            storedMessage.setExpectedRespondents(latestGroupMessage.getExpectedRespondents());
            storedMessage.setMessageString(latestGroupMessage.getMessageString());
            storedMessage.setAllMemberCodes(latestGroupMessage.getAllMemberCodes());
//            storedMessage.setGroupUUID(latestGroupMessage.getGroupUUID());
            storedMessage.setResponseStimulusId(latestGroupMessage.getResponseStimulusId());
            storedMessage.setResponseStimulusOptions(latestGroupMessage.getResponseStimulusOptions());
            storedMessage.setGroupId(latestGroupMessage.getGroupId());
//            storedMessage.setRequestedPhase(latestGroupMessage.getRequestedPhase());
        } else {
            GROUP_MANAGER.addGroupMember(incomingMessage);
            storedMessage = incomingMessage;
        }
        GROUP_MANAGER.updateResponderListForMessagePhase(storedMessage);
        GROUP_MANAGER.setUsersLastMessage(storedMessage);
        storedMessage.setGroupReady(GROUP_MANAGER.isGroupReady(storedMessage));
        return storedMessage;
    }
}
