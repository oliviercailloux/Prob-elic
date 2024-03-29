/open PRINTING

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import com.google.common.math.BigIntegerMath;

public BigInteger getNumFirstSum(int alpha, int gamma, int gammaPrime, int delta) {
	BigInteger num = BigInteger.ZERO;
	for (int i = 0; i <= delta; ++i) {
		final int prob = i + gamma + 1;
		final BigInteger fact1 = BigIntegerMath.binomial(i + gamma, gamma - gammaPrime);
		final BigInteger fact2 = BigIntegerMath.binomial(alpha + delta - i, alpha);
		BigInteger add = BigInteger.valueOf(prob).multiply(fact1).multiply(fact2);
		num = num.add(add);
	}
	return num;
}

public BigInteger getDenomFirstSum(int alpha, int gamma, int gammaPrime, int delta) {
	BigInteger denom = BigInteger.ZERO;
	for (int i = 0; i <= delta; ++i) {
		final BigInteger fact1 = BigIntegerMath.binomial(i + gamma, gamma - gammaPrime);
		final BigInteger fact2 = BigIntegerMath.binomial(alpha + delta - i, alpha);
		BigInteger add = fact1.multiply(fact2);
		denom = denom.add(add);
	}
	return BigInteger.valueOf(alpha + gamma + delta + 2).multiply(denom);
}

public BigInteger getNumComplicatedSum(int alpha, int gamma, int gammaPrime, int delta) {
	checkArgument(gammaPrime >= 1);
	final BigInteger rAndFact = getR(alpha, gamma + 1, gammaPrime, delta).multiply(BigInteger.valueOf(alpha + gamma + 2 - gammaPrime));
	final BigInteger bin = BigIntegerMath.binomial(alpha + gamma + delta + 1, delta + gammaPrime);
	final BigInteger binAndFact = BigInteger.valueOf(alpha + gamma + delta + 2).multiply(bin);
	final BigInteger subtr = binAndFact.subtract(rAndFact);
	return BigInteger.valueOf(gamma - gammaPrime + 1).multiply(subtr);
}

public BigInteger getDenomComplicatedSum(int alpha, int gamma, int gammaPrime, int delta) {
	checkArgument(gammaPrime >= 1);
	final BigInteger r = getR(alpha, gamma, gammaPrime, delta);
	final BigInteger bin = BigIntegerMath.binomial(alpha + gamma + delta + 1, delta + gammaPrime);
	final BigInteger subtr = bin.subtract(r);
	return BigInteger.valueOf(alpha + gamma + delta + 2).multiply(BigInteger.valueOf(alpha + gamma + 2 - gammaPrime)).multiply(subtr);
}

public BigInteger getR(int alpha, int gamma, int gammaPrime, int delta) {
	checkArgument(gammaPrime >= 1);
	BigInteger r = BigInteger.ZERO;
	for (int k = 0; k <= gammaPrime - 1; ++k) {
		final BigInteger fact1 = BigIntegerMath.binomial(gamma - gammaPrime + k, k);
		final BigInteger fact2 = BigIntegerMath.binomial(alpha + delta + gammaPrime - k, delta + gammaPrime - k);
		BigInteger add = fact1.multiply(fact2);
		r = r.add(add);
	}
	return r;
}

public BigInteger getNumGammaPrimeOne(int alpha, int gamma, int delta) {
	final BigInteger b1 = BigIntegerMath.binomial(alpha + gamma + delta + 2, delta + 1);
	final BigInteger b2 = BigIntegerMath.binomial(alpha + delta + 1, delta + 1);
	final BigInteger diff = b1.subtract(b2);
	final BigInteger num = BigInteger.valueOf(gamma).multiply(diff);
	return num;
}

public BigInteger getDenomGammaPrimeOne(int alpha, int gamma, int delta) {
	final int d1 = alpha + gamma + delta + 2;
	final BigInteger b1 = BigIntegerMath.binomial(alpha + gamma + delta + 1, delta + 1);
	final BigInteger b2 = BigIntegerMath.binomial(alpha + delta + 1, delta + 1);
	final BigInteger diff = b1.subtract(b2);
	final BigInteger denom = BigInteger.valueOf(d1).multiply(diff);
	return denom;
}

public int getNumGammaPrimeEqual(int alpha, int gamma, int delta) {
	return delta + (alpha + 2) * (gamma +1);
}

public int getDenomGammaPrimeEqual(int alpha, int gamma, int delta) {
	return (alpha + gamma + delta + 2) * (alpha + 2);
}

public void printFrac(int alpha, int gamma, int gammaPrime, int delta) {
	final BigInteger num = getNumFirstSum(alpha, gamma, gammaPrime, delta);
	final BigInteger denom = getDenomFirstSum(alpha, gamma, gammaPrime, delta);
	{
		final BigInteger scaling = BigInteger.valueOf(alpha + gamma + 2 - gammaPrime);
		final BigInteger numC = getNumComplicatedSum(alpha, gamma, gammaPrime, delta);
		final BigInteger denomC = getDenomComplicatedSum(alpha, gamma, gammaPrime, delta);
		final BigInteger scaledNum = scaling.multiply(num);
		final BigInteger scaledDenom = scaling.multiply(denom);
		assert scaledNum.equals(numC) : "num " + scaledNum + " VS " + numC;
		assert scaledDenom.equals(denomC) : "denom " + scaledDenom + " VS " + denomC;
	}
	if(gammaPrime == 1) {
		assert num.equals(getNumGammaPrimeOne(alpha, gamma, delta));
		assert denom.equals(getDenomGammaPrimeOne(alpha, gamma, delta));
	}
	if(gammaPrime == gamma) {
		final BigInteger scalingOriginal = BigInteger.valueOf(alpha + 2);
		final BigInteger scalingNew = BigIntegerMath.binomial(alpha + delta + 1, delta);
		final BigInteger scaledNum = scalingOriginal.multiply(num);
		final BigInteger scaledDenom = scalingOriginal.multiply(denom);
		final BigInteger newNum = scalingNew.multiply(BigInteger.valueOf(getNumGammaPrimeEqual(alpha, gamma, delta)));
		final BigInteger newDenom = scalingNew.multiply(BigInteger.valueOf(getDenomGammaPrimeEqual(alpha, gamma, delta)));
		assert scaledNum.equals(newNum);
		assert scaledDenom.equals(newDenom);
	}
	
	final double frac = num.doubleValue() / denom.doubleValue();
	println(alpha + ", " + gamma + ", " + delta + ": " + num + " / " + denom + " = " + frac);
}

public void printBunch(int alpha, int gamma, int gammaPrime) {
	final ImmutableList<Integer> deltas = ImmutableList.of(0, 1, 2, 3, 10, 100, 1000, 10000);
	for (int delta : deltas) {
		printFrac(alpha, gamma, gammaPrime, delta);
	}
}

int gamma = 2; printFrac(0, gamma, gamma, 0);
int gamma = 1; printBunch(0, gamma, gamma);

