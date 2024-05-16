package be.kuleuven.gt.app3.ForGroup;

import java.util.ArrayList;

public class GroupUnit {

    private String groupName;
    private ArrayList<FriendUnit> friends;
    private boolean isExpanded;

    public GroupUnit(String groupName, ArrayList<FriendUnit> friends) {
        this.groupName = groupName;
        this.friends = friends;
        this.isExpanded = false;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<FriendUnit> getFriends() {
        return friends;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


}
