import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

class WebPriceFinder extends PriceFinder {

    private Pattern pattern = Pattern.compile("\\$?(\\d+\\.\\d{2})");

    double findPrice(String itemUrl, Main main) {
        HttpURLConnection con = null;
        try{
            URL url = new URL(itemUrl);
            con = (HttpURLConnection) url.openConnection();
            String encoding = con.getContentEncoding();
            if (encoding == null)
                encoding = "utf-8";

            InputStreamReader reader = null;
            if("gzip".equals(encoding)){
                reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
            } else {
                reader = new InputStreamReader(con.getInputStream(), encoding);
            }

            BufferedReader in = new BufferedReader(reader);

            if (itemUrl.contains("bestbuy")) return bestBuyPattern(in);
            if (itemUrl.contains("apple")) return applePattern(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) con.disconnect();
        }

        JOptionPane.showMessageDialog(main, "Store not supported.", "Error", JOptionPane.ERROR_MESSAGE);
        return 0;
    }

    private double applePattern(BufferedReader in) throws IOException {
        String line;
        while((line = in.readLine()) != null){
            if (line.contains("\"price\":")){
                Matcher matcher = pattern.matcher(line);
                matcher.find();
                String price = matcher.group();
                return Double.parseDouble(price);
            }
        }

        return 0;
    }

    private double bestBuyPattern(BufferedReader in) throws IOException {
        String line;
        while ((line = in.readLine()) != null){
            if (line.contains("\"currentPrice\":")) {
                Matcher matcher = pattern.matcher(line);

                matcher.find();
                matcher.find();
                matcher.find();
                String price = matcher.group();
                return Double.parseDouble(price);
            }
        }
        return 0;
    }
}