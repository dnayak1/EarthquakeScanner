package dhirajnayak.com.earthquakescanner.searchScreen;

import android.support.v7.widget.SearchView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dhirajnayak on 1/16/18.
 * Making search view observable, reactive
 */

public class SearchViewObservable {
    public static Observable<String> fireSuggestions(SearchView searchView) {

        //bridge for observable and observer
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subject.onNext(newText);
                return true;
            }
        });
        return subject;
    }
}
