package net.treebear.kwifimanager.bean;

public class FamilyMemberBean {

    private long id;

    private String avatar;

    private String name;

    private String mobile;

    private boolean isAdmin;

    public FamilyMemberBean(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "FamilyMemberBean{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
