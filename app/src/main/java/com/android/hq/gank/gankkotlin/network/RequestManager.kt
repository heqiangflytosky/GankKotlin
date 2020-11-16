package com.android.hq.gank.gankkotlin.network

import com.android.hq.gank.gankkotlin.data.DailyDataResponse
import com.android.hq.gank.gankkotlin.data.GankApi
import com.android.hq.gank.gankkotlin.data.GankDataResponse
import com.android.hq.gank.gankkotlin.utils.AppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

class RequestManager {
    private val MAX_AGE = 4 * 60 * 60 //缓存4个小时
    private val CACHE_SIZE = 10 * 1024 * 1024//缓存10M；
    var service :GankService
    init {
        service = getRetrofit().create(GankService::class.java)
    }

    private fun getRetrofit():Retrofit {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
            lateinit var response: Response
            try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (request.url().toString().startsWith(GankApi.GANK_BASE_URL) && !request.url().toString().startsWith(
                    GankApi.GANK_SEARCH_URL
                )
            ) {
                response.newBuilder()
                    .header("Cache-Control", "max-age=$MAX_AGE")
                    .build()
            } else response
        }

        val cacheDirectory = File(AppUtils.getCacheDir(), "responses")
        val client = OkHttpClient.Builder()
            .cache(Cache(cacheDirectory, CACHE_SIZE.toLong()))
            .addNetworkInterceptor(interceptor)
            .build()
        //client.setConnectTimeout(2000, TimeUnit.MILLISECONDS);

        return Retrofit.Builder()
            .client(client)
            .baseUrl(GankApi.GANK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }

    companion object Factory {

        val instance : RequestManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RequestManager()
        }
    }


    fun getDailyData(callBack: CallBack<DailyDataResponse>) {

    }

    fun getGankData(category:String, pageCount:Int, page:Int, onSuccess:(reponse:GankDataResponse) -> Unit = {}, onFail: () -> Unit ={}){
        GlobalScope.launch(Dispatchers.IO) {
            val reponse = service.getGankData(category, pageCount, page).execute()
            withContext(Dispatchers.Main) {
                if (reponse.isSuccessful) {
                    onSuccess(reponse.body() as GankDataResponse)
                }else{
                    onFail()
                }
            }
        }
    }
}