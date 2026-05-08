package com.fiap.wellme.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(nullable = false, length = 255)
    private String criteria;
}
