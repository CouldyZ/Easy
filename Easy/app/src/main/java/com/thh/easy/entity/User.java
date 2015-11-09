package com.thh.easy.entity;

import android.app.Application;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by taCi on 2015/10/26.
 *
 * 用户实体
 */
public class User implements Serializable{

    private int id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private String number;    // 学号

    private String depart;

    private String tname;    // 真实姓名

    private String nickname;

    private String gender;

    private int jiecao;

    private String avatarFilePath;

    public User() {
    }

    public User(int id, String username, String password, String icon, String email, String number, String depart, String tname, String nickname, String gender, int jiecao) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.email = email;
        this.number = number;
        this.depart = depart;
        this.tname = tname;
        this.nickname = nickname;
        this.gender = gender;
        this.jiecao = jiecao;
    }

    public User(int id, String username, String password, String icon, String email, String number, String depart, String tname, String nickname, String gender, int jiecao, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.email = email;
        this.number = number;
        this.depart = depart;
        this.tname = tname;
        this.nickname = nickname;
        this.gender = gender;
        this.jiecao = jiecao;
        this.avatarFilePath = avatar;
    }

    /**
     * 将用户对象写入对象流并存入缓存
     * @param context
     */
    public void writeToCache(Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = context.openFileOutput("user", Application.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从缓存中获取对象
     *
     * @param context
     * @return
     */
    public User getCache(Context context) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = context.openFileInput("user");
            ois = new ObjectInputStream(fis);
            return (User) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getJiecao() {
        return jiecao;
    }

    public void setJiecao(int jiecao) {
        this.jiecao = jiecao;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
    }
}
