import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner read = null; 
		InputStreamReader input = null;
		BufferedReader buffin = null;
		try {
			read = new Scanner(new FileReader("courses.txt"));
			String userEmail = read.nextLine();
			
			//setting up a parser
			URL link = new URL("https://sites.concordia.ca/cq/daily-events/cancel_course.php");
			URLConnection linkc = (URLConnection)link.openConnection();
			input = new InputStreamReader(linkc.getInputStream());
			buffin = new BufferedReader(input);
			
			//creating list of courses
			ArrayList<Course> courses = parseCourses(read);
			String nextline;
			boolean found = false;
			
			SendEmail.sendMail(courses.get(0), userEmail);
			System.out.println("SENT!");
			
			//parsing info form the website
			while ((nextline = buffin.readLine()) != null) {
				if(nextline.contains("<body>")) 
					found = true;	
				else if(nextline.contains("<div class=\"content_box_4\">"))
					break;
				if(found) {
					if(nextline.contains("<p class=\"error\">")) //no classes cancelled
						System.exit(0);
					else{
						if(nextline.contains("<!-- Course Cancel Detail -->")) { //some classes canceled
							read.nextLine();
							nextline = read.nextLine();
							for(Course i : courses) {
								if(nextline.contains(i.toString()))
									SendEmail.sendMail(i, userEmail);  //Send email for cancelled class
							}
						}
					}
				}
					
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File was not found. Please try again.");
			System.exit(0);
		} catch (IOException e) {
			e.getStackTrace();
		}
		

	}
	
	public static ArrayList<Course> parseCourses(Scanner read){
		String nextline = "";
		ArrayList<Course> courses = new ArrayList<Course>();
		while(read.hasNextLine()) {
			nextline = read.nextLine();
			
			if (nextline.matches("^[a-zA-Z]{4}\\d{3,5};\\w*$")) {
				
				String course = nextline.substring(0, nextline.lastIndexOf(";"));
				String section = nextline.substring(nextline.lastIndexOf(";") + 1, nextline.length()).trim();
				courses.add(new Course(course, section));
			
			}
				
		}
		return courses;
	}

}
