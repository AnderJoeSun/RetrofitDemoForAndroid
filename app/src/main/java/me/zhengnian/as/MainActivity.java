package me.zhengnian.as;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

interface GetRequestInterface {
	// 在下面的注解里传入网络请求URL的末尾部份
	// getCall()是接受网络请求数据的方法
	@GET("index.html?from=blog")
	Call<Void> getCall();
}

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 创建Retrofit对象
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://zhengnian.me/") // 设置网络请求Url
				.addConverterFactory(GsonConverterFactory.create()) // 使用Gson解析
				.build();

		// 创建网络请求接口的实例
		GetRequestInterface request = retrofit.create(GetRequestInterface.class);

		// 对请求进行封装
		Call<Void> call = request.getCall();

		Log.i(TAG, "发送网络请求(异步)" );
		call.enqueue(new Callback<Void>() {
			// 响应处理
			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {
				Log.i(TAG, "成功，响应如下：");
				// 请求处理,输出结果
				//              Log.i(TAG, response.message().toString());
				Log.i(TAG, response.headers().toString());
			}

			// 请求失败
			@Override
			public void onFailure(Call<Void> call, Throwable throwable) {
				Log.e(TAG, "请求失败");
			}
		});

		// 发送网络请求（同步）
		// Response response = call.execute();
	}
}