package com.ewallet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BankTransaction implements Serializable {

	@Id //to set as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
	private int transactionId;
	@NonNull
	private String type;
	@NonNull
	private Date timestamp;
	@NonNull
	private float amount;
	@NonNull
	private float postBalance;
	@NonNull
	private String description;

	@ManyToOne
	@JsonIgnore
	@NonNull
	private Account transactionFromAccount;

}
