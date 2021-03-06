package repository;

import model.Admin;
import model.Customer;
import model.Role;
import utils.HashingPassword;
import utils.ReadAndWriteFile;

import java.text.ParseException;
import java.util.ArrayList;

public class UserRepository implements IUser {
    public final static String filePath = "src/data/user.txt";


    public UserRepository()  {
    }


    @Override
    public <T> void add(T newUser) {
        ArrayList<T> listUser = new ArrayList<>();
        listUser.add(newUser);
        ReadAndWriteFile.write(filePath, listUser);
    }




    @Override
    public <T> void update(T obj, Role role, String username) throws ParseException {
        ArrayList<Customer> listCustomer = getListCustomer();
        ArrayList<Admin> listAdmin = getListAdmins();
        switch (role) {
            case CUSTOMER:
                for (int i = 0; i < listCustomer.size(); i++) {
                    if (username.equals(listCustomer.get(i).getUsername())) {
                        listCustomer.set(i, (Customer) obj);
                        ReadAndWriteFile.writeClear(filePath, listCustomer);
                        ReadAndWriteFile.write(filePath, listAdmin);
                        break;
                    }
                }
            case ADMIN:
                for (int i = 0; i < listAdmin.size(); i++) {
                    if (username.equals(listAdmin.get(i).getUsername())) {
                        listAdmin.set(i, (Admin) obj);
                        ReadAndWriteFile.writeClear(filePath, listAdmin);
                        ReadAndWriteFile.write(filePath, listCustomer);
                        break;
                    }
                }
        }
    }

    public void updateLevel(String username, double levelUpdate) throws ParseException {
        ArrayList<Admin> listAdmin = getListAdmins();
        ArrayList<Customer> listCustomer = getListCustomer();
        for (Customer c : listCustomer) {
            if (c.getUsername().equals(username)) {
                c.setLevel(levelUpdate + c.getLevel());
                break;
            }
        }
        ReadAndWriteFile.writeClear(filePath, listCustomer);
        ReadAndWriteFile.write(filePath, listAdmin);
    }

    //    ki???m tra kh??ch h??ng c?? t???n t???i (username ho???c s??t) v?? m???t kh???u
    public boolean checkCustomer(String options, String password) {
        ArrayList<Customer> listCustomer = getListCustomer();
        for (Customer c : listCustomer) {
            if ((c.getUsername().equals(options) || ("0" + c.getPhoneNumber()).equals(options))
                    && c.getPassword().equals(HashingPassword.get_SHA_512_SecurePassword(password))) {
                return true;
            }
        }
        return false;
    }

    //    ki???m tra quan li c?? t???n t???i (username ho???c s??t) v?? m???t kh???u
    public boolean checkAdmin(String options, String password) throws ParseException {
        ArrayList<Admin> listAdmin = getListAdmins();
        for (Admin a : listAdmin) {
            if ((a.getUsername().equals(options) || ("0" + a.getPhoneNumber()).equals(options)) &&
                    a.getPassword().equals(HashingPassword.get_SHA_512_SecurePassword(password))) {
                return true;
            }
        }
        return false;
    }

    //    c?? t???n t???i t??n ng?????i d??ng hay khong
    public boolean existUser(String options) throws ParseException {
        ArrayList<Customer> listCustomer = getListCustomer();
        ArrayList<Admin> listAdmin = getListAdmins();
        for (Customer c : listCustomer) {
            if (c.getUsername().equals(options) || options.equals("0" + c.getPhoneNumber()))
                return true;
        }
        for (Admin a : listAdmin) {
            if (a.getUsername().equals(options) || options.equals("0" + a.getPhoneNumber()) || a.getEmail().equals(options))
                return true;
        }
        return false;
    }

    public boolean existCustomer(String options) {
        ArrayList<Customer> listCustomer = getListCustomer();
        for (Customer c : listCustomer) {
            if (c.getUsername().equals(options) || options.equals("0" + c.getPhoneNumber()))
                return true;
        }
        return false;
    }

    public ArrayList<Customer> getListCustomer() {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        ArrayList<String> listRecord = ReadAndWriteFile.read(filePath);
        for (String record : listRecord) {
            if (record.contains(String.valueOf(Role.CUSTOMER))) {
                listCustomer.add(new Customer(record));
            }
        }
        return listCustomer;
    }

    public ArrayList<Admin> getListAdmins() throws ParseException {
        ArrayList<Admin> listAdmin = new ArrayList<>();
        ArrayList<String> listRecord = ReadAndWriteFile.read(filePath);
        for (String record : listRecord) {
            if (record.contains(String.valueOf(Role.ADMIN))) {
                listAdmin.add(new Admin(record));
            }
        }
        return listAdmin;
    }

    //tra ve customer
    public Customer getCustomer(String options) {
        ArrayList<Customer> listCustomer=getListCustomer();
        for (Customer c : listCustomer) {
            if (options.equals(c.getUsername()) || options.equals("0" + c.getPhoneNumber())) {
                return c;
            }
        }
        return null;
    }

    //tra ve admin;
    public Admin getAdmin(String options) throws ParseException {
        ArrayList<Admin>listAdmin=getListAdmins();
        for (Admin a : listAdmin) {
            if (options.equals(a.getUsername()) || options.equals("0" + a.getPhoneNumber())) {
                return a;
            }
        }
        return null;
    }
}

