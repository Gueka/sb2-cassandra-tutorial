package net.gueka.user.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@UserDefinedType("location")
public class Location {
    String city;
    @Column("zip_code")
    String zipCode;
    String address;
}