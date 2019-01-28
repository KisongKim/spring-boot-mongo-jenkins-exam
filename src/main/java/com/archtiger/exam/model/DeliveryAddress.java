package com.archtiger.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "customer")
@EqualsAndHashCode(exclude = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DELIVERY_ADDRESS")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CITY", length = 64, nullable = false)
    private String city;

    @Column(name = "STREET", length = 64, nullable = false)
    private String street;

    @Column(name = "POSTAL_CODE", length = 64, nullable = false)
    private String postalCode;

    @Column(name = "FAVORITE", nullable = false)
    private Boolean favorite;

    @Column(name = "REGISTER_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime registerDateTime;

    public DeliveryAddress(Customer customer, String city, String street, String postalCode, Boolean favorite, LocalDateTime registerDateTime) {
        this.customer = customer;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.favorite = favorite;
        this.registerDateTime = registerDateTime;
    }

}
