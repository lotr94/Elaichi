
public class EURTrader extends BTCE implements Trader {
	
	public double getCurrentPrice() {
		try {
			return getTicker("btc_eur").avg;
		} catch (BTCEException e) {
			return -1;
		} 
		//there are a lot of different values in the ticker, I've opted for the average although the most recent buy orders may be more relevant
	}
	
	public boolean createBuyOrder(double price, double quantity) {
		try {
			trade("btc_eur", "buy", price, quantity);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}

	public boolean createSellOrder(double price, double quantity) {
		try {
			trade("btc_eur", "sell", price, quantity);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}

	public boolean terminateOrder(int orderId) {
		try {
			cancelOrder(orderId);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}
}