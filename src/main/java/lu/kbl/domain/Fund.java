package lu.kbl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fund.
 */
@Entity
@Table(name = "fund")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_range", nullable = false)
    private String range;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private SubCategory subCategory;

    @ManyToOne
    private Currency currency;

    @OneToMany(mappedBy = "fund")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VniHistory> vniHistories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fund_country",
               joinColumns = @JoinColumn(name="funds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="countries_id", referencedColumnName="id"))
    private Set<Country> countries = new HashSet<>();

    @JsonInclude()
    @Transient
    private Long lastVniValue;

    public Long getLastVniValue() {
        return lastVniValue;
    }

    public void setLastVniValue(Long lastVniValue) {
        this.lastVniValue = lastVniValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRange() {
        return range;
    }

    public Fund range(String range) {
        this.range = range;
        return this;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public Fund name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public Fund category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Fund subCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Fund currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<VniHistory> getVniHistories() {
        return vniHistories;
    }

    public Fund vniHistories(Set<VniHistory> vniHistories) {
        this.vniHistories = vniHistories;
        return this;
    }

    public Fund addVniHistory(VniHistory vniHistory) {
        this.vniHistories.add(vniHistory);
        vniHistory.setFund(this);
        return this;
    }

    public Fund removeVniHistory(VniHistory vniHistory) {
        this.vniHistories.remove(vniHistory);
        vniHistory.setFund(null);
        return this;
    }

    public void setVniHistories(Set<VniHistory> vniHistories) {
        this.vniHistories = vniHistories;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Fund countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Fund addCountry(Country country) {
        this.countries.add(country);
        country.getFunds().add(this);
        return this;
    }

    public Fund removeCountry(Country country) {
        this.countries.remove(country);
        country.getFunds().remove(this);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fund fund = (Fund) o;
        if (fund.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fund.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fund{" +
            "id=" + id +
            ", range='" + range + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
