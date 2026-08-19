package com.globaldynamicssystems.aurum.accounting.model;

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
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_of_accounts_id")
    private ChartOfAccounts chartOfAccounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Account parent;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature", length = 30)
    private AccountNature nature;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "postable", nullable = false)
    private Boolean postable = true;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "level")
    private Integer level = 1;

    // --- CONSTRUCTORES ---

    public Account() {
    }

    public Account(Long id, String code, String name, AccountType accountType, ChartOfAccounts chartOfAccounts) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.accountType = accountType;
        this.chartOfAccounts = chartOfAccounts;
        this.active = true;
        this.postable = true;
        this.level = 1;
    }

    // --- MÉTODOS DELEGADOS Y SOPORTE ---

    public Long getChartOfAccountsId() {
        return this.chartOfAccounts != null ? this.chartOfAccounts.getId() : null;
    }

    public Boolean isActive() {
        return active != null && active;
    }

    public Boolean getIsActive() {
        return isActive();
    }

    public Boolean isPostable() {
        return postable != null && postable;
    }

    public Boolean getIsPostable() {
        return isPostable();
    }

    // --- GETTERS Y SETTERS ---

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

    public ChartOfAccounts getChartOfAccounts() {
        return chartOfAccounts;
    }

    public void setChartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level != null ? level : 1;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    // --- EQUALS & HASHCODE ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) || (code != null && Objects.equals(code, account.code));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}