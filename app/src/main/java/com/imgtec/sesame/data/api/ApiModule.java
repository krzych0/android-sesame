/*
 * <b>Copyright (c) 2017, Imagination Technologies Limited and/or its affiliated group companies
 *  and/or licensors. </b>
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are permitted
 *  provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *      and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *      conditions and the following disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *
 *  3. Neither the name of the copyright holder nor the names of its contributors may be used to
 *      endorse or promote products derived from this software without specific prior written
 *      permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *  WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.imgtec.sesame.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imgtec.di.PerApp;
import com.imgtec.sesame.BuildConfig;
import com.imgtec.sesame.app.App;
import com.imgtec.sesame.data.Preferences;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 */
@Module
public class ApiModule {

  private static final long CACHE_DISK_SIZE = 50 * 1024 * 1024;

  private static String HOST = "http://example.com";

  @Provides
  @PerApp
  HostWrapper provideHostWrapper(Preferences prefs) {
    return new HostWrapper(prefs.getConfiguration());
  }

  @Provides
  @PerApp
  CredentialsWrapper provideCredentialsWrapper(Preferences prefs) {
    return new CredentialsWrapper(prefs.getConfiguration());
  }

  @Provides
  @PerApp
  AuthInterceptor provideAuthInterceptor(CredentialsWrapper credentialsWrapper) {
    return new AuthInterceptor(credentialsWrapper);
  }

  @Provides
  @PerApp
  OkHttpClient provideOkHttpClient(App app, AuthInterceptor oauthInterceptor) {
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, CACHE_DISK_SIZE);

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient
        .Builder()
        .cache(cache)
        .addInterceptor(oauthInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS);

    if (BuildConfig.DEBUG) {
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
    }

    return okHttpClientBuilder.build();
  }

  @Provides
  @PerApp
  Gson provideGson() {
    Gson gson = new GsonBuilder().create();
    return gson;
  }

  @Provides @PerApp
  Retrofit provideRetrofit(HostWrapper hostWrapper, Gson gson, OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(HOST)
        .client(okHttpClient)
        .build();
    return retrofit;
  }

  @Provides
  @PerApp
  RestApiService provideRestApiService(Retrofit retrofit) {
    return retrofit.create(RestApiService.class);
  }
}