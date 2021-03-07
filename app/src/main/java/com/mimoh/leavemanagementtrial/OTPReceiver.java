package com.mimoh.leavemanagementtrial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReceiver extends BroadcastReceiver {

    private static EditText ETotp;

    public void setETotp(EditText ETotp){
        OTPReceiver.ETotp = ETotp;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for(SmsMessage smsMessage : smsMessages) {
                String message = smsMessage.getMessageBody();
//                Log.e("mimoh", message);
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(message);
                String OTP = "";
                if (matcher.find() && !message.isEmpty())
                    OTP = matcher.group(0);  // 6 digit number
                ETotp.setText(OTP);
            }
        }

    }
}
