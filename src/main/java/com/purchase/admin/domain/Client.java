package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private UUID clientUid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "test")
    private Boolean test;

    @OneToMany(mappedBy = "client")
    private List<SearchHistory> searchHistory;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL)
    private Set<Session> sessions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return new EqualsBuilder()
                .append(clientUid, client.clientUid)
                .append(name, client.name)
                .append(email, client.email)
                .append(phone, client.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clientUid)
                .append(name)
                .append(email)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("supplierUid", clientUid)
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}