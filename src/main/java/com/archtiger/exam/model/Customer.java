package com.archtiger.exam.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "deliveryAddresses")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMAIL"})})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "EMAIL", length = 256, nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    @Column(name = "FAMILY_NAME", length = 64, nullable = false)
    private String familyName;

    @Column(name = "GIVEN_NAME", length = 64, nullable = false)
    private String givenName;

    @Column(name = "REGISTER_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime registerDateTime;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<DeliveryAddress> deliveryAddresses;

    public Customer(String email, String password, String familyName, String givenName, LocalDateTime registerDateTime) {
        this.email = email;
        this.password = password;
        this.familyName = familyName;
        this.givenName = givenName;
        this.registerDateTime = registerDateTime;
    }

    public Set<DeliveryAddress> deliveryAddresses() {
        if (deliveryAddresses == null) {
            deliveryAddresses = new LinkedHashSet<>();
        }
        return deliveryAddresses;
    }

}
