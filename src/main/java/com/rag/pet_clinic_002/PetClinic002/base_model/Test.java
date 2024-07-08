package com.rag.pet_clinic_002.PetClinic002.base_model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

public class Test {

	public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode jsonNode = objectMapper.readTree(new File("mydata.json")); 
		OkHttpClient client = new OkHttpClient();
		String toke = "";

		MediaType mediaType = MediaType.parse("application/json");
		// 'input' refers to JSON Payload
		RequestBody body = RequestBody.create(mediaType, jsonNode.asText());
		Request request = new Request.Builder()
		    .url("https://apis-sandbox.fedex.com/rate/v1/rates/quotes")
		    .post(body)
		    .addHeader("Content-Type", "application/json")
		    .addHeader("X-locale", "en_US")
		    .addHeader("Authorization", "Bearer ")
		    .build();
		            
		Response response = client.newCall(request).execute();
	}
}
