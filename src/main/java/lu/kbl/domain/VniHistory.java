package lu.kbl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VniHistory.
 */
@Entity
@Table(name = "vni_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VniHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "jhi_value", precision=10, scale=2)
    private BigDecimal value;

    @ManyToOne
    private Fund fund;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public VniHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public VniHistory value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Fund getFund() {
        return fund;
    }

    public VniHistory fund(Fund fund) {
        this.fund = fund;
        return this;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VniHistory vniHistory = (VniHistory) o;
        if (vniHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vniHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VniHistory{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
