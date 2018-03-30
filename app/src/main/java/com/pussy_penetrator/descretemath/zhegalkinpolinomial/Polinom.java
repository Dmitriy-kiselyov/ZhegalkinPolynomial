package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

/**
 * Created by Pussy_penetrator on 29.12.2015.
 */
public class Polinom {

    private StringBuilder mInput, mFormattedInput;
    private int mInputSize;

    public Polinom() {
        mInput = new StringBuilder();
        mFormattedInput = new StringBuilder();
        mInputSize = 0;
    }

    private int degree() throws IllegalArgumentException {
        if (mInputSize == 0) {
            throw new IllegalArgumentException("empty");
        } else if (mInputSize == 1) {
            return 0;
        } else {
            int degree = 1;
            int size = 2;

            do {
                if (size >= mInputSize)
                    return degree;
                degree++;
                size *= 2;
            } while (size <= mInputSize);

            throw new IllegalArgumentException("wrong_size");
        }
    }

    public CharSequence getPolinom() throws IllegalArgumentException {
        int degree = degree();
        if (degree == 0) {
            return mInput;
        } else {
            int[] arr = new int[mInputSize];

            for (int i = 0; i < mInputSize; ++i)
                arr[i] = mInput.charAt(i) - 48;

            for (int i = 1; i != mInputSize; i *= 2) {
                for (int sb = i; sb < mInputSize; sb += i) {
                    for (int num = 0; num < i; ++sb) {
                        arr[sb] = (arr[sb] + arr[sb - i]) % 2;
                        ++num;
                    }
                }
            }

            StringBuilder polinom = new StringBuilder(200);
            polinom.append("<html>&nbsp;");
            int num = 0;

            for (int mask = mInputSize - 1; mask > 0; --mask) {
                if (arr[mask] != 0) {
                    ++num;

                    for (int i = degree - 1; i >= 0; --i)
                        if ((mask & 1 << i) != 0)
                            polinom.append("X").append("<sub><b>").append(degree - i).append("</b></sub>");

                    polinom.append("&nbsp;").append("&oplus;").append(" ");
                }
            }

            if (arr[0] == 0 && num != 0)
                polinom.delete(polinom.length() - 9, polinom.length());
            else
                polinom.append(arr[0]);

            polinom.append("&nbsp;</html>");
            return polinom;
        }
    }

    public void add0() {
        mInput.append('0');
        mInputSize++;

        if ((mFormattedInput.length() + 1) % 5 == 0)
            mFormattedInput.append(' ');
        mFormattedInput.append('0');
    }

    public void add1() {
        mInput.append('1');
        mInputSize++;

        if ((mFormattedInput.length() + 1) % 5 == 0)
            mFormattedInput.append(' ');
        mFormattedInput.append('1');
    }

    public void removeLast() {
        mInput.setLength(Math.max(0, mInput.length() - 1));
        mInputSize = Math.max(0, mInputSize - 1);

        if (mFormattedInput.length() != 1 && mFormattedInput.length() % 5 == 1)
            mFormattedInput.setLength(Math.max(0, mFormattedInput.length() - 2));
        else
            mFormattedInput.setLength(Math.max(0, mFormattedInput.length() - 1));
    }

    public void removeAll() {
        mInput.setLength(0);
        mInputSize = 0;

        mFormattedInput.setLength(0);
    }

    public CharSequence getInput() {
        return mInput;
    }

    public CharSequence getFormattedInput() {
        return mFormattedInput;
    }

    public int size() {
        return mInputSize;
    }
}
