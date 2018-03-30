package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Pussy_penetrator on 11.01.2016.
 */
public class PolinomTextView extends TextView {

    //Smiles when polynomial cannot be created
    public final static Smile S_HELLO_SMILE = new Smile("(*^‿^*)", "こんにちは!");
    public final static Smile S_DEFAULT_1_SMILE = new Smile("ヽ(ˇヘˇ)ノ", "私は知りません");
    public final static Smile S_DEFAULT_2_SMILE = new Smile("∑(O_O;)", "何してるの？");
    public final static Smile S_DEFAULT_3_SMILE = new Smile("o(>< )o", "十分な");
    public final static Smile S_DEFAULT_4_SMILE = new Smile("(҂｀ﾛ´)凸", "わたしは、あなたが大嫌いです!");
    public final static Smile S_DEFAULT_5_SMILE = new Smile("(╥﹏╥)", "ダイ");

    //Attributes
    private Paint mTextPaint;
    private float mStateTextSize, mPolinomTextSize;
    private Smile mCurrentSmile;

    public PolinomTextView(Context context) {
        super(context);
        initialise();
    }

    public PolinomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    /**
     * Returns current smile if polynomial size is wrong, else returns null
     */
    public Smile getCurrentSmile() {
        return mCurrentSmile;
    }

    /**
     * Should be called by all constructors
     */
    private void initialise() {
        mTextPaint = new Paint();
        mTextPaint.set(this.getPaint());

        mPolinomTextSize = getResources().getDimension(R.dimen.polinom_size);
        switchToState(S_HELLO_SMILE);
    }

    /**
     * Re size the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     */
    private void measureText(String text, int textWidth, int textHeight) {
        if (textWidth <= 0 || textHeight <= 0)
            return;

        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        int targetHeight = textHeight - this.getPaddingBottom() - this.getPaddingTop();

        float hi = 600;
        float lo = 2;
        final float threshold = 0.5f; // How close we have to be

        mTextPaint.set(this.getPaint());

        Paint.FontMetrics fontMetrics;
        while ((hi - lo) > threshold) {
            float size = (hi + lo) / 2;
            mTextPaint.setTextSize(size);
            fontMetrics = mTextPaint.getFontMetrics();
            if (mTextPaint.measureText(text) >= targetWidth || Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent) >= targetHeight)
                hi = size; // too big
            else
                lo = size; // too small
        }
        // Use lo so that we undershoot rather than overshoot
        mStateTextSize = lo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = getMeasuredHeight();
        measureText(S_DEFAULT_1_SMILE.smile, parentWidth, height); // because it is the widest one
        this.setMeasuredDimension(parentWidth, height);

        if (mCurrentSmile != null)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mStateTextSize);
    }

    /**
     * Forces textView to show funny face
     *
     * @param smile funny face
     */
    public void switchToState(Smile smile) {
        mCurrentSmile = smile;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mStateTextSize);
        setText(smile.smile);
    }

    /**
     * Forces textView to show polynomial
     *
     * @param polinom string version of polynomial with html tags
     */
    public void switchToPolinom(String polinom) {
        mCurrentSmile = null;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mPolinomTextSize);
        setText(Html.fromHtml(polinom));
    }

    /**
     * Class for storing funny faces and tips for them
     */
    public static class Smile {
        private String smile;
        private String tip;

        Smile(String smile, String tip) {
            this.smile = smile;
            this.tip = tip;
        }

        public String getTip() {
            return tip;
        }
    }

}
