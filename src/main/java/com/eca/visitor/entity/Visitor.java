package com.eca.visitor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Slf4j
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Visitor implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitorId")
    private long visitorId;

    @Column(name = "createdOn",nullable = false,updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedOn",nullable = false)
    private  LocalDateTime updatedOn;

    @Version
    private Integer version;

    @Column(name = "visitorFirstName")
    @NotEmpty
    private String visitorFirstName;

    @Column(name = "userFirstName")
    private String userFirstName;

    @Column(name = "userLastName")
    private String userLastName;

    @Column(name = "visitorLastName")
    private String visitorLastName;

    @Column(name = "visitorRequestId",unique = true)
    private String visitorRequestId;

    @Column(name = "visitorAddressLine")
    private String visitorAddressLine;

    @Column(name = "visitorCity")
    private String visitorCity;

    @Column(name = "visitorState")
    private String visitorState;

    @Column(name = "visitorZipCode")
    private String visitorZipCode;

    @Column(name = "userPhoneNo")
    @NotNull
    private Long userPhoneNo;

    @Column(name = "userEmailId")
    private String userEmailId;

    @Column(name = "purposeOfVisiting")
    private String purposeOfVisiting;

    @JsonProperty("apartmentId")
    private int apartmentId;

    @JsonProperty("apartmentName")
    private String apartmentName;

    @PrePersist
    protected void setOnCreation() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void setUpdatedOn() {
        updatedOn = LocalDateTime.now();
    }

}
