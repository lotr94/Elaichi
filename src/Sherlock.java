
public class Sherlock extends Cryptsy{
	
	private double thresholdQuantity;
	private Market [] viableCurrencies;
	
	//set the threshold quantity based on the amount being transferred (I just picked 20000 for the fuck of it, we'll need to analyze it to find a more appropriate formula 
	void setThreshold(double amount){
		thresholdQuantity = amount*20000;
	}
	
	//removes currencies that have a traded volume too low to be considered
	void trimTheHerd() throws CryptsyException{
		Market[] markets = getMarkets();
		viableCurrencies = new Market[markets.length]; int x = 0;
		for (int i = 0; i < markets.length; i++) {
			if(markets[i].current_volume >= thresholdQuantity) {
				viableCurrencies[x] = markets[i];
				x++;
			}
		}
	}
	
	//picks currency most likely to rise in price
	int currencySelector() {
		return 0;
	}
	
}
