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

@RestController
public class WeeiaCalendarGetter {
	@RequestMapping(method = RequestMethod.GET)
	public String getWeeiaCalendar(@RequestParam("year") String year, @RequestParam("month") String month) {
		//jsoup beginners guide taken from https://jsoup.org/cookbook/input/load-document-from-url
		try {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=");
			stringBuilder.append(year);
			stringBuilder.append("&miesiac=");
			if (month.length() == 1) {
				month = "0" + month;
			}
			stringBuilder.append(month);
			stringBuilder.append("&lang=1");
			Document doc = Jsoup.connect(stringBuilder.toString()).get();
			Elements tds = doc.select("td");
			for (Element td : tds) {
				if(td.attr("class").equals("active")){
					System.out.println(td);
					/* this is how it is currently printed
<td class="active"><a class="active" href="https://facebook.com/events/s/akcja-integracja-odkryj-z-nami/3041545625959235/?ti=as">1</a>
 <div style="position: relative">
  <div class="calendar-text">
   <div class="InnerBox">
    <div>
     Akcja Integracja Online
    </div>
   </div>
   <div class="Indicator"></div>
  </div>
 </div></td>*/
				}
			}
			// TODO: 10.11.2020 take those element tds and make them into VEVENTs so i can build a calendar
			System.out.println("Test");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "test";
	}

}
