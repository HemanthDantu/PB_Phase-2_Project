import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.api.java.JavaSQLContext;
import org.apache.spark.sql.api.java.JavaSchemaRDD;
import org.apache.spark.sql.api.java.Row;

@WebServlet("/RDDQueries")
public class RDDQueries extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	URL url = getClass().getResource("BankingTweets.txt");

    public RDDQueries() {
      
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int user_choice = Integer.parseInt(request.getParameter("query_choice"));
	      System.out.println(user_choice);
	      
	      switch(user_choice)
			{
			case 1: 
		        RDDQuery1();
		       
		       response.sendRedirect("RDDQuery1.html");
				break;
		case 2: 
			RDDQuery2();   
			response.sendRedirect("RDDQuery2.html");
			break;
		case 3:
			RDDQuery3();
			response.sendRedirect("RDDQuery3.html");
			break;
				
			default: JOptionPane.showMessageDialog(null, "Invalid Option please Enter from 1 to 8");
						break;
			}
		
		
	}
	
/*-------------RDDQuery1 :Most Interested Type of Loan------------------------*/
	
	
	public void RDDQuery1() {

		String pathToFile = url.toString();

		SparkConf conf = new SparkConf().setAppName("Bank Analyisis").setMaster("local[*]");

		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaSQLContext sqlContext = new JavaSQLContext(sc);

		JavaSchemaRDD tweets = sqlContext.jsonFile(pathToFile);

		tweets.registerAsTable("tweetTable");

		tweets.printSchema();

		nbTweetByQuery1(sqlContext);

		sc.stop();

	}

	private void nbTweetByQuery1(JavaSQLContext sqlContext) {

		try {
			File outputFile = new File("C:/Users/dhema/workspace/TwitterAnalysis/WebContent/RDDQuery1.csv");

			FileWriter fw = new FileWriter(outputFile);

			JavaSchemaRDD count = sqlContext.sql("SELECT  COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%car loan%'");
			
			JavaSchemaRDD count1 = sqlContext
					.sql("SELECT  COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%home loan%'");
			JavaSchemaRDD count2 = sqlContext
					.sql("SELECT  COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%education loan%'");
			JavaSchemaRDD count3 = sqlContext
					.sql("SELECT  COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%personal loan%'");
			JavaSchemaRDD count4 = sqlContext
					.sql("SELECT  COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%mortgages%'");

			List<Row> car = count.collect();
			String car12 = car.toString();
			String car1 = car12.substring(car12.indexOf("[") + 2, car12.indexOf("]"));

			List<Row> home = count1.collect();
			String home12 = home.toString();
			String home1 = home12.substring(home12.indexOf("[") + 2, home12.indexOf("]"));

			List<Row> education = count2.collect();
			String education12 = education.toString();
			String education1 = education12.substring(education12.indexOf("[") + 2, education12.indexOf("]"));

			List<Row> personal = count3.collect();
			String personal12 = personal.toString();
			String personal1 = personal12.substring(personal12.indexOf("[") + 2, personal12.indexOf("]"));

			List<Row> mortgages = count4.collect();
			String mortgages12 = mortgages.toString();
			String mortgages1 = mortgages12.substring(mortgages12.indexOf("[") + 2, mortgages12.indexOf("]"));

			fw.append("LoanType");
			fw.append(',');
			fw.append("Count");
			fw.append("\n");
			fw.append("car");
			fw.append(',');
			fw.append(car1);
			fw.append("\n");
			fw.append("home");
			fw.append(',');
			fw.append(home1);
			fw.append("\n");
			fw.append("education");
			fw.append(',');
			fw.append(education1);
			fw.append("\n");
			fw.append("personal");
			fw.append(',');
			fw.append(personal1);
			fw.append("\n");
			fw.append("mortgages");
			fw.append(',');
			fw.append(mortgages1);
			fw.append("\n");

			fw.close();

		} catch (Exception exp) {
		}

	}
	
