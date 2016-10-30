

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

@WebServlet("/DataframeQueries")
public class DataframeQueries extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	URL url = getClass().getResource("BankingTweets.txt");


    public DataframeQueries() {
        super();
     
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
		        DataframeQuery1();
		       
		       response.sendRedirect("DataframeQuery1.html");
				break;
				
			case 2: 
				DataframeQuery2();
		       
		       response.sendRedirect("DataframeQuery2.html");
				break;
		
			case 3: 
				DataframeQuery3();
		       
		       response.sendRedirect("DataframeQuery3.html");
				break;
				
			default: JOptionPane.showMessageDialog(null, "Invalid Option please Enter from 1 to 8");
						break;
			}
	}
	
	public void DataframeQuery1()
	{
		
        String pathToFile = url.toString();	 
       
         SparkConf conf = new SparkConf();
         conf.setAppName("Spark MultipleContest Test");
         conf.set("spark.driver.allowMultipleContexts", "true");
         conf.setMaster("local");
         
         JavaSparkContext sc = new JavaSparkContext(conf);
 
         SQLContext sqlContext = new SQLContext(sc);

         DataFrame tweets = sqlContext.read().json(pathToFile);
         
           tweets.registerTempTable("tweetTable");
           
      DataFrame followers = sqlContext.sql("SELECT  user.name, user.followers_count AS c FROM tweetTable "
					+ "WHERE user.name LIKE '%Bank'" + "ORDER BY c DESC");
      
           Row[] rows = followers.collect();
           
         try
         {
        	File outputFile = new File("C:/Users/dhema/workspace/TwitterBankingAnalysis/WebContent/DataFrameQuery1.csv");

		    FileWriter fw = new FileWriter(outputFile);
		    

			fw.append("Name");
			fw.append(',');
			fw.append("Count");
			fw.append("\n");
           
           for (int i=0;i<12;i++) {
        	   
        	   fw.append(rows[i].get(0).toString());
			   fw.append(',');
			   fw.append(rows[i].get(1).toString());
			   fw.append("\n");
             
             }
           
           fw.close();
             
         }
         catch(Exception e)
         {
        	 
         }
         
         
		
        sc.stop();
        
	}
	
	public void DataframeQuery2()
	{
		 String pathToFile = url.toString();	 
	       
         SparkConf conf = new SparkConf();
         conf.setAppName("Spark MultipleContest Test");
         conf.set("spark.driver.allowMultipleContexts", "true");
         conf.setMaster("local");
         
         JavaSparkContext sc = new JavaSparkContext(conf);
 
         SQLContext sqlContext = new SQLContext(sc);

         DataFrame tweets = sqlContext.read().json(pathToFile);
         
           tweets.registerTempTable("tweetTable");
           
         DataFrame times = sqlContext.sql("SELECT created_at, COUNT(*) AS c FROM tweetTable " + "Group By created_at " + "order by c DESC");
      
           Row[] rows = times.collect();
           
         try
         {
        	File outputFile = new File("C:/Users/dhema/workspace/TwitterBankingAnalysis/WebContent/DataFrameQuery2.csv");

		    FileWriter fw = new FileWriter(outputFile);
		    

			fw.append("Time");
			fw.append(',');
			fw.append("Count");
			fw.append("\n");
           
           for (int i=0;i<8;i++) {
        	   
        	   fw.append(rows[i].get(0).toString());
			   fw.append(',');
			   fw.append(rows[i].get(1).toString());
			   fw.append("\n");
             
             }
           
           fw.close();
             
         }
         catch(Exception e)
         {
        	 
         }
         
         
		
        sc.stop();
	}
	
	public void DataframeQuery3()
	{
		 String pathToFile = url.toString();	 
	       
         SparkConf conf = new SparkConf();
         conf.setAppName("Spark MultipleContest Test");
         conf.set("spark.driver.allowMultipleContexts", "true");
         conf.setMaster("local");
         
         JavaSparkContext sc = new JavaSparkContext(conf);
 
         SQLContext sqlContext = new SQLContext(sc);

         DataFrame tweets = sqlContext.read().json(pathToFile);
         
           tweets.registerTempTable("tweetTable");
           
      DataFrame totalcount  = sqlContext.sql("SELECT COUNT(*) AS c FROM tweetTable " + "WHERE text LIKE '%Bank%'");
      
      DataFrame count = sqlContext.sql("SELECT COUNT(*) AS c1 FROM tweetTable " + "WHERE text LIKE '%JPMorgan%' OR text LIKE '%Wells Fargo%' OR text LIKE '%Citibank%' OR text LIKE '%PNC Bank%' OR text LIKE '%Bank of New York Mellon Corp%' OR text LIKE '%Capital One%' OR text LIKE '%TD Bank%' OR text LIKE '%HSBC Bank%' OR text LIKE '%Chase Bank%' OR text LIKE '%Goldman Sachs Bank%' OR text LIKE '%Manufacturers and Traders Trust%' OR text LIKE '%Santander Bank%' OR text LIKE '%Deutsche Bank%' OR text LIKE '%First Republic Bank%' OR text LIKE '%City National Bank%' OR text LIKE '%Morgan Stanley Private Bank%' OR text LIKE '%Signature Bank%' OR text LIKE '%BNY Mellon%' OR text LIKE '%BankUnited%' OR text LIKE '%Valley National Bank%' OR text LIKE '%Israel Discount Bank%' OR text LIKE '%Sterling National Bank%' OR text LIKE '%Safra National Bank%' OR text LIKE '%Mizuho Bank%' OR text LIKE '%Ally Bank%' OR text LIKE '%CIT BANK%' OR text LIKE '%iGObanking.com%' OR text LIKE '%AloStar Bank of Commerce%' OR text LIKE '%MySavingsDirect%' OR text LIKE '%American Express Bank%' OR text LIKE '%Discover Bank%' OR text LIKE '%Nationwide Bank%' OR text LIKE '%Arvest Bank%' OR text LIKE '%EverBank%' OR text LIKE '%Bank of America%' OR text LIKE '%First Midwest Bank%' OR text LIKE '%Sallie Mae Bank%' OR text LIKE '%Simple Bank%' OR text LIKE '%Bank of Internet%' OR text LIKE '%Bank5 Connect%' OR text LIKE '%First Niagara Bank%' OR text LIKE '%Barclays Bank%' OR text LIKE '%FNBO Direct%' OR text LIKE '%Great Western Bank%' OR text LIKE '%U.S. Bank%' OR text LIKE '%Wells Fargo Bank%' OR text LIKE '%AB Bank%' OR text LIKE '%ABN Amro Bank%' OR text LIKE '%Abu Dhabi Commercial Bank%' OR text LIKE '%Allahabad Bank%' OR text LIKE '%American Express Banking Corporation%' OR text LIKE '%Andhra Bank%' OR text LIKE '%Antwerp Diamond Bank%' OR text LIKE '%Axis Bank%' OR text LIKE '%Bank of Bahrain & Kuwait%' OR text LIKE '%Bank of Ceylon%' OR text LIKE '%Bank of India%' OR text LIKE '%Bank of Nova Scotia%' OR text LIKE '%Bank of Punjab%' OR text LIKE '%Bank of Rajasthan%' OR text LIKE '%Bank of Tokyo Mitsubishi UFJ%' OR text LIKE '%Barclays%' OR text LIKE '%BNP Paribas%' OR text LIKE '%Calyon Bank%' OR text LIKE '%Chinatrust%' OR text LIKE '%City Union Bank%' OR text LIKE '%Corporation Bank%' OR text LIKE '%Cosmos Bank%' OR text LIKE '%DBS Bank%' OR text LIKE '%Dena Bank%' OR text LIKE '%Development Credit Bank Limited%' OR text LIKE '%Export-Import%' OR text LIKE '%Federal Bank%' OR text LIKE '%Global Trust Bank%' OR text LIKE '%ICICI Bank%' OR text LIKE '%IDBI Bank%' OR text LIKE '%Indian Overseas Bank%' OR text LIKE '%IndusInd Bank Limited%' OR text LIKE '%JSC VTB Bank%' OR text LIKE '%Krung Thai Bank%' OR text LIKE '%Mandvi Co-operative Bank Ltd.%' OR text LIKE '%Mashreq Bank%' OR text LIKE '%National Bank for Agriculture and Rural Development%' OR text LIKE '%Nedungadi Bank Ltd%' OR text LIKE '%Oman International Bank%' OR text LIKE '%Oriental Bank of Commerce%' OR text LIKE '%Punjab & Sind Bank%' OR text LIKE '%Reserve Bank of India%' OR text LIKE '%Shinhan Bank%' OR text LIKE '%Societe Generale%' OR text LIKE '%Sonali Bank%' OR text LIKE '%Standard Chartered Bank%' OR text LIKE '%State Bank of Mauritius%' OR text LIKE '%State Bank of Travancore%' OR text LIKE '%Syndicate Bank%' OR text LIKE '%TimesBank%' OR text LIKE '%UBS AG%' OR text LIKE '%UCO Bank%' OR text LIKE '%Union Bank of India%' OR text LIKE '%Vysya Bank%' OR text LIKE '%Lloyds Bank%'");

      DataFrame count1 = sqlContext.sql("SELECT COUNT(*) AS c2 FROM tweetTable " + "WHERE text LIKE '%JPMorgan%' OR text LIKE '%Goldman Sachs%' OR text LIKE '%Bank of America%' OR text LIKE '%Morgan Stanley%' OR text LIKE '%Citigroup%' OR text LIKE '%Deutsche Bank%' OR text LIKE '%Credit Suisse%' OR text LIKE '%Barclays%' OR text LIKE '%UBS%' OR text LIKE '%Wells Fargo%' OR text LIKE '%RBC Capital Markets%' OR text LIKE '%Jefferies Group%' OR text LIKE '%BNP Paribas%' OR text LIKE '%Nomura Holdings%' OR text LIKE '%Mizuho%' OR text LIKE '%Lazard%' OR text LIKE '%Sumitomo Mitsui%' OR text LIKE '%Mitsubishi UFJ%' OR text LIKE '%Societe Generale%' OR text LIKE '%HSBC%' OR text LIKE '%Allen & Company%' OR text LIKE '%BBY Ltd%' OR text LIKE '%Blackstone Group%' OR text LIKE '%Brown Brothers Harriman%' OR text LIKE '%Brown, Shipley & Co.%' OR text LIKE '%BTG Pactual%' OR text LIKE '%Cain Brothers%' OR text LIKE '%Cantor Fitzgerald%' OR text LIKE '%Canaccord Financial Inc%' OR text LIKE '%Capstone Partners%' OR text LIKE '%Centerview Partners%' OR text LIKE '%China International Capital Corporation%' OR text LIKE '%CITIC Securities%' OR text LIKE '%Close Brothers Group%' OR text LIKE '%CLSA%' OR text LIKE '%Cowen Group%' OR text LIKE '%C.W. Downer & Co.%' OR text LIKE '%Daewoo Securities%' OR text LIKE '%Defoe Fournier & Cie.%' OR text LIKE '%Duff & Phelps%' OR text LIKE '%Europa Partners%' OR text LIKE '%Evercore Partners%' OR text LIKE '%FBR Capital Markets%' OR text LIKE '%Financo%' OR text LIKE '%Foros Group%' OR text LIKE '%Gleacher & Co.%' OR text LIKE '%Greenhill & Co.%' OR text LIKE '%Guggenheim Partners%' OR text LIKE '%Guosen Securities%' OR text LIKE '%Houlihan Lokey%' OR text LIKE '%Imperial Capital%' OR text LIKE '%Investec%' OR text LIKE '%Investment Technology Group%' OR text LIKE '%Janney Montgomery Scott%' OR text LIKE '%Keefe, Bruyette & Woods%' OR text LIKE '%Ladenburg Thalmann%' OR text LIKE '%Lincoln International%' OR text LIKE '%M.M.Warburg & CO%' OR text LIKE '%Marathon Capital%' OR text LIKE '%Mediobanca%' OR text LIKE '%Miller Buckfire & Co.%' OR text LIKE '%Moelis & Company%' OR text LIKE '%Morgan Keegan & Company%' OR text LIKE '%N M Rothschild & Sons%' OR text LIKE '%Needham and Company%' OR text LIKE '%Newedge%' OR text LIKE '%Oppenheimer & Co.%' OR text LIKE '%Panmure Gordon%' OR text LIKE '%Park Lane%' OR text LIKE '%Perella Weinberg Partners%' OR text LIKE '%Peter J. Solomon Company%' OR text LIKE '%Piper Jaffray%' OR text LIKE '%Pottinger%' OR text LIKE '%Raymond James%' OR text LIKE '%Robert W. Baird & Company%' OR text LIKE '%Sagent Advisors%' OR text LIKE '%Sanford Bernstein%' OR text LIKE '%Stephens Inc.%' OR text LIKE '%Stone Key Partners%' OR text LIKE '%Thomas Weisel Partners%' OR text LIKE '%Vermilion Partners%' OR text LIKE '%Wedbush Securities%' OR text LIKE '%William Blair & Company%' OR text LIKE '%Kotak Mahindra Bank%' OR text LIKE '%YES Bank%'");

 	  
      
      Row[] totalrows = totalcount.collect();
      Row[] rows = count.collect();
      Row[] rows1 = count1.collect();
      
      String totalrows11= totalrows[0].get(0).toString();
      String rows11= rows[0].get(0).toString();
      String rows111= rows1[0].get(0).toString();
      
      System.out.println(totalrows11);
      System.out.println(rows11);
      System.out.println(rows111);
      
      int totalrows123 = Integer.parseInt(totalrows11);
 	 int commercial123 = Integer.parseInt(rows11);
 	 int investment123 = Integer.parseInt(rows111);
 	 int foreigncount = totalrows123 - (commercial123+investment123);
 	 
     double commercialPercentage = ((commercial123 * 100) / totalrows123);
	 double investmentPercentage = ((investment123 * 100) / totalrows123);
	 float foreignPercentage = ((foreigncount * 100) / totalrows123);
	
	 System.out.println(commercialPercentage);
	 System.out.println(investmentPercentage);
	 System.out.println(foreignPercentage);
	
	 String commercialPercentage1 = Double.toString(commercialPercentage);
	 String investmentPercentage1 = Double.toString(investmentPercentage);
	 String foreignPercentage1 = Float.toString(foreignPercentage);
           
         try
         {
        	File outputFile = new File("C:/Users/dhema/workspace/TwitterBankingAnalysis/WebContent/DataFrameQuery3.csv");

		    FileWriter fw = new FileWriter(outputFile);
		    

		    fw.append("TweetStatus");
			 fw.append(',');
			 fw.append("Percentage");
			 fw.append("\n");
			 fw.append("CommercialBanks%");
			 fw.append(',');
			 fw.append(commercialPercentage1);
			 fw.append("\n");
			 fw.append("InvestmentBanks%");
			 fw.append(',');
			 fw.append(investmentPercentage1);
			 fw.append("\n");
			 fw.append("others%");
			 fw.append(',');
			 fw.append(foreignPercentage1);
			 fw.append("\n");
			
			 fw.close();
             
         }
         catch(Exception e)
         {
        	 
         }
         
         
		
        sc.stop();
	}
}
