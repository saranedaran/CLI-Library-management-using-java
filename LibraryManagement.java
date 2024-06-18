package jdbc;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.time.*;

class AdPwd{
	static Scanner sc = new Scanner(System.in);
	static String password(String name) throws Exception {
        String url = "jdbc:mysql://localhost:3306/library";
        String userName = "root";
        String passWord = "Saran7103$";
        String query = "SELECT password FROM adminlogin WHERE name=?";
        
        try (Connection con = DriverManager.getConnection(url, userName, passWord);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                } else {
                    throw new Exception("User not found");
                }
            }
        }
    }
}

class Admin extends AdPwd{ 
	
	void checkAdPwd() throws Exception {
		System.out.print("Enter your name: ");
		String name = sc.nextLine();
		System.out.print("Enter your pwd: ");
		String pwd = sc.nextLine();
		System.out.println("");
		String dbpwd = password(name);
		if(pwd.equals(dbpwd)) {
			menu();
		}
		else {
			System.out.println("Incorrect password");
		}
 }
	static void menu() throws Exception {
		System.out.println("1. Add book");
		System.out.println("2. View Status");
		System.out.println("3. Update Quantity");
		System.out.println("4. Check In");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		switch(ch) {
		case 1:
			addBook();
			break;
		case 2:
			viewStatus();
			break;
		case 3:
			updateQnty();
			break;
		case 4:
			checkIn();
			break;
		}
	}
	
	static void addBook() throws Exception{
		String url ="jdbc:mysql://localhost:3306/library";
		String username = "root";
		String passWord = "Saran7103$";
		
		System.out.print("Enter Book id: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Book name: ");
		String name = sc.nextLine();
		System.out.print("Enter Wing name: ");
		String wing = sc.nextLine();
		System.out.print("Enter Book Count: ");
		int cnt = sc.nextInt();
		System.out.print("Enter Book Availability: ");
		boolean flag = sc.nextBoolean();
		
		String query = "insert into book values("+id+",'"+name+"','"+wing+"',"+cnt+","+flag+");";
		Connection con = DriverManager.getConnection(url, username, passWord);
		Statement st = con.createStatement();
		int rs = st.executeUpdate(query);
		System.out.print("The Book is added!");
	}


	static void viewStatus() throws Exception{
		String url = "jdbc:mysql://localhost:3306/library";
		String username = "root";
		String passWord = "Saran7103$";
		
		String query = "select * from student";
		
		Connection con = DriverManager.getConnection(url, username, passWord);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		String rowFormat ="%-15s%-15s%-50s%-20s%-20s%n";
		System.out.printf(rowFormat, "Name", "Register ID", "Books Borrowed", "Check Out Date", "Check In Date");
		
		while (rs.next()) {
            String studentName = rs.getString(1);
            int registerId = rs.getInt(2);
            String booksBorrowed = rs.getString(3);
            Date checkOutDate = rs.getDate(4);
            Date checkInDate = rs.getDate(5);
            System.out.printf(rowFormat, studentName, registerId, booksBorrowed, checkOutDate, checkInDate);
        }

		con.close();
		
		
	}
	static void updateQnty() throws Exception {
		String url ="jdbc:mysql://localhost:3306/library";
		String username = "root";
		String passWord = "Saran7103$";
		
		System.out.print("Enter Book id: ");
		int id = sc.nextInt();
		System.out.print("Enter Quantity to be updated: ");
		int cnt = sc.nextInt();
		
		String query="select cnt from book where bookid="+id;
		
		Connection con = DriverManager.getConnection(url, username, passWord);
		Statement st = con.createStatement();
		ResultSet num = st.executeQuery(query);
		int n =0;
		if (num.next()) {
            n = num.getInt("cnt");
		}
		String nxtquery = "update book set cnt="+(cnt+n)+" where bookid="+id;
		int c = st.executeUpdate(nxtquery);
		System.out.print("\nQuantity is updated!");
		con.close();
	}
	
	
	static void checkIn()throws Exception {
		String url ="jdbc:mysql://localhost:3306/library";
		String username = "root";
		String passWord = "Saran7103$";
		
		System.out.print("Enter Book id: ");
		int bid = sc.nextInt();
		System.out.print("Enter Register id of the student: ");
		int sid = sc.nextInt();
		
		
		String query="delete from student where bookid = "+bid+" and register_id="+sid;
		
		Connection con = DriverManager.getConnection(url, username, passWord);
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		
		String nxtquery="select cnt from book where bookid="+bid;
		ResultSet num = st.executeQuery(nxtquery);
		int n =0;
		if (num.next()) {
            n = num.getInt("cnt");
		}
		String nxt1query = "update book set cnt="+(n+1)+" where bookid="+bid;
		int c = st.executeUpdate(nxt1query);
		
		System.out.print("\nBook is checked in!");
		con.close();
		
		
	}
	
	
	
	
}


