package cn.lemon.jcourse.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import cn.lemon.common.net.ServiceResponse;
import cn.lemon.jcourse.R;
import cn.lemon.jcourse.model.AccountModel;
import cn.lemon.jcourse.model.bean.Account;
import cn.lemon.jcourse.model.bean.Banner;

public class SplashActivity extends AppCompatActivity {

    private ImageView mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);

        mBanner = (ImageView) findViewById(R.id.banner);

        AccountModel.getInstance().getBanner(new ServiceResponse<Banner>() {
            @Override
            public void onNext(Banner banner) {
                super.onNext(banner);
                Glide.with(SplashActivity.this)
                        .load(banner.imageUrl)
                        .into(mBanner);
            }
        });

        if (AccountModel.getInstance().getAccount() != null) {
            AccountModel.getInstance().prolongToken(new ServiceResponse<Account>() {
                @Override
                public void onNext(Account account) {
                    super.onNext(account);
                    AccountModel.getInstance().saveAccount(account, false);
                }
            });
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                jumpHome();
            }
        }, 2000);
    }

    public void jumpHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
