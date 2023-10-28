package com.example.newassignment2tickerwatchlist;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle extraData = intent.getExtras();
//checks to see if it is recieved
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {

            if (extraData != null) {
                Object[] PDUS = (Object[]) extraData.get("PDUS");
                String format = extraData.getString("FORMAT").toString();
// go over PDU and converts to SMsmessages  then get the message
                for (int i = 0; i < PDUS.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) PDUS[i], format);
                    String message = currentMessage.getMessageBody();
                    Intent activityIntent = new Intent(context, MainActivity.class);
                    activityIntent.putExtra("SMS", message);
                    activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //starts the main activity
                    context.startActivity(activityIntent);
                }
            }
        }
    }
}