class StPwd{
	Scanner sc = new Scanner(System.in);
	static String password(String name) throws Exception {
        String url = "jdbc:mysql://localhost:3306/library";
        String userName = "root";
        String passWord = "Saran7103$";
        String query = "SELECT password FROM studentlogin WHERE name=?";
        
        try (Connection con = DriverManager.getConnection(url, userName, passWord);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                } else {
                    throw new Exception("User not found");
                }
            }
        }
    }
}
class Student extends StPwd{  
	static int regid=0;
	static String stname="";
	static Scanner sc = new Scanner(System.in);
	static String checkStPwd() throws Exception {
		System.out.print("Enter your Name: ");
		stname = sc.nextLine();
		System.out.print("Enter your Register Id: ");
		regid = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter your pwd: ");
		String pwd = sc.nextLine();
		System.out.println("");
		String dbpwd = password(stname);
		if (pwd.equals(dbpwd)) {
            System.out.println("Password verified!");
            return stname;
        } else {
            throw new Exception("Incorrect password");
        }
}
	
	
	static int menu() throws Exception{
		System.out.println("1. View Status");
		System.out.println("2. CheckOut");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		return ch;
	}
	static void Status(String name) throws Exception {
		String url = "jdbc:mysql://localhost:3306/library";
		String userName = "root";
		String passWord = "Saran7103$";
		
		String query = "select * from student where name = '"+name+"'";
		
		Connection con = DriverManager.getConnection(url, userName, passWord);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String rowFormat = "%-15s%-15s%-25s%-20s%-20s%n";
        System.out.printf(rowFormat, "Name", "Register ID", "Books Borrowed", "Check Out Date", "Check In Date");
		
		while (rs.next()) {
            String studentName = rs.getString(1);
            int registerId = rs.getInt(2);
            String booksBorrowed = rs.getString(3);
            Date checkOutDate = rs.getDate(4);
            Date checkInDate = rs.getDate(5);
            System.out.printf(rowFormat, studentName, registerId, booksBorrowed, checkOutDate, checkInDate);
        }

		con.close();
		
	}
	static void checkOut() throws Exception{
		System.out.println("You're here for check out a book!");
		System.out.println("Search by \n1. Book Id\n2. Book Name\n3. Book Wing");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		switch(ch) {
		case 1:
			int id = getid();
			int i =searchId(id);
			break;
		case 2:
			String name =getname();
			int n=searchname(name);
			break;
		case 3:
			String wing = getwing();
			int w = searchwing(wing);
			break;
		}
		
		

	}
	static int checkoutMenu() throws Exception{
		System.out.println("1. Yes");
		System.out.println("2. No");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		return ch;
	}
	static int getid()throws Exception {
		System.out.print("Enter the book id: ");
		int search = sc.nextInt();
		sc.nextLine();
		return search;
	}
	static String getname()throws Exception {
		System.out.print("Enter the book name: ");
		sc.nextLine();
		String search = sc.nextLine();
		return search;
	}
	static String getwing()throws Exception {
		sc.nextLine();
		System.out.print("Enter the book wing: ");
		String search = sc.nextLine();
		return search;
	}

