
public interface Trader {
	
	public double getCurrentPrice();
	
	public boolean createBuyOrder(double price, double quantity);

	public boolean createSellOrder(double price, double quantity);

	public boolean terminateOrder(int orderId);
}
