package com.eatery.filterData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.*;

/**
 * @author Bruntha
 *
 */
public class JSONParser { // to communicate with local server

	InputStream is = null;

	public JSONParser() {

	}

	public JSONObject getJSONFromResponse(InputStream is) throws IOException {
		JSONObject jObj = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			jObj = new JSONObject();

		} catch (IOException e) {
//			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		return jObj;

	}

}
