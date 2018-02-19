package com.qcuts.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request.GraphUserCallback;



import android.app.Activity;
import android.os.AsyncTask;

public class DBConnection extends AsyncTask<String, Void, String> {

	JSONListener jsonListener;
	Activity MainActivity;
	static JSONObject jsoncheck = new JSONObject();

	public DBConnection(JSONListener jsonListener) {
		this.jsonListener = jsonListener;
	}


	public DBConnection(GraphUserCallback graphUserCallback) {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String doInBackground(String... urls) {
		String result = "";
		try {

			String url1 = urls[0];
			// Log.d("url1", url1);
			String url2 = urls[1];
			// Log.d("url2", url2);
			result = GET(urls[0], urls[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return result;
	}

	public static String GET(String url, String json) {
		final String hostname = "enter-your-db-hostname";

		HttpResponse response = null;
		InputStream inputStream = null;
		HttpClient client = null;
		String result = "";
		try {
			HttpParams httpParams = new BasicHttpParams();
			client = new DefaultHttpClient(httpParams);

			String fullUrl = hostname + url;
			HttpPost request = new HttpPost(fullUrl);
			if (json != null && !json.isEmpty()) {
				request.setEntity(new ByteArrayEntity(json.toString().getBytes(
						"UTF8")));
				// Log.d("values", json.toString());

			}
			response = client.execute(request);
			// HttpEntity entity = response.getEntity();

			// receive response as inputStream
			inputStream = response.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null) {

				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}
		} catch (Exception e) {
			// Log.d("result error", e.getLocalizedMessage());
			try {
				jsoncheck.put("error", "Connection Refused");
				result = "Error";
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {

			try {
				inputStream.close();
				client.getConnectionManager().shutdown();

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		// Log.d("result ", result);
		return result;

	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try {
			this.jsonListener.JSONFeedBack(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
