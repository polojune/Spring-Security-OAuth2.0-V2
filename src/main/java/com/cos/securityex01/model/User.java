package com.cos.securityex01.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM - Object Relation Mapping

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class User {
	@Id // primary key
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String username; 
    private String password; 
    private String email;
    private String role;// ROLE_USER, ROLE_ADMIN
    //OAuth 유저를 위한 필드 2개
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;
}
