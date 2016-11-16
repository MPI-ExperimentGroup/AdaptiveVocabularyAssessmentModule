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
package nl.mpi.tg.eg.experiment.client.service;

import java.util.HashMap;
import nl.mpi.tg.eg.experiment.client.listener.TimedStimulusListener;
import nl.mpi.tg.eg.experiment.client.sharedobjects.GroupMessageMatch;

/**
 * @since Nov 8, 2016 1:47:57 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupParticipantService {

    private final HashMap<String, TimedStimulusListener> selfActivityListeners = new HashMap<>();
    private final HashMap<String, TimedStimulusListener> othersActivityListeners = new HashMap<>();
    private final String allMemberCodes;
    private final String groupCommunicationChannels;
    private final TimedStimulusListener connectedListener;
    private final TimedStimulusListener messageListener;
    private boolean isConnected = false;

    private final String userId;
    private final String screenId;
    private String userLabel = null;
//    private final String allMemberCodes = null;
    private String memberCode = null;
    private String groupId = null;
    private String stimulusId = null;
    private Integer stimulusIndex = null;
    private String messageString = null;
    private Boolean groupReady = false;
//    private Boolean userIdMatches = false;

    public GroupParticipantService(final String userId, String screenId, String groupMembers, String groupCommunicationChannels, TimedStimulusListener connectedListener, TimedStimulusListener messageListener) {
        this.userId = userId;
        this.allMemberCodes = groupMembers;
        this.groupCommunicationChannels = groupCommunicationChannels;
        this.connectedListener = connectedListener;
        this.messageListener = messageListener;
        this.screenId = screenId;
    }

    public void addGroupActivity(final String groupRole, final GroupMessageMatch groupMessageMatch, final TimedStimulusListener activityListener) {
        switch (groupMessageMatch) {
            case self:
                selfActivityListeners.put(groupRole, activityListener);
                break;
            case other:
                othersActivityListeners.put(groupRole, activityListener);
                break;
            case all:
                selfActivityListeners.put(groupRole, activityListener);
                othersActivityListeners.put(groupRole, activityListener);
                break;
        }
    }

    protected void handleGroupMessage(String userId, String screenId, String userLabel, String groupId, String allMemberCodes, String memberCode, String stimulusId, String stimulusIndex, String messageString, Boolean groupReady) {
        final boolean userIdMatches = this.userId.equals(userId);
        final boolean screenIdMatches = this.screenId.equals(screenId);
        if (userIdMatches && screenIdMatches) {
            this.userLabel = userLabel;
            this.memberCode = memberCode;
            this.groupId = groupId;
        }
        messageListener.postLoadTimerFired();
        if (this.groupId.equals(groupId)) {
//            this.allMemberCodes = allMemberCodes;
            this.stimulusId = stimulusId;
            this.stimulusIndex = Integer.parseInt(stimulusIndex);
            this.messageString = messageString;
            this.groupReady = groupReady;
            if (groupReady) {
                for (String groupRole : ((userIdMatches) ? selfActivityListeners : othersActivityListeners).keySet()) {
                    final String[] splitRole = groupRole.split(":");
                    int roleIndex = this.stimulusIndex % splitRole.length;
                    if (splitRole[roleIndex].contains(memberCode)) {
                        ((userIdMatches) ? selfActivityListeners : othersActivityListeners).get(groupRole).postLoadTimerFired();
                    }
                }
            }
        }
    }

    protected void setConnected(Boolean isConnected) {
        this.isConnected = isConnected;
        connectedListener.postLoadTimerFired();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public String getAllMemberCodes() {
        return allMemberCodes;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getStimulusId() {
        return stimulusId;
    }

    public Integer getStimulusIndex() {
        return stimulusIndex;
    }

    public String getMessageString() {
        return messageString;
    }

    public boolean isGroupReady() {
        return groupReady;
    }

    public native void joinGroupNetwork(String groupServerUrl) /*-{
        var groupParticipantService = this;
//        console.log("joinGroupNetwork: " + groupServerUrl + " : " + groupName);
        
        var socket = new $wnd.SockJS(groupServerUrl + 'gs-guide-websocket');
        stompClient = $wnd.Stomp.over(socket);
        stompClient.connect(
            {
//            withCredentials: false,
//            noCredentials : true
            },
            function (frame) {
            groupParticipantService.@nl.mpi.tg.eg.experiment.client.service.GroupParticipantService::setConnected(Ljava/lang/Boolean;)(@java.lang.Boolean::TRUE);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/shared/group', function (groupMessage) {
                var contentData = JSON.parse(groupMessage.body);
                console.log('contentData: ' + contentData);
                groupParticipantService.@nl.mpi.tg.eg.experiment.client.service.GroupParticipantService::handleGroupMessage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)(contentData.userId, contentData.screenId,contentData.userLabel, contentData.groupId, contentData.allMemberCodes, contentData.memberCode, contentData.stimulusId, Number(contentData.stimulusIndex), contentData.messageString, (contentData.groupReady)?@java.lang.Boolean::TRUE : @java.lang.Boolean::FALSE);
            });
        });
     }-*/;

    public void messageGroup(String stimulusId, String stimulusIndex, String messageString) {
        messageGroup(userId, screenId, allMemberCodes, stimulusId, stimulusIndex, messageString);
    }

    private native void messageGroup(String userId, String screenId, String allMemberCodes, String stimulusId, String stimulusIndex, String messageString) /*-{
    var groupParticipantService = this;
    stompClient.send("/app/group", {}, JSON.stringify({
        'userId': userId,
        'screenId': screenId,
        'userLabel': null,
        'allMemberCodes': allMemberCodes,
        'memberCode': null,
        'stimulusId': stimulusId,
        'stimulusIndex': stimulusIndex,
        'messageString': messageString,
        'groupReady': null
    }));
    }-*/;
}
