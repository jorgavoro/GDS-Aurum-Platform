package com.globaldynamicssystems.aurum.accounting.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gds_account")
public class Account extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private AccountNature nature;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "postable", nullable = false)
    private Boolean postable;

    @Column(name = "level", nullable = false)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Account parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_of_accounts_id", nullable = false)
    private ChartOfAccounts chartOfAccounts;

    public Account() {
    }

    public Account(Long id, String code, String name, String description, AccountType accountType,
                   AccountNature nature, Boolean active, Boolean postable, Integer level,
                   Account parent, ChartOfAccounts chartOfAccounts) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.accountType = accountType;
        this.nature = nature;
        this.active = active;
        this.postable = postable;
        this.level = level;
        this.parent = parent;
        this.chartOfAccounts = chartOfAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountNature getNature() {
        return nature;
    }

    public void setNature(AccountNature nature) {
        this.nature = nature;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPostable() {
        return postable;
    }

    public void setPostable(Boolean postable) {
        this.postable = postable;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public ChartOfAccounts getChartOfAccounts() {
        return chartOfAccounts;
    }

    public void setChartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }
}