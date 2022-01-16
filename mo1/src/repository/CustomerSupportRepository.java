package repository;

import model.CustomerSupport;
import model.CustomerSupportStatus;
import utils.ReadAndWriteFile;

import java.util.ArrayList;

public class CustomerSupportRepository {
    public final static String filePathCustomerSupport = "src/data/customer_support.txt";
    public final static String filePathSupportRep = "src/data/support_rep.txt";

    public void addSupport(CustomerSupport customerSupport, String path) {
        ArrayList<CustomerSupport> list = new ArrayList<>();
        list.add(customerSupport);
        ReadAndWriteFile.write(path, list);
    }

    public ArrayList<CustomerSupport> getList(String path) {
        ArrayList<CustomerSupport> list = new ArrayList<>();
        ArrayList<String> listRecord = ReadAndWriteFile.read(path);
        if (!listRecord.isEmpty()) {
            for (String record : listRecord) {
                list.add(new CustomerSupport(record));
            }

            return list;
        }
        return null;
    }

    public ArrayList<CustomerSupport> getListByUsername(String username, String path) {
        ArrayList<CustomerSupport> list = getList(path);
        ArrayList<CustomerSupport> listReturn = new ArrayList<>();
        for (CustomerSupport c : list) {
            if (c.getUsername().equals(username)) {
                listReturn.add(c);
            }
        }
        return listReturn;
    }

    public CustomerSupport getCustomerSupport(String username) {
        ArrayList<CustomerSupport> list = getList(filePathCustomerSupport);
        for (CustomerSupport c : list) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    public void update(String username, CustomerSupportStatus customerSupportStatus) {
        ArrayList<CustomerSupport> list = getList(filePathCustomerSupport);
        for (int i = 0; i < list.size(); i++) {
            CustomerSupport customerSupport;
            if (list.get(i).getUsername().equals(username)) {
                customerSupport = getCustomerSupport(username);
                customerSupport.setCustomerSupportStatus(customerSupportStatus);
                list.set(i, customerSupport);
            }
        }
        ReadAndWriteFile.writeClear(filePathCustomerSupport, list);
    }
}
