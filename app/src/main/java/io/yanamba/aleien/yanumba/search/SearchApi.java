package io.yanamba.aleien.yanumba.search;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aleien on 05.08.16.
 */

public interface SearchApi {

    @GET("search/")
    Call<ResponseBody> searchQuery(@Query("text") String phone);

}
