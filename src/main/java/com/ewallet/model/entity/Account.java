package com.ewallet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id //to set as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
	private int accountNumber;
	private float balance;
	@ManyToOne
	//@JsonIgnore
	private Customer accountHolder;
	@ManyToOne
	@JsonIgnore
	private Wallet walletHolder;
	@OneToMany(mappedBy = "transactionFromAccount")
	private List<BankTransaction> bankTransactions;
}
