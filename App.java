
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class App {
    JFrame f;

    public App() throws SQLException {
        f = new JFrame("Authetication");
        f.getContentPane().setBackground(Color.black);
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usrLabel = new JLabel("username:");
        JLabel passLabel = new JLabel("password:");
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField(8);
        JButton login = new JButton("Login");
        JLabel emptyLabel = new JLabel();
        JButton signUp = new JButton("SignUp");
        signUp.setForeground(Color.white);
        signUp.setBackground(Color.blue);
        signUp.setFont(new Font("Helvatica",Font.PLAIN,18));

        f.getContentPane().add(signUp);
        usrLabel.setBounds(10, 10, 300, 50);
        passLabel.setBounds(10, 90, 200, 50);
        username.setBounds(30, 50, 200, 20);
        password.setBounds(30, 130, 200, 20);
        login.setBounds(10,250,200,20);
        signUp.setBounds(10, 300, 200, 20);
        emptyLabel.setBounds(10, 340, 300, 30);

        usrLabel.setFont(new Font("Verdana",Font.PLAIN,18));
        usrLabel.setForeground(Color.white);

        passLabel.setFont(new Font("Verdana",Font.PLAIN,18));
        passLabel.setForeground(Color.white);

        username.setFont(new Font("Verdana",Font.PLAIN,15));
        password.setFont(new Font("Verdana",Font.PLAIN,15));


        login.setFont(new Font("Verdana", Font.PLAIN,18));
        login.setBackground(Color.blue);
        login.setForeground(Color.white);

        f.add(usrLabel);
        f.add(passLabel);
        f.add(username);
        f.add(password);
        f.add(login);
        f.add(emptyLabel);


        signUp.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                signUpView();
                f.dispose();
                f.setVisible(false);
            }
        });

        login.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Database myDB = new Database();
             
                String inpUsername = username.getText();
                char[] inpPass = password.getPassword();
                String position = "invalid";
                try {
                ResultSet rs = myDB.get("SELECT username, pass, position,customer_id FROM authTable");
                    while (rs.next()) {
                        char[] pass = rs.getString(2).toCharArray();
                        if (inpUsername.equals(rs.getString(1)) && Arrays.equals(inpPass,pass) ) {
                        position = rs.getString(3);
                        break;
                        }
                }
                if(position.equals("manager")) {
                    managerView();
                }
                else if(position.equals("customer")) {

                    customerView(Integer.parseInt(rs.getString(4)));
                }
                else {
                    invalidAuth();
                }
             }
                catch (SQLException e2) {
                    e2.printStackTrace();
                }
                f.dispose();
                f.setVisible(false);
            }
        });
        f.setVisible(true);
    
    }

public static void main(String[] args) throws SQLException {
   
    new App();

}

public static void addView() {
    JFrame f = new JFrame();
    f.setSize(400,400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JLabel nameLabel = new JLabel("Name: ");
    JLabel numLabel = new JLabel("Number: ");
    JLabel aboutLabel = new JLabel("About: ");

    nameLabel.setForeground(Color.white);
    numLabel.setForeground(Color.white);
    aboutLabel.setForeground(Color.white);


    JTextField name = new JTextField();
    JTextField num = new JTextField();
    JTextField about = new JTextField();
    JButton done = new JButton("Done");
    done.setBackground(Color.blue);
    done.setForeground(Color.white);
    JButton cancel = new JButton("Cancel");
    cancel.setBackground(Color.blue);
    cancel.setForeground(Color.white);
    done.addActionListener((ActionListener) new ActionListener() {
        

        @Override
        public void actionPerformed(ActionEvent e) {
            String inpName = name.getText();
            String inpNum = num.getText();
            String inpAbout = about.getText();

            Database myDB = new Database();
            try {
                myDB.insert(inpName, inpNum, inpAbout);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            managerView();
            f.dispose();
            f.setVisible(false);
        }
        
    });

    cancel.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            managerView();
            f.dispose();
            f.setVisible(false);
        }
        
    });

    nameLabel.setBounds(10,10,300,30);
    name.setBounds(20,50,300,30);
    numLabel.setBounds(10,90,300,30);
    num.setBounds(10,130,300,30);
    aboutLabel.setBounds(10,170,300,30);
    about.setBounds(10,210,300,30);
    done.setBounds(30,250,100,30);
    cancel.setBounds(180,250,100,30);

    f.add(nameLabel);
    f.add(name);
    f.add(numLabel);
    f.add(num);
    f.add(aboutLabel);
    f.add(about);
    f.add(done);
    f.add(cancel);

    f.setVisible(true);

}

