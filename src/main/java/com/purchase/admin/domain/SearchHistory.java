package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "search_history")
@EntityListeners(AuditingEntityListener.class)
public class SearchHistory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private UUID queryUid;

    @Setter(AccessLevel.NONE)
    @Column(name = "client_id", nullable = false, insertable = false, updatable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "query_string", nullable = false, length = 512)
    private String queryString;

    @Column(name = "message", nullable = false, length = 2048)
    private String message;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "searchHistory")
    private List<Supplier> suppliers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchHistory history = (SearchHistory) o;

        return new EqualsBuilder()
                .append(clientId, history.clientId)
                .append(queryString, history.queryString)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clientId)
                .append(queryString)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("clientId", clientId)
                .add("queryString", queryString)
                .toString();
    }
}