/*-------------RDDQuery 2:People's Opinion on Bank Security-------------------*/
	
	public void RDDQuery2() {

		String pathToFile = url.toString();
		SparkConf conf = new SparkConf().setAppName("Bank Analyisis").setMaster("local[*]");

		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaSQLContext sqlContext = new JavaSQLContext(sc);

		JavaSchemaRDD tweets = sqlContext.jsonFile(pathToFile);

		tweets.registerAsTable("tweetTable");

		tweets.printSchema();

		nbTweetQuery2(sqlContext);

		sc.stop();

	}

	private void nbTweetQuery2(JavaSQLContext sqlContext) {

		try {
			File outputFile = new File("C:/Users/dhema/workspace/TwitterAnalysis/WebContent/RDDQuery2.csv");

			FileWriter fw = new FileWriter(outputFile);

			JavaSchemaRDD count = sqlContext.sql("SELECT  COUNT(*) AS c FROM tweetTable "
					+ "WHERE text LIKE '%safe%' OR text LIKE '%secure%' OR text LIKE '%good%' OR text LIKE '%best%' OR text LIKE '%protected%' OR text LIKE '%okay%' OR text LIKE '%snug%' OR text LIKE '%buttonedup%' OR text LIKE '%appreciate%' OR text LIKE '%cherished%' OR text LIKE '%free from danger%' OR text LIKE '%guarded%' OR text LIKE '%impervious%' OR text LIKE '%impregnable%' OR text LIKE '%invioable%' OR text LIKE '%invulnarable%' OR text LIKE '%preserved%' OR text LIKE '%sheltered%' OR text LIKE '%shielded%' OR text LIKE '%unharmed%' OR text LIKE '%unhurt%' OR text LIKE '%immune%' OR text LIKE '%riskless%' OR text LIKE '%undamaged%' OR text LIKE '%unassailable%' OR text LIKE '%bestowal%' OR text LIKE '%finest%' OR text LIKE '%outstanding%' OR text LIKE '%super%' OR text LIKE '%prime%' OR text LIKE '%matchless%' OR text LIKE '%unrivaled%' OR text LIKE '%perfect%' OR text LIKE '%intact%' OR text LIKE '%fair%' OR text LIKE '%vindicated%' OR text LIKE '%watched%' OR text LIKE '%hale%' OR text LIKE '%well%' OR text LIKE '%healthy%' OR text LIKE '%watchful%' OR text LIKE '%vigilant%' OR text LIKE '%cagey%' OR text LIKE '%attentive%' OR text LIKE '%prudent%' OR text LIKE '%concerned%' OR text LIKE '%apprehensive%' OR text LIKE '%rigorous%' OR text LIKE '%honest%' OR text LIKE '%benefit%' OR text LIKE '%aid%' OR text LIKE '%support%' OR text LIKE '%helping hand%' OR text LIKE '%cooperation%' OR text LIKE '%like%' OR text LIKE '%interested%' OR text LIKE '%guidance%' OR text LIKE '%succor%' OR text LIKE '%assist%' OR text LIKE '%sophisticated%' OR text LIKE '%exceptional%' OR text LIKE '%prominent%' OR text LIKE '%superior%' OR text LIKE '%preferred%' OR text LIKE '%effective%' OR text LIKE '%efficient%' OR text LIKE '%restored%'  ");
			JavaSchemaRDD count1 = sqlContext.sql("SELECT  COUNT(*) AS c FROM tweetTable "
					+ "WHERE text LIKE '%insecure%' OR text LIKE '%worst%' OR text LIKE '%unhappy%' OR text LIKE '%bad%' OR text LIKE '%unprotected%' OR text LIKE '%theft%' OR text LIKE '%shaky%' OR text LIKE '%unassured%' OR text LIKE '%vague%' OR text LIKE '%touchy%' OR text LIKE '%careless%' OR text LIKE '%extroverted%' OR text LIKE '%uncareful%' OR text LIKE '%unsuspicious%' OR text LIKE '%rash%' OR text LIKE '%bold%' OR text LIKE '%incautious%' OR text LIKE '%harmful%' OR text LIKE '%poisionous%' OR text LIKE '%complain%' OR text LIKE '%dangerous%' OR text LIKE '%unreliable%' OR text LIKE '%precarious%' OR text LIKE '%unconfident%' OR text LIKE '%poor%' OR text LIKE '%corruption%' OR text LIKE '%corrupt%' OR text LIKE '%worthless%' OR text LIKE '%cruel%' OR text LIKE '%sad%' OR text LIKE '%danger%' OR text LIKE '%misbehaving%' OR text LIKE '%decline%' OR text LIKE '%deceive%' OR text LIKE '%inferior%' OR text LIKE '%insignificant%' OR text LIKE '%unskilled%' OR text LIKE '%fake%' OR text LIKE '%evil%' OR text LIKE '%mean%' OR text LIKE '%vile%' OR text LIKE '%wicked%' OR text LIKE '%immoral%' OR text LIKE '%froged%' OR text LIKE '%disappointed%' OR text LIKE '%unacceptable%' OR text LIKE '%second-rate%' OR text LIKE '%gross%' OR text LIKE '%rough%' OR text LIKE '%lousy%' OR text LIKE '%cheap%' OR text LIKE '%stink%' OR text LIKE '%fuck%' OR text LIKE '%horrible%' OR text LIKE '%junky%' OR text LIKE '%bummer%' OR text LIKE '%inefficient%' OR text LIKE '%awful%' OR text LIKE '%faulty%' OR text LIKE '%irritate%' OR text LIKE '%atrocious%' OR text LIKE '%lag%' OR text LIKE '%lie%' OR text LIKE '%malignant%' OR text LIKE '%malign%' OR text LIKE '%rough%' OR text LIKE '%ugly%' OR text LIKE '%robbery%' OR text LIKE '%piracy%' OR text LIKE '%fraud%' OR text LIKE '%cheat%' OR text LIKE '%mugging%' OR text LIKE '%extortion%'");

			List<Row> positive = count.collect();
			String positive12 = positive.toString();
			String positive1 = positive12.substring(positive12.indexOf("[") + 2, positive12.indexOf("]"));

			List<Row> negative = count1.collect();
			String negative12 = negative.toString();
			String negative1 = negative12.substring(negative12.indexOf("[") + 2, negative12.indexOf("]"));

			fw.append("Words");
			fw.append(',');
			fw.append("Count");
			fw.append("\n");
			fw.append("Secure");
			fw.append(',');
			fw.append(positive1);
			fw.append("\n");
			fw.append("Insecure");
			fw.append(',');
			fw.append("-" + negative1);
			fw.append("\n");
			fw.close();

		} catch (Exception exp) {
		}

	}
	
