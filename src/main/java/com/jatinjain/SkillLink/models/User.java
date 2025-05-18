package com.jatinjain.SkillLink.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String realName;
    private String email;
    private String password;
    private int spin;
    private String bio;
    private ArrayList<String> followersId;
    private ArrayList<String> followingId;

    public User() {}

    public User(String id, String userName, String realName, String email, String password, int spin, String bio) {
        this.id = id;
        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.password = password;
        this.spin = spin;
        this.bio = bio;
        this.followersId = new ArrayList<>();
        this.followingId = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSpin() {
        return spin;
    }

    public void setSpin(int spin) {
        this.spin = spin;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<String> getFollowersId() {
        return followersId;
    }

    public ArrayList<String> getFollowingId() {
        return followingId;
    }

    public void addFollower(String id) {
        followersId.add(id);
    }

    public void addFollowing(String id) {
        followingId.add(id);
    }

    public void removeFollowing(String id) {
        for(int i=0; i<followingId.size(); i++) {
            if(followingId.get(i).equals(id)) {
                followingId.remove(i);
                break;
            }
        }
    }

    public int numberOfFollowers() {
        return followersId.size();
    }

    public int numberOfFollowings() {
        return followingId.size();
    }
}
