package Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
