package com.purchase.admin.domain;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "application_settings")
public class ApplicationSettings {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_mode")
    private Boolean testMode;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 40)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationSettings settings = (ApplicationSettings) o;

        return new EqualsBuilder()
                .append(testMode, settings.testMode)
                .append(phone, settings.phone)
                .append(email, settings.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(testMode)
                .append(email)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("testMode", testMode)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}
