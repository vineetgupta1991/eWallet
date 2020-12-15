package com.ewallet.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CustomerDto {

	@JsonIgnore
	private int userId;
	private String fname;
	private String lname;
	private String email;

}
