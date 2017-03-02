package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexoladele.testingshit.MainScreenFragment;
import com.alexoladele.testingshit.R;

import static android.content.Context.MODE_PRIVATE;

// Declares WelcomeScreenFragment as subclass of fragment
public class WelcomeScreenFragment extends Fragment {
    private static final String TAG = "WelcomeScreenFragment";
    private Button startBtn;


    //    +++++++++++++++++++++ OVERRIDE - METHODS ++++++++++++++++++++++++++


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //    Attaches the Fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // Creates Fragment view for use
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startBtn = (Button) view.findViewById(R.id.start_screen_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Button Clicked");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();


//        Create Shared Prefs to skip Welcome screen if already seen
                getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putBoolean("isFirstRun", false).apply();
                Log.i(TAG, "onClick: Successfully Created Shared Pref");

                Log.i(TAG, "onClick: Replacing Fragment");
                transaction
                        .replace(R.id.root_layout, MainScreenFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    //    +++++++++++++++++++++ User METHODS ++++++++++++++++++++++++++

    // Method to create new instances of Fragment
    public static WelcomeScreenFragment newInstance() {
        return new WelcomeScreenFragment();
    }
}


