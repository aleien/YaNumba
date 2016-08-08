package io.yanamba.aleien.yanumba;

import org.jsoup.Jsoup;

import javax.inject.Inject;

import io.yanamba.aleien.yanumba.utils.HelperUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Searcher {
    private HelperUtils helperUtils;

    @Inject
    public Searcher(HelperUtils helperUtils) {
        this.helperUtils = helperUtils;
    }

    public Observable<String> search(String phoneNumber) {
        String searchQuery = helperUtils.getSearchQuery(phoneNumber);
        return Observable.fromCallable(() -> Jsoup.connect(searchQuery).get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(doc -> doc.getElementsByClass("organic").first().text());


    }
}
