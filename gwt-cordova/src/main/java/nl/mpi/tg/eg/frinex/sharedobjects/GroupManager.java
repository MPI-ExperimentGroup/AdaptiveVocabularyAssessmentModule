/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * @since Nov 18, 2016 2:53:20 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupManager {

    private final HashMap<String, GroupMessage> allMembersList = new HashMap<>();
    private final HashMap<String, String> allMemberCodes = new HashMap<>();
    private final HashMap<String, String> groupUUIDs = new HashMap<>();
    private final HashMap<String, List<String>> unAllocatedMemberCodes = new HashMap<>();
    private final HashMap<String, HashSet<String>> groupsMembers = new HashMap();
    String lastGroupId = null;

    public boolean isGroupMember(GroupMessage groupMessage) {
        final GroupMessage lastMessage = allMembersList.get(groupMessage.getUserId());
        if (lastMessage != null && lastMessage.getScreenId().equals(groupMessage.getScreenId())) {
            // preserve the group id when the browser window is refreshed without get parameters
            groupMessage.setGroupId(lastMessage.getGroupId());
        }
        if (groupMessage.getGroupId() == null) {
            final List<String> lastGroupMemberCodes = unAllocatedMemberCodes.get(lastGroupId);
            if (lastGroupMemberCodes == null || lastGroupMemberCodes.isEmpty()) {
                lastGroupId = null;
            }
            if (lastGroupId == null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                lastGroupId = "started:" + dateFormat.format(date);
            }
            groupMessage.setGroupId(lastGroupId);
        }
        if (!groupsMembers.containsKey(groupMessage.getGroupId())) {
            groupsMembers.put(groupMessage.getGroupId(), new HashSet<String>());
        }
        return groupsMembers.get(groupMessage.getGroupId()).contains(groupMessage.getUserId());
    }

    public boolean isGroupReady(final String groupId, final String memberId) {
        final List<String> membercodes = unAllocatedMemberCodes.get(groupId);
        if (membercodes == null) {
            return false;
        }
        return membercodes.isEmpty() && groupsMembers.get(groupId).contains(memberId);
    }

    public void addGroupMember(GroupMessage groupMessage) {
        if (allMemberCodes.get(groupMessage.getGroupId()) == null) {
            allMemberCodes.put(groupMessage.getGroupId(), groupMessage.getAllMemberCodes());
            unAllocatedMemberCodes.put(groupMessage.getGroupId(), new ArrayList<>(Arrays.asList(groupMessage.getAllMemberCodes().split(","))));
            // keeping a UUID for each group could help disambiguate when the server is restarted and the same group name reused
            groupUUIDs.put(groupMessage.getGroupId(), UUID.randomUUID().toString());

//        if (storedMessage.getGroupId() == null || storedMessage.getGroupId().isEmpty()) {
//            if (currentGroupId == null) {
//                currentGroupId = UUID.randomUUID().toString();
//                storedMessage.setGroupId(currentGroupId);
//            }
//        }
//        if (unAllocatedMemberCodes.containsKey(currentGroupId) && unAllocatedMemberCodes.get(currentGroupId).isEmpty()) {
////                currentGroupId = UUID.randomUUID().toString();
//        }
        }// else if (allMemberCodes.get(groupMessage.getGroupId()).equals(groupMessage.getAllMemberCodes())) {
        final List<String> availableMemberCodes = unAllocatedMemberCodes.get(groupMessage.getGroupId());
        if (groupMessage.getMemberCode() != null
                && !groupMessage.getMemberCode().isEmpty()
                && availableMemberCodes.contains(groupMessage.getMemberCode())) {
            // if the member code is provided and it is available then allocate it
            availableMemberCodes.remove(groupMessage.getMemberCode());
        } else if (!availableMemberCodes.isEmpty()) {
            groupMessage.setMemberCode(availableMemberCodes.remove(0));
        }
        groupMessage.setUserLabel(groupMessage.getMemberCode() + " : " + groupMessage.getGroupId());
        groupMessage.setGroupUUID(groupUUIDs.get(groupMessage.getGroupId()));
        allMembersList.put(groupMessage.getUserId(), groupMessage);
        groupsMembers.get(groupMessage.getGroupId()).add(groupMessage.getUserId());
        //}
    }

    public GroupMessage getGroupMember(final String memberId) {
        return allMembersList.get(memberId);
    }
}
