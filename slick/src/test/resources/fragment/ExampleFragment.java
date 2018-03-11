package test;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;

import com.github.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class ExampleFragment extends Fragment implements ExampleView {

    @Presenter
    ExamplePresenter presenter;

    @Override
    public void onAttach(Context context) {
        ExamplePresenter_Slick.bind(this, 1, 2f);
        super.onAttach(context);
    }
}