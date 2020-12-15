package com.ewallet.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id //to set as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
	private int walletId;
	@OneToMany(mappedBy = "walletHolder")
	private List<Account> accountsInWallet;
	@OneToOne
	private Customer walletOfCustomer;


}
