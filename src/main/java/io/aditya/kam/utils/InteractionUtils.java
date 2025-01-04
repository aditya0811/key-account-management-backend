package io.aditya.kam.utils;

import io.aditya.kam.dto.Customer;
import io.aditya.kam.dto.CustomerOrder;
import io.aditya.kam.dto.Interaction;
import io.aditya.kam.enums.LeadStatus;
import io.aditya.kam.service.CustomerOrderService;
import io.aditya.kam.service.CustomerService;
import io.aditya.kam.service.KeyAccountManagerService;
import io.aditya.kam.service.PointOfContactService;
import java.util.Optional;


public class InteractionUtils {

  /**
   *
   * @param interaction interaction having order details, KAM changed, last call scheduled. lead status
   * @param customerService Use to update customer table in case of lead status change, order palced, KAM changed
   * @param customerOrderService Use to update CustomerOrder table if an order is placed
   * @param keyAccountManagerService Use to find common hours for next meeting
   * @param pointOfContactService Use to find common hours for next meeting
   */
  public static void updateCustomerAndOrder(Interaction interaction, CustomerService customerService,
      CustomerOrderService customerOrderService, KeyAccountManagerService keyAccountManagerService,
      PointOfContactService pointOfContactService) {
    Optional<Customer> optionalCustomer = customerService.findById(interaction.getCustomerID());
    if (optionalCustomer.isPresent()) {
      Customer customer = optionalCustomer.get();
      // Lead status changed, lead status will remain PROSPECTIVE till no interaction is scheduled, which is by default
      if (customer.getLeadStatus() == LeadStatus.PROSPECTIVE) {
        customer.setLeadStatus(LeadStatus.TRACKING);
      }

      // Lead status changed
      if (interaction.getIsConverted()){
        customer.setLeadStatus(LeadStatus.CONVERTED);
      }

      // KAM changed
      if (interaction.getIsKeyAccountManagerChanged()){
        customer.setKeyAccountManagerID(interaction.getChangedKeyAccountManagerID());
      }

      //Order placed
      if (interaction.getIsOrderPlaced()){
        customer.setNumberOfOrders(customer.getNumberOfOrders()+1);
        customer.setTotalTransactionValue(customer.getTotalTransactionValue()+interaction.getTransactionValue());
        CustomerOrder customerOrder = CustomerOrder.builder()
            .customerID(interaction.getCustomerID())
            .orderID(interaction.getOrderID())
            .transactionValue(interaction.getTransactionValue())
            .interactionID(interaction.getInteractionID())
            .build();
        customerOrderService.create(customerOrder);
      }

      //last call is updated with current call, after every interaction.
      StringBuilder temp = new StringBuilder();
      for(char ch:customer.getNextCallScheduledTimestamp().toCharArray() ) {
        temp.append(ch);
      }

      customer.setLastCallScheduledTimestamp(temp.toString());
      String nextMeetingTimestamp = ApplicationUtils.getDateWithFrequencyOfCalls(keyAccountManagerService,
          pointOfContactService, customer);
      customer.setNextCallScheduledTimestamp(nextMeetingTimestamp);


      //final save customer
      customerService.update(customer);
    }


  }
}
