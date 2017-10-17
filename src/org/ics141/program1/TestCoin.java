package org.ics141.program1;


public class TestCoin {

	public static void main(String[] args) {
		int totalHeads = 0;
		int totalValue = 0;
		
		MonetaryCoin mCoin = new MonetaryCoin(1);
		mCoin.flip();
		System.out.println(mCoin);
		if(mCoin.isHeads()) {
			totalHeads += 1;
		}
		totalValue += mCoin.getValue();

		mCoin = new MonetaryCoin(10);
		mCoin.flip();
		System.out.println(mCoin);
		if(mCoin.isHeads()) {
			totalHeads += 1;
		}
		totalValue += mCoin.getValue();
		
		mCoin = new MonetaryCoin(2);
		mCoin.flip();
		System.out.println(mCoin);
		if(mCoin.isHeads()) {
			totalHeads += 1;
		}
		totalValue += mCoin.getValue();
		
		mCoin = new MonetaryCoin(3);
		mCoin.flip();
		System.out.println(mCoin);
		if(mCoin.isHeads()) {
			totalHeads += 1;
		}
		totalValue += mCoin.getValue();
		
		System.out.println("The total heads is " + totalHeads);
		System.out.println("The total value is " + totalValue);
	}

}
