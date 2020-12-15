package com.ewallet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id //to set as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
	private int userId;
	@NonNull
	private String fname;
	@NonNull
	private String lname;
	@Column(unique = true)
	@NonNull
	private String email;
	@OneToOne(mappedBy = "walletOfCustomer")
	@JsonIgnore
	private Wallet wallet;
	@OneToMany(mappedBy = "accountHolder")
	@JsonIgnore
	private List<Account> customerAccounts;
}
