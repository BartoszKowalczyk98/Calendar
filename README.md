# Read Me First
Api to generate a .ics file for single month from http://www.weeia.p.lodz.pl/


If you input line in url, program retrives calendar data as html doc with jsoup from weeia.p.lodz.pl
then creates a Calendar object using Ical4j library, fills it with events from gathered data and returns .ics file as a
 response from server.

Example url to input in browser after running application on local machine:

http://localhost:8080/?year="Input_year_here"&month="input_month_here"

Replace "Input_year_here" and "input_month_here" with actuall numbers like 2020 and 03.
Api will generate an ics file and show prompt about downloading a file to chosen path.


# Example .ics file content
BEGIN:VCALENDAR

PRODID:-//Bartosz Kowalczyk//iCal4j 1.0//PL

VERSION:2.0

CALSCALE:GREGORIAN

BEGIN:VEVENT

DTSTAMP:20201124T140435Z

DTSTART;VALUE=DATE:20201001

SUMMARY:

UID:test@example.com

URL:UrlLink

END:VEVENT

END:VCALENDAR


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)