public static void managerView() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setSize(400,400);
    f.setTitle("Manager");
    f.getContentPane().setBackground(Color.black);

    JButton add = new JButton("Add customer");
    add.setBackground(Color.blue);
    add.setForeground(Color.white);
    add.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            addView();
            f.dispose();
            f.setVisible(false);
}
    });

    JButton back = new JButton("Back");
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new App();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);
}
    });

    JButton accessCustomer = new JButton("Access Customer");
    accessCustomer.setBackground(Color.blue);
    accessCustomer.setForeground(Color.white);

    accessCustomer.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            askNumView();
            f.dispose();
            f.setVisible(false);
}
    });
  
    JButton listCustomers = new JButton("List all the customers");
    listCustomers.setBackground(Color.blue);
    listCustomers.setForeground(Color.white);


    listCustomers.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listView();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);
}
    });

    JLabel free = new JLabel();

    add.setBounds(10,10,300,30);
    accessCustomer.setBounds(10,90,300,30);
    listCustomers.setBounds(10,170,300,30);
    back.setBounds(10,250,300,30);

    free.setBounds(10,290,300,30);

    f.add(add);
    f.add(accessCustomer);
    f.add(listCustomers);
    f.add(back);
    f.add(free);

    f.setVisible(true);


}

public static void askNumView() {
    JFrame f= new JFrame();
    f.setSize(400,400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JLabel askNumLabel = new JLabel("Enter the customer id to get access");
    askNumLabel.setForeground(Color.white);
    JTextField askId = new JTextField();
    JButton submit = new JButton("Submit");
    submit.setBackground(Color.blue);
    submit.setForeground(Color.white);
    JButton back = new JButton("Back");
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            managerView();
            f.dispose();
            f.setVisible(false);
        }
        
    });

    submit.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int inpId = Integer.parseInt(askId.getText());

            managerUserView(inpId);
            f.dispose();
            f.setVisible(false);
        }
        
    });
    askNumLabel.setBounds(30,70,200,50);
    askId.setBounds(10,160,100,30);
    submit.setBounds(10,200,100,30);
    back.setBounds(120,200,100,30);

    f.add(askId);
    f.add(askNumLabel);
    f.add(submit);
    f.add(back);

    f.setVisible(true);

}

public static void managerUserView(int id) {
    JFrame f = new JFrame("Customer with id " + id);
    f.setSize(400,400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JButton nextRec = new JButton("Next Recharge Date");
    nextRec.setBackground(Color.blue);
    nextRec.setForeground(Color.white);
    JButton prepost = new JButton("Prepaid/Postpaid");
    prepost.setBackground(Color.blue);
    prepost.setForeground(Color.white);
    JButton details = new JButton("account detail");
    details.setBackground(Color.blue);
    details.setForeground(Color.white);
    JButton balance = new JButton("Balance");
    balance.setBackground(Color.blue);
    balance.setForeground(Color.white);
    JButton remove = new JButton("Remove User");
    remove.setBackground(Color.blue);
    remove.setForeground(Color.white);
    JButton back = new JButton("Back");
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    JButton update = new JButton("Update");
    update.setBackground(Color.blue);
    update.setForeground(Color.white);

    details.setBounds(10,10,300,30);
    nextRec.setBounds(10,60,300,30);
    prepost.setBounds(10,110,300,30);
    balance.setBounds(10,160,300,30);
    remove.setBounds(10,210,300,30);
    update.setBounds(10, 260, 300, 30);
    back.setBounds(10, 310, 300, 30);


    f.add(prepost);
    f.add(details);
    f.add(nextRec);
    f.add(balance);
    f.add(remove);
    f.add(back);
    f.add(update);

    update.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                updateView(id,"managerUserView");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);
        }       
    });

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            askNumView();
            f.dispose();
            f.setVisible(false);
        }       
    });

    remove.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Database myDB = new Database();
            try {
                myDB.delete(id);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            askNumView();
            f.dispose();
            f.setVisible(false);
        }       
    });

    nextRec.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            nextRecView(id,"mangerUserView");
            f.dispose();
            f.setVisible(false);
        }       
    });
   prepost.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            prepost.setText(Math.random()>0.5? "Prepaid" : "Postpaid"); 
            prepost.setEnabled(false);  
        }       
    });
    details.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                detailView(id,"managerUserView");    
                f.dispose();
                f.setVisible(false);    
                } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }       
    });
    balance.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
           
                balanceView(id,"managerUserView");
                f.dispose();
                f.setVisible(false);

        }
    });
    f.setVisible(true);
}

