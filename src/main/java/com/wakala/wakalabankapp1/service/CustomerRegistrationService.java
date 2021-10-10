package com.wakala.wakalabankapp1.service;

import com.wakala.wakalabankapp1.entity.Customer;
import com.wakala.wakalabankapp1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerRegistrationService {
    @Autowired
    private CustomerRepository customerRepository;

    public String register(String body, Customer customer, String imageUrl) {

        if (customer.getStage() == 0) {

            if (body.equalsIgnoreCase("2")) {
                //request full name
                customer.setDateCreated(LocalDateTime.now());
                customer.setStage(1);
                customerRepository.save(customer);

                return "What is your full name ? (first name, surname)";

            } else {
                return "Enter 1: to enter Pin \n 2 : Register ";
            }
        }
          else if (customer.getStage() == 1){
                customer.setFullName(body);
                customer.setStage(2);
                customer.setDateCreated(customer.getDateCreated());
                customerRepository.save(customer);

                return "What is your phone number?";
            }
          else if (customer.getStage() == 2){
              customer.setRefMobileNumber(body);
              customer.setStage(3);
              customer.setFullName(customer.getFullName());
              customer.setDateCreated(customer.getDateCreated());
              customerRepository.save(customer);

              return "What is your date or birth?";
        }
          else if(customer.getStage() == 3){
            customer.setStage(4);
            customer.setDateOfBirth(body);
            customer.setMobileNumber(customer.getMobileNumber());
            customer.setFullName(customer.getFullName());
            customer.setDateCreated(customer.getDateCreated());
            customerRepository.save(customer);

            return "What is your Nationality?";
        }
          else if (customer.getStage() == 4){
            customer.setStage(5);
            customer.setNationality(body);
            customer.setDateOfBirth(customer.getDateOfBirth());
            customer.setMobileNumber(customer.getMobileNumber());
            customer.setFullName(customer.getFullName());
            customer.setDateCreated(customer.getDateCreated());
            customerRepository.save(customer);

            return "Send Picture of your ID ";
        }
          else if (customer.getStage() == 5){
              if (imageUrl == null){
                  return "Send Picture of your ID ";
              }
            customer.setStage(6);
            customer.setIdImageUrl(imageUrl);
            customer.setNationality(customer.getNationality());
            customer.setDateOfBirth(customer.getDateOfBirth());
            customer.setMobileNumber(customer.getMobileNumber());
            customer.setFullName(customer.getFullName());
            customer.setDateCreated(customer.getDateCreated());
            customerRepository.save(customer);

            return "I the undersigned hereby apply to be a member of Wakala Bank and agree to abide by its terms " +
                    "and conditions.\n  \t " +
                    "1. Yes\n  \t " +
                    "2. No";
        }
          else if (customer.getStage()==6 && body.equalsIgnoreCase("1")){
              customer.setStatus("Submitted");
              customer.setStage(7);
              customer.setIdImageUrl(customer.getIdImageUrl());
              customer.setNationality(customer.getNationality());
              customer.setDateOfBirth(customer.getDateOfBirth());
              customer.setMobileNumber(customer.getMobileNumber());
              customer.setFullName(customer.getFullName());
              customer.setDateCreated(customer.getDateCreated());
              customerRepository.save(customer);
              return "Thank you for submitting the information,Your registration is under review";
          }
          else if (customer.getStage() == 6 && body.equalsIgnoreCase("2")){
              customer.setStage(0);
              customerRepository.save(customer);
              return "Application cancelled";
        }


        return "Contact admin";
    }
}
