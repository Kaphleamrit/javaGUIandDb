import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
   static Statement stm;
   static Connection con;
   static PreparedStatement pStm;

public Database() {
    try {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb", "root", "realGeek1!");
        stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 0);
        pStm = con.prepareStatement("insert into myTable (namee,phone,details)"+ " values(?,?,?)");
    } catch (SQLException e) {
        System.out.println(e);
    }   
}
    public ResultSet get(String query) throws SQLException {
        return stm.executeQuery(query);
    }

    public int size() throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM myTable");
        int n = 0;
        if(rs != null) {
            rs.last();
            n = rs.getRow();
        }
        return n;

    }

    public  void insert(String nm , String phone, String details) throws SQLException {
    pStm.setString(1, nm);
    pStm.setString(2,phone);
    pStm.setString(3,details);   
    pStm.executeUpdate();   
    }

    public void authInsert(String username , String password) throws SQLException {
        PreparedStatement authPstm = con.prepareStatement("INSERT INTO authTable(username,pass,position)" + 
        "VALUES(?,?,'customer')");
        authPstm.setString(1, username);
        authPstm.setString(2, password);
        authPstm.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement delStm = con.prepareStatement("DELETE FROM myTable WHERE customer_id = " + id);
        PreparedStatement authDelstm = con.prepareStatement("DELETE FROM authTable WHERE customer_id = " + id);

        delStm.executeUpdate();
        authDelstm.executeUpdate();
    }

    public void update(int id,String name,String phone, String details) throws SQLException {
        PreparedStatement upStm = con.prepareStatement("UPDATE myTable SET namee =?, phone =?, details =?  WHERE customer_id = ?") ;
        upStm.setString(1, name);
        upStm.setString(2, phone);
        upStm.setString(3, details);
        upStm.setInt(4, id);
        upStm.executeUpdate();
    }
}
