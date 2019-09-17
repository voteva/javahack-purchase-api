package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import com.purchase.admin.enumeration.SupplierStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "supplier")
public class Supplier {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private UUID supplierUid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "history_id", nullable = false, insertable = false, updatable = false)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private SearchHistory searchHistory;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SupplierStatus status;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private List<SupplierAnswer> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        return new EqualsBuilder()
                .append(name, supplier.name)
                .append(email, supplier.email)
                .append(phone, supplier.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(email)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}
