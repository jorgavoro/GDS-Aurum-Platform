package com.globaldynamicssystems.aurum.identity.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(
    name = "gds_external_identity",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "subject"})
    }
)
public class ExternalIdentity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String subject;

    @Column
    private String email;

    @Column(nullable = false)
    private Boolean active;

    public ExternalIdentity() {
        this.active = true;
    }

    public ExternalIdentity(Long id, User user, String provider, String subject, String email, Boolean active) {
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.subject = subject;
        this.email = email;
        this.active = active != null ? active : true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}