import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;


public class btcetester extends BTCE {

	private BTCE btce ;
	
	private static Properties load(File pfile) throws Exception {
		FileInputStream pfs = new FileInputStream(pfile.getAbsoluteFile()) ;
		Properties properties = new Properties() ;
		properties.load(pfs) ;
		return properties ;
	}
	
	
	public void setUp() throws Exception {
		// Note: Keys below do not have trade or withdraw permissions...only info
		String userdir = System.getProperty("user.dir") ;
		//Properties p = load(new File(userdir,"config.properties")) ;
		//String key = p.getProperty("btce.key") ;
		//String secret = p.getProperty("btce.secret") ;
		//int request_limit = Integer.parseInt(p.getProperty("btce.request_limit")) ;
		//int auth_request_limit = Integer.parseInt(p.getProperty("btce.auth_request_limit")) ;
		int request_limit = 15000;
		int auth_request_limit = 1000;
		btce = new BTCE() ;
		btce.setAuthKeys("5DSBVPHQ-BXIK6GY7-EANC006X-DA1BOT0R-SJPWKF3F", "4dcf28fc6bfd783632cb9ffd11c6fe8027c14d946708d8910fbae6b9244601b7") ;
		btce.setAuthRequestLimit(auth_request_limit) ;
		btce.setRequestLimit(request_limit) ;
	}

	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public void testActiveOrders() throws Exception {
		OrderList order_list = btce.getActiveOrders() ;
		if( order_list != null && order_list.info != null )
		for(OrderListOrder order:order_list.info.orders) {
			System.out.println(order) ;
		} else System.out.println("no orders") ;
	}
	
	 
	public void testTrade() throws Exception {
		// get hit at 1.014
		String trade_type = BTCE.TradeType.SELL ;
		double price = 98.26 ;
		String pair = BTCE.Pairs.BTC_USD ;
		Info info = btce.getInfo() ;
		double amount = 0, funds = 0 ;
		if( trade_type.compareToIgnoreCase(BTCE.TradeType.BUY)==0 ) {
			funds = info.info.funds.usd ;
			amount = round(funds / (price*1.002),4) ;
		}else if( trade_type.compareToIgnoreCase(BTCE.TradeType.SELL)==0 ) {
			amount = info.info.funds.btc ;
		}
		System.out.println(trade_type.toUpperCase()+" @ Price: "+price+" Amount: "+amount) ;
		Trade trade = btce.trade(pair,trade_type,price,amount) ;
		System.out.println(trade) ;
	}
	
	 
	public void testCancelOrder() throws Exception {
		int order_id = 31770453 ;
		CancelOrder cancel_order = btce.cancelOrder(order_id) ;
		System.out.println(cancel_order) ;
	}
	
	 
	public void testInfo() throws Exception {
		Info info = btce.getInfo() ;
		System.out.println(info) ;
	}

	 
	public void testTransactionHistory() throws Exception {
		TransactionHistory transaction_history = btce.getTransactionHistory() ;
		System.out.println(transaction_history.toString()) ;
	}
	
	 
	public void testTradeHistory() throws Exception {
		TradeHistory trade_history = btce.getTradeHistory() ;
		for(TradeHistoryOrder trade:trade_history.info.trades) {
			System.out.println(trade) ;
		}
		System.out.println(trade_history.toString()) ;
	}
	
	 
	public void testOrderList() throws Exception {
		OrderList order_list = btce.getOrderList() ;
		for(OrderListOrder order:order_list.info.orders) {
			System.out.println(order) ;
		}
	}
	
	 
	public void testTicker() throws Exception {
		Ticker t = btce.getTicker(BTCE.Pairs.BTC_USD) ;
		System.out.println(t) ;
	}
	
	 
	public void testTrades() throws Exception {
		TradesDetail[] trades = btce.getTrades(BTCE.Pairs.BTC_USD) ;
		System.out.println(Arrays.toString(trades)) ;
	}
	
	private void tradesSummary(TradesDetail[] trades,String type) {
		double min=Double.MAX_VALUE, max=0, avg=0, total=0, wall=0 ;
		double minPrice=Double.MAX_VALUE, maxPrice=0, avgPrice=0, totalPrice = 0, wallPrice = 0 ;
		int cnt = 0 ;
		for(TradesDetail trade:trades) {
			if( trade.trade_type.equalsIgnoreCase(type)) {
				cnt++ ;
				total += trade.amount ;
				totalPrice += trade.price ;
				if( minPrice > trade.price ) {
					min = trade.amount ;
					minPrice = trade.price ;
				}
				if( maxPrice < trade.price ) {
					max = trade.amount ;
					maxPrice = trade.price ;
				}
				if( wall < trade.amount ) {
					wall = trade.amount ;
					wallPrice = trade.price ;
				}
			}
		}
		avg = total/cnt ;
		avgPrice = totalPrice/cnt ;
		String tran_type = type.equalsIgnoreCase("ask")?"buy":"sell" ;
		System.out.println("=========== "+type+" ("+tran_type+") summary ==================") ;
		System.out.println("Min="+min+" Price="+minPrice) ;
		System.out.println("Max="+max+" Price="+maxPrice) ;
		System.out.println("Avg="+avg+" Price="+avgPrice) ;
		System.out.println("Wal="+wall+" Price="+wallPrice) ;
		System.out.println("Ttl="+total+" Price="+totalPrice) ;
		System.out.println() ;
	}
	
	 
	public void testMarketDepth() throws Exception {
		TradesDetail[] trades = btce.getTrades(BTCE.Pairs.BTC_USD) ;
		tradesSummary(trades,"ask") ;
		tradesSummary(trades,"bid") ;
		System.out.println("===================================") ;
		for(TradesDetail trade:trades) {
			System.out.println(trade) ;
		}
	}
}