import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Notifier {

	void info(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println(item.model + "\t\t" + item.variant + "\t\t" + item.grade + "\t\t" + ( item.stattrak.matches("yes") ? "STATTRAK" : "" ) );
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Current Price:\t\t"		+ tips.getCurrentPrice());
		System.out.println("Break-Even:\t\t"		+ tips.getBreakEven());
		System.out.println("Market Value:\t\t"		+ tips.getAverageRawValue() + "\tDELTA\t" + tips.getDeltaBreakEven());
	}

	void deal(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
		
		this.info(item, tips);
		System.out.println("Potential Profit:\t"	+ tips.getPotentialProfit());
		System.out.println("Yield on Cost:\t\t"		+ tips.getYieldOnCost());

		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
	}

	void fail(Item item){

		System.out.println("");
		System.out.println("No listings found for " + item.model + " " + item.variant + " " + item.grade + ( item.stattrak.matches("yes") ? " STATTRAK" : "" ) );
	}

	void text(Item item, FinancialAdvisor tips) {

		final String to		= "NUMBER@txt.bell.ca";
		final String from	= "EMAIL@gmail.com";
		final String pswd	= "isthisevenapassword";

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("stockr.ae", pswd);
				}
			}
		);

		try {

			Message msg = new MimeMessage(session);

			String content = "";
				content += item.model + " " + item.variant;
				content += "\n";
				content += item.grade + ( item.stattrak.matches("yes") ? " STATTRAK" : "" );
				content += "\n";
				content += "Current: "		+ tips.getCurrentPrice();
				content += "\n";
				content += "BE: "		+ tips.getBreakEven();
				content += "\n";
				content += "MV: "		+ tips.getAverageRawValue();
				content += "\n";
				content += "PP: "		+ tips.getPotentialProfit();
				content += "\n";
				content += "YOC: "		+ tips.getYieldOnCost();

			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setText(content);
			Transport.send(msg);
		
		} catch (MessagingException e) {
			
			System.out.println("Failed to text: " + e);

		}

	}

}
