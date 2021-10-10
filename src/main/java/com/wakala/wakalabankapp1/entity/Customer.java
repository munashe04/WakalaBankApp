package com.wakala.wakalabankapp1.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "CUSTOMER")

public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(40)")
    private String id;

    @CreationTimestamp
    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "REF_MOBILE_NUMBER")
    private String refMobileNumber;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "DOB")
    private String dateOfBirth;

    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MENU_STAGE")
    private int stage;

    @Column(name = "MENU_SELECTED")
    private String menuSelected;

    @Column(name = "ID_IMAGE_URL")
    private String idImageUrl;

    @JoinColumn(name = "LOAN_ID", referencedColumnName = "ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Loan loan;
}