public static void nextRecView(int id, String string) {
    JFrame f = new JFrame();
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JLabel nextDate = new JLabel();
    nextDate.setForeground(Color.white);
    nextDate.setFont(new Font("Helvatica",Font.PLAIN,16));
    String s = LocalDate.now().plusDays(28).toString();
    nextDate.setText("<html>" + s + " is the next recharge date</html>");
    JButton back = new JButton("Back");
    back.setForeground(Color.white);
    back.setBackground(Color.blue);
    back.setBounds(10, 230, 300, 30);
    nextDate.setBounds(10, 10, 300, 200);

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(string.equals("managerUserView")) managerUserView(id); else customerView(id);
            f.dispose();
            f.setVisible(false);

        }
    });
    f.add(nextDate);
    f.add(back);

    f.setVisible(true);

}

public void invalidAuth() {
    JFrame f = new JFrame("invalid credentials");
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);
    JLabel invalid = new JLabel("<html>Either the password or username or both are incorrect , please check and try again</html>");
    invalid.setForeground(Color.white);
    invalid.setBounds(10, 50, 300, 100);

    JButton back = new JButton("Back");
    back.setForeground(Color.white);
    back.setBackground(Color.blue);
    back.setBounds(10, 200, 300, 30);

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new App();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);

        }
    });
    f.add(invalid);
    f.setVisible(true);
    f.add(back);
}

public static void customerView(int id) {
    JFrame f = new JFrame("CustomerView with id " + id);

    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JButton nextRec = new JButton("Next Recharge Date");
    nextRec.setBackground(Color.blue);
    nextRec.setForeground(Color.white);
    JButton prepost = new JButton("Prepaid/Postpaid");
    prepost.setBackground(Color.blue);
    prepost.setForeground(Color.white);
    JButton details = new JButton("account detail");
    details.setBackground(Color.blue);
    details.setForeground(Color.white);
    JButton balance = new JButton("Balance");
    balance.setBackground(Color.blue);
    balance.setForeground(Color.white);
    JButton back = new JButton("Back");
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    JButton update = new JButton("Update");
    update.setBackground(Color.blue);
    update.setForeground(Color.white);

    details.setBounds(10, 10, 300, 30);
    nextRec.setBounds(10, 60, 300, 30);
    prepost.setBounds(10, 110, 300, 30);
    balance.setBounds(10, 160, 300, 30);
    update.setBounds(10, 210, 300, 30);
    back.setBounds(10, 260, 300, 30);

    f.add(prepost);
    f.add(details);
    f.add(nextRec);
    f.add(balance);
    f.add(back);
    f.add(update);

    update.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                updateView(id,"customerView");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);
        }
    });

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new App();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            f.dispose();
            f.setVisible(false);
        }
    });

    nextRec.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            nextRecView(id,"customerView");
            f.dispose();
            f.setVisible(false);
        }
    });
    prepost.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            prepost.setText(Math.random() > 0.5 ? "Prepaid" : "Postpaid");
            prepost.setEnabled(false); // here
        }
    });
    details.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                detailView(id,"customerView");
                f.dispose();
                f.setVisible(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    });
    balance.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            balanceView(id,"customerView");
            f.dispose();
            f.setVisible(false);

        }
    });
    f.setVisible(true);
}

public static void listView() throws SQLException {
    JFrame f = new JFrame("ListView");
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    Database myDB = new Database();
    int n = myDB.size();
    String[][] data = new String[n][n];

    String[] cols = { "Name", "Phone", "Details" };
    ResultSet rs = myDB.get("SELECT namee, phone, details FROM myTable");
    int i = 0;
    while (i < n) {
        rs.next();
        data[i][0] = rs.getString(1);
        data[i][1] = rs.getString(2);
        data[i][2] = rs.getString(3);
        i++;
    }
    JTable table = new JTable(data, cols);
    table.setBounds(10, 10, 380, 300);

    JScrollPane sp = new JScrollPane(table);
    sp.setBounds(15, 15, 380, 300);

    JButton back = new JButton("Back");
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.setBounds(15, 330, 380, 30);
    JLabel empty = new JLabel();
    empty.setBounds(30, 300, 300, 30);

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            managerView();
            f.dispose();
            f.setVisible(false);
        }
    });

    f.add(sp);
    f.add(empty);
    f.add(back);
    f.setVisible(true);

}

