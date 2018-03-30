package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sex_predator on 13.02.2016.
 */
public class BeatBox {

    public static final int DELETE_SOUND = 0;
    public static final int HIT_SOUND    = 1;
    public static final int ZERO_SOUND   = 2;
    public static final int ONE_SOUND    = 3;

    private final String SOUND_FOLDER = "sample_sounds";
    private final int    MAX_STREAMS  = 4;

    private AssetManager mAssets;
    private SoundPool    mSoundPool;
    private List<Sound>  mSounds;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        mSounds = new ArrayList<>(4);

        String[] files;
        try {
            files = mAssets.list(SOUND_FOLDER);
        }
        catch (IOException e) {
            Log.e("BeatBox", "Error loading sounds");
            return;
        }

        for (String name : files) {
            Sound sound = new Sound(SOUND_FOLDER, name);
            mSounds.add(sound);
            load(sound);
        }
    }

    private void load(Sound sound) {
        try {
            AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
            sound.setId(mSoundPool.load(afd, 1));
        }
        catch (IOException e) {
            Log.e("BeatBox", "Sound " + sound.getName() + " Not Found");
        }
    }

    public void play(int soundId) {
        mSoundPool.play(mSounds.get(soundId).getId(), 0.25f, 0.25f, 1, 0, 1);
    }

}
