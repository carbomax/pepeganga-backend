package io.swagger.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTestResponse {

	 @JsonProperty("id")
	  private Integer id = null;

	  @JsonProperty("nickname")
	  private String nickname = null;
	  
	  @JsonProperty("password")
	  private Integer password = null;

	  @JsonProperty("site_status")
	  private String site_status = null;

	public Integer getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public Integer getPassword() {
		return password;
	}

	public String getSite_status() {
		return site_status;
	}

}
