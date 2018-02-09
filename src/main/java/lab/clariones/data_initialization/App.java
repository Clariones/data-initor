package lab.clariones.data_initialization;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
	// Map<String, String> envs = System.getenv();
	// for(Entry<String, String> entry : envs.entrySet()){
	// System.out.println(entry.getKey()+"="+ entry.getValue());
	// }
	System.out.println("Starting POC http server.....");
	String configFileName = "config/spring_main.xml";
	if (args != null && args.length > 0) {
	    configFileName = args[0].trim();
	}
	ApplicationContext context = new FileSystemXmlApplicationContext(configFileName);

	DataOperator dataOp = (DataOperator) context.getBean("data_operator");
	Date tStart = new Date();
	dataOp.startWork();
	Date tEnd = new Date();
	System.out.println("\nMigration start from: " + tStart);
	System.out.println("              end at: " + tEnd);
	System.out.println("          Total used: " + (tEnd.getTime() - tStart.getTime()) / 1000.0 + " Seconds");
    }


}