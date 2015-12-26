package hu.supercluster.paperwork.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hu.supercluster.paperwork.Paperwork;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.gitSha) TextView gitSha;
    @Bind(R.id.buildTime) TextView buildTime;
    @Bind(R.id.env1) TextView env1;
    @Bind(R.id.env2) TextView env2;
    @Bind(R.id.extra1) TextView extra1;
    @Bind(R.id.extra2) TextView extra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showPaperwork();
    }

    private void showPaperwork() {
        final Paperwork paperwork = new Paperwork(this);

        gitSha.setText(paperwork.getGitSha());
        buildTime.setText(paperwork.getBuildTime());
        env1.setText(paperwork.getEnv("USER"));
        env2.setText(paperwork.getEnv("PWD"));
        extra1.setText(paperwork.getExtra("mydata1"));
        extra2.setText(paperwork.getExtra("mydata2"));
    }
}
