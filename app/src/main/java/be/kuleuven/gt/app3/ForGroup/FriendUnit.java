package be.kuleuven.gt.app3.ForGroup;

public class FriendUnit {

    private String name;
    private String label;
    private String time;

    private int ID;
    private String account;

    public FriendUnit() {
        label ="";
    }

    public void setLable(String lable){
        this.label = lable;
    }

    public String getLabel(){return label;}

    public String getName() {
        return name;
    }

    public void setName(String name){this.name = name;}

    public void setID(int ID){this.ID = ID;}
    public int getID(){return ID;}

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setAccount(String account){this.account = account;}

    public String getAccount(){return account;}



}
