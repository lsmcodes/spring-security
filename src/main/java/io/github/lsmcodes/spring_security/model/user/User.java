package io.github.lsmcodes.spring_security.model.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "table_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 50, nullable = false)
        private String name;

        @Column(length = 50, nullable = false)
        private String username;

        @Column(length = 100, nullable = false)
        private String password;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "table_roles", joinColumns = @JoinColumn(name = "user_id"))
        private List<String> roles = new ArrayList<>();

}