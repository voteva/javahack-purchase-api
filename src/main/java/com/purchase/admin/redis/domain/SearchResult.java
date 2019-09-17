package com.purchase.admin.redis.domain;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@RedisHash("SearchResult")
public class SearchResult {

    @Id
    private UUID id;
    private String siteName;
    private String url;
    private String email;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResult result = (SearchResult) o;

        return new EqualsBuilder()
                .append(siteName, result.siteName)
                .append(url, result.url)
                .append(email, result.email)
                .append(phone, result.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(siteName)
                .append(url)
                .append(email)
                .append(phone)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("siteName", siteName)
                .add("url", url)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}