		public static int searchId(int id) throws Exception{
			String url = "jdbc:mysql://localhost:3306/library";
		String userName = "root";
		String passWord = "Saran7103$";
		String query = "select * from book where  bookid="+id;
		
		Connection con = DriverManager.getConnection(url, userName, passWord);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int bookid=0;
		String name="";
		String wing="";
		int cnt=0;
		String rowFormat = "%-15s%-25s%-15s%-20s%-20s%n";
        System.out.printf(rowFormat, "Book Id", "Name", "Wing", "Book Count", "Availability");
		while (rs.next()) {
            bookid = rs.getInt(1);
            name = rs.getString(2);
            wing = rs.getString(3);
            cnt = rs.getInt(4);
            boolean availability = rs.getBoolean(5);

            // Format the output as a table row
            
            System.out.printf(rowFormat, bookid, name, wing, cnt, availability);
        }
		System.out.println("1. Yes");
		System.out.println("2. No");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		if(ch ==1) {
			 LocalDate coDate = LocalDate.now();
		     LocalDate ciDate = coDate.plusDays(15);
		     String nxtquery = "INSERT INTO student (name, register_id, book_borrowed, check_out_date, check_in_date, bookid) VALUES (?, ?, ?, ?, ?)";
		     PreparedStatement pstmt = con.prepareStatement(nxtquery);
		     pstmt.setString(1, stname);
		     pstmt.setInt(2, regid);
		     pstmt.setString(3, name);
		     pstmt.setString(4, String.valueOf(coDate));
		     pstmt.setString(5, String.valueOf(ciDate));

		     int rows = pstmt.executeUpdate();
			String uquery = "update book set cnt="+(cnt-1)+" where bookid="+bookid;
			Statement ust = con.createStatement();
			int r = st.executeUpdate(uquery);
			System.out.println("You have successfully borrowed the book!");
			System.out.println("Thank you! Don't forget to return the book on time"+Character.toString( 128_512 ));
			
		}
		con.close();
		return bookid;
	}
		public static int searchname(String n) throws Exception {
		    String url = "jdbc:mysql://localhost:3306/library";
		    String userName = "root";
		    String passWord = "Saran7103$";
		    String query = "SELECT * FROM book WHERE name = '" + n + "'";

		    try (Connection con = DriverManager.getConnection(url, userName, passWord);
		         Statement st = con.createStatement();
		         ResultSet rs = st.executeQuery(query)) {

		        int bookid = 0;
		        String name = "";
		        String wing = "";
		        int cnt = 0;
		        boolean availability = false;

		        while (rs.next()) {
		            bookid = rs.getInt("bookid");
		            name = rs.getString("name");
		            wing = rs.getString("wing");
		            cnt = rs.getInt("cnt");
		            availability = rs.getBoolean("availability");

		            // Format the output as a table row
		            String rowFormat = "%-15s%-25s%-15s%-20s%-20s%n";
		            System.out.printf(rowFormat, "Book Id", "Name", "Wing", "Book Count", "Availability");
		            System.out.printf(rowFormat, bookid, name, wing, cnt, availability);
		        }

		        System.out.println("1. Yes");
		        System.out.println("2. No");
		        System.out.print("choice-> ");
		        int ch = sc.nextInt();

		        if (ch == 1) {
		            LocalDate coDate = LocalDate.now();
		            LocalDate ciDate = coDate.plusDays(15);
		            String nxtquery = "INSERT INTO student (name, register_id, book_borrowed, check_out_date, check_in_date, bookid) " +
		                    "VALUES ('" + stname + "', " + regid + ", '" + name + "', '" + coDate + "', '" + ciDate + "', " + bookid + ")";
		            Statement nxtst = con.createStatement();
		            int rows = nxtst.executeUpdate(nxtquery);

		            String uquery = "UPDATE book SET cnt = " + (cnt - 1) + " WHERE bookid = " + bookid;
		            Statement ust = con.createStatement();
		            int r = ust.executeUpdate(uquery);

		            System.out.println("You have successfully borrowed the book!");
		            System.out.println("Thank you! Don't forget to return the book on time \uD83D\uDE00");
		        }

		        return bookid;
		    }
		}


		public static int searchwing(String w) throws Exception{
			String url = "jdbc:mysql://localhost:3306/library";
		String userName = "root";
		String passWord = "Saran7103$";
		String query = "select * from book where wing ='"+w+"'";
		
		Connection con = DriverManager.getConnection(url, userName, passWord);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int bookid =0;
		String name="";
		String wing="";
		int cnt=0;
	    String rowFormat = "%-15s%-25s%-15s%-20s%-20s%n";
	    System.out.printf(rowFormat, "Book Id", "Name", "Wing", "Book Count", "Availability");
		while (rs.next()) {
		    bookid = rs.getInt(1);
		    name = rs.getString(2);
		    wing = rs.getString(3);
		    cnt = rs.getInt(4);
		    boolean availability = rs.getBoolean(5);
		
		    // Format the output as a table row

		    System.out.printf(rowFormat, bookid, name, wing, cnt, availability);
		}
		System.out.print("Enter the book id: ");
		int bid = sc.nextInt();
		System.out.println("1. Yes");
		System.out.println("2. No");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		if(ch ==1) {
			 LocalDate coDate = LocalDate.now();
		     LocalDate ciDate = coDate.plusDays(15);
			String nxtquery = "insert into student values('"+stname+"',"+regid+",'"+name+"','"+String.valueOf(coDate)+"','"+String.valueOf(ciDate)+"',"+bid+");";
			Statement nxtst = con.createStatement();
			int rows = nxtst.executeUpdate(nxtquery);
			String uquery = "update book set cnt="+(cnt-1)+" where bookid="+bookid;
			Statement ust = con.createStatement();
			int r = st.executeUpdate(uquery);
			System.out.println("You have successfully borrowed the book!");
			System.out.println("Thank you! Don't forget to return the book on time"+Character.toString( 128_512 ));
			
		}
			con.close();
			return bookid;
	}
}

