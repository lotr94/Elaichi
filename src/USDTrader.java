
public class USDTrader extends BTCE implements Trader {
	
	@Override
	public double getCurrentPrice() {
		try {
			return getTicker("btc_usd").avg;
		} catch (BTCEException e) {
			return -1;
		} 
		//there are a lot of different values in the ticker, I've opted for the average although the most recent buy orders may be more relevant
	}
	
	@Override
	public boolean createBuyOrder(double price, double quantity) {
		try {
			trade("btc_usd", "buy", price, quantity);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean createSellOrder(double price, double quantity) {
		try {
			trade("btc_usd", "sell", price, quantity);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean terminateOrder(int orderId) {
		try {
			cancelOrder(orderId);
		} catch (BTCEException e) {
			return false;
		}
		return true;
	}
}
