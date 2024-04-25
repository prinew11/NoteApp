package be.kuleuven.gt.app3.ForNote;

import java.io.Serializable;

public class NoteUnit implements Serializable{
    private int id;
    private int flag;//0--new,1--old
    private String title;
    private String content;
    private int groupId;//分类ID
    private String groupName;//分类名称
    private int type;//笔记类型，1纯文本，2Html，3Markdown
    private String bgColor;//背景颜色，存储颜色代码
    private int isEncrypt ;//是否加密，0未加密，1加密
    private String createTime;
    private String updateTime;

    public NoteUnit(){
        flag = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(int isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getFlag(){return flag;}

    public void setFlag(int flag){this.flag = flag;}






}
