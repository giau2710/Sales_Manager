package model;

import repository.UserRepository;
import utils.TimeUtil;



public class CustomerSupport implements ICustomerSupport,Comparable<CustomerSupport> {
    private String username;
    private final String content;
    private final String requestDate;
    private CustomerSupportStatus customerSupportStatus;
    UserRepository userRepository = new UserRepository();



    public CustomerSupport(String username, String content, String requestDate,CustomerSupportStatus customerSupportStatus) {
        this.username = username;
        this.content = content;
        this.requestDate = requestDate;
        this.customerSupportStatus=customerSupportStatus;
    }

    public CustomerSupport(String record) {
        String[] fields = record.split(";");
        username = fields[0];
        content = fields[1];
        requestDate = fields[2];
        customerSupportStatus=CustomerSupportStatus.valueOf(fields[3]);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }



    public String getRequestDate() {
        return requestDate;
    }


    public CustomerSupportStatus getCustomerSupportStatus() {
        return customerSupportStatus;
    }

    public void setCustomerSupportStatus(CustomerSupportStatus customerSupportStatus) {
        this.customerSupportStatus = customerSupportStatus;
    }

    @Override
    public Customer getCustomer() {
        return userRepository.getCustomer(username);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s", getCustomer().getUsername(), content, requestDate,customerSupportStatus);
    }
    @Override
    public int compareTo(CustomerSupport o) {
        return (int) (TimeUtil.periodNow(o.getRequestDate()) - TimeUtil.periodNow(this.getRequestDate()));
    }
}
