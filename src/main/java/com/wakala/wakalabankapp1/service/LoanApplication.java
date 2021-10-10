package com.wakala.wakalabankapp1.service;

import com.wakala.wakalabankapp1.entity.Customer;
import com.wakala.wakalabankapp1.entity.Loan;
import com.wakala.wakalabankapp1.enums.LoanMenu;
import com.wakala.wakalabankapp1.repository.CustomerRepository;
import com.wakala.wakalabankapp1.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplication {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CustomerRepository customerRepository;

    public String application( Loan loan,Customer customer,String body,String imageUrl){
        if (loan.getMenuStage()== 1 ){
            if (body.equalsIgnoreCase("1")){
                loan.setMenuSelected(LoanMenu.LOAN_APPLICATION.getValue());
                loan.setMenuStage(2);
                loanRepository.save(loan);
                return "How much do you want to borrow?";
            }
        }
        else if (loan.getMenuStage() == 2 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())){
            loan.setAmount(body);
            loan.setMenuStage(3);
            loan.setMenuSelected(loan.getMenuSelected());
            loanRepository.save(loan);
            return "What do you want the funds for ? :\n" +
                    "1. Home extension\n" +
                    "2. Medical bills \n" +
                    "3.School fees \n" +
                    "4. Other";
        }
        else if (loan.getMenuStage() ==3 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())){
            //Setting the purpose of the selected option
            switch (body){
                case "1" :
                    loan.setPurpose("Home extension");
                    loan.setAmount(loan.getAmount());
                    loan.setMenuStage(4);
                    loan.setMenuSelected(loan.getMenuSelected());
                    loanRepository.save(loan);
                    return "What is your monthly salary? ";
                   // break;
                case "2" :
                    loan.setPurpose("Medical bills");
                    loan.setAmount(loan.getAmount());
                    loan.setMenuStage(4);
                    loan.setMenuSelected(loan.getMenuSelected());
                    loanRepository.save(loan);
                    return "What is your monthly salary? ";
                   // break;
                case "3" :
                    loan.setPurpose("School fees");
                    loan.setAmount(loan.getAmount());
                    loan.setMenuStage(4);
                    loan.setMenuSelected(loan.getMenuSelected());
                    loanRepository.save(loan);
                    return "What is your monthly salary? ";
                    //break;
                case "4" :
                    loan.setPurpose("Other");
                    loan.setAmount(loan.getAmount());
                    loan.setMenuStage(4);
                    loan.setMenuSelected(loan.getMenuSelected());
                    loanRepository.save(loan);
                    return "Please Specify the purpose";
                   // break;
                default:
                    //Returning the main menu in case the user enters an invalid option
                    return "1. Home extension\n" +
                            "2. Medical bills \n" +
                            "3.School fees \n" +
                            "4. Other";
            }

        }
        //setting the specific purpose of the option "Other"
        else if (loan.getMenuStage() == 4 && loan.getPurpose().equalsIgnoreCase("Other")){
            loan.setPurpose(body);
            loan.setAmount(loan.getAmount());
            loan.setMenuStage(4);
            loan.setMenuSelected(loan.getMenuSelected());
            loanRepository.save(loan);
            return "What is your monthly salary? ";

        }
        //Making sure the "Other" purpose has been specified first
        else if (loan.getMenuStage() == 4 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())
        && !loan.getPurpose().equalsIgnoreCase("Other")){
            loan.setSalary(body);
            loan.setPurpose(loan.getPurpose());
            loan.setAmount(loan.getAmount());
            loan.setMenuStage(5);
            loan.setMenuSelected(loan.getMenuSelected());
            loanRepository.save(loan);
            return "What is your monthly rental or mortgage?";
        }
        else if (loan.getMenuStage() == 5 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())){
            loan.setMonthlyRental(body);
            loan.setSalary(loan.getSalary());
            loan.setPurpose(loan.getPurpose());
            loan.setAmount(loan.getAmount());
            loan.setMenuStage(6);
            loan.setMenuSelected(loan.getMenuSelected());
            loanRepository.save(loan);
            return "What is your monthly household expenses?";
        }
        else if (loan.getMenuStage() == 6 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())){
            loan.setExpense(body);
            loan.setSalary(loan.getSalary());
            loan.setPurpose(loan.getPurpose());
            loan.setAmount(loan.getAmount());
            loan.setMenuStage(7);
            loan.setMenuSelected(loan.getMenuSelected());
            loanRepository.save(loan);
            return "Any changes to the details provided above should be advised to Wakala Bank.\n" +
                    "1. Yes \n " +
                    "2. No  \n ";

        }
        else if (loan.getMenuStage() == 7 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())
        && body.equalsIgnoreCase("2")) {

            loan.setExpense(loan.getExpense());
            loan.setSalary(loan.getSalary());
            loan.setPurpose(loan.getPurpose());
            loan.setAmount(loan.getAmount());
            loan.setMenuStage(8);
            loan.setMenuSelected(loan.getMenuSelected());
            loan.setStatus("Submitted");
            customer.setStage(0);
            customerRepository.save(customer);
            loanRepository.save(loan);
            return "Your application is being processed; we will come back with a response within 48 hours.";
        }
        else if (loan.getMenuStage() == 7 && loan.getMenuSelected().equalsIgnoreCase(LoanMenu.LOAN_APPLICATION.getValue())
                && body.equalsIgnoreCase("1")){

            loanRepository.delete(loan);
           customer.setMenuSelected(LoanMenu.LOAN_APPLICATION.getValue());
           customer.setStage(0);
           customerRepository.save(customer);

            return "Your application has been cancelled.";
        }


    return "contact admin";
    }
}
