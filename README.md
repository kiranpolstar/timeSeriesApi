# timeSeriesApi

Electricity Time Series API's

addTimeSeries:
http://localhost:9004/addTimeSeries  
This api helps to add the consumption details for one meter with unique meterId.
If User tries to update the reading for the next hour in the hourlyConsumption for the same meter, then it updates the existing hourlyConsumption.


getTimeSeriesByMeterId:
http://localhost:9004/getTimeSeries?fromDate=2018-08-09&meterId=kiran1
This helps to retrieve the consumption details for meter. Here fromDate is mandatory and you supposed to provide meterId or customerId in the request.
If you pass meterId along with fromDate it gives you the consumption details for that meter for that date.


getTimeSeriesByCustomerId:
http://localhost:9004/getTimeSeries?fromDate=2018-08-09&customerId=AB33829
If you pass meterId along with fromDate it gives you the consumption details for that meter for that date.


getWeeklySummaryForMeter:
http://localhost:9004/getWeeklySummaryForMeter?meterId=kiran1
This helps to retrieve the weekly consumption for the meter.


getWeeklySummaryForCustomer:
http://localhost:9004/getWeeklySummaryForCustomer?customerId=AB33829&fromDate=2018-08-09&toDate=2018-08-19
This helps to retrieve the weekly summary for customer even if he has more than one meter during that period.
It works based on from and to date. Hence this is dynamic in behavior. You can use this to get weekly/monthly/whatever period you want to see the summary.
