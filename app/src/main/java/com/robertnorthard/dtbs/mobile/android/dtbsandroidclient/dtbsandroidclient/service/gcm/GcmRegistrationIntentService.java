package com.robertnorthard.dtbs.mobile.android.dtbsandroidclient.dtbsandroidclient.service.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.robertnorthard.dtbs.mobile.android.dtbsandroidclient.R;
import com.robertnorthard.dtbs.mobile.android.dtbsandroidclient.dtbsandroidclient.service.config.DtbsPreferences;

/**
 * Service to handle Google cloud messenger token registration.
 *
 * @author robertnorthard
 */
public class GcmRegistrationIntentService extends IntentService {

    // TAG used for logging.
    private static final String TAG = GcmRegistrationIntentService.class.getName();

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     */
    public GcmRegistrationIntentService() {
        super(TAG);
    }

    /**
     * Invoked when service starts by worker thread.
     *
     * @param intent abstract description of task to be performed.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "Gcm registration token: " + token);

            sharedPreferences.edit().putString(DtbsPreferences.GCM_TOKEN, token).apply();
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            e.printStackTrace();
        }
    }
}