public static void detailView(int id, String string) throws SQLException {
    JFrame f = new JFrame();
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel detail = new JLabel();
    detail.setText(
            "<html><h2>Details</h2>This field should be filled with some of the detailed information of the customer.This field should be filled.</html>;"
                    + "</html>");
    JButton back = new JButton("Back");

    f.getContentPane().setBackground(Color.black);
    detail.setForeground(Color.white);
    detail.setFont(new Font("Helvetica", Font.PLAIN, 16));
    detail.setBounds(10, 10, 320, 300);

    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.setFont(new Font("Helvetica", Font.PLAIN, 16));
    back.setBounds(10, 320, 320, 30);

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("reached 6");

            if(string.equals("managerUserView")) managerUserView(id); else customerView(id);
            f.dispose();
            f.setVisible(false);
        }
    });
    f.add(detail);
    f.add(back);

    f.setVisible(true);
}

public static void balanceView(int id, String string) {
    JFrame f = new JFrame();
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    double balance = Math.random() * 500;
    JLabel bal = new JLabel();
    bal.setText("<html><h2>Balance</h2> you have total balance of " + balance + "</html>");
    JButton back = new JButton("Back");
    JButton recharge = new JButton("Recharge");

    f.getContentPane().setBackground(Color.black);
    bal.setForeground(Color.white);
    bal.setFont(new Font("Helvetica", Font.PLAIN, 16));
    bal.setBounds(10, 10, 300, 280);
    recharge.setBounds(10,300,320,30);

    recharge.setBackground(Color.blue);
    recharge.setForeground(Color.white);
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.setFont(new Font("Helvetica", Font.PLAIN, 16));
    back.setBounds(10, 340, 320, 30);

    recharge.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            rechargeView(id);
            f.dispose();
            f.setVisible(false);
        }       
    });

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

         if(string.equals("managerUserView")) managerUserView(id); else customerView(id);
            f.dispose();
            f.setVisible(false);
        }       
    });
    f.add(bal);
    f.add(back);
    if(string.equals("customerView")) f.add(recharge);


    f.setVisible(true);
}

public static void updateView(int id, String string) throws SQLException {
    JFrame f = new JFrame();
    f.setSize(400,400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JLabel nameLabel = new JLabel("Name: ");
    JLabel numLabel = new JLabel("Number: ");
    JLabel aboutLabel = new JLabel("About: ");

    nameLabel.setForeground(Color.white);
    numLabel.setForeground(Color.white);
    aboutLabel.setForeground(Color.white);

    Database myDb = new Database();
    ResultSet rs = myDb.get("SELECT namee,phone,details FROM myTable WHERE customer_id = " + id);
    rs.next();

    JTextField name = new JTextField(rs.getString(1));
    JTextField num = new JTextField(rs.getString(2));
    JTextField about = new JTextField(rs.getString(3));
    JButton done = new JButton("Done");
    done.setBackground(Color.blue);
    done.setForeground(Color.white);
    JButton cancel = new JButton("Cancel");
    cancel.setBackground(Color.blue);
    cancel.setForeground(Color.white);

    done.addActionListener((ActionListener) new ActionListener() {
        

        @Override
        public void actionPerformed(ActionEvent e) {
            String inpName = name.getText();
            String inpNum = num.getText();
            String inpAbout = about.getText();

            Database myDB = new Database();
            try {
                myDB.update(id,inpName, inpNum, inpAbout);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if(string.equals("managerUserView")) managerUserView(id); else customerView(id);
            f.dispose();
            f.setVisible(false);
        }
        
    });

    cancel.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(string.equals("managerUserView")) managerUserView(id); else customerView(id);
            f.dispose();
            f.setVisible(false);
        }
        
    });

    nameLabel.setBounds(10,10,300,30);
    name.setBounds(20,50,300,30);
    numLabel.setBounds(10,90,300,30);
    num.setBounds(10,130,300,30);
    aboutLabel.setBounds(10,170,300,30);
    about.setBounds(10,210,300,30);
    done.setBounds(30,250,100,30);
    cancel.setBounds(180,250,100,30);

    f.add(nameLabel);
    f.add(name);
    f.add(numLabel);
    f.add(num);
    f.add(aboutLabel);
    f.add(about);
    f.add(done);
    f.add(cancel);

    f.setVisible(true);

}

