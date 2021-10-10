package com.wakala.wakalabankapp1.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Loan {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", columnDefinition = "VARCHAR(40)")
    private String id;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "MENU_STAGE")
    private int menuStage;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MENU_SELECTED")
    private String menuSelected;

    @Column(name = "AMOUNT")
    private String amount;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "MONTHLY_SALARY")
    private String salary;

    @Column(name = "MONTHLY_RENTAL")
    private String monthlyRental;

    @Column(name = "MONTHLY_EXPENSES")
    private String expense;

    @Column(name = "CUSTOMER_MOBILE")
    private String mobileNumber;

    @OneToOne(mappedBy = "loan")
    @JoinColumn(name = "CUSTOMER_ID",referencedColumnName = "ID")
    private Customer customer;



}
