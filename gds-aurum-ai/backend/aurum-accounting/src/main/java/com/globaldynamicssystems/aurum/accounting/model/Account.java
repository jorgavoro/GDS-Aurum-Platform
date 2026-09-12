package com.globaldynamicssystems.aurum.accounting.model;

<<<<<<< HEAD
import com.globaldynamicssystems.aurum.model.AuditableEntity;
=======
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
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
<<<<<<< HEAD

@Entity
@Table(name = "gds_account")
public class Account extends AuditableEntity {
=======
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
=======
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

<<<<<<< HEAD
    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private AccountNature nature;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "postable", nullable = false)
    private Boolean postable;

    @Column(name = "level", nullable = false)
    private Integer level;
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_of_accounts_id")
    private ChartOfAccounts chartOfAccounts;
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Account parent;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_of_accounts_id", nullable = false)
    private ChartOfAccounts chartOfAccounts;
=======
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
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e

    public Account() {
    }

<<<<<<< HEAD
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

=======
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

>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
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

<<<<<<< HEAD
=======
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

>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
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

<<<<<<< HEAD
    public Integer getLevel() {
        return level;
=======
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level != null ? level : 1;
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> c55744ccda4dad50a465c4a088ad5c74ef64f07e
    }
}