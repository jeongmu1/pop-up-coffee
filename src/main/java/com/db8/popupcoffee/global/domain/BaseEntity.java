package com.db8.popupcoffee.global.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof BaseEntity that)) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy
            ? hibernateProxy.getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy
            ? hibernateProxy.getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy
            ? hibernateProxy.getHibernateLazyInitializer()
            .getPersistentClass().hashCode() : getClass().hashCode();
    }
}
