package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.Gender;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String displayName;
    private Gender gender;
    private String email;
    private String biography;
    private String avatarId;
    @ElementCollection
    private List<String> permissions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.displayName = "";
        this.gender = Gender.UNSPECIFIED;
        this.email = "";
        this.biography = "";
        this.avatarId = "";
        this.permissions = List.of();
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getBiography() {
        return biography;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(String permission) {
        return getPermissions().contains(permission);
    }
}
