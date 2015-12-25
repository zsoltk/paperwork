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
    @Bind(R.id.extras) TextView extras;

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
        extras.setText(paperwork.getExtras().toString());
    }
}
