package com.developers.bakingapp.util;

import com.developers.bakingapp.model.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Amanjeet Singh on 25/10/17.
 */

public interface ApiInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<Result>> getDetails();

}