class NewUser{
	static Scanner sc = new Scanner(System.in);
	static void addAdmin() throws Exception {
		String url = "jdbc:mysql://localhost:3306/library";
		String userName = "root";
		String passWord = "Saran7103$";
		
		System.out.println("Enter your user name:");
		String name = sc.nextLine();
		System.out.println("Enter your password:");
		String pwd = sc.nextLine();
		System.out.println("Re-enter your password:");
		String rpwd = sc.nextLine();
		if(pwd.equals(rpwd)) {
			String query = "insert into adminlogin values('"+name+"','"+pwd+"')";
			
			Connection con = DriverManager.getConnection(url, userName, passWord);
			Statement st = con.createStatement();
			int num = st.executeUpdate(query);
			
			System.out.println("New User Added!");
		}
	}
	static void addStudent() throws Exception {
		String url = "jdbc:mysql://localhost:3306/library";
		String userName = "root";
		String passWord = "Saran7103$";
		
		System.out.println("Enter your user name:");
		String name = sc.nextLine();
		System.out.println("Enter your password:");
		String pwd = sc.nextLine();
		System.out.println("Re-enter your password:");
		String rpwd = sc.nextLine();
		if(pwd.equals(rpwd)) {
			String query = "insert into studentlogin values('"+name+"','"+pwd+"')";
			
			Connection con = DriverManager.getConnection(url, userName, passWord);
			Statement st = con.createStatement();
			int num = st.executeUpdate(query);
			
			System.out.println("New User Added!");
		}
	}
	
	
}



public class LibraryManagement {
	
//	public static void 

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Student s = new Student();
		Admin a = new Admin();
		NewUser u = new NewUser();
		System.out.println("-".repeat(150));
		System.out.println(" ".repeat(60)+"Welcome to BOOK DORM Library");
		System.out.println("-".repeat(150));
		System.out.println("1. Admin");
		System.out.println("2. Student");
		System.out.println("3. New User");
		System.out.print("choice-> ");
		int ch = sc.nextInt();
		
		System.out.println("");
		switch(ch){
		case 1:
			a.checkAdPwd();
			break;
		case 2:
            String studentName = s.checkStPwd();
            int c = s.menu();
            if(c==1) {
                s.Status(studentName);
               
            } 
            else if(c==2) {
                s.checkOut(); // Pass the student's name to the Status() method
            }
            break;
		case 3:
			System.out.println("1. Add Admin");
			System.out.println("2. Add Student");
			System.out.print("choice-> ");
			int cho = sc.nextInt();
			if(cho==1) {
				u.addAdmin();
			}
			else if(cho==2) {
				u.addStudent();
			}
			break;
		case 4:
		default:
			System.out.println("The Exit");
			break;
			
		}
		System.out.println("");
		System.out.println("");
		System.out.println("Visit Again "+Character.toString( 128_512 ));
		String[] quotes = {
	            "A room without books is like a body without a soul. - Marcus Tullius Cicero",
	            "Books are a uniquely portable magic. - Stephen King",
	            "The more that you read, the more things you will know. The more that you learn, the more places you'll go. - Dr. Seuss",
	            "Books are the quietest and most constant of friends; they are the most accessible and wisest of counselors, and the most patient of teachers. - Charles William Eliot",
	            "A book is a dream that you hold in your hand. - Neil Gaiman",
	            "I cannot live without books. - Thomas Jefferson",
	            "The only thing you absolutely have to know is the location of the library. - Albert Einstein",
	            "A book is like a garden carried in the pocket. - Chinese Proverb",
	            "Reading is to the mind what exercise is to the body. - Joseph Addison",
	            "There is no friend as loyal as a book. - Ernest Hemingway"
	        };
		Random random = new Random();
        int randomIndex = random.nextInt(quotes.length);
        String randomQuote = quotes[randomIndex];
        System.out.println("-".repeat(150));
		System.out.println(randomQuote);
		System.out.println("-".repeat(150));
		
	}

}
