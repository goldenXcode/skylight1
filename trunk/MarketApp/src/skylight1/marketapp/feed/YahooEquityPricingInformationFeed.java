package skylight1.marketapp.feed;

import android.util.Log;
import skylight1.marketapp.EquityTimeSeries;
import skylight1.marketapp.model.EquityPricingInformation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class YahooEquityPricingInformationFeed extends AbstractEquityPricingInformation {
    private static final String TAG = YahooEquityPricingInformationFeed.class.getSimpleName();


    /*
     * Retrieve the basic information that is displayed on a watch list page.
     *  symbol, name, last price, market cap, price change, percent change
     *
     * @param aTicker The ticker symbol to request
     *
     */
    public void getBasicTickerInfo(String aTicker) {
        //TODO: How to return the information?  Create an new class?


        /*
        * s = symbol
        * n = name
        * l1 = last price
        * j1 = market capitalization
        * c - change and percent change
        */
        String url = "http://download.finance.yahoo.com/d/quotes.csv?s="
                + aTicker
                + "&f=snl1j1c";
//    Log.i(TAG,url);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            //
            // Loop over Yahoo response and extract pricing information

            while ((line = reader.readLine()) != null)

            {
                // AAPL",245.29,245.10,124.55,272.46,+12.76
                String[] parts = line.split(",");
                String ticker = parts[0].replace("\"", ""); // replaceAll didn't work!! Will do regex later
                String[] change = parts[4].replace("\"", "").split("-");
                String priceChangeStr = change[0].trim();
                String percentChangeStr = change[1].trim();
                Log.i(TAG, "(ticker,last)=>(" + ticker + "," + parts[1] + "," + parts[2]
                        + "," + parts[3]
                        + "," + priceChangeStr
                        + "," + percentChangeStr
                        + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YahooEquityPricingInformationFeed() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Set<EquityPricingInformation> setOfEquityPricingInformation = new HashSet<EquityPricingInformation>();

                Log.i(YahooEquityPricingInformationFeed.class.getName(), "Yahoo we are");

                try {
                    // Get all observed tickers
                    Set<String> allTickers = getTickers();

                    // return if no observed tickers
                    Log.i(TAG, "Observed tickers:" + allTickers);
                    if (allTickers.isEmpty()) {
                        Log.i(TAG, "No observed tickers");
                        return;
                    }

                    // Contact Yahoo for pricing
                    StringBuffer tickerList = new StringBuffer();
                    for (String ticker : allTickers) {
                        if (tickerList.length() != 0) {
                            tickerList.append(",");
                        }
                        tickerList.append(ticker);
                    }

                    String url = "http://download.finance.yahoo.com/d/quotes.csv?s="
                            + tickerList.toString()
                            + "&f=sb2b3jkm6";
                    Log.i(TAG, url);
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    //
                    // Loop over Yahoo response and extract pricing information

                    while ((line = reader.readLine()) != null) {
                        // AAPL",245.29,245.10,124.55,272.46,+12.76
                        String[] parts = line.split(",");
                        String ticker = parts[0].replace("\"", ""); // replaceAll didn't work!! Will do regex later

                        Log.i(TAG, "TICKER:" + ticker);
                        Log.i(TAG, line);
                        EquityPricingInformation information = new EquityPricingInformation(ticker, ticker,
                                new BigDecimal(parts[1]), null, new Date(), new BigDecimal(parts[2]),
                                null, new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));

                        Log.i(TAG, "Object not NULL?" + information);
                        setOfEquityPricingInformation.add(information);
                    }
                    Log.i(TAG, "Notfiying everyone");
                    notifyObservers(setOfEquityPricingInformation);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to get stock quotes", e);
                }
            }
        }, 1000, 5000);
    }

    /*
     *
     *
     */
    public List<EquityTimeSeries> getPriceHistoryForTicker(String aTicker) {
        List<EquityTimeSeries> aList = new ArrayList<EquityTimeSeries>();

        String url = "http://ichart.finance.yahoo.com/table.csv?s=" + aTicker + "&a=5&b=15&c=2010&d=05&e=29&f=2010&g=d";
        System.out.println(url);

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            //
            // Loop over Yahoo response and extract pricing information

            line = reader.readLine(); // skip first line

            while ((line = reader.readLine()) != null) {

                // AAPL",245.29,245.10,124.55,272.46,+12.76
                String[] parts = line.split(",");
//                System.out.println(parts[0] + "," + parts[1]);
                double open = Double.parseDouble(parts[1]);
                double high = Double.parseDouble(parts[2]);
                double low = Double.parseDouble(parts[3]);
                double close = Double.parseDouble(parts[4]);
                int volume = Integer.parseInt(parts[5]);

                aList.add(new EquityTimeSeries(parts[0], open, high, low, close, volume));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aList;
    }

    /*
     * Helper routine for getPriceHistoryForTicker()
     */
    public void showPrices(List<EquityTimeSeries> aList) {

        for (EquityTimeSeries ts : aList) {
            ts.dumpData();
        }
    }

    // =========
}