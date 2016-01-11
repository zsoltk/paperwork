package hu.supercluster.paperwork.integration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hu.supercluster.paperwork.Paperwork;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.simpleKey) TextView simpleKey;
    @Bind(R.id.buildTime1) TextView buildTime1;
    @Bind(R.id.buildTime2) TextView buildTime2;
    @Bind(R.id.buildTime3) TextView buildTime3;
    @Bind(R.id.gitSha) TextView gitSha;
    @Bind(R.id.gitTag) TextView gitTag;
    @Bind(R.id.gitInfo) TextView gitInfo;
    @Bind(R.id.shell) TextView shell;
    @Bind(R.id.env) TextView env;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showPaperwork();
    }

    private void showPaperwork() {
        final Paperwork paperwork = new Paperwork(this);

        simpleKey.setText(paperwork.get("simpleKey"));
        buildTime1.setText(paperwork.get("buildTime1"));
        buildTime2.setText(paperwork.get("buildTime2"));
        buildTime3.setText(paperwork.get("buildTime3"));
        gitSha.setText(paperwork.get("gitSha"));
        gitTag.setText(paperwork.get("gitTag"));
        gitInfo.setText(paperwork.get("gitInfo"));
        shell.setText(paperwork.get("shell"));
        env.setText(paperwork.get("someEnv"));
    }
}
