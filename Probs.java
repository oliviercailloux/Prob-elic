/open PRINTING

import com.google.common.math.IntMath;
import com.google.common.math.BigIntegerMath;

public BigInteger getNum(int alpha, int gamma, int delta) {
	final BigInteger b1 = BigIntegerMath.binomial(alpha + gamma + delta + 2, delta + 1);
	final BigInteger b2 = BigIntegerMath.binomial(alpha + delta + 1, delta + 1);
	final BigInteger diff = b1.subtract(b2);
	final BigInteger num = BigInteger.valueOf(gamma).multiply(diff);
	return num;
}

public long getNumLoop(int alpha, int gamma, int delta) {
	long num = 0;
	for (int i = 0; i <= delta; ++i) {
		final int prob = i + gamma + 1;
		final int fact1 = IntMath.binomial(i + gamma, gamma - 1);
		assert fact1 != Integer.MAX_VALUE : "f1";
		final int fact2 = IntMath.binomial(alpha + delta - i, alpha);
		assert fact2 != Integer.MAX_VALUE : "f2";
		num += ((long) prob) * fact1 * fact2;
		assert num >= 0 : "neg looping: " + prob + ", " + fact1 + ", " + fact2;
	}
	return num;
}

public BigInteger getDenom(int alpha, int gamma, int delta) {
	final int d1 = alpha + gamma + delta + 2;
	final BigInteger b1 = BigIntegerMath.binomial(alpha + gamma + delta + 1, delta + 1);
	final BigInteger b2 = BigIntegerMath.binomial(alpha + delta + 1, delta + 1);
	final BigInteger diff = b1.subtract(b2);
	final BigInteger denom = BigInteger.valueOf(d1).multiply(diff);
	return denom;
}

public void printFrac(int alpha, int gamma, int delta) {
	final BigInteger num = getNum(alpha, gamma, delta);
	final BigInteger denom = getDenom(alpha, gamma, delta);
	final double frac = num.doubleValue() / denom.doubleValue();
	println(alpha);
	println(gamma);
	println(delta);
	println(num);
	println(denom);
	println(frac);
}

public void printBunch(int alpha, int gamma) {
	printFrac(alpha, gamma, 0);
	printFrac(alpha, gamma, 1);
	printFrac(alpha, gamma, 2);
	printFrac(alpha, gamma, 3);
	printFrac(alpha, gamma, 10);
	printFrac(alpha, gamma, 100);
	printFrac(alpha, gamma, 1000);
	printFrac(alpha, gamma, 10000);
}

printBunch(1, 3);

