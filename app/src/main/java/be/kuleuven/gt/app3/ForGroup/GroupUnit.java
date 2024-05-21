package be.kuleuven.gt.app3.ForGroup;

import java.util.ArrayList;

public class GroupUnit {

    private String groupName;
    private ArrayList<FriendUnit> friends;
    private String date;
    private int ID;
    private int order;

    public GroupUnit() {


    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupname){groupName = groupname;}

    public void setFriends(ArrayList<FriendUnit> friends){this.friends = friends;}
    public ArrayList<FriendUnit> getFriends() {
        return friends;
    }

    public void addFriend(FriendUnit friend){friends.add(friend);}

    public void removeFriend(int id){friends.remove(id);}

    public int getOrder(){return order;}

    public void setOrder(int order){this.order = order;}

    public int getID(){return ID;}

    public void setId(int id) {
        this.ID = id;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

}

