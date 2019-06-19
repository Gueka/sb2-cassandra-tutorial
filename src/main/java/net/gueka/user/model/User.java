package net.gueka.user.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.DataType.Name;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("users")
public class User {

    @PrimaryKey
    UUID id;
    String name;
    String surname;

    Set<String> tags = new HashSet<>();

    @CassandraType(type = Name.UDT, userTypeName = "location") 
    Location location;
    //String timestamp;
    //String lastUpdate;
    //Bill date
    //Due date

}