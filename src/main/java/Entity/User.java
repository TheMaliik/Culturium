package Entity;

import java.util.Objects;


/*
@author TheMaliik
 */


public class User {
private int id;
private String fullName,email,mdp,tel,image;

private boolean is_blocked;
private boolean is_approved;


    public static User Current_User;

public User(){}

    public User(int id) {
        this.id = id;
    }

    public User(int id, String fullName, String email, String mdp, String tel, String image, boolean is_blocked, boolean is_approved) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.image = image;
        this.is_blocked = is_blocked;
        this.is_approved = is_approved;
    }

    public User(int id, String fullName, String email, String tel) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
    }

    public User(String fullName, String email, String tel) {
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
    }

    public User(int id, String fullName, String email, String mdp, String tel, String image) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.image = image;
    }
    public User( int id,String fullName, String email , String tel, String image) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
        this.image = image;
    }

    public User(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email) && Objects.equals(mdp, user.mdp) && Objects.equals(tel, user.tel) && Objects.equals(image, user.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, mdp, tel, image);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", tel='" + tel + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
}