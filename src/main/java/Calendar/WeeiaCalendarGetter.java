package Calendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeeiaCalendarGetter {
	@RequestMapping(method = RequestMethod.GET)
	public String getWeeiaCalendar(@RequestParam("year") String year, @RequestParam("month") String month) {
		//jsoup beginners guide taken from https://jsoup.org/cookbook/input/load-document-from-url
		try {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=").append(year).append("&miesiac=");
			if (month.length() == 1) {
				month = "0" + month;
			}
			stringBuilder.append(month).append("&lang=1");
			Document doc = Jsoup.connect(stringBuilder.toString()).get();
			Elements tds = doc.select("td");
			List<DataHolderClass> dataHolderClasses = new ArrayList<>();
			for (Element td : tds) {
				if(td.attr("class").equals("active")){
					Elements p = td.getElementsByTag("p");
					Element a = td.child(0);
					String url = a.attr("href");

					dataHolderClasses.add(new DataHolderClass(year, month, a.text(), url, p.text()));
				}
			}

			/*
			Create calendar and input some basic data inside most of code below was taken from:
			https://ical4j.github.io/ical4j-user-guide/examples/
			 */
			Calendar calendar = new Calendar();
			calendar.getProperties().add(new ProdId("-//Bartosz Kowalczyk//iCal4j 1.0//PL"));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);

			// Generate a UID for the event..
			UidGenerator ug = () -> new Uid("test@example.com");

			//Creating and filling ics file with our calendar
			FileOutputStream fout = new FileOutputStream("mycalendar.ics");

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(calendar, fout);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "test";
	}
	class DataHolderClass {
		private String month;
		private String day;
		private String year;
		private String url;
		private String title;

		public DataHolderClass(String year, String month, String day, String url, String title) {
			this.month = month;
			if (day.length() == 1) {
				this.day = "0" + day;
			} else
				this.day = day;
			this.year = year;
			this.url = url;
			this.title = title;
		}
	}

}
