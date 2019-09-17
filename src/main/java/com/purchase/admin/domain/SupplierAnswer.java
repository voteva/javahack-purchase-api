package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import com.purchase.admin.enumeration.ContactType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "supplier_answer")
@EntityListeners(AuditingEntityListener.class)
public class SupplierAnswer {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private UUID answerUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContactType type;

    @Column(name = "message", nullable = false, length = 5096)
    private String message;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierAnswer answer = (SupplierAnswer) o;

        return new EqualsBuilder()
                .append(type, answer.type)
                .append(message, answer.message)
                .append(createdDate, answer.createdDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(message)
                .append(createdDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("type", type)
                .add("message", message)
                .add("createdDate", createdDate)
                .toString();
    }
}