/*-------------RDDQuery 3:Service wise top 7 Banks----------------------------*/
	
	public void RDDQuery3() {
		String pathToFile = url.toString();

		SparkConf conf = new SparkConf().setAppName("Bank Analyisis").setMaster("local[*]");

		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaSQLContext sqlContext = new JavaSQLContext(sc);

		JavaSchemaRDD tweets = sqlContext.jsonFile(pathToFile);

		tweets.registerAsTable("tweetTable");

		tweets.printSchema();

		nbTweetByTweets(sqlContext);

		sc.stop();

	}

	private void nbTweetByTweets(JavaSQLContext sqlContext) {

		try {
			File outputFile = new File("C:/Users/dhema/workspace/TwitterAnalysis/WebContent/RDDQuery3.csv");

			FileWriter fw = new FileWriter(outputFile);

			JavaSchemaRDD count = sqlContext.sql("SELECT user.name, user.statuses_count AS c FROM tweetTable "
					+ "WHERE user.name LIKE '%Bank' " + "ORDER BY c");

			List<org.apache.spark.sql.api.java.Row> rows = count.collect();

			Collections.reverse(rows);

			String rows123 = rows.toString();

			String[] array = rows123.split("],");

			System.out.println(rows123);

			fw.append("Name");
			fw.append(',');
			fw.append("Count");
			fw.append("\n");

			for (int i = 0; i < 25; i++) {
				if (i == 0) {
					fw.append(array[0].substring(2));
					fw.append(',');
					fw.append("\n");
				} else {
					fw.append(array[i].substring(2));
					fw.append(',');
					fw.append("\n");
				}
			}

			fw.close();

		} catch (Exception exp) {
		}

	}
	
}