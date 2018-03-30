package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pussy_penetrator on 08.01.2016.
 */
public class ZhegalkinFragment extends Fragment {

    //Components
    private TextView        mBinaryBigText;
    private TextView        mBinarySmallText;
    private PolinomTextView mPolinomText;
    private Button          mZeroButton;
    private Button          mOneButton;
    private ImageButton     mEraseButton;

    //Logic
    private Polinom mPolinom;
    private BeatBox mBeatBox;

    //Attributes
    private float mBinaryBigHintFontSize, mBinaryBigFontSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPolinom = PolinomLab.get();
        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.zhegalkin_fragment, container, false);

        //set components
        mBinaryBigText = (TextView) v.findViewById(R.id.binary_big);
        mBinarySmallText = (TextView) v.findViewById(R.id.binary_small);
        mPolinomText = (PolinomTextView) v.findViewById(R.id.polinom);
        mZeroButton = (Button) v.findViewById(R.id.zero_button);
        mOneButton = (Button) v.findViewById(R.id.one_button);
        mEraseButton = (ImageButton) v.findViewById(R.id.erase_button);

        //set appearance
        mPolinomText.setTypeface(
                Typeface.createFromAsset(getActivity().getAssets(), "fonts/booleano.ttf"));
        mBinaryBigHintFontSize = getResources().getDimension(R.dimen.hint_size);
        mBinaryBigFontSize = getResources().getDimension(R.dimen.binary_size);

        //set listeners
        setListeners();

        update();

        return v;
    }

    /**
     * Sets listeners for buttons. Should be called only ones in onCreateView() method.
     */
    private void setListeners() {
        mZeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolinom.add0();
                mBeatBox.play(BeatBox.ZERO_SOUND);
                update();
            }
        });

        mOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolinom.add1();
                mBeatBox.play(BeatBox.ONE_SOUND);
                update();
            }
        });

        mEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolinom.removeLast();
                mBeatBox.play(BeatBox.DELETE_SOUND);
                update();
            }
        });

        mEraseButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPolinom.removeAll();
                mBeatBox.play(BeatBox.DELETE_SOUND);
                update();
                return true;
            }
        });

        mPolinomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPolinomText.getCurrentSmile() != null) {
                    mBeatBox.play(BeatBox.HIT_SOUND);
                    Toast.makeText(getActivity(), mPolinomText.getCurrentSmile().getTip(),
                                   Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates components if polynomial is somehow changed.
     */
    private void update() {
        try {
            CharSequence polinom = mPolinom.getPolinom(); //catch if cannot build polynomial
            mPolinomText.switchToPolinom(polinom.toString());
            mBinaryBigText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBinaryBigFontSize);
            mBinaryBigText.setSingleLine(true);
            mBinarySmallText.setBackgroundColor(getResources().getColor(R.color.green));
        }
        catch (IllegalArgumentException exception) {
            if (exception.getMessage().equals("empty")) {
                mPolinomText.switchToState(PolinomTextView.S_HELLO_SMILE);
                mBinaryBigText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBinaryBigHintFontSize);
                mBinaryBigText.setSingleLine(false); //for hint representation
                mBinarySmallText.setBackgroundColor(getResources().getColor(R.color.transparent));
            } else {
                if (mPolinom.size() < 16)
                    mPolinomText.switchToState(PolinomTextView.S_DEFAULT_1_SMILE);
                else if (mPolinom.size() < 32)
                    mPolinomText.switchToState(PolinomTextView.S_DEFAULT_2_SMILE);
                else if (mPolinom.size() < 64)
                    mPolinomText.switchToState(PolinomTextView.S_DEFAULT_3_SMILE);
                else if (mPolinom.size() < 128)
                    mPolinomText.switchToState(PolinomTextView.S_DEFAULT_4_SMILE);
                else mPolinomText.switchToState(PolinomTextView.S_DEFAULT_5_SMILE);

                mBinaryBigText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBinaryBigFontSize);
                mBinaryBigText.setSingleLine(true);
                mBinarySmallText.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        }

        mBinaryBigText.setText(mPolinom.getInput());
        mBinarySmallText.setText(mPolinom.getFormattedInput());
    }

}
