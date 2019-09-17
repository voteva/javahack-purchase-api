package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_email")
public class PurchaseEmail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_from", length = 60)
    private String emailFrom;

    @Column(name = "email_to", length = 60)
    private String emailTo;

    @Column(name = "subject", length = 512)
    private String subject;

    @Column(name = "content", length = 8096)
    private String content;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "supplier_uid", insertable = false, updatable = false)
    private UUID supplierUid;

    @Setter(AccessLevel.NONE)
    @Column(name = "client_id", insertable = false, updatable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseEmail email = (PurchaseEmail) o;

        return new EqualsBuilder()
                .append(emailFrom, email.emailFrom)
                .append(emailTo, email.emailTo)
                .append(subject, email.subject)
                .append(content, email.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(emailFrom)
                .append(emailTo)
                .append(subject)
                .append(content)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("emailFrom", emailFrom)
                .add("emailTo", emailTo)
                .add("subject", subject)
                .add("content", content)
                .toString();
    }
}
