package io.aditya.kam.comparator.customer;

import io.aditya.kam.dto.Customer;
import java.util.Comparator;
import org.springframework.stereotype.Service;


@Service
public class CustomerComparatorFactory {

  private static final String FREQUENCY = "frequency";
  private static final String TPV = "tpv";

  /**
   *
   * @param metric metric could be frequency and tpv, default return is tpv
   * @param descending result is true if its descending, otherwise it will be ascending order
   * @return
   */
  public Comparator<Customer> getComparator(String metric, boolean descending) {
    Comparator<Customer> comparator = null;
    if (metric.equals(FREQUENCY) && !descending) {
      comparator = new CustomerOrderFrequencyComparator();
    } else if (metric.equals(FREQUENCY)) {
      comparator = new CustomerOrderFrequencyComparator().reversed();
    } else if (metric.equals(TPV) && !descending) {
      comparator = new CustomerTPVComparator();
    } else {
      comparator = new CustomerTPVComparator().reversed();
    }
    return comparator;
  }
}
