package com.wakala.wakalabankapp1.service;

import com.wakala.wakalabankapp1.entity.Customer;
import com.wakala.wakalabankapp1.entity.Loan;
import com.wakala.wakalabankapp1.entity.MenuEnum;
import com.wakala.wakalabankapp1.repository.CustomerRepository;
import com.wakala.wakalabankapp1.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageProcessorService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CustomerRegistrationService customerRegistrationService;
    @Autowired
    LoanApplication loanApplication;


    public String processMessage(MultiValueMap<String, String> paramMap) {
        String formattedBody = null;

        //always check we need the whatsapp ID here
        if (paramMap != null && paramMap.get("WaId") != null) {
            String mobileNumber = paramMap.get("WaId").get(0);
            String formattedMobileNumber = mobileNumber.replace('[', ' ').replace(']', ' ').trim();
            if (paramMap != null && paramMap.get("Body") != null) {
                String body = paramMap.get("Body").get(0);
                formattedBody = body.replace('[', ' ').replace(']', ' ').trim();
            }
            Optional<Customer> optionalMainProfile = customerRepository.findByMobileNumber(formattedMobileNumber);

            //we have this profile set up before , let decide processing based on menu selected.
            if (optionalMainProfile.isPresent()) {
                //go to registration service
                if (optionalMainProfile.get().getMenuSelected().equalsIgnoreCase(MenuEnum.REGISTRATION.getValue())) {
                    String imageUrl = null;
                    if (paramMap.get("MediaUrl0") != null) {
                        imageUrl = paramMap.get("MediaUrl0").get(0);
                    }
                    return customerRegistrationService.register(formattedBody, optionalMainProfile.get(), imageUrl);
                } //go to registration service
                else if (optionalMainProfile.get().getMenuSelected().equalsIgnoreCase(MenuEnum.LOAN_APPLICATION.getValue())) {
                    // Item item = new Item();
                    String imageUrl = null;
                    if (paramMap.get("MediaUrl0") != null) {
                        imageUrl = paramMap.get("MediaUrl0").get(0);
                    }

                    Optional<Loan> pendingLoan = loanRepository.findByMobileNumberAndStatus(optionalMainProfile.get()
                            .getMobileNumber(), "Pending");

                    if (!pendingLoan.isPresent()) {
                        if (optionalMainProfile.get().getStage() == 0) {
                            Loan loan = new Loan();
                            loan.setMobileNumber(optionalMainProfile.get().getMobileNumber());
                            loan.setMenuStage(1);
                            loan.setStatus("Pending");
                            loan.setCustomerName(optionalMainProfile.get().getFullName());
                            optionalMainProfile.get().setStage(1);
                            customerRepository.save(optionalMainProfile.get());
                            loanRepository.save(loan);
                            //order main menu
                            return "Hi "+optionalMainProfile.get().getFullName() +
                                    ", What do you want to do today? \n\n" +
                                    "1. Loan application\n" +
                                    "2. Loan repayment\n" +
                                    "3. Make a svaings deposit\n" +
                                    "4. Check my deposit balance\n" +
                                    "5. Check my loan balance ";
                        }

                    }
                    else {

                        return loanApplication.application(pendingLoan.get(), optionalMainProfile.get(),
                                formattedBody, imageUrl);

                    }


                }

                return "";

            }
            else {
                //main menu
                Customer customer = new Customer();
                customer.setMobileNumber(formattedMobileNumber);
                customer.setDateCreated(LocalDateTime.now());
                customer.setStage(0);
                customer.setMenuSelected(MenuEnum.REGISTRATION.getValue());
               // loanRepository.save(loan);
                customerRepository.save(customer);
                return "Hi, Welcome to Wakala Bank\n \t " +
                        "1.Enter pin \n \t " +
                        "2.Register to open account";
            }
        }

        return " contact admin";

        }

    }

