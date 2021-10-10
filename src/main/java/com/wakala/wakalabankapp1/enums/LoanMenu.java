package com.wakala.wakalabankapp1.enums;

import lombok.Getter;

@Getter
public enum LoanMenu {
    LOAN_APPLICATION("Loan Application"),
    LOAN_REPAYMENT("Loan Repayment");




    private String value;
    LoanMenu(String value){
        this.value = value;

    }
}