public static void signUpView() {
    JFrame f = new JFrame();
    f.setSize(400,400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.black);

    JLabel nameLabel = new JLabel("Name: ");
    JLabel numLabel = new JLabel("Number: ");
    JLabel aboutLabel = new JLabel("About: ");
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel passwordLabel= new JLabel("Password: ");
    JLabel empty = new JLabel();


    nameLabel.setForeground(Color.white);
    numLabel.setForeground(Color.white);
    aboutLabel.setForeground(Color.white);
    usernameLabel.setForeground(Color.white);
    passwordLabel.setForeground(Color.white);

  

    JTextField name = new JTextField();
    JTextField num = new JTextField();
    JTextField about = new JTextField();
    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();

    JButton create = new JButton("Create");
    create.setBackground(Color.blue);
    create.setForeground(Color.white);
    JButton cancel = new JButton("Cancel");
    cancel.setBackground(Color.blue);
    cancel.setForeground(Color.white);

    create.addActionListener((ActionListener) new ActionListener() {
        

        @Override
        public void actionPerformed(ActionEvent e) {
            String inpName = name.getText();
            String inpNum = num.getText();
            String inpAbout = about.getText();
            String inpUsername = username.getText();
            String inpPassword = String.valueOf(password.getPassword());

            Database myDB = new Database();
            try {
                myDB.insert(inpName, inpNum, inpAbout);
                myDB.authInsert(inpUsername, inpPassword);
                new App();
                f.dispose();
                f.setVisible(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        
    });

    cancel.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new App();
                f.dispose();
                f.setVisible(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    });

    nameLabel.setBounds(10,5,250,20);
    name.setBounds(20,30,250,25);
    numLabel.setBounds(10,60,250,20);
    num.setBounds(10,85,250,25);
    aboutLabel.setBounds(10,115,250,20);
    about.setBounds(10,140,250,25);
    usernameLabel.setBounds(10,170,250,20);
    username.setBounds(10,195,250,25);
    passwordLabel.setBounds(10,225,250,20);
    password.setBounds(10,250,250,25);

    create.setBounds(30,300,100,30);
    cancel.setBounds(180,300,100,30);
    empty.setBounds(30,340,1000,30);

    f.add(empty);
    f.add(nameLabel);
    f.add(name);
    f.add(numLabel);
    f.add(num);
    f.add(aboutLabel);
    f.add(about);
    f.add(create);
    f.add(cancel);
    f.add(username);
    f.add(usernameLabel);
    f.add(password);
    f.add(passwordLabel);


    f.setVisible(true);
}

public static void rechargeView(int id) {
    JFrame f = new JFrame();
    f.setSize(400, 400);
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // double balance = Math.random() * 500;
    JLabel regLabel = new JLabel("Recharge Amount");
    JButton back = new JButton("Back");
    JButton recharge = new JButton("Recharge");
    JTextField amount = new JTextField();
    f.getContentPane().setBackground(Color.black);
    regLabel.setForeground(Color.white);
    regLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
    regLabel.setBounds(10, 10, 300, 30);
    amount.setBounds(10,60,300,30);
    recharge.setBounds(10,120,100,30);
    back.setBounds(10, 230, 100, 30);

    
    recharge.setBackground(Color.blue);
    recharge.setForeground(Color.white);
    back.setBackground(Color.blue);
    back.setForeground(Color.white);
    back.setFont(new Font("Helvetica", Font.PLAIN, 16));

    recharge.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            customerView(id);
            f.dispose();
            f.setVisible(false);
        }       
    });

    back.addActionListener((ActionListener) new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

         customerView(id);
            f.dispose();
            f.setVisible(false);
        }       
    });
    f.add(regLabel);
    f.add(back);
    f.add(recharge);
    f.add(amount);

    f.setVisible(true);
}
}