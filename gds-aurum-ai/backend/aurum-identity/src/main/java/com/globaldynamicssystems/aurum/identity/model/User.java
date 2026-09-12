package com.globaldynamicssystems.aurum.identity.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "gds_user")
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "password_hash", length = 72)
    private String passwordHash;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ExternalIdentity> externalIdentities;

    public User() {
        this.status = UserStatus.PENDING;
        this.emailVerified = false;
        this.externalIdentities = new ArrayList<>();
    }

    public User(String username, String email, String firstName, String lastName, UserStatus status, Boolean emailVerified) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status != null ? status : UserStatus.PENDING;
        this.emailVerified = emailVerified != null ? emailVerified : false;
        this.externalIdentities = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<ExternalIdentity> getExternalIdentities() {
        return externalIdentities;
    }

    public void setExternalIdentities(List<ExternalIdentity> externalIdentities) {
        this.externalIdentities = externalIdentities;
    }
}