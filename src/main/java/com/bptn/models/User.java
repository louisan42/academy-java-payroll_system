package com.bptn.models;


import jakarta.persistence.*;
import javafx.scene.text.Text;

@Entity
// We are adding quotes since postgres is case-sensitive
// and will treat regular strings as lowercase
@Table(name = "\"Users\"")
public class User implements Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "avatar_url")
    private String avatarUrl;
    private String email;

    public User(){};



    public User (String firstName, String lastName, String username,String email, String passwordHash, int id, String avatarUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPasswordHash () {
        return passwordHash;
    }

    public void setPasswordHash (String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getAvatarUrl () {
        return avatarUrl;
    }

    public void setAvatarUrl (String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    @Override
    public String getFirstName () {
        return this.firstName;
    }

    @Override
    public String getLastName () {
        return this.lastName;
    }

    @Override
    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
}
